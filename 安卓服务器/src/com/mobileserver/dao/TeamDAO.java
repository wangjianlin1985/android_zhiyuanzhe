package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Team;
import com.mobileserver.util.DB;

public class TeamDAO {

	public List<Team> QueryTeam(String teamUserName,String email,String teamName,String shoolName,String contactGroup,String mainUnit,String area,String telephone,String chargeMan) {
		List<Team> teamList = new ArrayList<Team>();
		DB db = new DB();
		String sql = "select * from Team where 1=1";
		if (!teamUserName.equals(""))
			sql += " and teamUserName like '%" + teamUserName + "%'";
		if (!email.equals(""))
			sql += " and email like '%" + email + "%'";
		if (!teamName.equals(""))
			sql += " and teamName like '%" + teamName + "%'";
		if (!shoolName.equals(""))
			sql += " and shoolName like '%" + shoolName + "%'";
		if (!contactGroup.equals(""))
			sql += " and contactGroup like '%" + contactGroup + "%'";
		if (!mainUnit.equals(""))
			sql += " and mainUnit like '%" + mainUnit + "%'";
		if (!area.equals(""))
			sql += " and area like '%" + area + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		if (!chargeMan.equals(""))
			sql += " and chargeMan like '%" + chargeMan + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Team team = new Team();
				team.setTeamUserName(rs.getString("teamUserName"));
				team.setPassword(rs.getString("password"));
				team.setEmail(rs.getString("email"));
				team.setTeamName(rs.getString("teamName"));
				team.setShoolName(rs.getString("shoolName"));
				team.setContactGroup(rs.getString("contactGroup"));
				team.setMainUnit(rs.getString("mainUnit"));
				team.setArea(rs.getString("area"));
				team.setAddress(rs.getString("address"));
				team.setPostCode(rs.getString("postCode"));
				team.setBirthDate(rs.getTimestamp("birthDate"));
				team.setPersonNum(rs.getInt("personNum"));
				team.setTelephone(rs.getString("telephone"));
				team.setChargeMan(rs.getString("chargeMan"));
				team.setCardNumber(rs.getString("cardNumber"));
				teamList.add(team);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return teamList;
	}
	/* 传入团队对象，进行团队的添加业务 */
	public String AddTeam(Team team) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新团队 */
			String sqlString = "insert into Team(teamUserName,password,email,teamName,shoolName,contactGroup,mainUnit,area,address,postCode,birthDate,personNum,telephone,chargeMan,cardNumber) values (";
			sqlString += "'" + team.getTeamUserName() + "',";
			sqlString += "'" + team.getPassword() + "',";
			sqlString += "'" + team.getEmail() + "',";
			sqlString += "'" + team.getTeamName() + "',";
			sqlString += "'" + team.getShoolName() + "',";
			sqlString += "'" + team.getContactGroup() + "',";
			sqlString += "'" + team.getMainUnit() + "',";
			sqlString += "'" + team.getArea() + "',";
			sqlString += "'" + team.getAddress() + "',";
			sqlString += "'" + team.getPostCode() + "',";
			sqlString += "'" + team.getBirthDate() + "',";
			sqlString += team.getPersonNum() + ",";
			sqlString += "'" + team.getTelephone() + "',";
			sqlString += "'" + team.getChargeMan() + "',";
			sqlString += "'" + team.getCardNumber() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "团队添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "团队添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除团队 */
	public String DeleteTeam(String teamUserName) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Team where teamUserName='" + teamUserName + "'";
			db.executeUpdate(sqlString);
			result = "团队删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "团队删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据用户名获取到团队 */
	public Team GetTeam(String teamUserName) {
		Team team = null;
		DB db = new DB();
		String sql = "select * from Team where teamUserName='" + teamUserName + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				team = new Team();
				team.setTeamUserName(rs.getString("teamUserName"));
				team.setPassword(rs.getString("password"));
				team.setEmail(rs.getString("email"));
				team.setTeamName(rs.getString("teamName"));
				team.setShoolName(rs.getString("shoolName"));
				team.setContactGroup(rs.getString("contactGroup"));
				team.setMainUnit(rs.getString("mainUnit"));
				team.setArea(rs.getString("area"));
				team.setAddress(rs.getString("address"));
				team.setPostCode(rs.getString("postCode"));
				team.setBirthDate(rs.getTimestamp("birthDate"));
				team.setPersonNum(rs.getInt("personNum"));
				team.setTelephone(rs.getString("telephone"));
				team.setChargeMan(rs.getString("chargeMan"));
				team.setCardNumber(rs.getString("cardNumber"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return team;
	}
	/* 更新团队 */
	public String UpdateTeam(Team team) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Team set ";
			sql += "password='" + team.getPassword() + "',";
			sql += "email='" + team.getEmail() + "',";
			sql += "teamName='" + team.getTeamName() + "',";
			sql += "shoolName='" + team.getShoolName() + "',";
			sql += "contactGroup='" + team.getContactGroup() + "',";
			sql += "mainUnit='" + team.getMainUnit() + "',";
			sql += "area='" + team.getArea() + "',";
			sql += "address='" + team.getAddress() + "',";
			sql += "postCode='" + team.getPostCode() + "',";
			sql += "birthDate='" + team.getBirthDate() + "',";
			sql += "personNum=" + team.getPersonNum() + ",";
			sql += "telephone='" + team.getTelephone() + "',";
			sql += "chargeMan='" + team.getChargeMan() + "',";
			sql += "cardNumber='" + team.getCardNumber() + "'";
			sql += " where teamUserName='" + team.getTeamUserName() + "'";
			db.executeUpdate(sql);
			result = "团队更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "团队更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
