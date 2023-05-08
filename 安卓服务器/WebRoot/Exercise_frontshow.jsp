<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Exercise" %>
<%@ page import="com.chengxusheji.domain.Team" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Team信息
    List<Team> teamList = (List<Team>)request.getAttribute("teamList");
    Exercise exercise = (Exercise)request.getAttribute("exercise");

%>
<HTML><HEAD><TITLE>查看活动</TITLE>
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
    <td width=30%>活动id:</td>
    <td width=70%><%=exercise.getExerciseId() %></td>
  </tr>

  <tr>
    <td width=30%>活动名称:</td>
    <td width=70%><%=exercise.getExerciseName() %></td>
  </tr>

  <tr>
    <td width=30%>活动日期:</td>
        <% java.text.DateFormat exerciseDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=exerciseDateSDF.format(exercise.getExerciseDate()) %></td>
  </tr>

  <tr>
    <td width=30%>服务时长:</td>
    <td width=70%><%=exercise.getServiceTime() %></td>
  </tr>

  <tr>
    <td width=30%>活动地点:</td>
    <td width=70%><%=exercise.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>参与人数:</td>
    <td width=70%><%=exercise.getPersonNum() %></td>
  </tr>

  <tr>
    <td width=30%>活动内容:</td>
    <td width=70%><%=exercise.getContent() %></td>
  </tr>

  <tr>
    <td width=30%>活动团队:</td>
    <td width=70%>
      <%=exercise.getTeamObj().getTeamName() %>
    </td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
