package com.spider.service;

import com.spider.bean.Fetch;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;

import javax.annotation.PreDestroy;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-30
 * Time: 上午11:34
 * To change this template use File | Settings | File Templates.
 */
public class FetchServiceImpl {

    private static FetchServiceImpl instance = new FetchServiceImpl();

    private FetchServiceImpl() {

    }

    public static FetchServiceImpl getInstance() {
        return instance;
    }

    private TTransport transport;
    private FetchServiceImpl client;

    @PreDestroy
    public void close() {
        this.client = null;
        if (this.transport != null) {
            this.transport.close();
            this.transport = null;
        }
    }


    public long create(Fetch fetch) throws TException {
        return client.create(fetch);
    }

    public boolean drop(long id) throws TException {
        return client.drop(id);
    }

    public boolean update(Fetch fetch) throws TException {
        return client.update(fetch);
    }

    public Fetch get(long id) throws TException {
        return client.get(id);
    }

    public Fetch getBySourceId(long sourceId) throws TException {
        return client.getBySourceId(sourceId);
    }
}