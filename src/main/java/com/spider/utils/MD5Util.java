package com.spider.utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-9-7
 * Time: 下午5:54
 * To change this template use File | Settings | File Templates.
 */
public class MD5Util {
    public static String getMD5HashValue(String fileName) {
        String hashValue = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(fileName.getBytes());
            byte[] byteHash = md5.digest();//取散列值
            int i = 0;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteHash.length; offset++) {
                i = byteHash[offset];
                if (i < 0) i += 256; //i为负数加256.
                if (i < 16) buf.append("0"); //小于16则十六进制数为1位,需补一位0.
                buf.append(Integer.toHexString(i));//转16进制数.
            }
            hashValue = buf.toString();
        } catch (NoSuchAlgorithmException e) {

        } catch (Exception e) {

        }
        return hashValue;
    }

    /**
     * 保存文件至根目录下
     * 保存过程取文件名MD5散列值，
     * 每两位散列值创建一级目录
     * <p/>
     * *
     */
    public static String createDir(String fileName) {

        String hashValue = getMD5HashValue(fileName);
        if (hashValue != null && !"".equals(hashValue) && hashValue.length() == 32) {
            String level1dir = hashValue.substring(0, 2);//2位=16*16=256个文件夹
            String level2dir = hashValue.substring(2, 4);
            return level1dir + File.separator + level2dir + File.separator;
        } else {
            return "error_img" + File.separator;
        }
    }

    public static void main(String[] args) {
        String fileName = "http://cmap100.blog.163.com/blog/static/5457171620127343456576";
        System.out.println(getMD5HashValue(fileName));
    }
}
