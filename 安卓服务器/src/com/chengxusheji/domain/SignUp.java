package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SignUp {
    /*����id*/
    private int signUpId;
    public int getSignUpId() {
        return signUpId;
    }
    public void setSignUpId(int signUpId) {
        this.signUpId = signUpId;
    }

    /*�Ŷӻ*/
    private Exercise exerciseObj;
    public Exercise getExerciseObj() {
        return exerciseObj;
    }
    public void setExerciseObj(Exercise exerciseObj) {
        this.exerciseObj = exerciseObj;
    }

    /*�����û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*����ʱ��*/
    private String signUpTime;
    public String getSignUpTime() {
        return signUpTime;
    }
    public void setSignUpTime(String signUpTime) {
        this.signUpTime = signUpTime;
    }

    /*���״̬*/
    private SignUpSate signUpState;
    public SignUpSate getSignUpState() {
        return signUpState;
    }
    public void setSignUpState(SignUpSate signUpState) {
        this.signUpState = signUpState;
    }

}