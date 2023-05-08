package com.mobileclient.domain;

import java.io.Serializable;

public class Exercise implements Serializable {
    /*活动id*/
    private int exerciseId;
    public int getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    /*活动名称*/
    private String exerciseName;
    public String getExerciseName() {
        return exerciseName;
    }
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    /*活动日期*/
    private java.sql.Timestamp exerciseDate;
    public java.sql.Timestamp getExerciseDate() {
        return exerciseDate;
    }
    public void setExerciseDate(java.sql.Timestamp exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    /*服务时长*/
    private String serviceTime;
    public String getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    /*活动地点*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*参与人数*/
    private int personNum;
    public int getPersonNum() {
        return personNum;
    }
    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    /*活动内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*活动团队*/
    private String teamObj;
    public String getTeamObj() {
        return teamObj;
    }
    public void setTeamObj(String teamObj) {
        this.teamObj = teamObj;
    }

}