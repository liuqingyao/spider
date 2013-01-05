package com.spider.reporter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-21
 * Time: 上午10:16
 * To change this template use File | Settings | File Templates.
 */
public class Reporter {

    public static final String NETWORK_ERROR = "network_error";
    public static final String HTML_FORMAT_ERROR = "html_format_error";
    public static final String RSS_FORMAT_ERROR = "rss_format_error";
    public static final String SERVICE_ERROR = "service_error";
    public static final String PARSER_ERROR = "parser_error";
    public static final String DEBUG = "debug_info";
    public static final String DEBUG_SOURCE_QUEUE = "debug_source_queue";
    public static final String DEBUG_START_INFO = "start_info";


    private static Map<String, Log> logMap = new HashMap<String, Log>();

    static {
        logMap.put(NETWORK_ERROR, LogFactory.getLog(NETWORK_ERROR));
        logMap.put(HTML_FORMAT_ERROR, LogFactory.getLog(HTML_FORMAT_ERROR));
        logMap.put(RSS_FORMAT_ERROR, LogFactory.getLog(RSS_FORMAT_ERROR));
        logMap.put(SERVICE_ERROR, LogFactory.getLog(SERVICE_ERROR));
        logMap.put(PARSER_ERROR, LogFactory.getLog(PARSER_ERROR));
        logMap.put(DEBUG, LogFactory.getLog(DEBUG));
        logMap.put(DEBUG_SOURCE_QUEUE, LogFactory.getLog(DEBUG_SOURCE_QUEUE));
        logMap.put(DEBUG_START_INFO, LogFactory.getLog(DEBUG_START_INFO));
    }

    public static void debug(String key, String msg) {
        try {
            logMap.get(key).debug(msg);
        } catch (Throwable e) {

        }

    }

    public static void info(String key, String msg) {
        try {
            logMap.get(key).info(msg);
        } catch (Throwable e) {

        }

    }

    public static void error(String key, String msg) {
        try {
            logMap.get(key).error(msg);
        } catch (Throwable e) {

        }

    }

    public static void fatal(String key, String msg) {
        try {
            logMap.get(key).fatal(msg);
        } catch (Throwable e) {

        }

    }
}
