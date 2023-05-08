package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Exercise;
import com.mobileserver.util.DB;

public class ExerciseDAO {

	public List<Exercise> QueryExercise(String exerciseName,Timestamp exerciseDate,String teamObj) {
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		DB db = new DB();
		String sql = "select * from Exercise where 1=1";
		if (!exerciseName.equals(""))
			sql += " and exerciseName like '%" + exerciseName + "%'";
		if(exerciseDate!=null)
			sql += " and exerciseDate='" + exerciseDate + "'";
		if (!teamObj.equals(""))
			sql += " and teamObj = '" + teamObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Exercise exercise = new Exercise();
				exercise.setExerciseId(rs.getInt("exerciseId"));
				exercise.setExerciseName(rs.getString("exerciseName"));
				exercise.setExerciseDate(rs.getTimestamp("exerciseDate"));
				exercise.setServiceTime(rs.getString("serviceTime"));
				exercise.setAddress(rs.getString("address"));
				exercise.setPersonNum(rs.getInt("personNum"));
				exercise.setContent(rs.getString("content"));
				exercise.setTeamObj(rs.getString("teamObj"));
				exerciseList.add(exercise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return exerciseList;
	}
	/* �������󣬽��л�����ҵ�� */
	public String AddExercise(Exercise exercise) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����» */
			String sqlString = "insert into Exercise(exerciseName,exerciseDate,serviceTime,address,personNum,content,teamObj) values (";
			sqlString += "'" + exercise.getExerciseName() + "',";
			sqlString += "'" + exercise.getExerciseDate() + "',";
			sqlString += "'" + exercise.getServiceTime() + "',";
			sqlString += "'" + exercise.getAddress() + "',";
			sqlString += exercise.getPersonNum() + ",";
			sqlString += "'" + exercise.getContent() + "',";
			sqlString += "'" + exercise.getTeamObj() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��� */
	public String DeleteExercise(int exerciseId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Exercise where exerciseId=" + exerciseId;
			db.executeUpdate(sqlString);
			result = "�ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݻid��ȡ��� */
	public Exercise GetExercise(int exerciseId) {
		Exercise exercise = null;
		DB db = new DB();
		String sql = "select * from Exercise where exerciseId=" + exerciseId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				exercise = new Exercise();
				exercise.setExerciseId(rs.getInt("exerciseId"));
				exercise.setExerciseName(rs.getString("exerciseName"));
				exercise.setExerciseDate(rs.getTimestamp("exerciseDate"));
				exercise.setServiceTime(rs.getString("serviceTime"));
				exercise.setAddress(rs.getString("address"));
				exercise.setPersonNum(rs.getInt("personNum"));
				exercise.setContent(rs.getString("content"));
				exercise.setTeamObj(rs.getString("teamObj"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return exercise;
	}
	/* ���» */
	public String UpdateExercise(Exercise exercise) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Exercise set ";
			sql += "exerciseName='" + exercise.getExerciseName() + "',";
			sql += "exerciseDate='" + exercise.getExerciseDate() + "',";
			sql += "serviceTime='" + exercise.getServiceTime() + "',";
			sql += "address='" + exercise.getAddress() + "',";
			sql += "personNum=" + exercise.getPersonNum() + ",";
			sql += "content='" + exercise.getContent() + "',";
			sql += "teamObj='" + exercise.getTeamObj() + "'";
			sql += " where exerciseId=" + exercise.getExerciseId();
			db.executeUpdate(sql);
			result = "����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
