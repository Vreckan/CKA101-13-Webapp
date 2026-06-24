package com.servicetype.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.servicetype.model.ServiceTypeService;
import com.servicetype.model.ServiceTypeVO;

@Controller
@RequestMapping("/servicetype")
public class ServiceTypeController {
	@Autowired
    private final ServiceTypeService serviceTypeSvc;
	@Autowired
    public ServiceTypeController(ServiceTypeService serviceTypeSvc) {
        this.serviceTypeSvc = serviceTypeSvc;
    }

    // 進入服務類型管理頁
    @GetMapping("/select")
    public String selectPage() {
        return "frontend/servicetype/select_page";
    }

    // 查全部服務類型
    @GetMapping("/list")
    public String listAll(Model model) {
        List<ServiceTypeVO> serviceTypeList = serviceTypeSvc.getAll();
        model.addAttribute("serviceTypeList", serviceTypeList);

        return "frontend/servicetype/listAllServiceType";
    }

    // 查單一服務類型
    @GetMapping("/one")
    public String getOneForDisplay(
            @RequestParam(value = "serviceTypeId", required = false) String serviceTypeIdStr,
            Model model) {

        Map<String, String> errorMsgs = new LinkedHashMap<>();

        Integer serviceTypeId = null;

        if (serviceTypeIdStr == null || serviceTypeIdStr.trim().isEmpty()) {
            errorMsgs.put("serviceTypeId", "請輸入服務類型編號");
        }

        if (errorMsgs.isEmpty()) {
            try {
                serviceTypeId = Integer.valueOf(serviceTypeIdStr.trim());

                if (serviceTypeId <= 0) {
                    errorMsgs.put("serviceTypeId", "服務類型編號必須大於 0");
                }

            } catch (NumberFormatException e) {
                errorMsgs.put("serviceTypeId", "服務類型編號只能輸入數字");
            }
        }

        if (!errorMsgs.isEmpty()) {
            model.addAttribute("errorMsgs", errorMsgs);
            return "frontend/servicetype/select_page";
        }

        ServiceTypeVO serviceTypeVO = serviceTypeSvc.findByPK(serviceTypeId);

        if (serviceTypeVO == null) {
            errorMsgs.put("serviceTypeId", "查無此服務類型編號");
            model.addAttribute("errorMsgs", errorMsgs);
            return "frontend/servicetype/select_page";
        }

        model.addAttribute("serviceTypeVO", serviceTypeVO);

        return "frontend/servicetype/listOneServiceType";
    }

    // 進入新增頁
    @GetMapping("/add")
    public String addPage() {
        return "frontend/servicetype/addServiceType";
    }

