package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SignUpDAO;
import com.mobileserver.domain.SignUp;

import org.json.JSONStringer;

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*��������ҵ������*/
	private SignUpDAO signUpDAO = new SignUpDAO();

	/*Ĭ�Ϲ��캯��*/
	public SignUpServlet() {
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
			/*��ȡ��ѯ������Ĳ�����Ϣ*/
			int exerciseObj = 0;
			if (request.getParameter("exerciseObj") != null)
				exerciseObj = Integer.parseInt(request.getParameter("exerciseObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			int signUpState = 0;
			if (request.getParameter("signUpState") != null)
				signUpState = Integer.parseInt(request.getParameter("signUpState"));

			/*����ҵ���߼���ִ�л������ѯ*/
			List<SignUp> signUpList = signUpDAO.QuerySignUp(exerciseObj,userObj,signUpState);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SignUps>").append("\r\n");
			for (int i = 0; i < signUpList.size(); i++) {
				sb.append("	<SignUp>").append("\r\n")
				.append("		<signUpId>")
				.append(signUpList.get(i).getSignUpId())
				.append("</signUpId>").append("\r\n")
				.append("		<exerciseObj>")
				.append(signUpList.get(i).getExerciseObj())
				.append("</exerciseObj>").append("\r\n")
				.append("		<userObj>")
				.append(signUpList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<signUpTime>")
				.append(signUpList.get(i).getSignUpTime())
				.append("</signUpTime>").append("\r\n")
				.append("		<signUpState>")
				.append(signUpList.get(i).getSignUpState())
				.append("</signUpState>").append("\r\n")
				.append("	</SignUp>").append("\r\n");
			}
			sb.append("</SignUps>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SignUp signUp: signUpList) {
				  stringer.object();
			  stringer.key("signUpId").value(signUp.getSignUpId());
			  stringer.key("exerciseObj").value(signUp.getExerciseObj());
			  stringer.key("userObj").value(signUp.getUserObj());
			  stringer.key("signUpTime").value(signUp.getSignUpTime());
			  stringer.key("signUpState").value(signUp.getSignUpState());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӻ��������ȡ������������������浽�½��Ļ�������� */ 
			SignUp signUp = new SignUp();
			int signUpId = Integer.parseInt(request.getParameter("signUpId"));
			signUp.setSignUpId(signUpId);
			int exerciseObj = Integer.parseInt(request.getParameter("exerciseObj"));
			signUp.setExerciseObj(exerciseObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			signUp.setUserObj(userObj);
			String signUpTime = new String(request.getParameter("signUpTime").getBytes("iso-8859-1"), "UTF-8");
			signUp.setSignUpTime(signUpTime);
			int signUpState = Integer.parseInt(request.getParameter("signUpState"));
			signUp.setSignUpState(signUpState);

			/* ����ҵ���ִ����Ӳ��� */
			String result = signUpDAO.AddSignUp(signUp);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����������ȡ������ı���id*/
			int signUpId = Integer.parseInt(request.getParameter("signUpId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = signUpDAO.DeleteSignUp(signUpId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���»����֮ǰ�ȸ���signUpId��ѯĳ�������*/
			int signUpId = Integer.parseInt(request.getParameter("signUpId"));
			SignUp signUp = signUpDAO.GetSignUp(signUpId);

			// �ͻ��˲�ѯ�Ļ�������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("signUpId").value(signUp.getSignUpId());
			  stringer.key("exerciseObj").value(signUp.getExerciseObj());
			  stringer.key("userObj").value(signUp.getUserObj());
			  stringer.key("signUpTime").value(signUp.getSignUpTime());
			  stringer.key("signUpState").value(signUp.getSignUpState());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���»��������ȡ������������������浽�½��Ļ�������� */ 
			SignUp signUp = new SignUp();
			int signUpId = Integer.parseInt(request.getParameter("signUpId"));
			signUp.setSignUpId(signUpId);
			int exerciseObj = Integer.parseInt(request.getParameter("exerciseObj"));
			signUp.setExerciseObj(exerciseObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			signUp.setUserObj(userObj);
			String signUpTime = new String(request.getParameter("signUpTime").getBytes("iso-8859-1"), "UTF-8");
			signUp.setSignUpTime(signUpTime);
			int signUpState = Integer.parseInt(request.getParameter("signUpState"));
			signUp.setSignUpState(signUpState);

			/* ����ҵ���ִ�и��²��� */
			String result = signUpDAO.UpdateSignUp(signUp);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
