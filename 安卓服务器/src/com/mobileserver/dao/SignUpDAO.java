package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SignUp;
import com.mobileserver.util.DB;

public class SignUpDAO {

	public List<SignUp> QuerySignUp(int exerciseObj,String userObj,int signUpState) {
		List<SignUp> signUpList = new ArrayList<SignUp>();
		DB db = new DB();
		String sql = "select * from SignUp where 1=1";
		if (exerciseObj != 0)
			sql += " and exerciseObj=" + exerciseObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (signUpState != 0)
			sql += " and signUpState=" + signUpState;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SignUp signUp = new SignUp();
				signUp.setSignUpId(rs.getInt("signUpId"));
				signUp.setExerciseObj(rs.getInt("exerciseObj"));
				signUp.setUserObj(rs.getString("userObj"));
				signUp.setSignUpTime(rs.getString("signUpTime"));
				signUp.setSignUpState(rs.getInt("signUpState"));
				signUpList.add(signUp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return signUpList;
	}
	/* 传入活动报名对象，进行活动报名的添加业务 */
	public String AddSignUp(SignUp signUp) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新活动报名 */
			String sqlString = "insert into SignUp(exerciseObj,userObj,signUpTime,signUpState) values (";
			sqlString += signUp.getExerciseObj() + ",";
			sqlString += "'" + signUp.getUserObj() + "',";
			sqlString += "'" + signUp.getSignUpTime() + "',";
			sqlString += signUp.getSignUpState() ;
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "活动报名添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "活动报名添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除活动报名 */
	public String DeleteSignUp(int signUpId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SignUp where signUpId=" + signUpId;
			db.executeUpdate(sqlString);
			result = "活动报名删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "活动报名删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据报名id获取到活动报名 */
	public SignUp GetSignUp(int signUpId) {
		SignUp signUp = null;
		DB db = new DB();
		String sql = "select * from SignUp where signUpId=" + signUpId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				signUp = new SignUp();
				signUp.setSignUpId(rs.getInt("signUpId"));
				signUp.setExerciseObj(rs.getInt("exerciseObj"));
				signUp.setUserObj(rs.getString("userObj"));
				signUp.setSignUpTime(rs.getString("signUpTime"));
				signUp.setSignUpState(rs.getInt("signUpState"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return signUp;
	}
	/* 更新活动报名 */
	public String UpdateSignUp(SignUp signUp) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SignUp set ";
			sql += "exerciseObj=" + signUp.getExerciseObj() + ",";
			sql += "userObj='" + signUp.getUserObj() + "',";
			sql += "signUpTime='" + signUp.getSignUpTime() + "',";
			sql += "signUpState=" + signUp.getSignUpState();
			sql += " where signUpId=" + signUp.getSignUpId();
			db.executeUpdate(sql);
			result = "活动报名更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "活动报名更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
