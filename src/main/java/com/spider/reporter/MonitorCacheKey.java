package com.spider.reporter;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-10
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
public class MonitorCacheKey {
    public static final String IMAGE_DL_FAIL = "spider_image_dl_fail";//图片下载失败，指图片没有下载完全
    public static final String IMAGE_DL_EXCEPTION = "spider_image_dl_exception"; //图片下载异常

    public static final String SOURCE_ERROR_PREFIX = "spider_source_error_"; //源抓取出现问题汇总 ，后加源ID
    public static final String ARTICLE_ERROR_PREFIX = "spider_article_error_"; //文章抓取出现问题汇总 ，后加文章ID
    public static final String EXTRACT_ERROR_PREFIX = "spider_extract_error_"; //文章内容提取出现问题汇总 ，后加源ID


    public static final String SOURCE_NETWORK_ERROR = "source_network_error"; //html源信息网络出错
    public static final String SOURCE_HTML_INFO_ERROR = "source_html_info_error"; //html源信息抓取出错
    public static final String SOURCE_RSS_FORMAT_ERROR = "source_rss_format_error"; //RSS源信息格式出错
    public static final String SOURCE_THRIFT_SERVICE_ERROR = "source_thrift_service_error"; //接口错误
    public static final String SOURCE_OTHER_ERROR = "source_other_error"; //RSS源信息格式出错


    public static final String ARTICLE_IMAGE_SERVICE_ERROR = "article_image_service_error";//接口错误

    public static final String EXTRACT_DB_ERROR = "extract_db_error";//数据库错误
    public static final String EXTRACT_ANALYZE_ERROR = "extract_analyze_error";//解析错误

    public static final String SOURCE_TRAVERSAL_5_CONSUME = "source5_traversal_consume";
    public static final String SOURCE_TRAVERSAL_LESS_5_CONSUME = "source_less_5_traversal_consume";

    public static final String SOURCE_SPIDER_HISTORY_OF = "source_spider_history_";//抓取记录

}
