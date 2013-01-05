package com.spider.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-5-15
 * Time: 下午6:58
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtil {
    private static String argPrefix = "dy/1/0/";
    private static String thumbnailHost = "http://s9.rr.itc.cn/";

    public static Map<String, String> getImageInfo(String urlStr) {
        Map<String, String> map = new HashMap<String, String>();
        BufferedImage buff = null;
        try {
            buff = ImageIO.read(new URL(urlStr));
            map.put("w", buff.getWidth() * 1L + "");
            map.put("h", buff.getHeight() * 1L + "");
            map.put("href", urlStr);
            buff.flush();
        } catch (Throwable e) {

        } finally {

        }
        return map;
    }

    private static String base64(String value) {
        if (value == null) return null;
        return Base64Util.encode(value.getBytes());
    }

}
