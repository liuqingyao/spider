package com.spider.tools;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-30
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class DbTool {

    public static Connection getConn(){
        Connection conn=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.10.57.61:3306/reader_bate?user=reader_bate&password=Ybsl6516");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();

        }

        return conn;
    }

    public static void close(ResultSet rs,Statement st,Connection conn){
        try {
            close0(rs, st, conn);
        } catch (SQLException e) {
            try {
                close0(rs,st,conn);
            } catch (SQLException e1) {

            }
        }
    }
    private static void close0(ResultSet rs,Statement st,Connection conn) throws SQLException {
        if(rs!=null){
            rs.close();
        }
        if(st!=null){
            st.close();
        }
        if(conn!=null){
            conn.close();
        }
    }
}
