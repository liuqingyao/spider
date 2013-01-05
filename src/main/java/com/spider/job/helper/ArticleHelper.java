package com.spider.job.helper;

import com.spider.bean.Article;
import com.spider.bean.Image;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-23
 * Time: 下午12:21
 * To change this template use File | Settings | File Templates.
 */
public class ArticleHelper {
    public static boolean validate(Article article, List<Image> images) {
        if (article == null || article.getContent() == null || article.getTitle() == null ||
                (article.getContent().trim().equals("") && images.size() <= 0)
                || article.getTitle().trim().equals("")) {
            return false;
        }
        return true;
    }
}
