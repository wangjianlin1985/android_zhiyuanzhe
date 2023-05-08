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

	/*����ҵ������*/
	private ExerciseDAO exerciseDAO = new ExerciseDAO();

	/*Ĭ�Ϲ��캯��*/
	public ExerciseServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ��Ĳ�����Ϣ*/
			String exerciseName = request.getParameter("exerciseName");
			exerciseName = exerciseName == null ? "" : new String(request.getParameter(
					"exerciseName").getBytes("iso-8859-1"), "UTF-8");
			Timestamp exerciseDate = null;
			if (request.getParameter("exerciseDate") != null)
				exerciseDate = Timestamp.valueOf(request.getParameter("exerciseDate"));
			String teamObj = "";
			if (request.getParameter("teamObj") != null)
				teamObj = request.getParameter("teamObj");

			/*����ҵ���߼���ִ�л��ѯ*/
			List<Exercise> exerciseList = exerciseDAO.QueryExercise(exerciseName,exerciseDate,teamObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӻ����ȡ��������������浽�½��Ļ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = exerciseDAO.AddExercise(exercise);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�������ȡ��Ļid*/
			int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = exerciseDAO.DeleteExercise(exerciseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���»֮ǰ�ȸ���exerciseId��ѯĳ���*/
			int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
			Exercise exercise = exerciseDAO.GetExercise(exerciseId);

			// �ͻ��˲�ѯ�Ļ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���»����ȡ��������������浽�½��Ļ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = exerciseDAO.UpdateExercise(exercise);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
