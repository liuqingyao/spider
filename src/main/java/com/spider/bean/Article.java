/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.spider.bean;

public class Article{

  public long id; // required
  public String title; // required
  public String summary; // required
  public String content; // required
  public int viewCount; // required
  public String originalUrl; // required
  public long channelId; // required
  public long sourceId; // required
  public int commentFlag; // required
  public int status; // required
  public int orderWeight; // required

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public int getCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(int commentFlag) {
        this.commentFlag = commentFlag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(int orderWeight) {
        this.orderWeight = orderWeight;
    }

    public long getSimilarArticleId() {
        return similarArticleId;
    }

    public void setSimilarArticleId(long similarArticleId) {
        this.similarArticleId = similarArticleId;
    }

    public long getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(long publishAt) {
        this.publishAt = publishAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long similarArticleId; // required
  public long publishAt; // required
  public long createdAt; // required
  public long updatedAt; // required

}
