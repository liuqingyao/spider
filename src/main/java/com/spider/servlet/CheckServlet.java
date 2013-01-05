package com.spider.servlet;

import com.spider.service.ArticleServiceImpl;
import com.spider.tools.DbTool;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 12-11-3
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public class CheckServlet extends HttpServlet {
    private static ArticleServiceImpl articleService = ArticleServiceImpl.getInstance();//ThriftClient.getArticleClient();

    private static final String ERROR = "ERROR";
    private static final String OK = "OK";
    private static final long TIME_OUT = 30*60*1000;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String result = ERROR;
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = DbTool.getConn();
            st = connection.createStatement();
            rs = st.executeQuery("SELECT created_at FROM articles ORDER BY id DESC LIMIT 1");

            if(rs.next()){
                long createdAt = rs.getLong(1);
                long cur = System.currentTimeMillis();
                if(cur - createdAt<TIME_OUT){
                    result = OK;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            DbTool.close(rs, st, connection);
        }

        Writer writer = null;
        try {
            writer = resp.getWriter();
            writer.write(result);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }
}
