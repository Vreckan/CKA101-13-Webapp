<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${serviceTypeList == null}">
    <jsp:forward page="/service/service.do">
        <jsp:param name="action" value="toServiceSelectPage" />
    </jsp:forward>
</c:if>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>WeBond｜我的服務上架管理</title>

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
	color: #fff;
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
	color: #111827;
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

.summary-row {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 18px;
	margin-bottom: 26px;
}

.summary-card {
	background: white;
	border: 1px solid #e5e7eb;
	padding: 20px;
	box-shadow: 0 8px 20px rgba(17, 24, 39, 0.04);
}

.summary-label {
	font-size: 13px;
	color: #6b7280;
	margin-bottom: 8px;
}

.summary-value {
	font-size: 22px;
	font-weight: 800;
	color: #111827;
}

.action-grid {
	display: grid;
	grid-template-columns: 1.1fr 1fr;
	gap: 22px;
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
	color: #111827;
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

.form-group {
	margin-bottom: 18px;
}

label {
	display: block;
	margin-bottom: 8px;
	font-size: 14px;
	font-weight: 700;
	color: #374151;
}

input[type="text"], input[type="number"] {
	width: 100%;
	padding: 12px 14px;
	border: 1px solid #d1d5db;
	background: #f9fafb;
	font-size: 15px;
	outline: none;
}

input[type="text"]:focus, input[type="number"]:focus {
	border-color: #111827;
	background: white;
	box-shadow: 0 0 0 3px rgba(17, 24, 39, 0.08);
}

.btn {
	display: inline-block;
	width: 100%;
	padding: 13px 18px;
	border: none;
	font-size: 15px;
	font-weight: 800;
	text-align: center;
	text-decoration: none;
	cursor: pointer;
	transition: 0.18s ease;
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

.btn-green {
	background: #059669;
	color: white;
}

.btn-green:hover {
	background: #047857;
	transform: translateY(-1px);
}

.large-action {
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.hint {
	margin-top: 14px;
	font-size: 13px;
	color: #9ca3af;
	line-height: 1.6;
}

.divider {
	height: 1px;
	background: #e5e7eb;
	margin: 22px 0;
}

@media ( max-width : 900px) {
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
	.summary-row, .action-grid {
		grid-template-columns: 1fr;
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
			<div class="nav-item">我的服務上架管理</div>
		</aside>

		<main class="main">

			<div class="topbar">
				<div class="page-title">
					<h1>我的服務上架管理</h1>
					<p>在這裡管理你想提供給其他使用者的服務內容。你可以查看服務列表、查詢指定服務， 或新增一筆準備上架的服務。</p>
				</div>

				<div class="mode-badge">開發測試模式</div>
			</div>

			<div class="summary-row">
				<div class="summary-card">
					<div class="summary-label">目前頁面定位</div>
					<div class="summary-value">前台會員</div>
				</div>

				<div class="summary-card">
					<div class="summary-label">服務資料來源</div>
					<div class="summary-value">Service</div>
				</div>

				<div class="summary-card">
					<div class="summary-label">會員登入狀態</div>
					<div class="summary-value">未串接</div>
				</div>
			</div>

			<div class="action-grid">

				<section class="panel">
					<div class="panel-header">
						<h2>服務查詢</h2>
						<p>查看目前服務資料，或輸入服務編號查詢指定服務內容。</p>
					</div>

					<div class="panel-body">

						<form method="post"
							action="${pageContext.request.contextPath}/service/service.do">
							<input type="hidden" name="action" value="getAll"> <input
								type="submit" class="btn btn-blue" value="查看服務列表">
						</form>

						<div class="hint">目前尚未串接登入，因此此功能暫時會依你的 Servlet/DAO 設定顯示資料。
							之後正式版應改為「只查詢目前登入會員的服務」。</div>

						<div class="divider"></div>

						<form method="post"
							action="${pageContext.request.contextPath}/service/service.do">
							<div class="form-group">
								<label for="serviceId">查詢單一服務編號</label> <input type="number"
									id="serviceId" name="serviceId" placeholder="請輸入服務編號，例如：1">
							</div>

							<input type="hidden" name="action" value="getOne_For_Display">
							<input type="submit" class="btn btn-dark" value="查詢指定服務">
						</form>

						<div class="divider"></div>

						<form method="post"
							action="${pageContext.request.contextPath}/service/service.do">
							<div class="form-group">
								<label for="serviceTypeId">依服務類型查詢服務</label> <select
									id="serviceTypeId" name="serviceTypeId">
									<option value="">請選擇服務類型</option>

									<c:forEach var="serviceTypeVO" items="${serviceTypeList}">
										<option value="${serviceTypeVO.svcTypeID}">
											${serviceTypeVO.typeName}</option>
									</c:forEach>
								</select>
							</div>

							<input type="hidden" name="action"
								value="getServices_By_ServiceType"> <input type="submit"
								class="btn btn-blue" value="查詢此類型底下服務">
						</form>

					</div>
				</section>

				<section class="panel">
					<div class="panel-header">
						<h2>新增上架服務</h2>
						<p>建立新的服務內容，包含服務類型、名稱、描述、費率與狀態。</p>
					</div>

					<div class="panel-body large-action">
						<div>
							<p style="margin-top: 0; color: #4b5563; line-height: 1.8;">
								你可以新增一筆服務資料，作為未來上架給其他使用者瀏覽的服務項目。 目前因為尚未串接登入，會員編號仍會在新增頁面中以測試欄位輸入。
							</p>
						</div>

						<a class="btn btn-green"
							href="${pageContext.request.contextPath}/frontend/service/addService.jsp">
							新增一筆服務 </a>
					</div>
				</section>

			</div>

		</main>

	</div>

</body>
</html>