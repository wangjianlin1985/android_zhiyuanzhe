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
<HTML><HEAD><TITLE>�޸��Ŷ�</TITLE>
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
/*��֤��*/
function checkForm() {
    var teamUserName = document.getElementById("team.teamUserName").value;
    if(teamUserName=="") {
        alert('�������û���!');
        return false;
    }
    var password = document.getElementById("team.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var email = document.getElementById("team.email").value;
    if(email=="") {
        alert('�������������!');
        return false;
    }
    var teamName = document.getElementById("team.teamName").value;
    if(teamName=="") {
        alert('������־Ը��������!');
        return false;
    }
    var shoolName = document.getElementById("team.shoolName").value;
    if(shoolName=="") {
        alert('����������ԺУ!');
        return false;
    }
    var contactGroup = document.getElementById("team.contactGroup").value;
    if(contactGroup=="") {
        alert('��������������!');
        return false;
    }
    var mainUnit = document.getElementById("team.mainUnit").value;
    if(mainUnit=="") {
        alert('���������ܵ�λ!');
        return false;
    }
    var area = document.getElementById("team.area").value;
    if(area=="") {
        alert('����������!');
        return false;
    }
    var address = document.getElementById("team.address").value;
    if(address=="") {
        alert('��������ϸ��ַ!');
        return false;
    }
    var postCode = document.getElementById("team.postCode").value;
    if(postCode=="") {
        alert('�������ʱ�!');
        return false;
    }
    var telephone = document.getElementById("team.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�˵绰!');
        return false;
    }
    var chargeMan = document.getElementById("team.chargeMan").value;
    if(chargeMan=="") {
        alert('�����븺��������!');
        return false;
    }
    var cardNumber = document.getElementById("team.cardNumber").value;
    if(cardNumber=="") {
        alert('�����븺�������֤!');
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
    <td width=30%>�û���:</td>
    <td width=70%><input id="team.teamUserName" name="team.teamUserName" type="text" value="<%=team.getTeamUserName() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="team.password" name="team.password" type="text" size="20" value='<%=team.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="team.email" name="team.email" type="text" size="50" value='<%=team.getEmail() %>'/></td>
  </tr>

  <tr>
    <td width=30%>־Ը��������:</td>
    <td width=70%><input id="team.teamName" name="team.teamName" type="text" size="60" value='<%=team.getTeamName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����ԺУ:</td>
    <td width=70%><input id="team.shoolName" name="team.shoolName" type="text" size="20" value='<%=team.getShoolName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="team.contactGroup" name="team.contactGroup" type="text" size="40" value='<%=team.getContactGroup() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���ܵ�λ:</td>
    <td width=70%><input id="team.mainUnit" name="team.mainUnit" type="text" size="40" value='<%=team.getMainUnit() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="team.area" name="team.area" type="text" size="20" value='<%=team.getArea() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϸ��ַ:</td>
    <td width=70%><input id="team.address" name="team.address" type="text" size="80" value='<%=team.getAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ʱ�:</td>
    <td width=70%><input id="team.postCode" name="team.postCode" type="text" size="20" value='<%=team.getPostCode() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat birthDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="team.birthDate"  name="team.birthDate" onclick="setDay(this);" value='<%=birthDateSDF.format(team.getBirthDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>־Ը������:</td>
    <td width=70%><input id="team.personNum" name="team.personNum" type="text" size="8" value='<%=team.getPersonNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�˵绰:</td>
    <td width=70%><input id="team.telephone" name="team.telephone" type="text" size="20" value='<%=team.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����������:</td>
    <td width=70%><input id="team.chargeMan" name="team.chargeMan" type="text" size="20" value='<%=team.getChargeMan() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���������֤:</td>
    <td width=70%><input id="team.cardNumber" name="team.cardNumber" type="text" size="30" value='<%=team.getCardNumber() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
