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

	<form method="get"
		action="${pageContext.request.contextPath}/servicetype/one">

		<label>請輸入服務類型編號：</label>

		<input type="text"
			   name="serviceTypeId"
			   value="<c:out value='${param.serviceTypeId}' />">

		<span style="color: red;">
			<c:out value="${errorMsgs.serviceTypeId}" />
		</span>

		<input type="submit" value="查詢">
	</form>

	<br>

	<h3>查詢全部服務類型</h3>

	<form method="get"
		action="${pageContext.request.contextPath}/servicetype/list">
		<input type="submit" value="查詢全部">
	</form>

	<br>

	<a href="${pageContext.request.contextPath}/servicetype/add">
		前往新增服務類型
	</a>

	<br>
	<br>

	<c:if test="${not empty errorMsgs}">
		<ul style="color: red;">
			<c:forEach var="entry" items="${errorMsgs}">
				<li>
					<c:out value="${entry.value}" />
				</li>
			</c:forEach>
		</ul>
	</c:if>

</body>
</html>