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
	
	<h1>Simple Board</h1>
	<div id="write">
		<button type="button" class="btn btn-default" onclick="location.href='boardForm'">write</button>
	</div>
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th width="6%" style="text-align: center;">No</th> 
				<th width="50%">Name</th>
				<th width="14%" style="text-align: center;">Register</th>
				<th width="10%" style="text-align: center;">Reg-Date</th>
				<th width="10%" style="text-align: center;">Counter</th>
				<th width="10%" style="text-align: center;">Attach</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="listview" items="${listview}" varStatus="status">	
				<c:url var="link" value="boardRead">
					<c:param name="brdno" value="${listview.brdno}" />
				</c:url>		
										  				
				<tr>
					<td style="text-align: center;"><c:out value="${searchVO.totRow-((searchVO.page-1)*searchVO.displayRowCount + status.index)}"/></td>
					<td>
						<a href="${link}"><c:out value="${listview.brdtitle}"/></a>
						<c:if test="${listview.replycnt>0}">
							(<c:out value="${listview.replycnt}"/>)
						</c:if>						
					</td>
					<td><c:out value="${listview.brdwriter}"/></td>
					<td style="text-align: center;"><c:out value="${listview.brddate}"/></td>
					<td style="text-align: center;"><c:out value="${listview.brdhit}"/></td>
					<td style="text-align: center;"><c:out value="${listview.filecnt}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<hr/>
	<form id="form1" name="form1"  method="post">
	<jsp:include page="/WEB-INF/jsp/common/pagingforSubmit.jsp" />
	
	<div class="row" >
	  <div class="col-lg-6">
	  </div>
	
	  <div class="col-lg-6">
	    <div class="input-group">
	      <span class="input-group-addon">
	        <input type="checkbox" aria-label="Checkbox for following text input" name="searchType" value="brdtitle" <c:if test="${fn:indexOf(searchVO.searchType, 'brdtitle')!=-1}">checked="checked"</c:if>>
	      	<label class="chkselect" for="searchType1">title</label>
	      </span>
	      &nbsp;
	      <span class="input-group-addon">
	        <input type="checkbox" aria-label="Checkbox for following text input" name="searchType" value="brdmemo" <c:if test="${fn:indexOf(searchVO.searchType, 'brdmemo')!=-1}">checked="checked"</c:if>>
	      	<label class="chkselect" for="searchType2">content</label>
	      </span>
	      &nbsp;
	      <input type="text" class="form-control" name="searchKeyword" placeholder="press the keyword"  maxlength="50" value='<c:out value="${searchVO.searchKeyword}"/>' onkeydown="if(event.keyCode == 13) { fn_formSubmit();}">
	      <span class="input-group-btn">
	        <button class="btn btn-default" type="button" onclick="fn_formSubmit()">find</button>
	      </span>
	    </div>
	  </div>
	</div>
	
	</form>	
	 
	</div>
</body>
</html>
