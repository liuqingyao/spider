package com.spider.job;

import com.spider.bean.Article;
import com.spider.bean.Fetch;
import com.spider.bean.Image;
import com.spider.bean.SourceVO;
import com.spider.cache.Cache;
import com.spider.cache.CacheManager;
import com.spider.job.helper.ArticleGenerator;
import com.spider.job.helper.ArticleHelper;
import com.spider.job.helper.SpiderUrlUtil;
import com.spider.job.queue.SourceQueue;
import com.spider.parser.bean.ExtractUrlBean;
import com.spider.parser.te.BodyExtract;
import com.spider.parser.te.HtmlExtract;
import com.spider.parser.te.RssExtract;
import com.spider.parser.te.helper.HtmlObject;
import com.spider.parser.te.helper.LinksHelper;
import com.spider.reporter.Profile;
import com.spider.reporter.Reporter;
import com.spider.service.ArticleServiceImpl;
import com.spider.service.FetchServiceImpl;
import com.spider.service.ImageServiceImpl;
import com.spider.utils.ExceptionUtil;
import org.apache.thrift.TException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhouwang
 * Date: 12-9-20
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */

public class SourceJobTask extends Thread {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SourceJob.class);

    private long lastExecuteTime = System.currentTimeMillis();
    private int flag;
    private static ArticleServiceImpl articleService = ArticleServiceImpl.getInstance();//ThriftClient.getArticleClient();
    private static ImageServiceImpl imageService = ImageServiceImpl.getInstance();//ThriftClient.getImageClient();
    private static FetchServiceImpl fetchService = FetchServiceImpl.getInstance();//
    private static Cache cache = CacheManager.getCacheImpl();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private FutureTask futureTask = null;
    private static final int TIME_OUT = 10 * 60;

    public void run() {

        while (true) {

            try {
                futureTask = new FutureTask(new MyTask());

                executorService.execute(futureTask);

                futureTask.get(TIME_OUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            } catch (ExecutionException e) {
                logger.error(e.getMessage(), e);
            } catch (TimeoutException e) {
                logger.error(e.getMessage(), e);
            } finally {
                futureTask.cancel(true);
                futureTask = null;
            }

        }
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(long lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public SourceJobTask() {
    }

    public SourceJobTask(int flag) {
        this.flag = flag;
    }


    class MyTask implements Callable {

        @Override
        public Object call() throws Exception {
            SourceVO sourceVO = null;
            Fetch fetch = null;
            try {
                sourceVO = SourceQueue.getInstance().take();

                if (sourceVO == null || null == sourceVO.getSourceUrl() || "".equals(sourceVO.getSourceUrl().trim())) {
                    return null;
                }
                fetch = fetchService.getBySourceId(sourceVO.getId());

                Set<String> urls = null;
                Profile getUrlProfile = Profile.getProfileOf("get_url");

                //获取合适的链接地址，如果出错，抛出异常
                if (sourceVO.getFetchType() == 1/*RSS*/) {
                    urls = RssExtract.getUrls(sourceVO, fetch);
                } else if (sourceVO.getFetchType() == 2/*html*/) {
                    urls = HtmlExtract.getUrls(sourceVO, fetch);
                }
                getUrlProfile.end();

                //处理链接，如果出错，抛出异常
                process(urls, sourceVO, fetch);

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (sourceVO != null) {
                    fetch.setLastFetchAt(System.currentTimeMillis());
                    fetchService.update(fetch);
                }
            }
            return null;
        }

        private void process(Set<String> urls, SourceVO sourceVO, Fetch fetch) throws Exception {

            if (null != urls) {

                Profile bodyExtractProfile = Profile.getProfileOf("body_extract");
                List<HtmlObject> exContentList = null;
                Set<ExtractUrlBean> urlBeans = new HashSet<ExtractUrlBean>();
                try {
                    urlBeans = SpiderUrlUtil.urlsForExtract(urls);
                    exContentList = BodyExtract.getHtml(urlBeans);
                } catch (Exception e) {
                    Reporter.error(Reporter.PARSER_ERROR, "source_id:" + sourceVO.getId() + "\n" + ExceptionUtil.stackInfo(e) + "\n" + LinksHelper.links(urlBeans));
                    fetch.setFetchError(Reporter.PARSER_ERROR);
                    throw e;
                }
                bodyExtractProfile.end();
                fetch.setFetchError("");

                if (exContentList != null && exContentList.size() > 0) {
                    Profile saveProfile = Profile.getProfileOf("save_article_image");
                    Iterator<HtmlObject> it = exContentList.iterator();
                    while (it.hasNext()) {
                        try {
                            HtmlObject htmlObject = it.next();
                            if (!SpiderUrlUtil.isStored(htmlObject.getUrl()) && !cache.exists(htmlObject.getUrl())) {
                                cache.set(htmlObject.getUrl(), "1", TIME_OUT);
                                Article article = ArticleGenerator.entryToArticle(htmlObject, sourceVO);
                                List<Image> images = ArticleGenerator.getArticleImages(htmlObject);
                                if (!ArticleHelper.validate(article, images)) {
                                    continue;
                                }

                                long articleId = 0;
                                try {
                                    articleId = articleService.create(article);
                                } catch (TException e) {
                                    Reporter.error(Reporter.SERVICE_ERROR, article.getOriginalUrl() + ",source_url:" + sourceVO.getSourceUrl() + ",source_id:" + sourceVO.getId() + ",msg:" + e.getMessage());
                                }

                                for (int i = 0, size = images == null ? 0 : images.size(); i < size; i++) {
                                    Image image = images.get(i);
                                    image.setArticleId(articleId);
                                    try {
                                        imageService.create(image);
                                    } catch (TException e) {
                                        Reporter.error(Reporter.SERVICE_ERROR, image.getUrl() + ",origin_url:" + image.getOriginalUrl() + ",article_id:" + image.getArticleId() + ",msg:" + e.getMessage());
                                    }
                                }

                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    } //end of while
                    saveProfile.end();
                }
            }
        }
    }


}
