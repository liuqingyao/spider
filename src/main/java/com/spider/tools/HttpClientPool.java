package com.spider.tools;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Created with IntelliJ IDEA.
 * User: zhouwang
 * Date: 12-9-19
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientPool {
    private static HttpParams httpParams = null;
    private static ThreadSafeClientConnManager clientConnManager = null;
    //最大连接数
    public static final int MAX_TOTAL_CONNECTIONS = 400;
    //每个路由最大连接数
    public static final int MAX_ROUTE_CONNECTIONS = 50;
    //链接超时时间
    public static final int CONNECT_TIMEOUT = 10000;
    //读取超时时间
    public static final int READ_TIMEOUT = 10000;

    static {
        httpParams = new BasicHttpParams();

        httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        httpParams.setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1");

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

        clientConnManager = new ThreadSafeClientConnManager(schemeRegistry);

        clientConnManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        clientConnManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        clientConnManager.closeExpiredConnections();

        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);

        System.setProperty("sun.net.client.defaultConnectTimeout", CONNECT_TIMEOUT+"");
        System.setProperty("sun.net.client.defaultReadTimeout", READ_TIMEOUT+"");

    }

    public static HttpClient getHttpClient() {
        return new DefaultHttpClient(clientConnManager, httpParams);
    }

    public static void release() {
        if (clientConnManager != null) {
            clientConnManager.shutdown();
        }
    }

}
