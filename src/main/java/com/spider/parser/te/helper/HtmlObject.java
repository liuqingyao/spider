package com.spider.parser.te.helper;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-23
 * Time: 下午12:16
 * To change this template use File | Settings | File Templates.
 */
public class HtmlObject {
    private String title;
    private String content;
    private String url;
    private String tempUrl;
    public String getTitle() {
        return title;
    }

    public String getTempUrl() {
        return tempUrl;
    }

    public void setTempUrl(String tempUrl) {
        this.tempUrl = tempUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
