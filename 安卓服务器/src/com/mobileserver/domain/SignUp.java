package com.mobileserver.domain;

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
    private int exerciseObj;
    public int getExerciseObj() {
        return exerciseObj;
    }
    public void setExerciseObj(int exerciseObj) {
        this.exerciseObj = exerciseObj;
    }

    /*�����û�*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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
    private int signUpState;
    public int getSignUpState() {
        return signUpState;
    }
    public void setSignUpState(int signUpState) {
        this.signUpState = signUpState;
    }

}