<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>����û�</TITLE> 
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
    var user_name = document.getElementById("userInfo.user_name").value;
    if(user_name=="") {
        alert('�������û���!');
        return false;
    }
    var password = document.getElementById("userInfo.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var name = document.getElementById("userInfo.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var sex = document.getElementById("userInfo.sex").value;
    if(sex=="") {
        alert('�������Ա�!');
        return false;
    }
    var schoolName = document.getElementById("userInfo.schoolName").value;
    if(schoolName=="") {
        alert('������ѧУѧԺ!');
        return false;
    }
    var specialInfo = document.getElementById("userInfo.specialInfo").value;
    if(specialInfo=="") {
        alert('�������꼶רҵ!');
        return false;
    }
    var telephone = document.getElementById("userInfo.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
    var address = document.getElementById("userInfo.address").value;
    if(address=="") {
        alert('��������ϵ��ַ!');
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
    <s:form action="UserInfo/UserInfo_AddUserInfo.action" method="post" id="userInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>�û���:</td>
    <td width=70%><input id="userInfo.user_name" name="userInfo.user_name" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="userInfo.password" name="userInfo.password" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="userInfo.email" name="userInfo.email" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="userInfo.name" name="userInfo.name" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="userInfo.sex" name="userInfo.sex" type="text" size="4" /></td>
  </tr>

  <tr>
    <td width=30%>�û���Ƭ:</td>
    <td width=70%><input id="userPhotoFile" name="userPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>ѧУѧԺ:</td>
    <td width=70%><input id="userInfo.schoolName" name="userInfo.schoolName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�꼶רҵ:</td>
    <td width=70%><input id="userInfo.specialInfo" name="userInfo.specialInfo" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="userInfo.nation" name="userInfo.nation" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>������ò:</td>
    <td width=70%><input id="userInfo.politicalStatus" name="userInfo.politicalStatus" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="userInfo.birthday"  name="userInfo.birthday" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>֤������:</td>
    <td width=70%><input id="userInfo.cardNumber" name="userInfo.cardNumber" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="userInfo.telephone" name="userInfo.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��ַ:</td>
    <td width=70%><input id="userInfo.address" name="userInfo.address" type="text" size="80" /></td>
  </tr>

  <tr>
    <td width=30%>����Ȥ����Ŀ:</td>
    <td width=70%><input id="userInfo.interest" name="userInfo.interest" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>���˽���:</td>
    <td width=70%><textarea id="userInfo.introduce" name="userInfo.introduce" rows="5" cols="50"></textarea></td>
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
