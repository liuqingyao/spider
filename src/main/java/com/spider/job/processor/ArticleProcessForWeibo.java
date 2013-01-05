package com.spider.job.processor;

import com.spider.bean.Article;
import com.spider.bean.Image;
import com.spider.bean.SourceVO;
import com.spider.job.helper.ArticleGenerator;
import com.spider.job.helper.SpiderUrlUtil;
import com.spider.parser.te.BodyExtract;
import com.spider.parser.te.helper.HtmlObject;
import com.spider.service.ArticleServiceImpl;
import com.spider.service.ImageServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-17
 * Time: 上午11:57
 * 单个源处理，主要适用于微博文章抽取
 */
public class ArticleProcessForWeibo {

    private static final Log log = LogFactory.getLog("weibo_error_detail");
    private static ArticleServiceImpl articleService = ArticleServiceImpl.getInstance();//ThriftClient.getArticleClient();
    private static ImageServiceImpl imageService = ImageServiceImpl.getInstance();//ThriftClient.getImageClient();
    private static SourceVO weiboSource = new SourceVO();

    static {
        weiboSource.setId(999);
        weiboSource.setChannelId(-1);
    }

    public static long handler(String url) throws Exception {
        long articleId = -1;
        if (null != url) {


            if (!SpiderUrlUtil.isStored(url)) {
                HtmlObject htmlObject = null;
                try {
                    htmlObject = BodyExtract.getSingleHtml(url);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw e;
                }

                Article article = ArticleGenerator.entryToArticle(htmlObject, weiboSource);
                List<Image> images = ArticleGenerator.getArticleImages(htmlObject);

                if (article != null && article.getContent() != null && article.getTitle() != null && article.getContent().trim().equals("") && article.getTitle().trim().equals("")) {
                    return -1;
                }

                //入库文章
                try {
                    articleId = articleService.create(article);
                } catch (TException e) {
                    log.error(e.getMessage(), e);
                    throw e;
                }

                //入库图片
                for (int i = 0, size = images == null ? 0 : images.size(); i < size; i++) {
                    Image image = images.get(i);
                    image.setArticleId(articleId);
                    try {
                        imageService.create(image);
                    } catch (TException e) {
                        log.error(e.getMessage(), e);
                    }
                }

            } else {
                articleId = articleService.getArticleByUrl(url).getId();
            }
        }
        return articleId;
    }
}
