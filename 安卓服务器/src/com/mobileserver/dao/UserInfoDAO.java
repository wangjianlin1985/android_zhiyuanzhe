package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.UserInfo;
import com.mobileserver.util.DB;

public class UserInfoDAO {

	public List<UserInfo> QueryUserInfo(String user_name,String email,String name,String schoolName,String specialInfo,Timestamp birthday) {
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		DB db = new DB();
		String sql = "select * from UserInfo where 1=1";
		if (!user_name.equals(""))
			sql += " and user_name like '%" + user_name + "%'";
		if (!email.equals(""))
			sql += " and email like '%" + email + "%'";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if (!schoolName.equals(""))
			sql += " and schoolName like '%" + schoolName + "%'";
		if (!specialInfo.equals(""))
			sql += " and specialInfo like '%" + specialInfo + "%'";
		if(birthday!=null)
			sql += " and birthday='" + birthday + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setName(rs.getString("name"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setUserPhoto(rs.getString("userPhoto"));
				userInfo.setSchoolName(rs.getString("schoolName"));
				userInfo.setSpecialInfo(rs.getString("specialInfo"));
				userInfo.setNation(rs.getString("nation"));
				userInfo.setPoliticalStatus(rs.getString("politicalStatus"));
				userInfo.setBirthday(rs.getTimestamp("birthday"));
				userInfo.setCardNumber(rs.getString("cardNumber"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setAddress(rs.getString("address"));
				userInfo.setInterest(rs.getString("interest"));
				userInfo.setIntroduce(rs.getString("introduce"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfoList;
	}
	/* 传入用户对象，进行用户的添加业务 */
	public String AddUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新用户 */
			String sqlString = "insert into UserInfo(user_name,password,email,name,sex,userPhoto,schoolName,specialInfo,nation,politicalStatus,birthday,cardNumber,telephone,address,interest,introduce) values (";
			sqlString += "'" + userInfo.getUser_name() + "',";
			sqlString += "'" + userInfo.getPassword() + "',";
			sqlString += "'" + userInfo.getEmail() + "',";
			sqlString += "'" + userInfo.getName() + "',";
			sqlString += "'" + userInfo.getSex() + "',";
			sqlString += "'" + userInfo.getUserPhoto() + "',";
			sqlString += "'" + userInfo.getSchoolName() + "',";
			sqlString += "'" + userInfo.getSpecialInfo() + "',";
			sqlString += "'" + userInfo.getNation() + "',";
			sqlString += "'" + userInfo.getPoliticalStatus() + "',";
			sqlString += "'" + userInfo.getBirthday() + "',";
			sqlString += "'" + userInfo.getCardNumber() + "',";
			sqlString += "'" + userInfo.getTelephone() + "',";
			sqlString += "'" + userInfo.getAddress() + "',";
			sqlString += "'" + userInfo.getInterest() + "',";
			sqlString += "'" + userInfo.getIntroduce() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "用户添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除用户 */
	public String DeleteUserInfo(String user_name) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from UserInfo where user_name='" + user_name + "'";
			db.executeUpdate(sqlString);
			result = "用户删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据用户名获取到用户 */
	public UserInfo GetUserInfo(String user_name) {
		UserInfo userInfo = null;
		DB db = new DB();
		String sql = "select * from UserInfo where user_name='" + user_name + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setName(rs.getString("name"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setUserPhoto(rs.getString("userPhoto"));
				userInfo.setSchoolName(rs.getString("schoolName"));
				userInfo.setSpecialInfo(rs.getString("specialInfo"));
				userInfo.setNation(rs.getString("nation"));
				userInfo.setPoliticalStatus(rs.getString("politicalStatus"));
				userInfo.setBirthday(rs.getTimestamp("birthday"));
				userInfo.setCardNumber(rs.getString("cardNumber"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setAddress(rs.getString("address"));
				userInfo.setInterest(rs.getString("interest"));
				userInfo.setIntroduce(rs.getString("introduce"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfo;
	}
	/* 更新用户 */
	public String UpdateUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update UserInfo set ";
			sql += "password='" + userInfo.getPassword() + "',";
			sql += "email='" + userInfo.getEmail() + "',";
			sql += "name='" + userInfo.getName() + "',";
			sql += "sex='" + userInfo.getSex() + "',";
			sql += "userPhoto='" + userInfo.getUserPhoto() + "',";
			sql += "schoolName='" + userInfo.getSchoolName() + "',";
			sql += "specialInfo='" + userInfo.getSpecialInfo() + "',";
			sql += "nation='" + userInfo.getNation() + "',";
			sql += "politicalStatus='" + userInfo.getPoliticalStatus() + "',";
			sql += "birthday='" + userInfo.getBirthday() + "',";
			sql += "cardNumber='" + userInfo.getCardNumber() + "',";
			sql += "telephone='" + userInfo.getTelephone() + "',";
			sql += "address='" + userInfo.getAddress() + "',";
			sql += "interest='" + userInfo.getInterest() + "',";
			sql += "introduce='" + userInfo.getIntroduce() + "'";
			sql += " where user_name='" + userInfo.getUser_name() + "'";
			db.executeUpdate(sql);
			result = "用户更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
