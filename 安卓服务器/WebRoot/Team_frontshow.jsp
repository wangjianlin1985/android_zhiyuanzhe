<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Team" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Team team = (Team)request.getAttribute("team");

%>
<HTML><HEAD><TITLE>�鿴�Ŷ�</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>�û���:</td>
    <td width=70%><%=team.getTeamUserName() %></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><%=team.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=team.getEmail() %></td>
  </tr>

  <tr>
    <td width=30%>־Ը��������:</td>
    <td width=70%><%=team.getTeamName() %></td>
  </tr>

  <tr>
    <td width=30%>����ԺУ:</td>
    <td width=70%><%=team.getShoolName() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=team.getContactGroup() %></td>
  </tr>

  <tr>
    <td width=30%>���ܵ�λ:</td>
    <td width=70%><%=team.getMainUnit() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=team.getArea() %></td>
  </tr>

  <tr>
    <td width=30%>��ϸ��ַ:</td>
    <td width=70%><%=team.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>�ʱ�:</td>
    <td width=70%><%=team.getPostCode() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
        <% java.text.DateFormat birthDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=birthDateSDF.format(team.getBirthDate()) %></td>
  </tr>

  <tr>
    <td width=30%>־Ը������:</td>
    <td width=70%><%=team.getPersonNum() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�˵绰:</td>
    <td width=70%><%=team.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>����������:</td>
    <td width=70%><%=team.getChargeMan() %></td>
  </tr>

  <tr>
    <td width=30%>���������֤:</td>
    <td width=70%><%=team.getCardNumber() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
