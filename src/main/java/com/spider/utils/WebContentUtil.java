package com.spider.utils;

import com.sun.jndi.toolkit.url.UrlUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-5-15
 * Time: 下午5:57
 * 网络连接
 */
public class WebContentUtil {
    public static Logger logger = Logger.getLogger(UrlUtil.class);

    public static String getContent(String urlStr) {
        return getContent(urlStr, "UTF-8");
    }

    public static String getContent(String urlStr, String charset) {
        if (urlStr == null || urlStr.trim().length() <= 0) {
            return null;
        }

        URL url = null;
        HttpURLConnection con = null;
        InputStreamReader isr = null;
        StringBuffer sb = new StringBuffer();

        try {
            url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            isr = new InputStreamReader(con.getInputStream(), charset);

            if (isr != null) {
                int len = -1;
                char[] buff = new char[500];

                while ((len = isr.read(buff)) != -1) {
                    sb.append(buff, 0, len);
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {

            }
            con.disconnect();
        }
        return sb.toString();
    }


}
