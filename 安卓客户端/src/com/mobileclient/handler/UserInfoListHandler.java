package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.UserInfo;
public class UserInfoListHandler extends DefaultHandler {
	private List<UserInfo> userInfoList = null;
	private UserInfo userInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (userInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("user_name".equals(tempString)) 
            	userInfo.setUser_name(valueString); 
            else if ("password".equals(tempString)) 
            	userInfo.setPassword(valueString); 
            else if ("email".equals(tempString)) 
            	userInfo.setEmail(valueString); 
            else if ("name".equals(tempString)) 
            	userInfo.setName(valueString); 
            else if ("sex".equals(tempString)) 
            	userInfo.setSex(valueString); 
            else if ("userPhoto".equals(tempString)) 
            	userInfo.setUserPhoto(valueString); 
            else if ("schoolName".equals(tempString)) 
            	userInfo.setSchoolName(valueString); 
            else if ("specialInfo".equals(tempString)) 
            	userInfo.setSpecialInfo(valueString); 
            else if ("nation".equals(tempString)) 
            	userInfo.setNation(valueString); 
            else if ("politicalStatus".equals(tempString)) 
            	userInfo.setPoliticalStatus(valueString); 
            else if ("birthday".equals(tempString)) 
            	userInfo.setBirthday(Timestamp.valueOf(valueString));
            else if ("cardNumber".equals(tempString)) 
            	userInfo.setCardNumber(valueString); 
            else if ("telephone".equals(tempString)) 
            	userInfo.setTelephone(valueString); 
            else if ("address".equals(tempString)) 
            	userInfo.setAddress(valueString); 
            else if ("interest".equals(tempString)) 
            	userInfo.setInterest(valueString); 
            else if ("introduce".equals(tempString)) 
            	userInfo.setIntroduce(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("UserInfo".equals(localName)&&userInfo!=null){
			userInfoList.add(userInfo);
			userInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		userInfoList = new ArrayList<UserInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("UserInfo".equals(localName)) {
            userInfo = new UserInfo(); 
        }
        tempString = localName; 
	}

	public List<UserInfo> getUserInfoList() {
		return this.userInfoList;
	}
}
