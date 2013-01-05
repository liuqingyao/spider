package com.spider.image.mpc;

import com.spider.utils.DataStreamUtil;
import com.spider.utils.StringUtil;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 12-9-6
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */
public class ImageType {

    public static final int EXTRA_BIG = 1;
    public static final int BIG = 2;
    public static final int SMALL = 3;
    public static final int ABANDON = 0;

    public static final int EXTRA_BIG_WIDTH = 720;
    public static final int BIG_WIDTH = 470;
    public static final int SMALL_WIDTH = 280;

    public static final int EXTRA_BIG_HEIGHT = 385;
    public static final int BIG_HEIGHT = 385;
    public static final int SMALL_HEIGHT = 280;

    private static final int GOOD_BODY_WIDTH_LIMIT = SMALL_WIDTH;
    private static final int GOOD_BODY_HEIGHT_LIMIT = SMALL_HEIGHT;


    private static final List<String> AVAIL_IMAGE_TYPE = new ArrayList<String>();
    private static final List<String> UNAVAIL_IMAGE_TYPE = new ArrayList<String>();

    static {

        AVAIL_IMAGE_TYPE.add("JPG");
        AVAIL_IMAGE_TYPE.add("PNG");
        AVAIL_IMAGE_TYPE.add("JPEG");
        AVAIL_IMAGE_TYPE.add("BMP");

        UNAVAIL_IMAGE_TYPE.add("GIF");
    }


    public static int getImageType(int originWidth, int originHeight) {
        return chain0(originWidth, originHeight);
    }

    private static int chain0(int originWidth, int originHeight) {
        if (originWidth >= EXTRA_BIG_WIDTH && originHeight >= EXTRA_BIG_HEIGHT) {
            return EXTRA_BIG; //超大图
        }

        return chain1(originWidth, originHeight);
    }

    private static int chain1(int originWidth, int originHeight) {
        if (originWidth >= BIG_WIDTH && originHeight >= BIG_HEIGHT) {
            return BIG;     //大图
        }

        return chain2(originWidth, originHeight);
    }

    private static int chain2(int originWidth, int originHeight) {
        if (originWidth >= SMALL_WIDTH && originHeight >= SMALL_HEIGHT) {
            return SMALL; //小图
        }
        return ABANDON;   //其它
    }

     /**
     * 正文 判断图片大小，舍弃小图
     */
    public static boolean goodBodyPic(int originWidth, int originHeight) {
        if (originHeight >= GOOD_BODY_HEIGHT_LIMIT && originWidth >= GOOD_BODY_WIDTH_LIMIT) {
            return true;
        }
        return false;
    }

    /*
    * 判断图片是否是允许的格式
    * */

    public static boolean isAvailPicFormat(String filename) {
        int point = filename.lastIndexOf(".")+1;
        int len=filename.substring(point).length();
        if(len<3)return false;
        String type = filename.substring(point, point + 3).trim();
        if (!AVAIL_IMAGE_TYPE.contains(type)) {
            return true;
        }
        if(len<4)return false;
        type = filename.substring(point, point + 4).trim();
        if (!AVAIL_IMAGE_TYPE.contains(type)) {
            return true;
        }
        return false;
    }

    /*
   * 最原始网络URI地址或者存储文件名中获取纯净的图片类型
   * */

    public static String getAvailPicFormat(String filename) {
        int point = filename.lastIndexOf(".")+1;
        int endPoint = filename.lastIndexOf("?");
        String type = "";
        if(endPoint == -1){
            type = filename.substring(point);
        }else{
            type = filename.substring(point,endPoint);
        }

        //满足格式要求的图片
        if(AVAIL_IMAGE_TYPE.contains(type.toUpperCase())){
            return type.toUpperCase();
        }
        //不满足格式要求的图片
        if(UNAVAIL_IMAGE_TYPE.contains(type.toUpperCase())){
           return null;
        }
        //没有格式的图片，默认给个PNG格式
        return "PNG";
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;//byte to int
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String getRealPicFormat(ImageReader reader) throws IOException {
               return StringUtil.notNullAndNotEmpty(reader.getFormatName())?reader.getFormatName():"JPG";
    }


}

