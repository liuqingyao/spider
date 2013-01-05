package com.spider.download;

import com.spider.image.ImageUtil;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-26
 * Time: 下午6:11
 * To change this template use File | Settings | File Templates.
 */
public class ImageContent {
    public static boolean downImageFile(String file,String picUrl){
       return ImageUtil.downImageFile(file,picUrl);
    }
}
