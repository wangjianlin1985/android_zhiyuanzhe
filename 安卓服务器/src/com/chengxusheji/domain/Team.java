package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Team {
    /*用户名*/
    private String teamUserName;
    public String getTeamUserName() {
        return teamUserName;
    }
    public void setTeamUserName(String teamUserName) {
        this.teamUserName = teamUserName;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*电子邮箱*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*志愿团体名称*/
    private String teamName;
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /*所属院校*/
    private String shoolName;
    public String getShoolName() {
        return shoolName;
    }
    public void setShoolName(String shoolName) {
        this.shoolName = shoolName;
    }

    /*联络团体*/
    private String contactGroup;
    public String getContactGroup() {
        return contactGroup;
    }
    public void setContactGroup(String contactGroup) {
        this.contactGroup = contactGroup;
    }

    /*主管单位*/
    private String mainUnit;
    public String getMainUnit() {
        return mainUnit;
    }
    public void setMainUnit(String mainUnit) {
        this.mainUnit = mainUnit;
    }

    /*区域*/
    private String area;
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    /*详细地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*邮编*/
    private String postCode;
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /*成立日期*/
    private Timestamp birthDate;
    public Timestamp getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /*志愿者人数*/
    private int personNum;
    public int getPersonNum() {
        return personNum;
    }
    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    /*联系人电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*负责人姓名*/
    private String chargeMan;
    public String getChargeMan() {
        return chargeMan;
    }
    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan;
    }

    /*负责人身份证*/
    private String cardNumber;
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}