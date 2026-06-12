<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>WeBond｜我的服務列表</title>

<style>
    * {
        box-sizing: border-box;
    }

    body {
        margin: 0;
        font-family: "Microsoft JhengHei", "Segoe UI", Arial, sans-serif;
        background: #f3f4f6;
        color: #111827;
    }

    .page {
        min-height: 100vh;
        display: flex;
    }

    .sidebar {
        width: 240px;
        background: #111827;
        color: white;
        padding: 28px 22px;
    }

    .brand {
        font-size: 24px;
        font-weight: 800;
        letter-spacing: 1px;
        margin-bottom: 8px;
    }

    .brand-subtitle {
        font-size: 13px;
        color: #9ca3af;
        margin-bottom: 36px;
    }

    .nav-title {
        font-size: 12px;
        color: #6b7280;
        letter-spacing: 1px;
        margin-bottom: 12px;
    }

    .nav-item {
        padding: 12px 14px;
        background: #1f2937;
        border-left: 4px solid #38bdf8;
        font-size: 14px;
        margin-bottom: 10px;
    }

    .main {
        flex: 1;
        padding: 36px 44px;
    }

    .topbar {
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
        margin-bottom: 28px;
    }

    .page-title h1 {
        margin: 0;
        font-size: 30px;
        font-weight: 800;
    }

    .page-title p {
        margin: 10px 0 0;
        color: #6b7280;
        font-size: 15px;
        line-height: 1.7;
    }

    .mode-badge {
        background: #fff7ed;
        color: #c2410c;
        border: 1px solid #fed7aa;
        padding: 8px 12px;
        font-size: 13px;
        font-weight: 700;
    }

    .toolbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 18px;
        gap: 12px;
    }

    .toolbar-left {
        color: #6b7280;
        font-size: 14px;
    }

    .toolbar-actions {
        display: flex;
        gap: 10px;
    }

    .btn {
        display: inline-block;
        padding: 10px 16px;
        border: none;
        font-size: 14px;
        font-weight: 800;
        text-align: center;
        text-decoration: none;
        cursor: pointer;
        transition: 0.18s ease;
        font-family: inherit;
    }

    .btn-dark {
        background: #111827;
        color: white;
    }

    .btn-dark:hover {
        background: #000;
        transform: translateY(-1px);
    }

    .btn-light {
        background: white;
        color: #111827;
        border: 1px solid #d1d5db;
    }

    .btn-light:hover {
        background: #f9fafb;
        transform: translateY(-1px);
    }

    .btn-edit {
        background: #2563eb;
        color: white;
        width: 72px;
    }

    .btn-edit:hover {
        background: #1d4ed8;
    }

    .btn-delete {
        background: #dc2626;
        color: white;
        width: 72px;
    }

    .btn-delete:hover {
        background: #b91c1c;
    }

    .panel {
        background: white;
        border: 1px solid #e5e7eb;
        box-shadow: 0 10px 24px rgba(17, 24, 39, 0.05);
    }

    .panel-header {
        padding: 22px 24px;
        border-bottom: 1px solid #e5e7eb;
    }

    .panel-header h2 {
        margin: 0;
        font-size: 20px;
        font-weight: 800;
    }

    .panel-header p {
        margin: 8px 0 0;
        color: #6b7280;
        font-size: 14px;
        line-height: 1.6;
    }

    .table-wrapper {
        width: 100%;
        overflow-x: auto;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        min-width: 1100px;
    }

    th {
        background: #f9fafb;
        color: #374151;
        font-size: 13px;
        text-align: left;
        padding: 14px 16px;
        border-bottom: 1px solid #e5e7eb;
        white-space: nowrap;
    }

    td {
        padding: 15px 16px;
        border-bottom: 1px solid #e5e7eb;
        font-size: 14px;
        vertical-align: middle;
    }

    tr:hover td {
        background: #f9fafb;
    }

    .id-text {
        font-weight: 800;
        color: #111827;
    }

    .muted {
        color: #6b7280;
    }

    .description-cell {
        max-width: 280px;
        color: #4b5563;
        line-height: 1.6;
    }

    .price {
        font-weight: 800;
        color: #111827;
    }

    .badge {
        display: inline-block;
        padding: 5px 10px;
        font-size: 12px;
        font-weight: 800;
    }

    .badge-online {
        background: #dcfce7;
        color: #166534;
        border: 1px solid #86efac;
    }

    .badge-offline {
        background: #f3f4f6;
        color: #4b5563;
        border: 1px solid #d1d5db;
    }

    .action-form {
        margin: 0;
        display: inline-block;
    }

    .empty-state {
        background: white;
        border: 1px solid #e5e7eb;
        padding: 48px 32px;
        text-align: center;
        box-shadow: 0 10px 24px rgba(17, 24, 39, 0.05);
    }

    .empty-state h2 {
        margin: 0;
        font-size: 24px;
        color: #111827;
    }

    .empty-state p {
        margin: 12px 0 24px;
        color: #6b7280;
        line-height: 1.7;
    }

    @media (max-width: 960px) {
        .page {
            display: block;
        }

        .sidebar {
            width: 100%;
        }

        .main {
            padding: 28px 20px;
        }

        .topbar,
        .toolbar {
            display: block;
        }

        .mode-badge {
            display: inline-block;
            margin-top: 16px;
        }

        .toolbar-actions {
            margin-top: 14px;
        }
    }
