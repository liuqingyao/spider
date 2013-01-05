package com.spider.job.queue;

import com.spider.cache.CacheManager;
import com.spider.utils.StringUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 12-11-3
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public class QueueServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
//        CacheManager.getCacheImpl().lpush(SourceQueue.KEY, StringUtil.ensureNotEmptyAndNotNull(req.getParameter("link"),""));
//        Writer writer = null;
//        try {
//            writer = resp.getWriter();
//            writer.write("SUCCESS!");
//        } catch (IOException e) {
//           e.printStackTrace();
//        }

    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req,resp);
    }
}
