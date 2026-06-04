<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>WeBond｜修改服務資料</title>

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
        grid-template-columns: 1.5fr 0.8fr;
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

    .form-row {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 18px;
    }

    .form-group {
        margin-bottom: 20px;
    }

    label {
        display: block;
        margin-bottom: 8px;
        font-size: 14px;
        font-weight: 700;
        color: #374151;
    }

    .required {
        color: #dc2626;
        margin-left: 3px;
    }

    input[type="text"],
    input[type="number"],
    textarea,
    select {
        width: 100%;
        padding: 12px 14px;
        border: 1px solid #d1d5db;
        background: #f9fafb;
        font-size: 15px;
        font-family: inherit;
        outline: none;
    }

    input[readonly] {
        background: #e5e7eb;
        color: #6b7280;
        cursor: not-allowed;
    }

    textarea {
        resize: vertical;
        min-height: 130px;
        line-height: 1.7;
    }

    input:focus,
    textarea:focus,
    select:focus {
        border-color: #111827;
        background: white;
        box-shadow: 0 0 0 3px rgba(17, 24, 39, 0.08);
    }

    input[readonly]:focus {
        background: #e5e7eb;
        box-shadow: none;
        border-color: #d1d5db;
    }

    .field-hint {
        margin-top: 6px;
        font-size: 12px;
        color: #9ca3af;
        line-height: 1.5;
    }

    .btn-row {
        display: flex;
        gap: 14px;
        margin-top: 28px;
    }

    .btn {
        display: inline-block;
        padding: 13px 20px;
        border: none;
        font-size: 15px;
        font-weight: 800;
        text-align: center;
        text-decoration: none;
        cursor: pointer;
        transition: 0.18s ease;
        font-family: inherit;
    }

    .btn-primary {
        flex: 1;
        background: #111827;
        color: white;
    }

    .btn-primary:hover {
        background: #000;
        transform: translateY(-1px);
    }

    .btn-secondary {
        flex: 1;
        background: white;
        color: #111827;
        border: 1px solid #d1d5db;
    }

    .btn-secondary:hover {
        background: #f9fafb;
        transform: translateY(-1px);
    }

    .btn-blue {
        flex: 1;
        background: #2563eb;
        color: white;
    }

    .btn-blue:hover {
        background: #1d4ed8;
        transform: translateY(-1px);
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

    .info-box {
        border-left: 4px solid #2563eb;
        background: #eff6ff;
        padding: 16px 18px;
        color: #1e3a8a;
        font-size: 14px;
        line-height: 1.7;
        margin-bottom: 18px;
    }

    .warning-box {
        border-left: 4px solid #f97316;
        background: #fff7ed;
        padding: 16px 18px;
        color: #9a3412;
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

        .content-layout,
        .form-row {
            grid-template-columns: 1fr;
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
        <div class="nav-item">修改服務資料</div>
    </aside>

    <main class="main">

        <div class="topbar">
            <div class="page-title">
                <h1>修改服務資料</h1>
                <p>
                    編輯目前這筆服務的基本內容。服務編號與建立時間不可修改，其他欄位可依實際需求更新。
                </p>
            </div>

            <div class="mode-badge">
                開發測試模式
            </div>
        </div>

        <c:if test="${empty serviceVO}">
            <div class="empty-state">
                <h2>查無此服務資料，無法修改</h2>
                <p>系統沒有找到對應的服務資料，請返回服務管理頁重新查詢。</p>

                <a class="btn btn-primary" href="${pageContext.request.contextPath}/frontend/service/select_page.jsp">
                    返回服務管理
                </a>
            </div>
        </c:if>

        <c:if test="${not empty serviceVO}">

            <div class="content-layout">

                <section class="panel">
                    <div class="panel-header">
                        <h2>編輯服務內容</h2>
                        <p>目前正在修改服務編號 #${serviceVO.serviceId}。</p>
                    </div>

                    <div class="panel-body">

                        <form method="post" action="${pageContext.request.contextPath}/service/service.do">

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="serviceId">服務編號</label>
                                    <input type="number" id="serviceId" name="serviceId" value="${serviceVO.serviceId}" readonly>
                                    <div class="field-hint">主鍵不可修改。</div>
                                </div>

                                <div class="form-group">
                                    <label for="createdAt">建立時間</label>
                                    <input type="text" id="createdAt" value="${serviceVO.createdAt}" readonly>
                                    <div class="field-hint">建立時間通常不會在更新時更動。</div>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="serviceTypeId">服務類型編號 <span class="required">*</span></label>
                                    <input type="number" id="serviceTypeId" name="serviceTypeId" value="${serviceVO.serviceTypeId}">
                                </div>

                                <div class="form-group">
                                    <label for="memberId">會員編號 <span class="required">*</span></label>
                                    <input type="number" id="memberId" name="memberId" value="${serviceVO.memberId}">
                                    <div class="field-hint">開發測試用。正式登入後不應讓使用者自行修改。</div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="serviceName">服務名稱 <span class="required">*</span></label>
                                <input type="text" id="serviceName" name="serviceName" value="${serviceVO.serviceName}">
                            </div>

                            <div class="form-group">
                                <label for="description">服務描述 <span class="required">*</span></label>
                                <textarea id="description" name="description">${serviceVO.description}</textarea>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="hourlyRate">每小時費率 <span class="required">*</span></label>
                                    <input type="number" id="hourlyRate" name="hourlyRate" value="${serviceVO.hourlyRate}">
                                </div>

                                <div class="form-group">
                                    <label for="status">服務狀態 <span class="required">*</span></label>
                                    <select id="status" name="status">
                                        <option value="0" ${serviceVO.status == 0 ? "selected" : ""}>下架</option>
                                        <option value="1" ${serviceVO.status == 1 ? "selected" : ""}>上架</option>
                                    </select>
                                </div>
                            </div>

                            <input type="hidden" name="action" value="update">

                            <div class="btn-row">
                                <input type="submit" class="btn btn-blue" value="送出修改">

                                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/frontend/service/select_page.jsp">
                                    返回服務管理
                                </a>
                            </div>

                        </form>

                    </div>
                </section>

                <aside class="panel">
                    <div class="panel-header">
                        <h2>修改提醒</h2>
                        <p>目前此頁仍屬於開發測試版本。</p>
                    </div>

                    <div class="panel-body">
                        <div class="info-box">
                            此頁會將表單送到 <strong>/service/service.do</strong>，並透過
                            <strong>action=update</strong> 執行更新流程。
                        </div>

                        <div class="warning-box">
                            正式上線後，會員編號不應開放使用者自行修改，應由登入 session 判斷目前會員身分。
                        </div>

                        <ul class="side-list">
                            <li>服務編號是主鍵，不應修改。</li>
                            <li>建立時間通常不應在更新時修改。</li>
                            <li>正式版建議加入欄位驗證。</li>
                            <li>正式版建議加入權限檢查，避免修改他人服務。</li>
                        </ul>
                    </div>
                </aside>

            </div>

        </c:if>

    </main>

</div>

</body>
</html>