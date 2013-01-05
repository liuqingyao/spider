package com.spider.download.manager;

import com.spider.job.SourceJob;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-26
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
public class DlJobManager extends HttpServlet {


    //启动一个JOB

    private static final Logger logger = Logger.getLogger(SourceJob.class);

    public void init() throws ServletException {
        try {
            DlJob.getInstance().startJob();
        } catch (Exception e) {
            logger.error("DL_JOB start error:"+e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        logger.info("threadFix over.");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req,resp);
    }
}
