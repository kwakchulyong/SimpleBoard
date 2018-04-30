<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Simple Board-Login</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
	<style>
	#write {
	      text-align: right;
	}
	
	</style>
	
</head>
<body>

<div class="container">
	<div class="row">
		<div class="col-md-6">
        <div class="login-box well">
<c:choose>
	<c:when test="${not empty sessionScope.userLoginInfo}">
		<legend>login Success</legend>
		<div class="form-group">
		Name : ${sessionScope.userLoginInfo.userName}<br>
		Email : <c:out value="${sessionScope.userLoginInfo.email}"/>
		</div>
		<a href="logout">logout</a>
		<br>
		<a href="boardList">board</a>
	</c:when>
	<c:otherwise>
		<form accept-charset="UTF-8" role="form" name="form1" method="post" action="loginProcess">
			<legend>Login</legend>
			<div class="form-group">
				<label for="userId">User ID</label>
				<input type="text" id="userId" name="userId" class="form-control" placeholder="User ID" >
			</div>
			<div class="form-group">
				<label for="password">Password</label>
				<input type="password" id="password" name="password" class="form-control">
			</div>
			<div class="form-group" id="write">
				<input type="submit" value="login">
				<input type="reset" value="reset">
			</div>
		
		</form>
	</c:otherwise>
</c:choose>
	</div>
	</div>
	</div>
</div>

</body>
</html>