<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
</head>

<body>
<form id="form1" name="form1" method="post" action="servlet/LoginServlet">
  <p>用户名:
    <label for="textfield"></label>
  <input type="text" name="uname" id="textfield" />
  </p>
  <p> 密 码 	    
    ：   
    <label for="textfield2"></label>
    <input type="password" name="upwd" id="textfield2" />
  </p>
  <p>
    <input type="submit" name="button" id="button" value="登录" />
  </p>
</form>
</body>
</html>
