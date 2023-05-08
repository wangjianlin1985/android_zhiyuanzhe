package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Exercise;
import com.mobileclient.util.HttpUtil;

/*活动管理业务逻辑层*/
public class ExerciseService {
	/* 添加活动 */
	public String AddExercise(Exercise exercise) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("exerciseId", exercise.getExerciseId() + "");
		params.put("exerciseName", exercise.getExerciseName());
		params.put("exerciseDate", exercise.getExerciseDate().toString());
		params.put("serviceTime", exercise.getServiceTime());
		params.put("address", exercise.getAddress());
		params.put("personNum", exercise.getPersonNum() + "");
		params.put("content", exercise.getContent());
		params.put("teamObj", exercise.getTeamObj());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExerciseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询活动 */
	public List<Exercise> QueryExercise(Exercise queryConditionExercise) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ExerciseServlet?action=query";
		if(queryConditionExercise != null) {
			urlString += "&exerciseName=" + URLEncoder.encode(queryConditionExercise.getExerciseName(), "UTF-8") + "";
			if(queryConditionExercise.getExerciseDate() != null) {
				urlString += "&exerciseDate=" + URLEncoder.encode(queryConditionExercise.getExerciseDate().toString(), "UTF-8");
			}
			urlString += "&teamObj=" + URLEncoder.encode(queryConditionExercise.getTeamObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ExerciseListHandler exerciseListHander = new ExerciseListHandler();
		xr.setContentHandler(exerciseListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Exercise> exerciseList = exerciseListHander.getExerciseList();
		return exerciseList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Exercise exercise = new Exercise();
				exercise.setExerciseId(object.getInt("exerciseId"));
				exercise.setExerciseName(object.getString("exerciseName"));
				exercise.setExerciseDate(Timestamp.valueOf(object.getString("exerciseDate")));
				exercise.setServiceTime(object.getString("serviceTime"));
				exercise.setAddress(object.getString("address"));
				exercise.setPersonNum(object.getInt("personNum"));
				exercise.setContent(object.getString("content"));
				exercise.setTeamObj(object.getString("teamObj"));
				exerciseList.add(exercise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exerciseList;
	}

	/* 更新活动 */
	public String UpdateExercise(Exercise exercise) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("exerciseId", exercise.getExerciseId() + "");
		params.put("exerciseName", exercise.getExerciseName());
		params.put("exerciseDate", exercise.getExerciseDate().toString());
		params.put("serviceTime", exercise.getServiceTime());
		params.put("address", exercise.getAddress());
		params.put("personNum", exercise.getPersonNum() + "");
		params.put("content", exercise.getContent());
		params.put("teamObj", exercise.getTeamObj());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExerciseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除活动 */
	public String DeleteExercise(int exerciseId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("exerciseId", exerciseId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExerciseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "活动信息删除失败!";
		}
	}

	/* 根据活动id获取活动对象 */
	public Exercise GetExercise(int exerciseId)  {
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("exerciseId", exerciseId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExerciseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Exercise exercise = new Exercise();
				exercise.setExerciseId(object.getInt("exerciseId"));
				exercise.setExerciseName(object.getString("exerciseName"));
				exercise.setExerciseDate(Timestamp.valueOf(object.getString("exerciseDate")));
				exercise.setServiceTime(object.getString("serviceTime"));
				exercise.setAddress(object.getString("address"));
				exercise.setPersonNum(object.getInt("personNum"));
				exercise.setContent(object.getString("content"));
				exercise.setTeamObj(object.getString("teamObj"));
				exerciseList.add(exercise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = exerciseList.size();
		if(size>0) return exerciseList.get(0); 
		else return null; 
	}
}
