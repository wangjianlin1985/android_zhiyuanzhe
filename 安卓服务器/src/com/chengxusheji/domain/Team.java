package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Team {
    /*�û���*/
    private String teamUserName;
    public String getTeamUserName() {
        return teamUserName;
    }
    public void setTeamUserName(String teamUserName) {
        this.teamUserName = teamUserName;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*��������*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*־Ը��������*/
    private String teamName;
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /*����ԺУ*/
    private String shoolName;
    public String getShoolName() {
        return shoolName;
    }
    public void setShoolName(String shoolName) {
        this.shoolName = shoolName;
    }

    /*��������*/
    private String contactGroup;
    public String getContactGroup() {
        return contactGroup;
    }
    public void setContactGroup(String contactGroup) {
        this.contactGroup = contactGroup;
    }

    /*���ܵ�λ*/
    private String mainUnit;
    public String getMainUnit() {
        return mainUnit;
    }
    public void setMainUnit(String mainUnit) {
        this.mainUnit = mainUnit;
    }

    /*����*/
    private String area;
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    /*��ϸ��ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*�ʱ�*/
    private String postCode;
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /*��������*/
    private Timestamp birthDate;
    public Timestamp getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /*־Ը������*/
    private int personNum;
    public int getPersonNum() {
        return personNum;
    }
    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    /*��ϵ�˵绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*����������*/
    private String chargeMan;
    public String getChargeMan() {
        return chargeMan;
    }
    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan;
    }

    /*���������֤*/
    private String cardNumber;
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}