package com.mobileserver.domain;

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
    private java.sql.Timestamp newsDate;
    public java.sql.Timestamp getNewsDate() {
        return newsDate;
    }
    public void setNewsDate(java.sql.Timestamp newsDate) {
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