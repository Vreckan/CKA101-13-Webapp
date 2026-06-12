<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>全部服務類型資料</title>
</head>
<body>

	<h2>全部服務類型資料</h2>

	<hr>

	<c:if test="${empty serviceTypeList}">
		<p style="color: red;">目前沒有任何服務類型資料。</p>
	</c:if>

	<c:if test="${not empty serviceTypeList}">
		<table border="1" cellpadding="8" cellspacing="0">
			<tr>
				<th>服務類型編號</th>
				<th>類型名稱</th>
				<th>類型描述</th>
				<th>類型狀態</th>
				<th>圖片路徑</th>
				<th>圖片預覽</th>
				<th>修改</th>
				<th>刪除</th>
			</tr>

			<c:forEach var="serviceTypeVO" items="${serviceTypeList}">
				<tr>
					<td>${serviceTypeVO.svcTypeID}</td>
					<td>${serviceTypeVO.typeName}</td>
					<td>${serviceTypeVO.descrip}</td>
					<td>${serviceTypeVO.typeMode == 0 ? '動態' : '靜態'}</td>
					<td>${serviceTypeVO.imgURL}</td>

					<td>
						<img src="${pageContext.request.contextPath}${serviceTypeVO.imgURL}"
							 width="120">
					</td>

					<td>
						<form method="get"
							  action="${pageContext.request.contextPath}/servicetype/edit">
							<input type="hidden"
								   name="serviceTypeId"
								   value="${serviceTypeVO.svcTypeID}">
							<input type="submit" value="修改">
						</form>
					</td>

					<td>
						<form method="post"
							  action="${pageContext.request.contextPath}/servicetype/delete"
							  onsubmit="return confirm('確定要刪除這筆服務類型嗎？');">
							<input type="hidden"
								   name="serviceTypeId"
								   value="${serviceTypeVO.svcTypeID}">
							<input type="submit" value="刪除">
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<br>

	<a href="${pageContext.request.contextPath}/servicetype/select">
		回服務類型查詢首頁
	</a>

</body>
</html>