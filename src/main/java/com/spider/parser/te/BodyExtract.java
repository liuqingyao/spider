package com.spider.parser.te;

import com.spider.parser.bean.ExtractUrlBean;
import com.spider.parser.te.helper.HtmlObject;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-23
 * Time: 下午12:14
 * To change this template use File | Settings | File Templates.
 */
public class BodyExtract {

    public static List<HtmlObject> getHtml(Set<ExtractUrlBean> urls) throws SQLException {
//        return new ExtractService().ServiceExt(urls);
        //调用parser
        return null;
    }

    public static HtmlObject getSingleHtml(String url) {
//        return new ExtractCon().getContentByUrl(url);
        //调用parser
        return null;
    }
}
