package com.spider.servlet;

import com.spider.job.processor.ArticleProcessForWeibo;
import com.spider.utils.MpcJsonUtil;
import com.spider.utils.WebUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-17
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
public class HtmlServlet extends HttpServlet {

    private static final Log accessLog = LogFactory.getLog("weibo_access");
    private static final Log errorLog = LogFactory.getLog("weibo_error");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = request.getParameter("url");
        accessLog.info(url);

        Map map = new HashMap();
        try {

            long articleId = ArticleProcessForWeibo.handler(url);
            map.put("article_id",articleId);
            WebUtils.returnJson(MpcJsonUtil.successJson(map),response);

        } catch (Exception e) {
            WebUtils.returnJson(MpcJsonUtil.errorJsonResponse(e.getMessage()),response);
            errorLog.info(url+","+e.getMessage());
        }finally {

        }

    }

}
