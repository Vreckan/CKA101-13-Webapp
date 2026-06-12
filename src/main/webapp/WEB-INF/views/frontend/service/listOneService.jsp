<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>WeBond｜服務詳細資料</title>

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

    .content-layout {
        display: grid;
        grid-template-columns: 1.4fr 0.8fr;
        gap: 24px;
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

    .panel-body {
        padding: 24px;
    }

    .detail-grid {
        display: grid;
        grid-template-columns: 160px 1fr;
        border-top: 1px solid #e5e7eb;
    }

    .detail-label,
    .detail-value {
        padding: 18px 0;
        border-bottom: 1px solid #e5e7eb;
        font-size: 15px;
    }

    .detail-label {
        color: #6b7280;
        font-weight: 700;
    }

    .detail-value {
        color: #111827;
        line-height: 1.7;
    }

    .service-name {
        font-size: 22px;
        font-weight: 800;
    }

    .description {
        white-space: pre-wrap;
        color: #374151;
    }

    .price {
        font-size: 20px;
        font-weight: 800;
        color: #111827;
    }

    .badge {
        display: inline-block;
        padding: 6px 12px;
        font-size: 13px;
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

    .btn-row {
        display: flex;
        gap: 12px;
        margin-top: 24px;
    }

    .btn {
        display: inline-block;
        padding: 12px 18px;
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

    .btn-blue {
        background: #2563eb;
        color: white;
    }

    .btn-blue:hover {
        background: #1d4ed8;
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

    .action-form {
        margin: 0;
    }

    .info-box {
        border-left: 4px solid #2563eb;
        background: #eff6ff;
        padding: 16px 18px;
        color: #1e3a8a;
        font-size: 14px;
        line-height: 1.7;
        margin-bottom: 18px;
    }

    .side-list {
        padding-left: 18px;
        margin: 12px 0 0;
        color: #4b5563;
        font-size: 14px;
        line-height: 1.8;
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

        .topbar {
            display: block;
        }

        .mode-badge {
            display: inline-block;
            margin-top: 16px;
        }

        .content-layout {
            grid-template-columns: 1fr;
        }

        .detail-grid {
            grid-template-columns: 1fr;
        }

        .detail-label {
            padding-bottom: 4px;
            border-bottom: none;
        }

        .detail-value {
            padding-top: 0;
        }

        .btn-row {
            flex-direction: column;
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
        <div class="nav-item">服務詳細資料</div>
    </aside>

    <main class="main">

        <div class="topbar">
            <div class="page-title">
                <h1>服務詳細資料</h1>
                <p>
                    查看指定服務的完整內容。此頁可用於新增完成後顯示結果，或查詢單一服務資料。
                </p>
            </div>

            <div class="mode-badge">
                開發測試模式
            </div>
        </div>

        <c:if test="${empty serviceVO}">
            <div class="empty-state">
                <h2>查無此服務資料</h2>
                <p>系統沒有找到對應的服務資料，請確認服務編號是否正確。</p>

                <a class="btn btn-dark" href="${pageContext.request.contextPath}/frontend/service/select_page.jsp">
                    返回服務管理
                </a>
            </div>
        </c:if>

        <c:if test="${not empty serviceVO}">

            <div class="content-layout">

                <section class="panel">
                    <div class="panel-header">
                        <h2>${serviceVO.serviceName}</h2>
                        <p>服務編號 #${serviceVO.serviceId}</p>
                    </div>

                    <div class="panel-body">

                        <div class="detail-grid">

                            <div class="detail-label">服務編號</div>
                            <div class="detail-value">
                                #${serviceVO.serviceId}
                            </div>

                            <div class="detail-label">服務類型編號</div>
                            <div class="detail-value">
                                ${serviceVO.serviceTypeId}
                            </div>

                            <div class="detail-label">會員編號</div>
                            <div class="detail-value">
                                ${serviceVO.memberId}
                            </div>

                            <div class="detail-label">服務名稱</div>
                            <div class="detail-value service-name">
                                ${serviceVO.serviceName}
                            </div>

                            <div class="detail-label">服務描述</div>
                            <div class="detail-value description">
                                ${serviceVO.description}
                            </div>

                            <div class="detail-label">每小時費率</div>
                            <div class="detail-value">
                                <span class="price">${serviceVO.hourlyRate}</span>
                            </div>

                            <div class="detail-label">服務狀態</div>
                            <div class="detail-value">
                                <c:choose>
                                    <c:when test="${serviceVO.status == 1}">
                                        <span class="badge badge-online">上架</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-offline">下架</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="detail-label">建立時間</div>
                            <div class="detail-value">
                                ${serviceVO.createdAt}
                            </div>

                        </div>

                        <div class="btn-row">
                            <form class="action-form" method="post" action="${pageContext.request.contextPath}/service/service.do">
                                <input type="hidden" name="serviceId" value="${serviceVO.serviceId}">
                                <input type="hidden" name="action" value="getOne_For_Update">
                                <input type="submit" class="btn btn-blue" value="修改此服務">
                            </form>

                            <a class="btn btn-light" href="${pageContext.request.contextPath}/frontend/service/select_page.jsp">
                                返回管理首頁
                            </a>
                        </div>

                    </div>
                </section>

                <aside class="panel">
                    <div class="panel-header">
                        <h2>資料狀態</h2>
                        <p>目前這筆服務資料的基本狀態與開發提醒。</p>
                    </div>

                    <div class="panel-body">
                        <div class="info-box">
                            目前尚未串接登入系統，因此會員編號仍為測試資料。
                            正式版應確認這筆服務是否屬於目前登入會員。
                        </div>

                        <ul class="side-list">
                            <li>服務狀態 1 代表上架。</li>
                            <li>服務狀態 0 代表下架。</li>
                            <li>建立時間通常不應在修改時更動。</li>
                            <li>正式版建議加上權限檢查。</li>
                        </ul>
                    </div>
                </aside>

            </div>

        </c:if>

    </main>

</div>

</body>
</html>