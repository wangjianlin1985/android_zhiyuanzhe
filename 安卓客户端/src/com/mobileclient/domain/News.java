package com.mobileclient.domain;

import java.io.Serializable;

public class News implements Serializable {
    /*新闻id*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*日期*/
    private java.sql.Timestamp newsDate;
    public java.sql.Timestamp getNewsDate() {
        return newsDate;
    }
    public void setNewsDate(java.sql.Timestamp newsDate) {
        this.newsDate = newsDate;
    }

    /*来源*/
    private String comeFrom;
    public String getComeFrom() {
        return comeFrom;
    }
    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    /*内容*/
    private String newsContent;
    public String getNewsContent() {
        return newsContent;
    }
    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

}