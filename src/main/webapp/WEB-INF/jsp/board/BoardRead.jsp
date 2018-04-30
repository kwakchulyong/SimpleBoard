<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

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
	if ( $.trim($("#rewriter1").val()) == "") {
		alert("Please Enter the Name");
		$("#rewriter1").focus();
		return;
	}
	if ($.trim($("#rememo1").val()) == "") {
		alert("Please Enter the Content");
		$("#rememo1").focus();
		return;
	}
	$("#form1").submit();	
}

function fn_replyDelete(reno){
	if (!confirm("Will you delete this?")) {
		return;
	}
	$("#form2").attr("action", "boardReplyDelete");
	$("#reno2").val(reno);
	$("#form2").submit();	
} 

var updateReno = updateRememo = null;
function fn_replyUpdate(reno){
	hideDiv("replyDialog");
	
	$("#replyDiv").show();
	
	if (updateReno) {
		$("#replyDiv").appendTo(document.body);
		$("#reply"+updateReno).text(updateRememo);
	} 
	
	$("#reno2").val(reno);
	$("#rememo2").val($("#reply"+reno).text());
	$("#reply"+reno).text("");
	$("#replyDiv").appendTo($("#reply"+reno));
	$("#rememo2").focus();
	updateReno   = reno;
	updateRememo = $("#rememo2").val();
} 

function fn_replyUpdateSave(){
	if ($.trim($("#rememo2").val()) == "") {
		alert("Please Enter the Content");
		$("#rememo2").focus();
		return;
	}
	
	$("#form2").attr("action", "boardReplySave");
	$("#form2").submit();	
} 

function fn_replyUpdateCancel(){
	hideDiv("#replyDiv");
	
	$("#reply"+updateReno).text(updateRememo);
	updateReno = updateRememo = null;
} 

function hideDiv(id){
	$(id).hide();
	$(id).appendTo(document.body);
}

function fn_replyReply(reno){
	$("#replyDialog").show();
	
	if (updateReno) {
		fn_replyUpdateCancel();
	} 
	
	$("#reparent3").val(reno);
	$("#rememo3").val("");
	$("#replyDialog").appendTo($($("#reply"+reno)));
	$("#rewriter3").focus();
} 
function fn_replyReplyCancel(){
	hideDiv("#replyDialog");
} 

function fn_replyReplySave(){
	if ( $.trim($("#rewriter3").val()) == "") {
		alert("Please Enter the Name");
		$("#rewriter3").focus();
		return;
	}
	if ($.trim($("#rememo3").val()) == "") {
		alert("Please Enter the Content");
		$("#rememo3").focus();
		return;
	}
	$("#form3").attr("action", "boardReplySave");
	$("#form3").submit();
}
</script>

