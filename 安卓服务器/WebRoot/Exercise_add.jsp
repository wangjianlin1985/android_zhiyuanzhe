<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Team" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Team��Ϣ
    List<Team> teamList = (List<Team>)request.getAttribute("teamList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��ӻ</TITLE> 
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
    var exerciseName = document.getElementById("exercise.exerciseName").value;
    if(exerciseName=="") {
        alert('����������!');
        return false;
    }
    var serviceTime = document.getElementById("exercise.serviceTime").value;
    if(serviceTime=="") {
        alert('���������ʱ��!');
        return false;
    }
    var address = document.getElementById("exercise.address").value;
    if(address=="") {
        alert('�������ص�!');
        return false;
    }
    var content = document.getElementById("exercise.content").value;
    if(content=="") {
        alert('����������!');
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
    <TD align="left" vAlign=top >
    <s:form action="Exercise/Exercise_AddExercise.action" method="post" id="exerciseAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>�����:</td>
    <td width=70%><input id="exercise.exerciseName" name="exercise.exerciseName" type="text" size="60" /></td>
  </tr>

  <tr>
    <td width=30%>�����:</td>
    <td width=70%><input type="text" readonly id="exercise.exerciseDate"  name="exercise.exerciseDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="exercise.serviceTime" name="exercise.serviceTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ص�:</td>
    <td width=70%><input id="exercise.address" name="exercise.address" type="text" size="60" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="exercise.personNum" name="exercise.personNum" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>�����:</td>
    <td width=70%><textarea id="exercise.content" name="exercise.content" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>��Ŷ�:</td>
    <td width=70%>
      <select name="exercise.teamObj.teamUserName">
      <%
        for(Team team:teamList) {
      %>
          <option value='<%=team.getTeamUserName() %>'><%=team.getTeamName() %></option>
      <%
        }
      %>
    </td>
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
