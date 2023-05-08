<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Exercise" %>
<%@ page import="com.chengxusheji.domain.Team" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Team信息
    List<Team> teamList = (List<Team>)request.getAttribute("teamList");
    Exercise exercise = (Exercise)request.getAttribute("exercise");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改活动</TITLE>
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
    var exerciseName = document.getElementById("exercise.exerciseName").value;
    if(exerciseName=="") {
        alert('请输入活动名称!');
        return false;
    }
    var serviceTime = document.getElementById("exercise.serviceTime").value;
    if(serviceTime=="") {
        alert('请输入服务时长!');
        return false;
    }
    var address = document.getElementById("exercise.address").value;
    if(address=="") {
        alert('请输入活动地点!');
        return false;
    }
    var content = document.getElementById("exercise.content").value;
    if(content=="") {
        alert('请输入活动内容!');
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
    <TD align="left" vAlign=top ><s:form action="Exercise/Exercise_ModifyExercise.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>活动id:</td>
    <td width=70%><input id="exercise.exerciseId" name="exercise.exerciseId" type="text" value="<%=exercise.getExerciseId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>活动名称:</td>
    <td width=70%><input id="exercise.exerciseName" name="exercise.exerciseName" type="text" size="60" value='<%=exercise.getExerciseName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>活动日期:</td>
    <% DateFormat exerciseDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="exercise.exerciseDate"  name="exercise.exerciseDate" onclick="setDay(this);" value='<%=exerciseDateSDF.format(exercise.getExerciseDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>服务时长:</td>
    <td width=70%><input id="exercise.serviceTime" name="exercise.serviceTime" type="text" size="20" value='<%=exercise.getServiceTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>活动地点:</td>
    <td width=70%><input id="exercise.address" name="exercise.address" type="text" size="60" value='<%=exercise.getAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>参与人数:</td>
    <td width=70%><input id="exercise.personNum" name="exercise.personNum" type="text" size="8" value='<%=exercise.getPersonNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>活动内容:</td>
    <td width=70%><textarea id="exercise.content" name="exercise.content" rows=5 cols=50><%=exercise.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>活动团队:</td>
    <td width=70%>
      <select name="exercise.teamObj.teamUserName">
      <%
        for(Team team:teamList) {
          String selected = "";
          if(team.getTeamUserName().equals(exercise.getTeamObj().getTeamUserName()))
            selected = "selected";
      %>
          <option value='<%=team.getTeamUserName() %>' <%=selected %>><%=team.getTeamName() %></option>
      <%
        }
      %>
    </td>
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
