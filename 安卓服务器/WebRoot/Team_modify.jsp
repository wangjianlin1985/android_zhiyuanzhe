<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Team" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Team team = (Team)request.getAttribute("team");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改团队</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var teamUserName = document.getElementById("team.teamUserName").value;
    if(teamUserName=="") {
        alert('请输入用户名!');
        return false;
    }
    var password = document.getElementById("team.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var email = document.getElementById("team.email").value;
    if(email=="") {
        alert('请输入电子邮箱!');
        return false;
    }
    var teamName = document.getElementById("team.teamName").value;
    if(teamName=="") {
        alert('请输入志愿团体名称!');
        return false;
    }
    var shoolName = document.getElementById("team.shoolName").value;
    if(shoolName=="") {
        alert('请输入所属院校!');
        return false;
    }
    var contactGroup = document.getElementById("team.contactGroup").value;
    if(contactGroup=="") {
        alert('请输入联络团体!');
        return false;
    }
    var mainUnit = document.getElementById("team.mainUnit").value;
    if(mainUnit=="") {
        alert('请输入主管单位!');
        return false;
    }
    var area = document.getElementById("team.area").value;
    if(area=="") {
        alert('请输入区域!');
        return false;
    }
    var address = document.getElementById("team.address").value;
    if(address=="") {
        alert('请输入详细地址!');
        return false;
    }
    var postCode = document.getElementById("team.postCode").value;
    if(postCode=="") {
        alert('请输入邮编!');
        return false;
    }
    var telephone = document.getElementById("team.telephone").value;
    if(telephone=="") {
        alert('请输入联系人电话!');
        return false;
    }
    var chargeMan = document.getElementById("team.chargeMan").value;
    if(chargeMan=="") {
        alert('请输入负责人姓名!');
        return false;
    }
    var cardNumber = document.getElementById("team.cardNumber").value;
    if(cardNumber=="") {
        alert('请输入负责人身份证!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Team/Team_ModifyTeam.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>用户名:</td>
    <td width=70%><input id="team.teamUserName" name="team.teamUserName" type="text" value="<%=team.getTeamUserName() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="team.password" name="team.password" type="text" size="20" value='<%=team.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>电子邮箱:</td>
    <td width=70%><input id="team.email" name="team.email" type="text" size="50" value='<%=team.getEmail() %>'/></td>
  </tr>

  <tr>
    <td width=30%>志愿团体名称:</td>
    <td width=70%><input id="team.teamName" name="team.teamName" type="text" size="60" value='<%=team.getTeamName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>所属院校:</td>
    <td width=70%><input id="team.shoolName" name="team.shoolName" type="text" size="20" value='<%=team.getShoolName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联络团体:</td>
    <td width=70%><input id="team.contactGroup" name="team.contactGroup" type="text" size="40" value='<%=team.getContactGroup() %>'/></td>
  </tr>

  <tr>
    <td width=30%>主管单位:</td>
    <td width=70%><input id="team.mainUnit" name="team.mainUnit" type="text" size="40" value='<%=team.getMainUnit() %>'/></td>
  </tr>

  <tr>
    <td width=30%>区域:</td>
    <td width=70%><input id="team.area" name="team.area" type="text" size="20" value='<%=team.getArea() %>'/></td>
  </tr>

  <tr>
    <td width=30%>详细地址:</td>
    <td width=70%><input id="team.address" name="team.address" type="text" size="80" value='<%=team.getAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>邮编:</td>
    <td width=70%><input id="team.postCode" name="team.postCode" type="text" size="20" value='<%=team.getPostCode() %>'/></td>
  </tr>

  <tr>
    <td width=30%>成立日期:</td>
    <% DateFormat birthDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="team.birthDate"  name="team.birthDate" onclick="setDay(this);" value='<%=birthDateSDF.format(team.getBirthDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>志愿者人数:</td>
    <td width=70%><input id="team.personNum" name="team.personNum" type="text" size="8" value='<%=team.getPersonNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系人电话:</td>
    <td width=70%><input id="team.telephone" name="team.telephone" type="text" size="20" value='<%=team.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>负责人姓名:</td>
    <td width=70%><input id="team.chargeMan" name="team.chargeMan" type="text" size="20" value='<%=team.getChargeMan() %>'/></td>
  </tr>

  <tr>
    <td width=30%>负责人身份证:</td>
    <td width=70%><input id="team.cardNumber" name="team.cardNumber" type="text" size="30" value='<%=team.getCardNumber() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
