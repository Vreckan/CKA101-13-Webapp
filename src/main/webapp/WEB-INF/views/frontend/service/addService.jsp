<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<title>WeBond｜新增上架服務</title>

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

.input-with-error {
	display: grid;
	grid-template-columns: minmax(0, 1fr) auto;
	align-items: center;
	gap: 10px;
}

.field-error {
	color: #dc2626;
	font-size: 13px;
	font-weight: 700;
	white-space: nowrap;
}

.input-error {
	border-color: #dc2626 !important;
	background: #fef2f2 !important;
}

input[type="text"], input[type="number"], textarea, select {
	width: 100%;
	padding: 12px 14px;
	border: 1px solid #d1d5db;
	background: #f9fafb;
	font-size: 15px;
	font-family: inherit;
	outline: none;
}

textarea {
	resize: vertical;
	min-height: 130px;
	line-height: 1.7;
}

input:focus, textarea:focus, select:focus {
	border-color: #111827;
	background: white;
	box-shadow: 0 0 0 3px rgba(17, 24, 39, 0.08);
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
}

.side-list {
	padding-left: 18px;
	margin: 12px 0 0;
	color: #4b5563;
	font-size: 14px;
	line-height: 1.8;
}

@media ( max-width : 960px) {
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
	.content-layout, .form-row {
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
			<div class="nav-item">新增上架服務</div>
		</aside>

		<main class="main">

			<div class="topbar">
				<div class="page-title">
					<h1>新增上架服務</h1>
					<p>建立一筆你想提供給其他使用者的服務內容。填寫完成後，系統會將資料新增到服務資料表中。</p>
				</div>

				<div class="mode-badge">開發測試模式</div>
			</div>

			<div class="content-layout">

				<section class="panel">
					<div class="panel-header">
						<h2>服務基本資料</h2>
						<p>請填寫服務類型、服務名稱、描述、費率與目前狀態。</p>
					</div>

					<div class="panel-body">

						<form method="post"
							action="${pageContext.request.contextPath}/service/service.do">

							<div class="form-row">
								<div class="form-group">
									<div class="input-with-error">
										<input type="number" id="serviceTypeId" name="serviceTypeId"
											value="<c:out value='${serviceVO.serviceTypeId}' />"
											class="${not empty errorMsgs.serviceTypeId ? 'input-error' : ''}"
											placeholder="例如：1">

										<c:if test="${not empty errorMsgs.serviceTypeId}">
											<span class="field-error"> <c:out
													value="${errorMsgs.serviceTypeId}" />
											</span>
										</c:if>
									</div>
								</div>

								<div class="form-group">
									<div class="input-with-error">
										<input type="number" id="memberId" name="memberId"
											value="<c:out value='${serviceVO.memberId}' />"
											class="${not empty errorMsgs.memberId ? 'input-error' : ''}"
											placeholder="例如：1">

										<c:if test="${not empty errorMsgs.memberId}">
											<span class="field-error"> <c:out
													value="${errorMsgs.memberId}" />
											</span>
										</c:if>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="serviceName">服務名稱 <span class="required">*</span></label>
								<div class="input-with-error">
									<input type="text" id="serviceName" name="serviceName"
										value="<c:out value='${serviceVO.serviceName}' />"
										class="${not empty errorMsgs.serviceName ? 'input-error' : ''}"
										placeholder="例如：週末咖啡廳聊天陪伴">

									<c:if test="${not empty errorMsgs.serviceName}">
										<span class="field-error"> <c:out
												value="${errorMsgs.serviceName}" />
										</span>
									</c:if>
								</div>


							</div>

							<div class="form-group">
								<div class="input-with-error">
									<textarea id="description" name="description"
										class="${not empty errorMsgs.description ? 'input-error' : ''}"
										placeholder="請簡單描述這項服務的內容、適合對象、注意事項等。"><c:out
											value="${serviceVO.description}" /></textarea>

									<c:if test="${not empty errorMsgs.description}">
										<span class="field-error"> <c:out
												value="${errorMsgs.description}" />
										</span>
									</c:if>
								</div>
							</div>

							<div class="form-row">
								<div class="form-group">
									<div class="input-with-error">
										<input type="number" id="hourlyRate" name="hourlyRate"
											value="<c:out value='${serviceVO.hourlyRate}' />"
											class="${not empty errorMsgs.hourlyRate ? 'input-error' : ''}"
											placeholder="例如：500">

										<c:if test="${not empty errorMsgs.hourlyRate}">
											<span class="field-error"> <c:out
													value="${errorMsgs.hourlyRate}" />
											</span>
										</c:if>
									</div>
								</div>

								<div class="form-group">
									<div class="input-with-error">
										<select id="status" name="status"
											class="${not empty errorMsgs.status ? 'input-error' : ''}">

											<option value="">請選擇服務狀態</option>

											<option value="0" ${serviceVO.status == 0 ? 'selected' : ''}>
												下架</option>

											<option value="1" ${serviceVO.status == 1 ? 'selected' : ''}>
												上架</option>
										</select>

										<c:if test="${not empty errorMsgs.status}">
											<span class="field-error"> <c:out
													value="${errorMsgs.status}" />
											</span>
										</c:if>
									</div>
								</div>
							</div>

							<input type="hidden" name="action" value="insert">

							<div class="btn-row">
								<input type="submit" class="btn btn-primary" value="送出新增">

								<a class="btn btn-secondary"
									href="${pageContext.request.contextPath}/frontend/service/select_page.jsp">
									返回服務管理 </a>
							</div>

						</form>

					</div>
				</section>

				<aside class="panel">
					<div class="panel-header">
						<h2>填寫提醒</h2>
						<p>目前此頁面定位為前台會員的服務上架入口。</p>
					</div>

					<div class="panel-body">
						<div class="info-box">
							目前尚未串接登入系統，因此會員編號暫時需要手動輸入。之後登入完成後，這個欄位應該移除。</div>

						<div class="warning-box">
							正式上線時，使用者不應該自行輸入會員編號，避免冒用其他會員身分建立服務。</div>

						<ul class="side-list">
							<li>服務名稱建議簡潔清楚。</li>
							<li>服務描述應包含內容、限制與注意事項。</li>
							<li>費率建議使用整數，避免前後端格式錯誤。</li>
							<li>狀態可先用 0 / 1 測試 CRUD。</li>
						</ul>
					</div>
				</aside>

			</div>

		</main>

	</div>

</body>
</html>