package com.service.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.service.model.ServiceService;
import com.service.model.ServiceVO;
import com.servicetype.model.ServiceTypeService;
import com.servicetype.model.ServiceTypeVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/service/service.do")
public class ServiceServlet extends HttpServlet {
	
	private ServiceService serviceSvc;
	private ServiceTypeService serviceTypeSvc;
	
	@Override
	public void init() throws ServletException {
	    var ctx = org.springframework.web.context.support.WebApplicationContextUtils
	            .getRequiredWebApplicationContext(getServletContext());

	    serviceSvc = ctx.getBean(ServiceService.class);
	    serviceTypeSvc = ctx.getBean(ServiceTypeService.class);
	}
	
	

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if (action == null) {
			RequestDispatcher successView = req.getRequestDispatcher("/frontend/service/select_page.jsp");
			successView.forward(req, res);
			return;
		}

		if ("getAll".equals(action)) {
//			ServiceService serviceSvc = new ServiceService();

			List<ServiceVO> list = serviceSvc.getAll();

			req.setAttribute("serviceList", list);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/service/listAllService.jsp");

			successView.forward(req, res);
			return;
		}

		if ("getOne_For_Display".equals(action)) {

		    Map<String, String> errorMsgs = new LinkedHashMap<>();
		    req.setAttribute("errorMsgs", errorMsgs);

		    String serviceIdStr = req.getParameter("serviceId");
		    Integer serviceId = null;

		    // 1. 檢查是否空白
		    if (serviceIdStr == null || serviceIdStr.trim().isEmpty()) {
		        errorMsgs.put("serviceId", "請輸入服務編號");
		    }

		    // 2. 檢查是否為數字
		    if (errorMsgs.isEmpty()) {
		        try {
		            serviceId = Integer.valueOf(serviceIdStr.trim());

		            if (serviceId <= 0) {
		                errorMsgs.put("serviceId", "服務編號必須大於 0");
		            }

		        } catch (NumberFormatException e) {
		            errorMsgs.put("serviceId", "服務編號只能輸入數字");
		        }
		    }

		    // 3. 格式錯誤，回到查詢頁面
		    if (!errorMsgs.isEmpty()) {
		        RequestDispatcher failureView =
		                req.getRequestDispatcher("/frontend/service/select_page.jsp");

		        failureView.forward(req, res);
		        return;
		    }

//		    ServiceService serviceSvc = new ServiceService();
		    ServiceVO serviceVO = serviceSvc.getOneService(serviceId);

		    // 4. 檢查資料是否存在
		    if (serviceVO == null) {
		        errorMsgs.put("serviceId", "查無此服務編號");

		        RequestDispatcher failureView =
		                req.getRequestDispatcher("/frontend/service/select_page.jsp");

		        failureView.forward(req, res);
		        return;
		    }

		    req.setAttribute("serviceVO", serviceVO);

		    RequestDispatcher successView =
		            req.getRequestDispatcher("/frontend/service/listOneService.jsp");

		    successView.forward(req, res);
		    return;
		}

