<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>服務類型底下的服務</title>
</head>
<body>

<h2>此服務類型底下的服務列表</h2>

<hr>

<c:if test="${empty serviceList}">
    <p style="color:red;">這個服務類型底下目前沒有任何服務。</p>
</c:if>

<c:if test="${not empty serviceList}">
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>服務編號</th>
            <th>服務名稱</th>
            <th>服務類型</th>
            <th>服務描述</th>
            <th>服務狀態</th>
        </tr>

        <c:forEach var="serviceVO" items="${serviceList}">
            <tr>
                <td>${serviceVO.serviceId}</td>
                <td>${serviceVO.serviceName}</td>
                <td>${serviceVO.serviceType.typeName}</td>
                <td>${serviceVO.description}</td>
                <td>${serviceVO.status}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<br>

<a href="${pageContext.request.contextPath}/servicetype/select">
    回服務查詢頁
</a>

</body>
</html>