    // 新增服務類型
    @PostMapping("/insert")
    public String insert(
            @RequestParam(value = "typeName", required = false) String typeName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "typeMode", required = false) String typeModeStr,
            @RequestParam(value = "imgURL", required = false) String imgURL,
            Model model) {

        Map<String, String> errorMsgs = new LinkedHashMap<>();

        Integer typeMode = null;

        if (typeName == null || typeName.trim().isEmpty()) {
            errorMsgs.put("typeName", "服務類型名稱請勿空白");
        } else if (typeName.trim().length() > 50) {
            errorMsgs.put("typeName", "服務類型名稱不可超過 50 字");
        }

        if (description == null || description.trim().isEmpty()) {
            errorMsgs.put("description", "服務類型描述請勿空白");
        } else if (description.trim().length() > 255) {
            errorMsgs.put("description", "服務類型描述不可超過 255 字");
        }

        if (typeModeStr == null || typeModeStr.trim().isEmpty()) {
            errorMsgs.put("typeMode", "請選擇服務類型狀態");
        } else {
            try {
                typeMode = Integer.valueOf(typeModeStr.trim());

                if (typeMode != 0 && typeMode != 1) {
                    errorMsgs.put("typeMode", "類型狀態只能是 0 或 1");
                }

            } catch (NumberFormatException e) {
                errorMsgs.put("typeMode", "類型狀態格式錯誤");
            }
        }

        if (imgURL == null || imgURL.trim().isEmpty()) {
            errorMsgs.put("imgURL", "圖片路徑請勿空白");
        } else if (imgURL.trim().length() > 255) {
            errorMsgs.put("imgURL", "圖片路徑不可超過 255 字");
        } else if (!imgURL.trim().matches("^/images/service/.+\\.(jpg|jpeg|png|gif|webp)$")) {
            errorMsgs.put("imgURL", "圖片路徑格式錯誤，例如：/images/service/walk.jpg");
        }

        ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
        serviceTypeVO.setTypeName(typeName);
        serviceTypeVO.setDescrip(description);
        serviceTypeVO.setTypeMode(typeMode);
        serviceTypeVO.setImgURL(imgURL);

        if (!errorMsgs.isEmpty()) {
            model.addAttribute("errorMsgs", errorMsgs);
            model.addAttribute("serviceTypeVO", serviceTypeVO);

            return "frontend/servicetype/addServiceType";
        }

        serviceTypeVO = serviceTypeSvc.add(
                typeName.trim(),
                description.trim(),
                typeMode,
                imgURL.trim()
        );

        model.addAttribute("serviceTypeVO", serviceTypeVO);

        return "frontend/servicetype/listOneServiceType";
    }

    // 進入修改頁
    @GetMapping("/edit")
    public String editPage(
            @RequestParam("serviceTypeId") Integer serviceTypeId,
            Model model) {

        ServiceTypeVO serviceTypeVO = serviceTypeSvc.findByPK(serviceTypeId);

        model.addAttribute("serviceTypeVO", serviceTypeVO);

        return "frontend/servicetype/update_service_type_input";
    }

    // 修改服務類型
    @PostMapping("/update")
    public String update(
            @RequestParam(value = "serviceTypeId", required = false) String serviceTypeIdStr,
            @RequestParam(value = "typeName", required = false) String typeName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "typeMode", required = false) String typeModeStr,
            @RequestParam(value = "imgURL", required = false) String imgURL,
            Model model) {

        Map<String, String> errorMsgs = new LinkedHashMap<>();

        Integer serviceTypeId = null;
        Integer typeMode = null;

        if (serviceTypeIdStr == null || serviceTypeIdStr.trim().isEmpty()) {
            errorMsgs.put("serviceTypeId", "服務類型編號遺失");
        } else {
            try {
                serviceTypeId = Integer.valueOf(serviceTypeIdStr.trim());
            } catch (NumberFormatException e) {
                errorMsgs.put("serviceTypeId", "服務類型編號格式錯誤");
            }
        }

        if (typeName == null || typeName.trim().isEmpty()) {
            errorMsgs.put("typeName", "服務類型名稱請勿空白");
        } else if (typeName.trim().length() > 50) {
            errorMsgs.put("typeName", "服務類型名稱不可超過 50 字");
        }

        if (description == null || description.trim().isEmpty()) {
            errorMsgs.put("description", "服務類型描述請勿空白");
        } else if (description.trim().length() > 255) {
            errorMsgs.put("description", "服務類型描述不可超過 255 字");
        }

        if (typeModeStr == null || typeModeStr.trim().isEmpty()) {
            errorMsgs.put("typeMode", "請選擇服務類型狀態");
        } else {
            try {
                typeMode = Integer.valueOf(typeModeStr.trim());

                if (typeMode != 0 && typeMode != 1) {
                    errorMsgs.put("typeMode", "類型狀態只能是 0 或 1");
                }

            } catch (NumberFormatException e) {
                errorMsgs.put("typeMode", "類型狀態格式錯誤");
            }
        }

        if (imgURL == null || imgURL.trim().isEmpty()) {
            errorMsgs.put("imgURL", "圖片路徑請勿空白");
        } else if (imgURL.trim().length() > 255) {
            errorMsgs.put("imgURL", "圖片路徑不可超過 255 字");
        } else if (!imgURL.trim().matches("^/images/service/.+\\.(jpg|jpeg|png|gif|webp)$")) {
            errorMsgs.put("imgURL", "圖片路徑格式錯誤，例如：/images/service/walk.jpg");
        }

        ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
        serviceTypeVO.setSvcTypeID(serviceTypeId);
        serviceTypeVO.setTypeName(typeName);
        serviceTypeVO.setDescrip(description);
        serviceTypeVO.setTypeMode(typeMode);
        serviceTypeVO.setImgURL(imgURL);

        if (!errorMsgs.isEmpty()) {
            model.addAttribute("errorMsgs", errorMsgs);
            model.addAttribute("serviceTypeVO", serviceTypeVO);

            return "frontend/servicetype/update_service_type_input";
        }

        serviceTypeVO = serviceTypeSvc.update(
                serviceTypeId,
                typeName.trim(),
                description.trim(),
                typeMode,
                imgURL.trim()
        );

        model.addAttribute("serviceTypeVO", serviceTypeVO);

        return "frontend/servicetype/listOneServiceType";
    }

    // 刪除服務類型
    @PostMapping("/delete")
    public String delete(
            @RequestParam("serviceTypeId") Integer serviceTypeId) {

        serviceTypeSvc.delete(serviceTypeId);

        return "redirect:/servicetype/list";
    }
}