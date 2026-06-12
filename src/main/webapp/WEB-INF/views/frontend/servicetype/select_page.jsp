<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>服務類型查詢首頁</title>
</head>
<body>
	<h2>WeBond｜服務類型管理</h2>
	<hr>
	<h2>查詢單一服務類型</h2>
	<br>
	<form method="post"
		action="${pageContext.request.contextPath}/servicetype/servicetype.do">
		<label>請輸入服務類型編號：</label> <input type="text" name="serviceTypeId">

		<input type="hidden" name="action" value="getOne_For_Display">
		<span style="color: red;"> ${errorMsgs.serviceTypeId} </span> <input
			type="submit" value="查詢">
	</form>

	<br>
	<h3>查詢全部服務類型</h3>

	<form method="post"
		action="${pageContext.request.contextPath}/servicetype/servicetype.do">
		<input type="hidden" name="action" value="getAll"> <input
			type="submit" value="查詢全部">
	</form>

	<br>

	<a
		href="${pageContext.request.contextPath}/frontend/servicetype/addServiceType.jsp">
		前往新增服務類型 </a>

	<br>


	<c:if test="${not empty errorMsgs}">
		<ul style="color: red;">
			<c:forEach var="message" items="${errorMsgs}">
				<li>${message}</li>
			</c:forEach>
		</ul>
	</c:if>

</body>
</html>