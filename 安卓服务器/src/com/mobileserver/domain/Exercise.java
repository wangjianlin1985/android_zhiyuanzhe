package com.mobileserver.domain;

public class Exercise {
    /*�id*/
    private int exerciseId;
    public int getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    /*�����*/
    private String exerciseName;
    public String getExerciseName() {
        return exerciseName;
    }
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    /*�����*/
    private java.sql.Timestamp exerciseDate;
    public java.sql.Timestamp getExerciseDate() {
        return exerciseDate;
    }
    public void setExerciseDate(java.sql.Timestamp exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    /*����ʱ��*/
    private String serviceTime;
    public String getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    /*��ص�*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*��������*/
    private int personNum;
    public int getPersonNum() {
        return personNum;
    }
    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    /*�����*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*��Ŷ�*/
    private String teamObj;
    public String getTeamObj() {
        return teamObj;
    }
    public void setTeamObj(String teamObj) {
        this.teamObj = teamObj;
    }

}