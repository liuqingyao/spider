package com.sohu.reader.spider.parser.bean;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-17
 * Time: 上午11:37
 * To change this template use File | Settings | File Templates.
 */
public class ExtractUrlBean {
    private String url = "";
    private boolean extracted = false;

    public ExtractUrlBean(String url, boolean extracted) {
        this.url = url;
        this.extracted = extracted;
    }

    public ExtractUrlBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isExtracted() {
        return extracted;
    }

    public void setExtracted(boolean extracted) {
        this.extracted = extracted;
    }
}