</style>

</head>

<body>

<div class="page">

    <aside class="sidebar">
        <div class="brand">WeBond</div>
        <div class="brand-subtitle">Member Service Center</div>

        <div class="nav-title">SERVICE</div>
        <div class="nav-item">我的服務列表</div>
    </aside>

    <main class="main">

        <div class="topbar">
            <div class="page-title">
                <h1>我的服務列表</h1>
                <p>
                    查看目前已建立的服務資料，可進一步修改服務內容或刪除測試資料。
                    正式登入完成後，此頁應改為只顯示目前會員自己的服務。
                </p>
            </div>

            <div class="mode-badge">
                開發測試模式
            </div>
        </div>

        <div class="toolbar">
            <div class="toolbar-left">
                服務資料總覽
            </div>

            <div class="toolbar-actions">
                <a class="btn btn-light" href="${pageContext.request.contextPath}/frontend/service/select_page.jsp">
                    返回管理首頁
                </a>

                <a class="btn btn-dark" href="${pageContext.request.contextPath}/frontend/service/addService.jsp">
                    新增服務
                </a>
            </div>
        </div>

        <c:if test="${empty serviceList}">
            <div class="empty-state">
                <h2>目前沒有任何服務資料</h2>
                <p>你尚未建立服務資料。可以先新增一筆服務，測試上架流程是否正常。</p>

                <a class="btn btn-dark" href="${pageContext.request.contextPath}/frontend/service/addService.jsp">
                    新增第一筆服務
                </a>
            </div>
        </c:if>

        <c:if test="${not empty serviceList}">
            <section class="panel">
                <div class="panel-header">
                    <h2>服務資料表</h2>
                    <p>以下列出目前查詢到的服務資料，包含服務類型、會員、費率、狀態與建立時間。</p>
                </div>

                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>服務編號</th>
                                <th>服務類型</th>
                                <th>會員編號</th>
                                <th>服務名稱</th>
                                <th>服務描述</th>
                                <th>每小時費率</th>
                                <th>狀態</th>
                                <th>建立時間</th>
                                <th>修改</th>
                                <th>刪除</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="serviceVO" items="${serviceList}">
                                <tr>
                                    <td>
                                        <span class="id-text">#${serviceVO.serviceId}</span>
                                    </td>

                                    <td>
                                        <span class="muted">${serviceVO.serviceTypeId}</span>
                                    </td>

                                    <td>
                                        <span class="muted">${serviceVO.memberId}</span>
                                    </td>

                                    <td>
                                        <strong>${serviceVO.serviceName}</strong>
                                    </td>

                                    <td class="description-cell">
                                        ${serviceVO.description}
                                    </td>

                                    <td>
                                        <span class="price">${serviceVO.hourlyRate}</span>
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${serviceVO.status == 1}">
                                                <span class="badge badge-online">上架</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-offline">下架</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <span class="muted">${serviceVO.createdAt}</span>
                                    </td>

                                    <td>
                                        <form class="action-form" method="post" action="${pageContext.request.contextPath}/service/service.do">
                                            <input type="hidden" name="serviceId" value="${serviceVO.serviceId}">
                                            <input type="hidden" name="action" value="getOne_For_Update">
                                            <input type="submit" class="btn btn-edit" value="修改">
                                        </form>
                                    </td>

                                    <td>
                                        <form class="action-form" method="post" action="${pageContext.request.contextPath}/service/service.do"
                                              onsubmit="return confirm('確定要刪除這筆服務嗎？');">
                                            <input type="hidden" name="serviceId" value="${serviceVO.serviceId}">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="submit" class="btn btn-delete" value="刪除">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>
        </c:if>

    </main>

</div>

</body>
</html>