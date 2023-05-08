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
	/* �������״̬���󣬽������״̬�����ҵ�� */
	public String AddSignUpSate(SignUpSate signUpSate) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������״̬ */
			String sqlString = "insert into SignUpSate(stateName) values (";
			sqlString += "'" + signUpSate.getStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���״̬��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���״̬���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����״̬ */
	public String DeleteSignUpSate(int stateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SignUpSate where stateId=" + stateId;
			db.executeUpdate(sqlString);
			result = "���״̬ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���״̬ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����״̬id��ȡ�����״̬ */
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
	/* �������״̬ */
	public String UpdateSignUpSate(SignUpSate signUpSate) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SignUpSate set ";
			sql += "stateName='" + signUpSate.getStateName() + "'";
			sql += " where stateId=" + signUpSate.getStateId();
			db.executeUpdate(sql);
			result = "���״̬���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���״̬����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
