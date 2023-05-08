package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SignUpSate;
import com.mobileserver.util.DB;

public class SignUpSateDAO {

	public List<SignUpSate> QuerySignUpSate() {
		List<SignUpSate> signUpSateList = new ArrayList<SignUpSate>();
		DB db = new DB();
		String sql = "select * from SignUpSate where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SignUpSate signUpSate = new SignUpSate();
				signUpSate.setStateId(rs.getInt("stateId"));
				signUpSate.setStateName(rs.getString("stateName"));
				signUpSateList.add(signUpSate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return signUpSateList;
	}
	/* 传入审核状态对象，进行审核状态的添加业务 */
	public String AddSignUpSate(SignUpSate signUpSate) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新审核状态 */
			String sqlString = "insert into SignUpSate(stateName) values (";
			sqlString += "'" + signUpSate.getStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "审核状态添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "审核状态添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除审核状态 */
	public String DeleteSignUpSate(int stateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SignUpSate where stateId=" + stateId;
			db.executeUpdate(sqlString);
			result = "审核状态删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "审核状态删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据状态id获取到审核状态 */
	public SignUpSate GetSignUpSate(int stateId) {
		SignUpSate signUpSate = null;
		DB db = new DB();
		String sql = "select * from SignUpSate where stateId=" + stateId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				signUpSate = new SignUpSate();
				signUpSate.setStateId(rs.getInt("stateId"));
				signUpSate.setStateName(rs.getString("stateName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return signUpSate;
	}
	/* 更新审核状态 */
	public String UpdateSignUpSate(SignUpSate signUpSate) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SignUpSate set ";
			sql += "stateName='" + signUpSate.getStateName() + "'";
			sql += " where stateId=" + signUpSate.getStateId();
			db.executeUpdate(sql);
			result = "审核状态更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "审核状态更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
