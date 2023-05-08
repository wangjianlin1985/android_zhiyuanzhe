package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TeamDAO;
import com.mobileserver.domain.Team;

import org.json.JSONStringer;

public class TeamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造团队业务层对象*/
	private TeamDAO teamDAO = new TeamDAO();

	/*默认构造函数*/
	public TeamServlet() {
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
			/*获取查询团队的参数信息*/
			String teamUserName = request.getParameter("teamUserName");
			teamUserName = teamUserName == null ? "" : new String(request.getParameter(
					"teamUserName").getBytes("iso-8859-1"), "UTF-8");
			String email = request.getParameter("email");
			email = email == null ? "" : new String(request.getParameter(
					"email").getBytes("iso-8859-1"), "UTF-8");
			String teamName = request.getParameter("teamName");
			teamName = teamName == null ? "" : new String(request.getParameter(
					"teamName").getBytes("iso-8859-1"), "UTF-8");
			String shoolName = request.getParameter("shoolName");
			shoolName = shoolName == null ? "" : new String(request.getParameter(
					"shoolName").getBytes("iso-8859-1"), "UTF-8");
			String contactGroup = request.getParameter("contactGroup");
			contactGroup = contactGroup == null ? "" : new String(request.getParameter(
					"contactGroup").getBytes("iso-8859-1"), "UTF-8");
			String mainUnit = request.getParameter("mainUnit");
			mainUnit = mainUnit == null ? "" : new String(request.getParameter(
					"mainUnit").getBytes("iso-8859-1"), "UTF-8");
			String area = request.getParameter("area");
			area = area == null ? "" : new String(request.getParameter(
					"area").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");
			String chargeMan = request.getParameter("chargeMan");
			chargeMan = chargeMan == null ? "" : new String(request.getParameter(
					"chargeMan").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行团队查询*/
			List<Team> teamList = teamDAO.QueryTeam(teamUserName,email,teamName,shoolName,contactGroup,mainUnit,area,telephone,chargeMan);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Teams>").append("\r\n");
			for (int i = 0; i < teamList.size(); i++) {
				sb.append("	<Team>").append("\r\n")
				.append("		<teamUserName>")
				.append(teamList.get(i).getTeamUserName())
				.append("</teamUserName>").append("\r\n")
				.append("		<password>")
				.append(teamList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<email>")
				.append(teamList.get(i).getEmail())
				.append("</email>").append("\r\n")
				.append("		<teamName>")
				.append(teamList.get(i).getTeamName())
				.append("</teamName>").append("\r\n")
				.append("		<shoolName>")
				.append(teamList.get(i).getShoolName())
				.append("</shoolName>").append("\r\n")
				.append("		<contactGroup>")
				.append(teamList.get(i).getContactGroup())
				.append("</contactGroup>").append("\r\n")
				.append("		<mainUnit>")
				.append(teamList.get(i).getMainUnit())
				.append("</mainUnit>").append("\r\n")
				.append("		<area>")
				.append(teamList.get(i).getArea())
				.append("</area>").append("\r\n")
				.append("		<address>")
				.append(teamList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("		<postCode>")
				.append(teamList.get(i).getPostCode())
				.append("</postCode>").append("\r\n")
				.append("		<birthDate>")
				.append(teamList.get(i).getBirthDate())
				.append("</birthDate>").append("\r\n")
				.append("		<personNum>")
				.append(teamList.get(i).getPersonNum())
				.append("</personNum>").append("\r\n")
				.append("		<telephone>")
				.append(teamList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<chargeMan>")
				.append(teamList.get(i).getChargeMan())
				.append("</chargeMan>").append("\r\n")
				.append("		<cardNumber>")
				.append(teamList.get(i).getCardNumber())
				.append("</cardNumber>").append("\r\n")
				.append("	</Team>").append("\r\n");
			}
			sb.append("</Teams>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Team team: teamList) {
				  stringer.object();
			  stringer.key("teamUserName").value(team.getTeamUserName());
			  stringer.key("password").value(team.getPassword());
			  stringer.key("email").value(team.getEmail());
			  stringer.key("teamName").value(team.getTeamName());
			  stringer.key("shoolName").value(team.getShoolName());
			  stringer.key("contactGroup").value(team.getContactGroup());
			  stringer.key("mainUnit").value(team.getMainUnit());
			  stringer.key("area").value(team.getArea());
			  stringer.key("address").value(team.getAddress());
			  stringer.key("postCode").value(team.getPostCode());
			  stringer.key("birthDate").value(team.getBirthDate());
			  stringer.key("personNum").value(team.getPersonNum());
			  stringer.key("telephone").value(team.getTelephone());
			  stringer.key("chargeMan").value(team.getChargeMan());
			  stringer.key("cardNumber").value(team.getCardNumber());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加团队：获取团队参数，参数保存到新建的团队对象 */ 
			Team team = new Team();
			String teamUserName = new String(request.getParameter("teamUserName").getBytes("iso-8859-1"), "UTF-8");
			team.setTeamUserName(teamUserName);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			team.setPassword(password);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			team.setEmail(email);
			String teamName = new String(request.getParameter("teamName").getBytes("iso-8859-1"), "UTF-8");
			team.setTeamName(teamName);
			String shoolName = new String(request.getParameter("shoolName").getBytes("iso-8859-1"), "UTF-8");
			team.setShoolName(shoolName);
			String contactGroup = new String(request.getParameter("contactGroup").getBytes("iso-8859-1"), "UTF-8");
			team.setContactGroup(contactGroup);
			String mainUnit = new String(request.getParameter("mainUnit").getBytes("iso-8859-1"), "UTF-8");
			team.setMainUnit(mainUnit);
			String area = new String(request.getParameter("area").getBytes("iso-8859-1"), "UTF-8");
			team.setArea(area);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			team.setAddress(address);
			String postCode = new String(request.getParameter("postCode").getBytes("iso-8859-1"), "UTF-8");
			team.setPostCode(postCode);
			Timestamp birthDate = Timestamp.valueOf(request.getParameter("birthDate"));
			team.setBirthDate(birthDate);
			int personNum = Integer.parseInt(request.getParameter("personNum"));
			team.setPersonNum(personNum);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			team.setTelephone(telephone);
			String chargeMan = new String(request.getParameter("chargeMan").getBytes("iso-8859-1"), "UTF-8");
			team.setChargeMan(chargeMan);
			String cardNumber = new String(request.getParameter("cardNumber").getBytes("iso-8859-1"), "UTF-8");
			team.setCardNumber(cardNumber);

			/* 调用业务层执行添加操作 */
			String result = teamDAO.AddTeam(team);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除团队：获取团队的用户名*/
			String teamUserName = new String(request.getParameter("teamUserName").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = teamDAO.DeleteTeam(teamUserName);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新团队之前先根据teamUserName查询某个团队*/
			String teamUserName = new String(request.getParameter("teamUserName").getBytes("iso-8859-1"), "UTF-8");
			Team team = teamDAO.GetTeam(teamUserName);

			// 客户端查询的团队对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("teamUserName").value(team.getTeamUserName());
			  stringer.key("password").value(team.getPassword());
			  stringer.key("email").value(team.getEmail());
			  stringer.key("teamName").value(team.getTeamName());
			  stringer.key("shoolName").value(team.getShoolName());
			  stringer.key("contactGroup").value(team.getContactGroup());
			  stringer.key("mainUnit").value(team.getMainUnit());
			  stringer.key("area").value(team.getArea());
			  stringer.key("address").value(team.getAddress());
			  stringer.key("postCode").value(team.getPostCode());
			  stringer.key("birthDate").value(team.getBirthDate());
			  stringer.key("personNum").value(team.getPersonNum());
			  stringer.key("telephone").value(team.getTelephone());
			  stringer.key("chargeMan").value(team.getChargeMan());
			  stringer.key("cardNumber").value(team.getCardNumber());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新团队：获取团队参数，参数保存到新建的团队对象 */ 
			Team team = new Team();
			String teamUserName = new String(request.getParameter("teamUserName").getBytes("iso-8859-1"), "UTF-8");
			team.setTeamUserName(teamUserName);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			team.setPassword(password);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			team.setEmail(email);
			String teamName = new String(request.getParameter("teamName").getBytes("iso-8859-1"), "UTF-8");
			team.setTeamName(teamName);
			String shoolName = new String(request.getParameter("shoolName").getBytes("iso-8859-1"), "UTF-8");
			team.setShoolName(shoolName);
			String contactGroup = new String(request.getParameter("contactGroup").getBytes("iso-8859-1"), "UTF-8");
			team.setContactGroup(contactGroup);
			String mainUnit = new String(request.getParameter("mainUnit").getBytes("iso-8859-1"), "UTF-8");
			team.setMainUnit(mainUnit);
			String area = new String(request.getParameter("area").getBytes("iso-8859-1"), "UTF-8");
			team.setArea(area);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			team.setAddress(address);
			String postCode = new String(request.getParameter("postCode").getBytes("iso-8859-1"), "UTF-8");
			team.setPostCode(postCode);
			Timestamp birthDate = Timestamp.valueOf(request.getParameter("birthDate"));
			team.setBirthDate(birthDate);
			int personNum = Integer.parseInt(request.getParameter("personNum"));
			team.setPersonNum(personNum);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			team.setTelephone(telephone);
			String chargeMan = new String(request.getParameter("chargeMan").getBytes("iso-8859-1"), "UTF-8");
			team.setChargeMan(chargeMan);
			String cardNumber = new String(request.getParameter("cardNumber").getBytes("iso-8859-1"), "UTF-8");
			team.setCardNumber(cardNumber);

			/* 调用业务层执行更新操作 */
			String result = teamDAO.UpdateTeam(team);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
