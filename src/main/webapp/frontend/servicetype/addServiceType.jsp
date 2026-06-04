<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.servicetype.model.*"%>

<%
//新增失敗放回物件
ServiceTypeVO serviceTypeVO = (ServiceTypeVO) request.getAttribute("serviceTypeVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>服務類型新增 - addServiceType.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}

table {
	width: 520px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 6px;
}

input[type="text"] {
	width: 320px;
}
</style>

</head>

<body bgcolor="white">

	<table id="table-1">
		<tr>
			<td>
				<h3>服務類型新增 - addServiceType.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增：</h3>

<%-- 	<%-- 錯誤表列：如果 Servlet 有放 errorMsgs，這裡會顯示 --%> --%>
<%-- 	<c:if test="${not empty errorMsgs}"> --%>
<!-- 		<font style="color: red">請修正以下錯誤：</font> -->
<!-- 		<ul> -->
<%-- 			<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 				<li style="color: red">${message}</li> --%>
<%-- 			</c:forEach> --%>
<!-- 		</ul> -->
<%-- 	</c:if> --%>

	<form method="post"
		action="${pageContext.request.contextPath}/servicetype/servicetype.do"
		name="form1">

		<table>

			<tr>
				<td>類型名稱：</td>
				<td><input type="text" name="typeName"
					value="<%=(serviceTypeVO == null) ? "陪同散步" : serviceTypeVO.getTypeName()%>"
					size="45" /></td>
			</tr>

			<tr>
				<td>類型描述：</td>
				<td><input type="text" name="description"
					value="<%=(serviceTypeVO == null) ? "陪同使用者在公園、河堤或市區散步聊天" : serviceTypeVO.getDescrip()%>"
					size="45" /></td>
			</tr>

			<tr>
				<td>類型狀態：</td>
				<td><select name="typeMode">
						<option value="0"
							<%=(serviceTypeVO != null && serviceTypeVO.getTypeMode() != null && serviceTypeVO.getTypeMode() == 0) ? "selected"
				: ""%>>
							0：動態</option>

						<option value="1"
							<%=(serviceTypeVO != null && serviceTypeVO.getTypeMode() != null && serviceTypeVO.getTypeMode() == 1) ? "selected"
				: ""%>>
							1：靜態</option>
				</select></td>
			</tr>

			<tr>
				<td>預設圖片路徑：</td>
				<td><input type="text" name="imgURL"
					value="<%=(serviceTypeVO == null) ? "/images/service/walk.jpg" : serviceTypeVO.getImgURL()%>"
					size="45" /></td>
			</tr>

		</table>

		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">

	</form>

</body>
</html>