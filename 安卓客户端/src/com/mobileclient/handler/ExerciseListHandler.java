package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Exercise;
public class ExerciseListHandler extends DefaultHandler {
	private List<Exercise> exerciseList = null;
	private Exercise exercise;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (exercise != null) { 
            String valueString = new String(ch, start, length); 
            if ("exerciseId".equals(tempString)) 
            	exercise.setExerciseId(new Integer(valueString).intValue());
            else if ("exerciseName".equals(tempString)) 
            	exercise.setExerciseName(valueString); 
            else if ("exerciseDate".equals(tempString)) 
            	exercise.setExerciseDate(Timestamp.valueOf(valueString));
            else if ("serviceTime".equals(tempString)) 
            	exercise.setServiceTime(valueString); 
            else if ("address".equals(tempString)) 
            	exercise.setAddress(valueString); 
            else if ("personNum".equals(tempString)) 
            	exercise.setPersonNum(new Integer(valueString).intValue());
            else if ("content".equals(tempString)) 
            	exercise.setContent(valueString); 
            else if ("teamObj".equals(tempString)) 
            	exercise.setTeamObj(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Exercise".equals(localName)&&exercise!=null){
			exerciseList.add(exercise);
			exercise = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		exerciseList = new ArrayList<Exercise>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Exercise".equals(localName)) {
            exercise = new Exercise(); 
        }
        tempString = localName; 
	}

	public List<Exercise> getExerciseList() {
		return this.exerciseList;
	}
}
