<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Simple Board</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<style>
#write {
      text-align: right;
}

</style>

<script>
function fn_formSubmit(){
	document.form1.submit();	
}
</script>
</head>
<body>
	
	<div class="container">
	
	<h1>Simple Board-UserList</h1>
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th width="25%" style="text-align: center;">userId</th> 
				<th width="25%" style="text-align: center;">password</th>
				<th width="25%" style="text-align: center;">userName</th>
				<th width="25%" style="text-align: center;">email</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="listview" items="${listview}" varStatus="status">	
										  				
				<tr>
					<td style="text-align: center;"><c:out value="${listview.userId}"/></td>
					<td style="text-align: center;"><c:out value="${listview.password}"/></td>
					<td style="text-align: center;"><c:out value="${listview.userName}"/></td>
					<td style="text-align: center;"><c:out value="${listview.email}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>
