<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>指定服務的時段列表</title>
<style>
body {
    font-family: "Microsoft JhengHei", Arial, sans-serif;
    background: #f3f4f6;
    margin: 0;
    padding: 40px;
}
.container {
    max-width: 1100px;
    margin: auto;
    background: white;
    padding: 30px;
    border: 1px solid #ddd;
}
table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}
th, td {
    border: 1px solid #ddd;
    padding: 10px;
}
th {
    background: #f9fafb;
}
a.btn {
    display: inline-block;
    background: #111827;
    color: white;
    padding: 10px 16px;
    text-decoration: none;
}
.empty {
    color: red;
    font-weight: bold;
    margin-top: 20px;
}
</style>
</head>

<body>
<div class="container">

    <h1>指定服務的時段列表</h1>

    <a class="btn" href="${pageContext.request.contextPath}/serviceslot/select">返回查詢頁</a>

    <c:if test="${empty serviceSlotList}">
        <div class="empty">此服務目前沒有任何時段。</div>
    </c:if>

    <c:if test="${not empty serviceSlotList}">
        <table>
            <tr>
                <th>時段編號</th>
                <th>服務編號</th>
                <th>開始時間</th>
                <th>結束時間</th>
                <th>狀態</th>
                <th>鎖定到期時間</th>
            </tr>

            <c:forEach var="slot" items="${serviceSlotList}">
                <tr>
                    <td>${slot.serviceSlotId}</td>
                    <td>${slot.serviceId}</td>
                    <td>${slot.startTime}</td>
                    <td>${slot.endTime}</td>
                    <td>
                        <c:choose>
                            <c:when test="${slot.slotStatus == 0}">可預約</c:when>
                            <c:when test="${slot.slotStatus == 1}">已預約</c:when>
                            <c:otherwise>鎖定 / 暫停</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty slot.lockExpiresAt}">
                                ${slot.lockExpiresAt}
                            </c:when>
                            <c:otherwise>無</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</div>
</body>
</html>