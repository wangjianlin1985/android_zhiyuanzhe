<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.SignUp" %>
<%@ page import="com.chengxusheji.domain.Exercise" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.SignUpSate" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Exercise��Ϣ
    List<Exercise> exerciseList = (List<Exercise>)request.getAttribute("exerciseList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�SignUpSate��Ϣ
    List<SignUpSate> signUpSateList = (List<SignUpSate>)request.getAttribute("signUpSateList");
    SignUp signUp = (SignUp)request.getAttribute("signUp");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ļ����</TITLE>
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
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="SignUp/SignUp_ModifySignUp.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><input id="signUp.signUpId" name="signUp.signUpId" type="text" value="<%=signUp.getSignUpId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�Ŷӻ:</td>
    <td width=70%>
      <select name="signUp.exerciseObj.exerciseId">
      <%
        for(Exercise exercise:exerciseList) {
          String selected = "";
          if(exercise.getExerciseId() == signUp.getExerciseObj().getExerciseId())
            selected = "selected";
      %>
          <option value='<%=exercise.getExerciseId() %>' <%=selected %>><%=exercise.getExerciseName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�����û�:</td>
    <td width=70%>
      <select name="signUp.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(signUp.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="signUp.signUpTime" name="signUp.signUpTime" type="text" size="30" value='<%=signUp.getSignUpTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���״̬:</td>
    <td width=70%>
      <select name="signUp.signUpState.stateId">
      <%
        for(SignUpSate signUpSate:signUpSateList) {
          String selected = "";
          if(signUpSate.getStateId() == signUp.getSignUpState().getStateId())
            selected = "selected";
      %>
          <option value='<%=signUpSate.getStateId() %>' <%=selected %>><%=signUpSate.getStateName() %></option>
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
