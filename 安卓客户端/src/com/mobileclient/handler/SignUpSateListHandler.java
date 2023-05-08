package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SignUpSate;
public class SignUpSateListHandler extends DefaultHandler {
	private List<SignUpSate> signUpSateList = null;
	private SignUpSate signUpSate;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (signUpSate != null) { 
            String valueString = new String(ch, start, length); 
            if ("stateId".equals(tempString)) 
            	signUpSate.setStateId(new Integer(valueString).intValue());
            else if ("stateName".equals(tempString)) 
            	signUpSate.setStateName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SignUpSate".equals(localName)&&signUpSate!=null){
			signUpSateList.add(signUpSate);
			signUpSate = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		signUpSateList = new ArrayList<SignUpSate>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SignUpSate".equals(localName)) {
            signUpSate = new SignUpSate(); 
        }
        tempString = localName; 
	}

	public List<SignUpSate> getSignUpSateList() {
		return this.signUpSateList;
	}
}
