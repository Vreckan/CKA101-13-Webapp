package com.serviceslot.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.serviceslot.model.ServiceSlotService;
import com.serviceslot.model.ServiceSlotVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/serviceslot/serviceslot.do")
public class ServiceSlotServlet extends HttpServlet {

	private ServiceSlotService serviceSlotSvc;

	@Override
	public void init() throws ServletException {
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

		serviceSlotSvc = ctx.getBean(ServiceSlotService.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if (action == null) {
			RequestDispatcher successView = req.getRequestDispatcher("/frontend/serviceslot/select_page.jsp");
			successView.forward(req, res);
			return;
		}

		// 查全部服務時段
		if ("getAll".equals(action)) {

			List<ServiceSlotVO> list = serviceSlotSvc.getAll();

			req.setAttribute("serviceSlotList", list);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/serviceslot/listAllServiceSlot.jsp");
			successView.forward(req, res);
			return;
		}

		// 查單一服務時段
		if ("getOne_For_Display".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<>();
			req.setAttribute("errorMsgs", errorMsgs);

			Integer serviceSlotId = null;
			String serviceSlotIdStr = req.getParameter("serviceSlotId");

			if (serviceSlotIdStr == null || serviceSlotIdStr.trim().length() == 0) {
				errorMsgs.put("serviceSlotId", "請輸入服務時段編號");
			} else {
				try {
					serviceSlotId = Integer.valueOf(serviceSlotIdStr.trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("serviceSlotId", "服務時段編號只能輸入數字");
				}
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/serviceslot/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			ServiceSlotVO serviceSlotVO = serviceSlotSvc.getOneServiceSlot(serviceSlotId);

			System.out.println("查單一 serviceSlotId = " + serviceSlotId);
			System.out.println("查到的 serviceSlotVO = " + serviceSlotVO);

			if (serviceSlotVO != null) {
				System.out.println("查到的 serviceId = " + serviceSlotVO.getServiceId());
			}
			
			if (serviceSlotVO == null) {
				errorMsgs.put("serviceSlotId", "查無此服務時段編號");

				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/serviceslot/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			req.setAttribute("serviceSlotVO", serviceSlotVO);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/serviceslot/listOneServiceSlot.jsp");
			successView.forward(req, res);
			return;
		}

		// 依 serviceId 查詢該服務底下的所有時段
		if ("getByServiceId".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<>();
			req.setAttribute("errorMsgs", errorMsgs);

			Integer serviceId = null;
			String serviceIdStr = req.getParameter("serviceId");

			if (serviceIdStr == null || serviceIdStr.trim().length() == 0) {
				errorMsgs.put("serviceId", "請輸入服務編號");
			} else {
				try {
					serviceId = Integer.valueOf(serviceIdStr.trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("serviceId", "服務編號只能輸入數字");
				}
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/serviceslot/select_page.jsp");
				failureView.forward(req, res);
				return;
			}

			List<ServiceSlotVO> list = serviceSlotSvc.getByServiceId(serviceId);

			req.setAttribute("serviceSlotList", list);

			RequestDispatcher successView = req
					.getRequestDispatcher("/frontend/serviceslot/listServiceSlotByService.jsp");
			successView.forward(req, res);
			return;
		}

		// 新增服務時段
		if ("insert".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<>();
			req.setAttribute("errorMsgs", errorMsgs);

			Integer serviceId = parseInteger(req, "serviceId", "服務編號", errorMsgs);
			LocalDateTime startTime = parseDateTime(req, "startTime", "開始時間", errorMsgs);
			LocalDateTime endTime = parseDateTime(req, "endTime", "結束時間", errorMsgs);
			Byte slotStatus = parseByte(req, "slotStatus", "時段狀態", errorMsgs);
			LocalDateTime lockExpiresAt = parseDateTimeAllowEmpty(req, "lockExpiresAt", "鎖定到期時間", errorMsgs);

			ServiceSlotVO serviceSlotVO = new ServiceSlotVO();
			serviceSlotVO.setServiceId(serviceId);
			serviceSlotVO.setStartTime(startTime);
			serviceSlotVO.setEndTime(endTime);
			serviceSlotVO.setSlotStatus(slotStatus);
			serviceSlotVO.setLockExpiresAt(lockExpiresAt);

			if (startTime != null && endTime != null && !endTime.isAfter(startTime)) {
				errorMsgs.put("endTime", "結束時間必須晚於開始時間");
			}

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("serviceSlotVO", serviceSlotVO);

				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/serviceslot/addServiceSlot.jsp");
				failureView.forward(req, res);
				return;
			}

			serviceSlotVO = serviceSlotSvc.add(serviceId, startTime, endTime, slotStatus, lockExpiresAt);

			req.setAttribute("serviceSlotVO", serviceSlotVO);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/serviceslot/listOneServiceSlot.jsp");
			successView.forward(req, res);
			return;
		}

		// 進入修改頁
		if ("getOne_For_Update".equals(action)) {

			Integer serviceSlotId = Integer.valueOf(req.getParameter("serviceSlotId"));

			ServiceSlotVO serviceSlotVO = serviceSlotSvc.getOneServiceSlot(serviceSlotId);

			req.setAttribute("serviceSlotVO", serviceSlotVO);

			RequestDispatcher successView = req
					.getRequestDispatcher("/frontend/serviceslot/update_service_slot_input.jsp");
			successView.forward(req, res);
			return;
		}

		// 修改服務時段
		if ("update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<>();
			req.setAttribute("errorMsgs", errorMsgs);

			Integer serviceSlotId = parseInteger(req, "serviceSlotId", "服務時段編號", errorMsgs);
			Integer serviceId = parseInteger(req, "serviceId", "服務編號", errorMsgs);
			LocalDateTime startTime = parseDateTime(req, "startTime", "開始時間", errorMsgs);
			LocalDateTime endTime = parseDateTime(req, "endTime", "結束時間", errorMsgs);
			Byte slotStatus = parseByte(req, "slotStatus", "時段狀態", errorMsgs);
			LocalDateTime lockExpiresAt = parseDateTimeAllowEmpty(req, "lockExpiresAt", "鎖定到期時間", errorMsgs);

			ServiceSlotVO serviceSlotVO = new ServiceSlotVO();
			serviceSlotVO.setServiceSlotId(serviceSlotId);
			serviceSlotVO.setServiceId(serviceId);
			serviceSlotVO.setStartTime(startTime);
			serviceSlotVO.setEndTime(endTime);
			serviceSlotVO.setSlotStatus(slotStatus);
			serviceSlotVO.setLockExpiresAt(lockExpiresAt);

			if (startTime != null && endTime != null && !endTime.isAfter(startTime)) {
				errorMsgs.put("endTime", "結束時間必須晚於開始時間");
			}

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("serviceSlotVO", serviceSlotVO);

				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/serviceslot/update_service_slot_input.jsp");
				failureView.forward(req, res);
				return;
			}

			serviceSlotVO = serviceSlotSvc.update(serviceSlotId, serviceId, startTime, endTime, slotStatus,
					lockExpiresAt);

			req.setAttribute("serviceSlotVO", serviceSlotVO);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/serviceslot/listOneServiceSlot.jsp");
			successView.forward(req, res);
			return;
		}

		// 刪除服務時段
		if ("delete".equals(action)) {

			Integer serviceSlotId = Integer.valueOf(req.getParameter("serviceSlotId"));

			serviceSlotSvc.delete(serviceSlotId);

			List<ServiceSlotVO> list = serviceSlotSvc.getAll();

			req.setAttribute("serviceSlotList", list);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/serviceslot/listAllServiceSlot.jsp");
			successView.forward(req, res);
			return;
		}
	}

	private Integer parseInteger(HttpServletRequest req, String paramName, String fieldName,
			Map<String, String> errorMsgs) {

		String value = req.getParameter(paramName);

		if (value == null || value.trim().length() == 0) {
			errorMsgs.put(paramName, fieldName + "請勿空白");
			return null;
		}

		try {
			return Integer.valueOf(value.trim());
		} catch (NumberFormatException e) {
			errorMsgs.put(paramName, fieldName + "只能輸入數字");
			return null;
		}
	}

	private Byte parseByte(HttpServletRequest req, String paramName, String fieldName, Map<String, String> errorMsgs) {

		String value = req.getParameter(paramName);

		if (value == null || value.trim().length() == 0) {
			errorMsgs.put(paramName, fieldName + "請勿空白");
			return null;
		}

		try {
			return Byte.valueOf(value.trim());
		} catch (NumberFormatException e) {
			errorMsgs.put(paramName, fieldName + "格式錯誤");
			return null;
		}
	}

	private LocalDateTime parseDateTime(HttpServletRequest req, String paramName, String fieldName,
			Map<String, String> errorMsgs) {

		String value = req.getParameter(paramName);

		if (value == null || value.trim().length() == 0) {
			errorMsgs.put(paramName, fieldName + "請勿空白");
			return null;
		}

		try {
			return parseFlexibleDateTime(value.trim());
		} catch (Exception e) {
			errorMsgs.put(paramName, fieldName + "格式錯誤");
			return null;
		}
	}

	private LocalDateTime parseDateTimeAllowEmpty(HttpServletRequest req, String paramName, String fieldName,
			Map<String, String> errorMsgs) {

		String value = req.getParameter(paramName);

		if (value == null || value.trim().length() == 0) {
			return null;
		}

		try {
			return parseFlexibleDateTime(value.trim());
		} catch (Exception e) {
			errorMsgs.put(paramName, fieldName + "格式錯誤");
			return null;
		}
	}

	private LocalDateTime parseFlexibleDateTime(String value) {
		if (value.length() == 16) {
			return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
		}

		if (value.length() == 19 && value.contains("T")) {
			return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		}

		return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}