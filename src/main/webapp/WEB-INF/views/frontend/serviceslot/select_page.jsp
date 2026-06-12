<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>服務時段管理</title>
<style>
body {
    font-family: "Microsoft JhengHei", Arial, sans-serif;
    background: #f3f4f6;
    margin: 0;
    padding: 40px;
}
.container {
    max-width: 900px;
    margin: auto;
    background: white;
    padding: 30px;
    border: 1px solid #ddd;
}
h1 {
    margin-top: 0;
}
.card {
    border: 1px solid #ddd;
    padding: 20px;
    margin-bottom: 20px;
}
label {
    display: block;
    font-weight: bold;
    margin-bottom: 8px;
}
input {
    width: 100%;
    padding: 10px;
    margin-bottom: 8px;
}
button, a.btn {
    display: inline-block;
    background: #111827;
    color: white;
    padding: 10px 16px;
    border: none;
    text-decoration: none;
    cursor: pointer;
}
.err {
    color: red;
    font-weight: bold;
    margin-bottom: 10px;
}
</style>
</head>

<body>
<div class="container">

    <h1>服務時段管理</h1>

    <c:if test="${not empty errorMsgs.action}">
        <div class="err">${errorMsgs.action}</div>
    </c:if>

    <div class="card">
        <h2>查詢單一服務時段</h2>

        <form method="get" action="${pageContext.request.contextPath}/serviceslot/one">

            <label>服務時段編號</label>
            <input type="number" name="serviceSlotId" value="${param.serviceSlotId}">

            <c:if test="${not empty errorMsgs.serviceSlotId}">
                <div class="err">${errorMsgs.serviceSlotId}</div>
            </c:if>

            <button type="submit">查詢服務時段</button>
        </form>
    </div>

    <div class="card">
        <h2>依服務編號查詢時段</h2>

        <form method="get" action="${pageContext.request.contextPath}/serviceslot/service">

            <label>服務編號</label>
            <input type="number" name="serviceId" value="${param.serviceId}">

            <c:if test="${not empty errorMsgs.serviceId}">
                <div class="err">${errorMsgs.serviceId}</div>
            </c:if>

            <button type="submit">查詢此服務的時段</button>
        </form>
    </div>

    <div class="card">
        <h2>其他操作</h2>

        <p>
            <a class="btn" href="${pageContext.request.contextPath}/serviceslot/add">
                新增服務時段
            </a>
        </p>

        <p>
            <a class="btn" href="${pageContext.request.contextPath}/serviceslot/list">
                查看全部服務時段
            </a>
        </p>
    </div>

</div>
</body>
</html>