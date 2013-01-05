package com.spider.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-6-8
 * Time: 下午6:09
 * To change this template use File | Settings | File Templates.
 */
public class DataStreamUtil {
    public static InputStream bytesToInputStream(byte[] bytes ){
        return new ByteArrayInputStream(bytes);
    }
    public static byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        int count = 0;
        while ((count = is.read(buffer)) != -1) {
            byteStream.write(buffer);
        }
        byte[] data = byteStream.toByteArray();
        byteStream.close();
        return data;
    }

}
