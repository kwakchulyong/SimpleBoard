<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>board</title>

<link rel="stylesheet" href="/webjars/bootstrap/4.1.0/dist/css/bootstrap.min.css">
<script src="/webjars/jquery/3.3.1/dist/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.1.0/dist/js/bootstrap.min.js"></script>
<style>
#write {
      text-align: right;
}

</style>

<script>
function fn_formSubmit(){
	if ( $.trim($("#brdwriter").val()) == "") {
		alert("Please input the name");
		$("#brdwriter").focus();
		return;
	}
	if ($.trim($("#brdtitle").val()) == "") {
		alert("Please input the title");
		$("#brdtitle").focus();
		return;
	}
	if ($.trim($("#brdmemo").val()) == "") {
		alert("Please input the content");
		$("#brdmemo").focus();
		return;
	}
	$("#form1").submit();
} 
</script>
</head>
<body>
	<div class="container">
	<h2>Simple Board Insert Form</h2>
	<form role="form" id="form1" name="form1" action="boardSave" method="post" enctype="multipart/form-data">
		<div class="form-group"> 
		<label for="brdwriter">Name</label> 
		<input type="text" class="form-control" placeholder="Enter Name" id="brdwriter" name="brdwriter" size="20" maxlength="20" value="<c:out value="${boardInfo.brdwriter}"/>">
		</div> 
		
		<div class="form-group"> 
		<label for="brdtitle">Title</label> 
		<input type="text" class="form-control" placeholder="Enter Title" id="brdtitle" name="brdtitle" size="70" maxlength="250" value="<c:out value="${boardInfo.brdtitle}"/>">
		
		</div> 
		
		<div class="form-group"> 
		<label for="brdmemo">Content</label> 
		<textarea class="form-control" id="brdmemo" name="brdmemo" rows="5" cols="60"><c:out value="${boardInfo.brdmemo}"/></textarea>
		
		</div> 

		<div class="form-group"> 
		<label for="exampleInputFile">File input</label> 
		<input type="file" id="exampleInputFile" name="uploadfile" multiple="" />
		</div> 
		
		<input type="hidden" name="brdno" value="<c:out value="${boardInfo.brdno}"/>"> 	
		<div id="write">
			<button type="button" class="btn btn-default" onclick="location.href='boardList'">back</button>
			<button type="submit" class="btn btn-default" onclick="fn_formSubmit(); return false;">Submit</button>
		</div>
	</form>	
	</div>
</body>
</html>
