package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ExerciseDAO;
import com.mobileserver.domain.Exercise;

import org.json.JSONStringer;

public class ExerciseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造活动业务层对象*/
	private ExerciseDAO exerciseDAO = new ExerciseDAO();

	/*默认构造函数*/
	public ExerciseServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询活动的参数信息*/
			String exerciseName = request.getParameter("exerciseName");
			exerciseName = exerciseName == null ? "" : new String(request.getParameter(
					"exerciseName").getBytes("iso-8859-1"), "UTF-8");
			Timestamp exerciseDate = null;
			if (request.getParameter("exerciseDate") != null)
				exerciseDate = Timestamp.valueOf(request.getParameter("exerciseDate"));
			String teamObj = "";
			if (request.getParameter("teamObj") != null)
				teamObj = request.getParameter("teamObj");

			/*调用业务逻辑层执行活动查询*/
			List<Exercise> exerciseList = exerciseDAO.QueryExercise(exerciseName,exerciseDate,teamObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Exercises>").append("\r\n");
			for (int i = 0; i < exerciseList.size(); i++) {
				sb.append("	<Exercise>").append("\r\n")
				.append("		<exerciseId>")
				.append(exerciseList.get(i).getExerciseId())
				.append("</exerciseId>").append("\r\n")
				.append("		<exerciseName>")
				.append(exerciseList.get(i).getExerciseName())
				.append("</exerciseName>").append("\r\n")
				.append("		<exerciseDate>")
				.append(exerciseList.get(i).getExerciseDate())
				.append("</exerciseDate>").append("\r\n")
				.append("		<serviceTime>")
				.append(exerciseList.get(i).getServiceTime())
				.append("</serviceTime>").append("\r\n")
				.append("		<address>")
				.append(exerciseList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("		<personNum>")
				.append(exerciseList.get(i).getPersonNum())
				.append("</personNum>").append("\r\n")
				.append("		<content>")
				.append(exerciseList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<teamObj>")
				.append(exerciseList.get(i).getTeamObj())
				.append("</teamObj>").append("\r\n")
				.append("	</Exercise>").append("\r\n");
			}
			sb.append("</Exercises>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Exercise exercise: exerciseList) {
				  stringer.object();
			  stringer.key("exerciseId").value(exercise.getExerciseId());
			  stringer.key("exerciseName").value(exercise.getExerciseName());
			  stringer.key("exerciseDate").value(exercise.getExerciseDate());
			  stringer.key("serviceTime").value(exercise.getServiceTime());
			  stringer.key("address").value(exercise.getAddress());
			  stringer.key("personNum").value(exercise.getPersonNum());
			  stringer.key("content").value(exercise.getContent());
			  stringer.key("teamObj").value(exercise.getTeamObj());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加活动：获取活动参数，参数保存到新建的活动对象 */ 
			Exercise exercise = new Exercise();
			int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
			exercise.setExerciseId(exerciseId);
			String exerciseName = new String(request.getParameter("exerciseName").getBytes("iso-8859-1"), "UTF-8");
			exercise.setExerciseName(exerciseName);
			Timestamp exerciseDate = Timestamp.valueOf(request.getParameter("exerciseDate"));
			exercise.setExerciseDate(exerciseDate);
			String serviceTime = new String(request.getParameter("serviceTime").getBytes("iso-8859-1"), "UTF-8");
			exercise.setServiceTime(serviceTime);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			exercise.setAddress(address);
			int personNum = Integer.parseInt(request.getParameter("personNum"));
			exercise.setPersonNum(personNum);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			exercise.setContent(content);
			String teamObj = new String(request.getParameter("teamObj").getBytes("iso-8859-1"), "UTF-8");
			exercise.setTeamObj(teamObj);

			/* 调用业务层执行添加操作 */
			String result = exerciseDAO.AddExercise(exercise);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除活动：获取活动的活动id*/
			int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
			/*调用业务逻辑层执行删除操作*/
			String result = exerciseDAO.DeleteExercise(exerciseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新活动之前先根据exerciseId查询某个活动*/
			int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
			Exercise exercise = exerciseDAO.GetExercise(exerciseId);

			// 客户端查询的活动对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("exerciseId").value(exercise.getExerciseId());
			  stringer.key("exerciseName").value(exercise.getExerciseName());
			  stringer.key("exerciseDate").value(exercise.getExerciseDate());
			  stringer.key("serviceTime").value(exercise.getServiceTime());
			  stringer.key("address").value(exercise.getAddress());
			  stringer.key("personNum").value(exercise.getPersonNum());
			  stringer.key("content").value(exercise.getContent());
			  stringer.key("teamObj").value(exercise.getTeamObj());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新活动：获取活动参数，参数保存到新建的活动对象 */ 
			Exercise exercise = new Exercise();
			int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
			exercise.setExerciseId(exerciseId);
			String exerciseName = new String(request.getParameter("exerciseName").getBytes("iso-8859-1"), "UTF-8");
			exercise.setExerciseName(exerciseName);
			Timestamp exerciseDate = Timestamp.valueOf(request.getParameter("exerciseDate"));
			exercise.setExerciseDate(exerciseDate);
			String serviceTime = new String(request.getParameter("serviceTime").getBytes("iso-8859-1"), "UTF-8");
			exercise.setServiceTime(serviceTime);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			exercise.setAddress(address);
			int personNum = Integer.parseInt(request.getParameter("personNum"));
			exercise.setPersonNum(personNum);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			exercise.setContent(content);
			String teamObj = new String(request.getParameter("teamObj").getBytes("iso-8859-1"), "UTF-8");
			exercise.setTeamObj(teamObj);

			/* 调用业务层执行更新操作 */
			String result = exerciseDAO.UpdateExercise(exercise);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
