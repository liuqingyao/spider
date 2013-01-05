package com.spider.service;

import com.spider.bean.Source;
import com.spider.bean.SourceVO;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-21
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
public class SourceServiceImpl{

    private static SourceServiceImpl instance = new SourceServiceImpl();
    private SourceServiceImpl(){

    }
    public static SourceServiceImpl getInstance(){
        return instance;
    }

    private TTransport transport;
    private SourceServiceImpl client;

    public Source get(long l) throws TException {
        return client.get(l);
    }

    public SourceVO getSourceVO(long l) throws TException {
        return client.getSourceVO(l);
    }

    public long createSourceVO(SourceVO sourceVO) throws TException {
        return 0;  
    }

    public boolean dropSourceVO(long l) throws TException {
        return false;  
    }

    public boolean updateSourceVO(SourceVO sourceVO) throws TException {
        return false;  
    }

    public List<SourceVO> getSourceVOList() throws TException {
        return client.getSourceVOList();
    }

    public boolean online(long l) throws TException {
        return client.online(l);  
    }

    public boolean offline(long l) throws TException {
        return client.offline(l);  
    }

    public int getMaxOrderWeight() throws TException {
        return 0;  
    }

    public List<SourceVO> getPageSearchSources(long l, int i, int i1, String s, int i2, int i3, int i4, int i5) throws TException {
        return client.getPageSearchSources(l,i,i1,s,i2,i3,i4,i5);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getPageSearchSourcesCount(long l, int i, int i1, String s, int i2, int i3, int i4, int i5) throws TException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<SourceVO> getSourceByChannel(long l) throws TException {
        return null;  
    }

    public int updateFetchStatus(int i, long l) throws TException {
        return client.updateFetchStatus(i,l);
    }

    public int getSourceVOCountByChannelId(long l) throws TException {
        return 0;  
    }

    public int updateUpdateAt(long l) throws TException {
        return client.updateUpdateAt(l);
    }


}
