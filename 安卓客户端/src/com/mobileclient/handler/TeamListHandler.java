package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Team;
public class TeamListHandler extends DefaultHandler {
	private List<Team> teamList = null;
	private Team team;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (team != null) { 
            String valueString = new String(ch, start, length); 
            if ("teamUserName".equals(tempString)) 
            	team.setTeamUserName(valueString); 
            else if ("password".equals(tempString)) 
            	team.setPassword(valueString); 
            else if ("email".equals(tempString)) 
            	team.setEmail(valueString); 
            else if ("teamName".equals(tempString)) 
            	team.setTeamName(valueString); 
            else if ("shoolName".equals(tempString)) 
            	team.setShoolName(valueString); 
            else if ("contactGroup".equals(tempString)) 
            	team.setContactGroup(valueString); 
            else if ("mainUnit".equals(tempString)) 
            	team.setMainUnit(valueString); 
            else if ("area".equals(tempString)) 
            	team.setArea(valueString); 
            else if ("address".equals(tempString)) 
            	team.setAddress(valueString); 
            else if ("postCode".equals(tempString)) 
            	team.setPostCode(valueString); 
            else if ("birthDate".equals(tempString)) 
            	team.setBirthDate(Timestamp.valueOf(valueString));
            else if ("personNum".equals(tempString)) 
            	team.setPersonNum(new Integer(valueString).intValue());
            else if ("telephone".equals(tempString)) 
            	team.setTelephone(valueString); 
            else if ("chargeMan".equals(tempString)) 
            	team.setChargeMan(valueString); 
            else if ("cardNumber".equals(tempString)) 
            	team.setCardNumber(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Team".equals(localName)&&team!=null){
			teamList.add(team);
			team = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		teamList = new ArrayList<Team>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Team".equals(localName)) {
            team = new Team(); 
        }
        tempString = localName; 
	}

	public List<Team> getTeamList() {
		return this.teamList;
	}
}