</head>
<body>
	<div class="container">
	
	<h1>Simple Board</h1>


		<table class="table table-striped table-bordered table-hover">
			<colgroup>
				<col width='15%' />
				<col width='*%' />
			</colgroup>
			<tbody>
				<tr>
					<td>Name</td> 
					<td><c:out value="${boardInfo.brdwriter}"/></td> 
				</tr>
				<tr>
					<td>Title</td> 
					<td><c:out value="${boardInfo.brdtitle}"/></td>  
				</tr>
				<tr>
					<td>Content</td> 
					<td><c:out value="${boardInfo.brdmemo}" escapeXml="false"/></td> 
				</tr>
				<tr>
					<td>Attach</td> 
					<td>
						<c:forEach var="listview" items="${listview}" varStatus="status">	
            				<a href="fileDownload?filename=<c:out value="${listview.filename}"/>&downname=<c:out value="${listview.realname }"/>"> 							 
							<c:out value="${listview.filename}"/></a> <c:out value="${listview.size2String()}"/><br/>
						</c:forEach>					
					</td> 
				</tr>
			</tbody>
		</table>    


		
		<p>&nbsp;</p>
		<%-- 
		<c:forEach var="replylist" items="${replylist}" varStatus="status">
			<div id="replyItem<c:out value="${replylist.reno}"/>" 
				 style="border: 1px solid gray; width: 600px; padding: 5px; margin-top: 5px; margin-left: <c:out value="${20*replylist.redepth}"/>px; display: inline-block">	
				<c:out value="${replylist.rewriter}"/> <c:out value="${replylist.redate}"/>
				<a href="#" onclick="fn_replyDelete('<c:out value="${replylist.reno}"/>')">delete</a>
				<a href="#" onclick="fn_replyUpdate('<c:out value="${replylist.reno}"/>')">modify</a>
				<a href="#" onclick="fn_replyReply('<c:out value="${replylist.reno}"/>')">reply</a>
				<br/>
				<div id="reply<c:out value="${replylist.reno}"/>"><c:out value="${replylist.rememo}"/></div>
			</div><br/>
		</c:forEach>
		 --%>
		
		<c:forEach var="replylist" items="${replylist}" varStatus="status">
			<div id="replyItem<c:out value="${replylist.reno}"/>" 
				 style="display: inline-block" class="form-group">	
				<c:out value="${replylist.rewriter}"/> <c:out value="${replylist.redate}"/>
				<a href="#" onclick="fn_replyDelete('<c:out value="${replylist.reno}"/>')">delete</a>
				<a href="#" onclick="fn_replyUpdate('<c:out value="${replylist.reno}"/>')">modify</a>
				<a href="#" onclick="fn_replyReply('<c:out value="${replylist.reno}"/>')">reply</a>
				<br/>
				<div id="reply<c:out value="${replylist.reno}"/>"><c:out value="${replylist.rememo}"/></div>
			</div><br/>
		</c:forEach>
		
		<div class="form-group">
			<form role="form" id="form1" name="form1" action="boardReplySave" method="post">
				<input type="hidden" id="brdno1" name="brdno" value="<c:out value="${boardInfo.brdno}"/>"> 
				Name: <input type="text" id="rewriter1" name="rewriter" class="form-control" size="20" maxlength="20"> <br/>
				Content: <textarea id="rememo1" name="rememo" class="form-control" rows="3" cols="60" maxlength="500" placeholder="Please enter the content"></textarea>
				<a href="#" onclick="fn_formSubmit()">Save reply</a>
			</form>
		</div>
		
		

		<div id="replyDiv" class="form-group" style="display:none">
			<form role="form" id="form2" name="form2" action="boardReplySave" method="post">
				<input type="hidden" id="brdno2" name="brdno" value="<c:out value="${boardInfo.brdno}"/>"> 
				<input type="hidden" id="reno2" name="reno"> 
				<textarea id="rememo2" name="rememo" class="form-control" rows="3" cols="60" maxlength="500"></textarea>
				<a href="#" onclick="fn_replyUpdateSave()">Save</a>
				<a href="#" onclick="fn_replyUpdateCancel()">Cancel</a>
			</form>
		</div>
		
		<div id="replyDialog" class="form-group" style="display:none">
			<form role="form" id="form3" name="form3" action="boardReplySave" method="post">
				<input type="hidden" id="brdno3" name="brdno" value="<c:out value="${boardInfo.brdno}"/>"> 
				<input type="hidden" id="reno3" name="reno"> 
				<input type="hidden" id="reparent3" name="reparent"> 
				Name: <input type="text" id="rewriter3" name="rewriter" class="form-control" size="20" maxlength="20"> <br/>
				<textarea id="rememo3" name="rememo" class="form-control" rows="3" cols="60" maxlength="500"></textarea>
				<a href="#" onclick="fn_replyReplySave()">Save</a>
				<a href="#" onclick="fn_replyReplyCancel()">Cancel</a>
			</form>
		</div>	
		
		<div id="write">
			<button type="button" class="btn btn-default" onclick="location.href='boardList'">List</button>
			<button type="button" class="btn btn-default" onclick="location.href='boardDelete?brdno=<c:out value="${boardInfo.brdno}"/>'">Delete</button>
			<button type="button" class="btn btn-default" onclick="location.href='boardForm?brdno=<c:out value="${boardInfo.brdno}"/>'">Modify</button>
		</div>
			
	</div>
							
</body>
</html>
