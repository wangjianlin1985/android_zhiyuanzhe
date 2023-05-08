package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SignUp {
    /*报名id*/
    private int signUpId;
    public int getSignUpId() {
        return signUpId;
    }
    public void setSignUpId(int signUpId) {
        this.signUpId = signUpId;
    }

    /*团队活动*/
    private Exercise exerciseObj;
    public Exercise getExerciseObj() {
        return exerciseObj;
    }
    public void setExerciseObj(Exercise exerciseObj) {
        this.exerciseObj = exerciseObj;
    }

    /*报名用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
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
    private SignUpSate signUpState;
    public SignUpSate getSignUpState() {
        return signUpState;
    }
    public void setSignUpState(SignUpSate signUpState) {
        this.signUpState = signUpState;
    }

}