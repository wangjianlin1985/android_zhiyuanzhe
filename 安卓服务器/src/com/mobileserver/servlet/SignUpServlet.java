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

	/*构造活动报名业务层对象*/
	private SignUpDAO signUpDAO = new SignUpDAO();

	/*默认构造函数*/
	public SignUpServlet() {
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
			/*获取查询活动报名的参数信息*/
			int exerciseObj = 0;
			if (request.getParameter("exerciseObj") != null)
				exerciseObj = Integer.parseInt(request.getParameter("exerciseObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			int signUpState = 0;
			if (request.getParameter("signUpState") != null)
				signUpState = Integer.parseInt(request.getParameter("signUpState"));

			/*调用业务逻辑层执行活动报名查询*/
			List<SignUp> signUpList = signUpDAO.QuerySignUp(exerciseObj,userObj,signUpState);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加活动报名：获取活动报名参数，参数保存到新建的活动报名对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = signUpDAO.AddSignUp(signUp);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除活动报名：获取活动报名的报名id*/
			int signUpId = Integer.parseInt(request.getParameter("signUpId"));
			/*调用业务逻辑层执行删除操作*/
			String result = signUpDAO.DeleteSignUp(signUpId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新活动报名之前先根据signUpId查询某个活动报名*/
			int signUpId = Integer.parseInt(request.getParameter("signUpId"));
			SignUp signUp = signUpDAO.GetSignUp(signUpId);

			// 客户端查询的活动报名对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新活动报名：获取活动报名参数，参数保存到新建的活动报名对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = signUpDAO.UpdateSignUp(signUp);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
