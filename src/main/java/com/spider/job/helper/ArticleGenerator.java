package com.spider.job.helper;

import com.spider.bean.Article;
import com.spider.bean.Image;
import com.spider.bean.SourceVO;
import com.spider.image.mpc.ImageProcessAndSave;
import com.spider.parser.body.BodyCleaner;
import com.spider.parser.te.helper.HtmlObject;
import com.spider.utils.HTMLDecoder;
import com.spider.utils.HtmlTagUtil;
import com.spider.utils.StringUtil;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-10-12
 * Time: 下午5:02
 * To change this template use File | Settings | File Templates.
 */
public class ArticleGenerator {
    private static final Logger logger = Logger.getLogger(ArticleGenerator.class);


    public static String getArticleOriginContent(SyndEntry syndEntry) {
        //正文获取
        String content = "";
        List<SyndContentImpl> contents = syndEntry.getContents();
        if (contents != null && !contents.isEmpty()) {
            for (SyndContentImpl s : contents) {
                content += s.getValue();
            }
        } else {
            content += syndEntry.getDescription().getValue();
        }
        return content;
    }

    public static List<Image> getArticleImages(HtmlObject htmlObject) {
        //正文图片列表
        List<String> allPicList = HtmlTagUtil.extractPic(htmlObject.getContent());
        List<Image> pics = new java.util.ArrayList<Image>();
        int countOfPic = 0;
        for (int i = 0, len = allPicList == null ? 0 : allPicList.size(); i < len; i++) {
            try {
                String picPath = allPicList.get(i);
                Image pic = ImageProcessAndSave.downloadImage(SpiderUrlUtil.getAbsPicUrl(picPath, htmlObject.getUrl()));
                if (null != pic && null != pic.getUrl() && !"".equals(pic.getUrl())) {
                    if (countOfPic == 0) {
                        pic.setCoverFlag(1);
                    } else {
                        pic.setCoverFlag(0);
                    }
                    pics.add(pic);
                    countOfPic++;
                }
            } catch (Throwable e) {
                logger.error(e.getMessage());
            }
        }
        return pics;
    }

    /**
     * 为了尽快释放资源，SyndEntry会被设置成null
     */
    public static Article entryToArticle(HtmlObject htmlObject, SourceVO source) {
        Article article = new Article();
        //title
        article.setTitle(getTitle(htmlObject));

        long cur = new Date().getTime();
        article.setPublishAt(cur);
        article.setUpdatedAt(cur);
        article.setCreatedAt(cur);
        article.setOriginalUrl(htmlObject.getUrl());
        //content  正文
        String content = BodyCleaner.getCleanContent(htmlObject.getContent()); //正文内容去噪
        article.setContent(content);
        //summary  摘要
        article.setSummary(getSummary(content));
        //source and channel
        if (source != null) {
            article.setSourceId(source.getId());
            article.setChannelId(source.getChannelId());
        }
        return article;
    }

    private static String getSummary(String content) {  //summary 限制45个字
        if (content == null || content.trim().length() == 0) return "";

        String[] summaries = content.split("\\n");
        if (summaries.length == 1) {
            return StringUtil.limitLen(summaries[0], 45);
        } else if (summaries.length > 1) {
            for (String summary : summaries) {
                if (summary != null && summary.trim().length() > 10) {
                    return StringUtil.limitLen(summary, 45);
                }
            }
            return StringUtil.limitLen(summaries[0], 45);
        }

        return "";
    }

    private static final Pattern p = Pattern.compile("[:：]{2,}");

    private static String getTitle(HtmlObject htmlObject) {
        String title = BodyCleaner.cleanNewLine(HTMLDecoder.decode(StringUtil.ensureNotEmpty(htmlObject.getTitle(), ""))).trim();
        Matcher m = p.matcher(title);
        return m.replaceAll(":");
    }

}
