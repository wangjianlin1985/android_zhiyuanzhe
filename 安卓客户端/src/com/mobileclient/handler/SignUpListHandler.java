package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SignUp;
public class SignUpListHandler extends DefaultHandler {
	private List<SignUp> signUpList = null;
	private SignUp signUp;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (signUp != null) { 
            String valueString = new String(ch, start, length); 
            if ("signUpId".equals(tempString)) 
            	signUp.setSignUpId(new Integer(valueString).intValue());
            else if ("exerciseObj".equals(tempString)) 
            	signUp.setExerciseObj(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	signUp.setUserObj(valueString); 
            else if ("signUpTime".equals(tempString)) 
            	signUp.setSignUpTime(valueString); 
            else if ("signUpState".equals(tempString)) 
            	signUp.setSignUpState(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SignUp".equals(localName)&&signUp!=null){
			signUpList.add(signUp);
			signUp = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		signUpList = new ArrayList<SignUp>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SignUp".equals(localName)) {
            signUp = new SignUp(); 
        }
        tempString = localName; 
	}

	public List<SignUp> getSignUpList() {
		return this.signUpList;
	}
}
