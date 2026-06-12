package com.service.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String listByServiceType(
            @RequestParam("serviceTypeId") Integer serviceTypeId,
            Model model) {

        List<ServiceVO> serviceList =
                serviceSvc.getServicesByServiceTypeId(serviceTypeId);

        model.addAttribute("serviceList", serviceList);

        return "frontend/service/listServicesByType";
    }
}