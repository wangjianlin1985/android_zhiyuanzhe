package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Team;
import com.mobileclient.util.HttpUtil;

/*团队管理业务逻辑层*/
public class TeamService {
	/* 添加团队 */
	public String AddTeam(Team team) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("teamUserName", team.getTeamUserName());
		params.put("password", team.getPassword());
		params.put("email", team.getEmail());
		params.put("teamName", team.getTeamName());
		params.put("shoolName", team.getShoolName());
		params.put("contactGroup", team.getContactGroup());
		params.put("mainUnit", team.getMainUnit());
		params.put("area", team.getArea());
		params.put("address", team.getAddress());
		params.put("postCode", team.getPostCode());
		params.put("birthDate", team.getBirthDate().toString());
		params.put("personNum", team.getPersonNum() + "");
		params.put("telephone", team.getTelephone());
		params.put("chargeMan", team.getChargeMan());
		params.put("cardNumber", team.getCardNumber());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TeamServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询团队 */
	public List<Team> QueryTeam(Team queryConditionTeam) throws Exception {
		String urlString = HttpUtil.BASE_URL + "TeamServlet?action=query";
		if(queryConditionTeam != null) {
			urlString += "&teamUserName=" + URLEncoder.encode(queryConditionTeam.getTeamUserName(), "UTF-8") + "";
			urlString += "&email=" + URLEncoder.encode(queryConditionTeam.getEmail(), "UTF-8") + "";
			urlString += "&teamName=" + URLEncoder.encode(queryConditionTeam.getTeamName(), "UTF-8") + "";
			urlString += "&shoolName=" + URLEncoder.encode(queryConditionTeam.getShoolName(), "UTF-8") + "";
			urlString += "&contactGroup=" + URLEncoder.encode(queryConditionTeam.getContactGroup(), "UTF-8") + "";
			urlString += "&mainUnit=" + URLEncoder.encode(queryConditionTeam.getMainUnit(), "UTF-8") + "";
			urlString += "&area=" + URLEncoder.encode(queryConditionTeam.getArea(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionTeam.getTelephone(), "UTF-8") + "";
			urlString += "&chargeMan=" + URLEncoder.encode(queryConditionTeam.getChargeMan(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		TeamListHandler teamListHander = new TeamListHandler();
		xr.setContentHandler(teamListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Team> teamList = teamListHander.getTeamList();
		return teamList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Team> teamList = new ArrayList<Team>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Team team = new Team();
				team.setTeamUserName(object.getString("teamUserName"));
				team.setPassword(object.getString("password"));
				team.setEmail(object.getString("email"));
				team.setTeamName(object.getString("teamName"));
				team.setShoolName(object.getString("shoolName"));
				team.setContactGroup(object.getString("contactGroup"));
				team.setMainUnit(object.getString("mainUnit"));
				team.setArea(object.getString("area"));
				team.setAddress(object.getString("address"));
				team.setPostCode(object.getString("postCode"));
				team.setBirthDate(Timestamp.valueOf(object.getString("birthDate")));
				team.setPersonNum(object.getInt("personNum"));
				team.setTelephone(object.getString("telephone"));
				team.setChargeMan(object.getString("chargeMan"));
				team.setCardNumber(object.getString("cardNumber"));
				teamList.add(team);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teamList;
	}

	/* 更新团队 */
	public String UpdateTeam(Team team) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("teamUserName", team.getTeamUserName());
		params.put("password", team.getPassword());
		params.put("email", team.getEmail());
		params.put("teamName", team.getTeamName());
		params.put("shoolName", team.getShoolName());
		params.put("contactGroup", team.getContactGroup());
		params.put("mainUnit", team.getMainUnit());
		params.put("area", team.getArea());
		params.put("address", team.getAddress());
		params.put("postCode", team.getPostCode());
		params.put("birthDate", team.getBirthDate().toString());
		params.put("personNum", team.getPersonNum() + "");
		params.put("telephone", team.getTelephone());
		params.put("chargeMan", team.getChargeMan());
		params.put("cardNumber", team.getCardNumber());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TeamServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除团队 */
	public String DeleteTeam(String teamUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("teamUserName", teamUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TeamServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "团队信息删除失败!";
		}
	}

	/* 根据用户名获取团队对象 */
	public Team GetTeam(String teamUserName)  {
		List<Team> teamList = new ArrayList<Team>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("teamUserName", teamUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TeamServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Team team = new Team();
				team.setTeamUserName(object.getString("teamUserName"));
				team.setPassword(object.getString("password"));
				team.setEmail(object.getString("email"));
				team.setTeamName(object.getString("teamName"));
				team.setShoolName(object.getString("shoolName"));
				team.setContactGroup(object.getString("contactGroup"));
				team.setMainUnit(object.getString("mainUnit"));
				team.setArea(object.getString("area"));
				team.setAddress(object.getString("address"));
				team.setPostCode(object.getString("postCode"));
				team.setBirthDate(Timestamp.valueOf(object.getString("birthDate")));
				team.setPersonNum(object.getInt("personNum"));
				team.setTelephone(object.getString("telephone"));
				team.setChargeMan(object.getString("chargeMan"));
				team.setCardNumber(object.getString("cardNumber"));
				teamList.add(team);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = teamList.size();
		if(size>0) return teamList.get(0); 
		else return null; 
	}
}
