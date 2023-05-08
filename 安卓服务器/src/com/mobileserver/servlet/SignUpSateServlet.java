package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SignUpSateDAO;
import com.mobileserver.domain.SignUpSate;

import org.json.JSONStringer;

public class SignUpSateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�������״̬ҵ������*/
	private SignUpSateDAO signUpSateDAO = new SignUpSateDAO();

	/*Ĭ�Ϲ��캯��*/
	public SignUpSateServlet() {
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
			/*��ȡ��ѯ���״̬�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�����״̬��ѯ*/
			List<SignUpSate> signUpSateList = signUpSateDAO.QuerySignUpSate();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SignUpSates>").append("\r\n");
			for (int i = 0; i < signUpSateList.size(); i++) {
				sb.append("	<SignUpSate>").append("\r\n")
				.append("		<stateId>")
				.append(signUpSateList.get(i).getStateId())
				.append("</stateId>").append("\r\n")
				.append("		<stateName>")
				.append(signUpSateList.get(i).getStateName())
				.append("</stateName>").append("\r\n")
				.append("	</SignUpSate>").append("\r\n");
			}
			sb.append("</SignUpSates>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SignUpSate signUpSate: signUpSateList) {
				  stringer.object();
			  stringer.key("stateId").value(signUpSate.getStateId());
			  stringer.key("stateName").value(signUpSate.getStateName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������״̬����ȡ���״̬�������������浽�½������״̬���� */ 
			SignUpSate signUpSate = new SignUpSate();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			signUpSate.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			signUpSate.setStateName(stateName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = signUpSateDAO.AddSignUpSate(signUpSate);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����״̬����ȡ���״̬��״̬id*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = signUpSateDAO.DeleteSignUpSate(stateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�������״̬֮ǰ�ȸ���stateId��ѯĳ�����״̬*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			SignUpSate signUpSate = signUpSateDAO.GetSignUpSate(stateId);

			// �ͻ��˲�ѯ�����״̬���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("stateId").value(signUpSate.getStateId());
			  stringer.key("stateName").value(signUpSate.getStateName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������״̬����ȡ���״̬�������������浽�½������״̬���� */ 
			SignUpSate signUpSate = new SignUpSate();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			signUpSate.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			signUpSate.setStateName(stateName);

			/* ����ҵ���ִ�и��²��� */
			String result = signUpSateDAO.UpdateSignUpSate(signUpSate);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
