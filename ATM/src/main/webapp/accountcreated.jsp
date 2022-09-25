<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Thank you for creating a new account <%= request.getParameter("Fname") %></h3>
	<h4>To go to login page click below</h4>
	<a href="index.html"><input type="button" value="login"></a>
</body>
</html>