<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
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
	width: 900px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 6px;
	font-size: 18px;
}

input[type="text"] {
	width: 520px;
	font-size: 18px;
}

select {
	font-size: 18px;
}

.error {
	color: red;
	font-size: 15px;
	margin-left: 10px;
	white-space: nowrap;
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
					<a href="${pageContext.request.contextPath}/servicetype/select">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增：</h3>

	<form method="post"
		action="${pageContext.request.contextPath}/servicetype/insert"
		name="form1">

		<table>

			<tr>
				<td>類型名稱：</td>
				<td>
					<input type="text" name="typeName"
						value="${empty serviceTypeVO.typeName ? '陪同散步' : serviceTypeVO.typeName}"
						size="45" />
					<span class="error">${errorMsgs.typeName}</span>
				</td>
			</tr>

			<tr>
				<td>類型描述：</td>
				<td>
					<input type="text" name="description"
						value="${empty serviceTypeVO.descrip ? '陪同使用者在公園、河堤或市區散步聊天' : serviceTypeVO.descrip}"
						size="45" />
					<span class="error">${errorMsgs.description}</span>
				</td>
			</tr>

			<tr>
				<td>類型狀態：</td>
				<td>
					<select name="typeMode">
						<option value="0"
							${empty serviceTypeVO.typeMode || serviceTypeVO.typeMode == 0 ? 'selected' : ''}>
							0：動態
						</option>

						<option value="1"
							${serviceTypeVO.typeMode == 1 ? 'selected' : ''}>
							1：靜態
						</option>
					</select>
					<span class="error">${errorMsgs.typeMode}</span>
				</td>
			</tr>

			<tr>
				<td>預設圖片路徑：</td>
				<td>
					<input type="text" name="imgURL"
						value="${empty serviceTypeVO.imgURL ? '/images/service/walk.jpg' : serviceTypeVO.imgURL}"
						size="45" />
					<span class="error">${errorMsgs.imgURL}</span>
				</td>
			</tr>

		</table>

		<br>

		<input type="submit" value="送出新增">

	</form>

</body>
</html>