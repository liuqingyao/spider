package com.spider.image;

import com.spider.cache.Cache;
import com.spider.cache.CacheManager;
import com.spider.reporter.MonitorCacheKey;
import com.spider.tools.HttpClientPool;
import com.spider.utils.ExceptionUtil;
import com.spider.utils.MathUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-9-24
 * Time: 下午7:18
 * 原图存储
 */
public class ImageUtil {
    private static final Logger logger = Logger.getLogger(ImageUtil.class);
    private static Cache cache = CacheManager.getCacheImpl();

    private static final long MIN_LEN = 512;
    private static final long MAX_LEN = 1024*1024*10;

    public static InputStream getImageInputStream(String picUrl){
        HttpClient client = HttpClientPool.getHttpClient();
        HttpGet get = new HttpGet(picUrl);
        try {
            HttpResponse response = client.execute(get);
            return response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean downImageFile(String file,String picUrl){
        FileOutputStream out = null;
        InputStream inputStream = null;
        HttpClient client = HttpClientPool.getHttpClient();
        HttpGet get = new HttpGet(picUrl);
        try{
            HttpResponse response = client.execute(get);
            long contentLength = response.getEntity().getContentLength();
            boolean availableContentLength = MathUtil.between(MIN_LEN,MAX_LEN,contentLength);
            inputStream = response.getEntity().getContent();
            out = new FileOutputStream(new File(file));

            byte[] buffer = new byte[1024 * 8];

            int count = 0;
            while ((count = inputStream.read(buffer))!=-1){
                out.write(buffer,0,count);
                contentLength-=count;
                out.flush();
            }
            if(contentLength>0 && availableContentLength){
                 cache.rpush(MonitorCacheKey.IMAGE_DL_FAIL,picUrl);
                 return false;
            }

        }catch (Exception e){
            logger.error(e.getMessage());
            cache.rpush(MonitorCacheKey.IMAGE_DL_EXCEPTION, picUrl+"\n"+ExceptionUtil.stackInfo(e));
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }

        }
        return true;
    }

}
