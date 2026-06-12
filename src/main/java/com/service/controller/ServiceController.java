package com.service.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.service.model.ServiceService;
import com.service.model.ServiceVO;
import com.servicetype.model.ServiceTypeService;
import com.servicetype.model.ServiceTypeVO;

@Controller
@RequestMapping("/service")
public class ServiceController {

	private final ServiceService serviceSvc;
	private final ServiceTypeService serviceTypeSvc;

	public ServiceController(ServiceService serviceSvc, ServiceTypeService serviceTypeSvc) {
		this.serviceSvc = serviceSvc;
		this.serviceTypeSvc = serviceTypeSvc;
	}

	// 進入 service 查詢頁
	@GetMapping("/select")
	public String selectPage(Model model) {

		List<ServiceTypeVO> serviceTypeList = serviceTypeSvc.getAll();

		model.addAttribute("serviceTypeList", serviceTypeList);

		return "frontend/service/select_page";
	}

	// 查全部 service
	@GetMapping("/list")
	public String listAll(Model model) {

		List<ServiceVO> serviceList = serviceSvc.getAll();

		model.addAttribute("serviceList", serviceList);

		return "frontend/service/listAllService";
	}

	// 依服務類型查 service
	@GetMapping("/type")
	public String listByServiceType(@RequestParam("serviceTypeId") Integer serviceTypeId, Model model) {

		List<ServiceVO> serviceList = serviceSvc.getServicesByServiceTypeId(serviceTypeId);

		model.addAttribute("serviceList", serviceList);

		return "frontend/service/listServicesByType";
	}

	@GetMapping("/one")
	public String getOneForDisplay(@RequestParam(value = "serviceId", required = false) String serviceIdStr,
			Model model) {

		Map<String, String> errorMsgs = new LinkedHashMap<>();

		Integer serviceId = null;

		if (serviceIdStr == null || serviceIdStr.trim().isEmpty()) {
			errorMsgs.put("serviceId", "請輸入服務編號");
		}

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

		if (!errorMsgs.isEmpty()) {
			model.addAttribute("errorMsgs", errorMsgs);

			List<ServiceTypeVO> serviceTypeList = serviceTypeSvc.getAll();
			model.addAttribute("serviceTypeList", serviceTypeList);

			return "frontend/service/select_page";
		}

		ServiceVO serviceVO = serviceSvc.getOneService(serviceId);

		if (serviceVO == null) {
			errorMsgs.put("serviceId", "查無此服務編號");

			model.addAttribute("errorMsgs", errorMsgs);

			List<ServiceTypeVO> serviceTypeList = serviceTypeSvc.getAll();
			model.addAttribute("serviceTypeList", serviceTypeList);

			return "frontend/service/select_page";
		}

		model.addAttribute("serviceVO", serviceVO);

		return "frontend/service/listOneService";
	}

	@PostMapping("/insert")
	public String insert(@RequestParam(value = "serviceTypeId", required = false) String serviceTypeIdStr,
			@RequestParam(value = "memberId", required = false) String memberIdStr,
			@RequestParam(value = "serviceName", required = false) String serviceName,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "hourlyRate", required = false) String hourlyRateStr,
			@RequestParam(value = "status", required = false) String statusStr, Model model) {

		Map<String, String> errorMsgs = new LinkedHashMap<>();

		Integer serviceTypeId = null;
		Integer memberId = null;
		Integer hourlyRate = null;
		Byte status = null;

		// 服務類型
		try {
			serviceTypeId = Integer.valueOf(serviceTypeIdStr);
			if (serviceTypeId <= 0) {
				errorMsgs.put("serviceTypeId", "服務類型編號必須大於 0");
			}
		} catch (Exception e) {
			errorMsgs.put("serviceTypeId", "請選擇服務類型");
		}

		// 會員編號
		try {
			memberId = Integer.valueOf(memberIdStr);
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
			hourlyRate = Integer.valueOf(hourlyRateStr);
			if (hourlyRate < 0) {
				errorMsgs.put("hourlyRate", "每小時費率不可小於 0");
			}
		} catch (Exception e) {
			errorMsgs.put("hourlyRate", "每小時費率必須是整數");
		}

		// 狀態
		try {
			status = Byte.valueOf(statusStr);
			if (status != 0 && status != 1) {
				errorMsgs.put("status", "服務狀態只能是 0 或 1");
			}
		} catch (Exception e) {
			errorMsgs.put("status", "請選擇服務狀態");
		}

		ServiceVO serviceVO = new ServiceVO();
		serviceVO.setServiceTypeId(serviceTypeId);
		serviceVO.setMemberId(memberId);
		serviceVO.setServiceName(serviceName);
		serviceVO.setDescription(description);
		serviceVO.setHourlyRate(hourlyRate);
		serviceVO.setStatus(status);

		if (!errorMsgs.isEmpty()) {
			model.addAttribute("errorMsgs", errorMsgs);
			model.addAttribute("serviceVO", serviceVO);
			model.addAttribute("serviceTypeList", serviceTypeSvc.getAll());

			return "frontend/service/addService";
		}

		serviceVO = serviceSvc.add(serviceTypeId, memberId, serviceName.trim(), description.trim(), hourlyRate, status,
				LocalDateTime.now());

		model.addAttribute("serviceVO", serviceVO);

		return "frontend/service/listOneService";
	}

	@GetMapping("/add")
	public String addPage(Model model) {

		model.addAttribute("serviceTypeList", serviceTypeSvc.getAll());

		return "frontend/service/addService";
	}

	@GetMapping("/edit")
	public String editPage(@RequestParam("serviceId") Integer serviceId, Model model) {

		ServiceVO serviceVO = serviceSvc.getOneService(serviceId);

		model.addAttribute("serviceVO", serviceVO);
		model.addAttribute("serviceTypeList", serviceTypeSvc.getAll());

		return "frontend/service/update_service_input";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam("serviceId") Integer serviceId) {

		serviceSvc.delete(serviceId);

		return "redirect:/service/list";
	}
	
	@PostMapping("/update")
	public String update(
	        @RequestParam("serviceId") Integer serviceId,
	        @RequestParam("serviceTypeId") Integer serviceTypeId,
	        @RequestParam("memberId") Integer memberId,
	        @RequestParam("serviceName") String serviceName,
	        @RequestParam("description") String description,
	        @RequestParam("hourlyRate") Integer hourlyRate,
	        @RequestParam("status") Byte status,
	        Model model) {

	    ServiceVO serviceVO = serviceSvc.update(
	            serviceId,
	            serviceTypeId,
	            memberId,
	            serviceName,
	            description,
	            hourlyRate,
	            status
	    );

	    model.addAttribute("serviceVO", serviceVO);

	    return "frontend/service/listOneService";
	}
	
}