<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>新增服務時段</title>
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
label {
    display: block;
    font-weight: bold;
    margin-top: 15px;
    margin-bottom: 8px;
}
input, select {
    width: 100%;
    padding: 10px;
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
a.btn-light {
    background: #6b7280;
}
.err {
    color: red;
    font-weight: bold;
    margin-top: 5px;
}
</style>
</head>

<body>
<div class="container">

    <h1>新增服務時段</h1>

    <form method="post" action="${pageContext.request.contextPath}/serviceslot/insert">

        <label>服務編號</label>
        <input type="number" name="serviceId" value="${serviceSlotVO.serviceId}">
        <c:if test="${not empty errorMsgs.serviceId}">
            <div class="err">${errorMsgs.serviceId}</div>
        </c:if>

        <label>開始時間</label>
        <input type="datetime-local" name="startTime" value="${serviceSlotVO.startTime}">
        <c:if test="${not empty errorMsgs.startTime}">
            <div class="err">${errorMsgs.startTime}</div>
        </c:if>

        <label>結束時間</label>
        <input type="datetime-local" name="endTime" value="${serviceSlotVO.endTime}">
        <c:if test="${not empty errorMsgs.endTime}">
            <div class="err">${errorMsgs.endTime}</div>
        </c:if>

        <label>時段狀態</label>
        <select name="slotStatus">
            <option value="">請選擇</option>
            <option value="0" ${serviceSlotVO.slotStatus == 0 ? "selected" : ""}>0｜可預約</option>
            <option value="1" ${serviceSlotVO.slotStatus == 1 ? "selected" : ""}>1｜已預約</option>
            <option value="2" ${serviceSlotVO.slotStatus == 2 ? "selected" : ""}>2｜鎖定 / 暫停</option>
        </select>
        <c:if test="${not empty errorMsgs.slotStatus}">
            <div class="err">${errorMsgs.slotStatus}</div>
        </c:if>

        <label>鎖定到期時間，可空白</label>
        <input type="datetime-local" name="lockExpiresAt" value="${serviceSlotVO.lockExpiresAt}">
        <c:if test="${not empty errorMsgs.lockExpiresAt}">
            <div class="err">${errorMsgs.lockExpiresAt}</div>
        </c:if>

        <button type="submit">新增</button>
        <a class="btn btn-light" href="${pageContext.request.contextPath}/serviceslot/select">返回</a>
    </form>

</div>
</body>
</html>