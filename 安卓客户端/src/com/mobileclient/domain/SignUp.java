package com.mobileclient.domain;

import java.io.Serializable;

public class SignUp implements Serializable {
    /*报名id*/
    private int signUpId;
    public int getSignUpId() {
        return signUpId;
    }
    public void setSignUpId(int signUpId) {
        this.signUpId = signUpId;
    }

    /*团队活动*/
    private int exerciseObj;
    public int getExerciseObj() {
        return exerciseObj;
    }
    public void setExerciseObj(int exerciseObj) {
        this.exerciseObj = exerciseObj;
    }

    /*报名用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*报名时间*/
    private String signUpTime;
    public String getSignUpTime() {
        return signUpTime;
    }
    public void setSignUpTime(String signUpTime) {
        this.signUpTime = signUpTime;
    }

    /*审核状态*/
    private int signUpState;
    public int getSignUpState() {
        return signUpState;
    }
    public void setSignUpState(int signUpState) {
        this.signUpState = signUpState;
    }

}