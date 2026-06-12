<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>單一服務類型資料</title>
</head>
<body>

<h2>單一服務類型資料</h2>

<hr>

<c:if test="${not empty serviceTypeVO}">

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>服務類型編號</th>
            <th>類型名稱</th>
            <th>類型描述</th>
            <th>類型狀態</th>
        </tr>

        <tr>
            <td>${serviceTypeVO.svcTypeID}</td>
            <td>${serviceTypeVO.typeName}</td>
            <td>${serviceTypeVO.descrip}</td>
            <td>
                ${serviceTypeVO.typeMode == 0 ? '動態' : '靜態'}
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${empty serviceTypeVO}">
    <p style="color:red;">查無此服務類型資料。</p>
</c:if>

<br>

<a href="${pageContext.request.contextPath}/frontend/servicetype/select_page.jsp">回服務類型查詢首頁</a>

</body>
</html>