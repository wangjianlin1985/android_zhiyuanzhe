package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SignUpSate;
import com.mobileclient.util.HttpUtil;

/*审核状态管理业务逻辑层*/
public class SignUpSateService {
	/* 添加审核状态 */
	public String AddSignUpSate(SignUpSate signUpSate) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", signUpSate.getStateId() + "");
		params.put("stateName", signUpSate.getStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SignUpSateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询审核状态 */
	public List<SignUpSate> QuerySignUpSate(SignUpSate queryConditionSignUpSate) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SignUpSateServlet?action=query";
		if(queryConditionSignUpSate != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SignUpSateListHandler signUpSateListHander = new SignUpSateListHandler();
		xr.setContentHandler(signUpSateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SignUpSate> signUpSateList = signUpSateListHander.getSignUpSateList();
		return signUpSateList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SignUpSate> signUpSateList = new ArrayList<SignUpSate>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SignUpSate signUpSate = new SignUpSate();
				signUpSate.setStateId(object.getInt("stateId"));
				signUpSate.setStateName(object.getString("stateName"));
				signUpSateList.add(signUpSate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signUpSateList;
	}

	/* 更新审核状态 */
	public String UpdateSignUpSate(SignUpSate signUpSate) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", signUpSate.getStateId() + "");
		params.put("stateName", signUpSate.getStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SignUpSateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除审核状态 */
	public String DeleteSignUpSate(int stateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SignUpSateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "审核状态信息删除失败!";
		}
	}

	/* 根据状态id获取审核状态对象 */
	public SignUpSate GetSignUpSate(int stateId)  {
		List<SignUpSate> signUpSateList = new ArrayList<SignUpSate>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SignUpSateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SignUpSate signUpSate = new SignUpSate();
				signUpSate.setStateId(object.getInt("stateId"));
				signUpSate.setStateName(object.getString("stateName"));
				signUpSateList.add(signUpSate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = signUpSateList.size();
		if(size>0) return signUpSateList.get(0); 
		else return null; 
	}
}
