package com.spider.image.mpc;

import com.spider.bean.Image;
import com.spider.cache.Cache;
import com.spider.cache.CacheManager;
import com.spider.constants.Config;
import com.spider.image.ImageUtil;
import com.spider.reporter.MonitorCacheKey;
import com.spider.utils.ExceptionUtil;
import com.spider.utils.IDUtil;
import com.spider.utils.MD5Util;
import com.spider.utils.TimeUtil;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 12-9-6
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */
public class ImageProcessAndSave {
    private static Cache cache = CacheManager.getCacheImpl();
    private static final Logger logger = Logger.getLogger(ImageProcessAndSave.class);

    //原始图片存储
    public static Image downloadImage(String url) {

        //过滤图片格式
        if (!ImageType.isAvailPicFormat(url)) {
            return null;
        }

        Image pic = new Image();
        pic.setOriginalUrl(url);
        InputStream inputStream = null;
        ImageInputStream iis = null;
        try {
            inputStream = ImageUtil.getImageInputStream(url);
            iis = ImageIO.createImageInputStream(inputStream);

            ImageReader reader = ImageIO.getImageReaders(iis).next();

            BufferedImage image = ImageIO.read(iis);

            inputStream.close();
//
            if (null == image) return null;
            //过滤 正文图片大小
            if (!ImageType.goodBodyPic(image.getWidth(), image.getHeight())) {
                return null;
            }

            String fileName = IDUtil.getImageId();
            String filePath = getAbsSaveFilePath(fileName);
            String fileFormat = ImageType.getRealPicFormat(reader);

            if (null == fileFormat || fileFormat.toUpperCase().equals("GIF")) return null;

            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            boolean downloadSuccess = ImageUtil.downImageFile(filePath + fileName + "." + fileFormat, url);

            if (!downloadSuccess) {
                return null;
            }

            pic.setWidth(image.getWidth());
            pic.setHeight(image.getHeight());
            pic.setUrl(getUrl(fileName) + "." + fileFormat);
        } catch (IOException e) {
            cache.rpush(MonitorCacheKey.IMAGE_DL_EXCEPTION, TimeUtil.curTime() + " " + url + "\n" + ExceptionUtil.stackInfo(e));
            return null;
        } catch (Exception e) {
            cache.rpush(MonitorCacheKey.IMAGE_DL_EXCEPTION, TimeUtil.curTime() + " " + url + "\n" + ExceptionUtil.stackInfo(e));
            return null;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                logger.error("close inputStream error:" + e.getMessage());
            }
            iis = null;
        }


        return pic;
    }

    private static String getAbsSaveFilePath(String fileName) {
        return Config.getValue("img_path") + MD5Util.createDir(fileName);
    }

    private static String getUrl(String fileName) {
        return Config.getValue("host") + MD5Util.createDir(fileName) + fileName;
    }


}
