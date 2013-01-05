package com.spider.download;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-3
 * Time: 下午6:57
 * download 改变自jSoup
 */
public class DownLoadUtil {
    public static String html(String url) throws IOException {
       return new String(WebContent.connect(url).userAgent("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11").get());
    }
    public static boolean image(String url,String des) throws IOException {
        return  ImageContent.downImageFile(des, url);
    }
}
