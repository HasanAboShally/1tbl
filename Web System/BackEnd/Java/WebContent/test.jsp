<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
Registration : <br>
<form action="web/users/register" method="POST">
	UserName<input type="text" name="username" />
	Password<input type="text" name="password"/>
	Email<input type="text" name="email"/>
	FirstName<input type="text" name="firstname"/>
	LastName<input type="text" name="lastname"/>
	<input type="submit" />
</form>
<br><br>
Login:<br>
<form action="web/users/login" method="POST">
	UserName<input type="text" name="username" />
	Password<input type="text" name="password"/>
	<input type="submit" />
</body>
</html>