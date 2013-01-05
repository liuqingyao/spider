package com.spider.job;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-10-12
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public class JobManager extends HttpServlet {

    //启动一个JOB

    public void init() throws ServletException {
        SourceJob.getInstance().startSourceJob();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req,resp);
    }


}
