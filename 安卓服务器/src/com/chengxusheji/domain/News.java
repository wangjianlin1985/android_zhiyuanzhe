package com.chengxusheji.domain;

import java.sql.Timestamp;
public class News {
    /*����id*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*����*/
    private Timestamp newsDate;
    public Timestamp getNewsDate() {
        return newsDate;
    }
    public void setNewsDate(Timestamp newsDate) {
        this.newsDate = newsDate;
    }

    /*��Դ*/
    private String comeFrom;
    public String getComeFrom() {
        return comeFrom;
    }
    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    /*����*/
    private String newsContent;
    public String getNewsContent() {
        return newsContent;
    }
    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

}