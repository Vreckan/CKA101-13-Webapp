<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>單一服務時段</title>
<style>
body {
    font-family: "Microsoft JhengHei", Arial, sans-serif;
    background: #f3f4f6;
    margin: 0;
    padding: 40px;
}
.container {
    max-width: 800px;
    margin: auto;
    background: white;
    padding: 30px;
    border: 1px solid #ddd;
}
.row {
    display: grid;
    grid-template-columns: 180px 1fr;
    border-bottom: 1px solid #ddd;
    padding: 12px 0;
}
.label {
    font-weight: bold;
    color: #374151;
}
button, a.btn {
    display: inline-block;
    margin-top: 20px;
    background: #111827;
    color: white;
    padding: 10px 16px;
    border: none;
    text-decoration: none;
    cursor: pointer;
}
.btn-gray {
    background: #6b7280;
}
.empty {
    color: red;
    font-weight: bold;
}
form {
    display: inline;
}
</style>
</head>

<body>
<div class="container">

    <h1>單一服務時段資料</h1>

    <c:if test="${empty serviceSlotVO}">
        <div class="empty">查無服務時段資料。</div>
        <a class="btn btn-gray" href="${pageContext.request.contextPath}/serviceslot/select">返回查詢頁</a>
    </c:if>

    <c:if test="${not empty serviceSlotVO}">

        <div class="row">
            <div class="label">時段編號</div>
            <div>${serviceSlotVO.serviceSlotId}</div>
        </div>

        <div class="row">
            <div class="label">服務編號</div>
            <div>${serviceSlotVO.serviceId}</div>
        </div>

        <div class="row">
            <div class="label">開始時間</div>
            <div>${serviceSlotVO.startTime}</div>
        </div>

        <div class="row">
            <div class="label">結束時間</div>
            <div>${serviceSlotVO.endTime}</div>
        </div>

        <div class="row">
            <div class="label">時段狀態</div>
            <div>
                <c:choose>
                    <c:when test="${serviceSlotVO.slotStatus == 0}">可預約</c:when>
                    <c:when test="${serviceSlotVO.slotStatus == 1}">已預約</c:when>
                    <c:otherwise>鎖定 / 暫停</c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="row">
            <div class="label">鎖定到期時間</div>
            <div>
                <c:choose>
                    <c:when test="${not empty serviceSlotVO.lockExpiresAt}">
                        ${serviceSlotVO.lockExpiresAt}
                    </c:when>
                    <c:otherwise>無</c:otherwise>
                </c:choose>
            </div>
        </div>

        <form method="get" action="${pageContext.request.contextPath}/serviceslot/edit">
            <input type="hidden" name="serviceSlotId" value="${serviceSlotVO.serviceSlotId}">
            <button type="submit">修改</button>
        </form>

        <a class="btn btn-gray" href="${pageContext.request.contextPath}/serviceslot/list">
            回全部列表
        </a>

        <a class="btn btn-gray" href="${pageContext.request.contextPath}/serviceslot/select">
            回查詢頁
        </a>

    </c:if>

</div>
</body>
</html>