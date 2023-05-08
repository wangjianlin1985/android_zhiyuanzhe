package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SignUp;
import com.mobileclient.util.HttpUtil;

/*活动报名管理业务逻辑层*/
public class SignUpService {
	/* 添加活动报名 */
	public String AddSignUp(SignUp signUp) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("signUpId", signUp.getSignUpId() + "");
		params.put("exerciseObj", signUp.getExerciseObj() + "");
		params.put("userObj", signUp.getUserObj());
		params.put("signUpTime", signUp.getSignUpTime());
		params.put("signUpState", signUp.getSignUpState() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SignUpServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询活动报名 */
	public List<SignUp> QuerySignUp(SignUp queryConditionSignUp) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SignUpServlet?action=query";
		if(queryConditionSignUp != null) {
			urlString += "&exerciseObj=" + queryConditionSignUp.getExerciseObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionSignUp.getUserObj(), "UTF-8") + "";
			urlString += "&signUpState=" + queryConditionSignUp.getSignUpState();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SignUpListHandler signUpListHander = new SignUpListHandler();
		xr.setContentHandler(signUpListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SignUp> signUpList = signUpListHander.getSignUpList();
		return signUpList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SignUp> signUpList = new ArrayList<SignUp>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SignUp signUp = new SignUp();
				signUp.setSignUpId(object.getInt("signUpId"));
				signUp.setExerciseObj(object.getInt("exerciseObj"));
				signUp.setUserObj(object.getString("userObj"));
				signUp.setSignUpTime(object.getString("signUpTime"));
				signUp.setSignUpState(object.getInt("signUpState"));
				signUpList.add(signUp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signUpList;
	}

	/* 更新活动报名 */
	public String UpdateSignUp(SignUp signUp) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("signUpId", signUp.getSignUpId() + "");
		params.put("exerciseObj", signUp.getExerciseObj() + "");
		params.put("userObj", signUp.getUserObj());
		params.put("signUpTime", signUp.getSignUpTime());
		params.put("signUpState", signUp.getSignUpState() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SignUpServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除活动报名 */
	public String DeleteSignUp(int signUpId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("signUpId", signUpId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SignUpServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "活动报名信息删除失败!";
		}
	}

	/* 根据报名id获取活动报名对象 */
	public SignUp GetSignUp(int signUpId)  {
		List<SignUp> signUpList = new ArrayList<SignUp>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("signUpId", signUpId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SignUpServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SignUp signUp = new SignUp();
				signUp.setSignUpId(object.getInt("signUpId"));
				signUp.setExerciseObj(object.getInt("exerciseObj"));
				signUp.setUserObj(object.getString("userObj"));
				signUp.setSignUpTime(object.getString("signUpTime"));
				signUp.setSignUpState(object.getInt("signUpState"));
				signUpList.add(signUp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = signUpList.size();
		if(size>0) return signUpList.get(0); 
		else return null; 
	}
}