		if ("insert".equals(action)) {

		    Map<String, String> errorMsgs = new LinkedHashMap<>();
		    req.setAttribute("errorMsgs", errorMsgs);

		    Integer serviceTypeId = null;
		    Integer memberId = null;
		    Integer hourlyRate = null;
		    Byte status = null;

		    String serviceName = req.getParameter("serviceName");
		    String description = req.getParameter("description");

		    // 服務類型編號
		    try {
		        serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));
		        if (serviceTypeId <= 0) {
		            errorMsgs.put("serviceTypeId", "服務類型編號必須大於 0");
		        }
		    } catch (Exception e) {
		        errorMsgs.put("serviceTypeId", "請選擇服務類型");
		    }

		    // 會員編號
		    try {
		        memberId = Integer.valueOf(req.getParameter("memberId"));
		        if (memberId <= 0) {
		            errorMsgs.put("memberId", "會員編號必須大於 0");
		        }
		    } catch (Exception e) {
		        errorMsgs.put("memberId", "會員編號格式錯誤");
		    }

		    // 服務名稱
		    if (serviceName == null || serviceName.trim().isEmpty()) {
		        errorMsgs.put("serviceName", "服務名稱請勿空白");
		    } else if (serviceName.trim().length() > 50) {
		        errorMsgs.put("serviceName", "服務名稱不可超過 50 個字");
		    }

		    // 服務描述
		    if (description == null || description.trim().isEmpty()) {
		        errorMsgs.put("description", "服務描述請勿空白");
		    }

		    // 每小時費率
		    try {
		        hourlyRate = Integer.valueOf(req.getParameter("hourlyRate"));
		        if (hourlyRate < 0) {
		            errorMsgs.put("hourlyRate", "每小時費率不可小於 0");
		        }
		    } catch (Exception e) {
		        errorMsgs.put("hourlyRate", "每小時費率必須是整數");
		    }

		    // 狀態
		    try {
		        status = Byte.valueOf(req.getParameter("status"));
		        if (status != 0 && status != 1) {
		            errorMsgs.put("status", "服務狀態只能是 0 或 1");
		        }
		    } catch (Exception e) {
		        errorMsgs.put("status", "請選擇服務狀態");
		    }

		    // 保留使用者原本輸入的資料
		    ServiceVO serviceVO = new ServiceVO();
		    serviceVO.setServiceTypeId(serviceTypeId);
		    serviceVO.setMemberId(memberId);
		    serviceVO.setServiceName(serviceName);
		    serviceVO.setDescription(description);
		    serviceVO.setHourlyRate(hourlyRate);
		    serviceVO.setStatus(status);

		    if (!errorMsgs.isEmpty()) {
		        req.setAttribute("serviceVO", serviceVO);

		        RequestDispatcher failureView =
		                req.getRequestDispatcher("/frontend/service/addService.jsp");

		        failureView.forward(req, res);
		        return;
		    }

//		    ServiceService serviceSvc = new ServiceService();

		    serviceVO = serviceSvc.add(
		            serviceTypeId,
		            memberId,
		            serviceName.trim(),
		            description.trim(),
		            hourlyRate,
		            status,
		            LocalDateTime.now()
		    );

		    req.setAttribute("serviceVO", serviceVO);

		    RequestDispatcher successView =
		            req.getRequestDispatcher("/frontend/service/listOneService.jsp");

		    successView.forward(req, res);
		    return;
		}

		if ("getOne_For_Update".equals(action)) {
			Integer serviceId = Integer.valueOf(req.getParameter("serviceId"));

//			ServiceService serviceSvc = new ServiceService();

			ServiceVO serviceVO = serviceSvc.getOneService(serviceId);

			req.setAttribute("serviceVO", serviceVO);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/service/update_service_input.jsp");

			successView.forward(req, res);
			return;
		}

		if ("update".equals(action)) {
			Integer serviceId = Integer.valueOf(req.getParameter("serviceId"));
			Integer serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));
			Integer memberId = Integer.valueOf(req.getParameter("memberId"));
			String serviceName = req.getParameter("serviceName");
			String description = req.getParameter("description");
			Integer hourlyRate = Integer.valueOf(req.getParameter("hourlyRate"));
			Byte status = Byte.valueOf(req.getParameter("status"));

//			ServiceService serviceSvc = new ServiceService();

			ServiceVO serviceVO = serviceSvc.update(serviceId, serviceTypeId, memberId, serviceName, description,
					hourlyRate, status);

			req.setAttribute("serviceVO", serviceVO);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/service/listOneService.jsp");

			successView.forward(req, res);
			return;
		}

		if ("delete".equals(action)) {
			Integer serviceId = Integer.valueOf(req.getParameter("serviceId"));

//			ServiceService serviceSvc = new ServiceService();

			serviceSvc.delete(serviceId);

			List<ServiceVO> list = serviceSvc.getAll();

			req.setAttribute("serviceList", list);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/service/listAllService.jsp");

			successView.forward(req, res);
			return;
		}

		if ("getServices_By_ServiceType".equals(action)) {

			Integer serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));

//			ServiceService serviceSvc = new ServiceService();
			List<ServiceVO> serviceList = serviceSvc.getServicesByServiceTypeId(serviceTypeId);

			req.setAttribute("serviceList", serviceList);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/service/listServicesByType.jsp");
			successView.forward(req, res);
			return;
		}
		if ("toServiceSelectPage".equals(action)) {

			System.out.println("進入 toServiceSelectPage");

//			ServiceTypeService serviceTypeSvc = new ServiceTypeService();
			List<ServiceTypeVO> list = serviceTypeSvc.getAll();

			System.out.println("服務類型數量 = " + list.size());

			req.setAttribute("serviceTypeList", list);

			RequestDispatcher successView = req.getRequestDispatcher("/frontend/service/select_page.jsp");
			successView.forward(req, res);
			return;
		}
	}
}