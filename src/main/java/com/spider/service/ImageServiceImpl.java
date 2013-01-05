package com.spider.service;

import com.spider.bean.Image;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: daluwang
 * Date: 12-11-12
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */

public class ImageServiceImpl{

    private static ImageServiceImpl instance = new ImageServiceImpl();
    private ImageServiceImpl(){

    }
    public static ImageServiceImpl getInstance(){
        return instance;
    }


    private TTransport transport;
    private ImageServiceImpl client;

    @PreDestroy
    public  void close() {
        this.client = null;
        if (this.transport != null) {
            this.transport.close();
            this.transport = null;
        }
    }


   
    public long create(Image image) throws TException {
        return client.create(image);  //To change body of implemented methods use File | Settings | File Templates.
    }

   
    public Image get(long l) throws TException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

   
    public boolean drop(long l) throws TException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

   
    public boolean update(Image image) throws TException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

   
    public int batchCreate(List<Image> images) throws TException {
        return client.batchCreate(images);
    }

   
    public List<Image> getImagesInArticle(long l) throws TException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

   
    public Image getCoverImageInArticle(long l) throws TException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

   
    public boolean updateCoverImageInArticle(long l, long l1) throws TException {
        return client.updateCoverImageInArticle(l,l1);
    }

}
