package com.serviceslot.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.serviceslot.model.ServiceSlotService;
import com.serviceslot.model.ServiceSlotVO;

@Controller
@RequestMapping("/serviceslot")
public class ServiceSlotController {
	@Autowired
    private final ServiceSlotService serviceSlotSvc;
	@Autowired
    public ServiceSlotController(ServiceSlotService serviceSlotSvc) {
        this.serviceSlotSvc = serviceSlotSvc;
    }

    // 進入服務時段管理首頁
    @GetMapping("/select")
    public String selectPage() {
        return "frontend/serviceslot/select_page";
    }

    // 查全部服務時段
    @GetMapping("/list")
    public String listAll(Model model) {
        List<ServiceSlotVO> serviceSlotList = serviceSlotSvc.getAll();

        model.addAttribute("serviceSlotList", serviceSlotList);

        return "frontend/serviceslot/listAllServiceSlot";
    }

    // 查單一服務時段
    @GetMapping("/one")
    public String getOneForDisplay(
            @RequestParam(value = "serviceSlotId", required = false) String serviceSlotIdStr,
            Model model) {

        Map<String, String> errorMsgs = new LinkedHashMap<>();

        Integer serviceSlotId = parseIntegerValue(
                serviceSlotIdStr,
                "serviceSlotId",
                "服務時段編號",
                errorMsgs
        );

        if (!errorMsgs.isEmpty()) {
            model.addAttribute("errorMsgs", errorMsgs);
            return "frontend/serviceslot/select_page";
        }

        ServiceSlotVO serviceSlotVO = serviceSlotSvc.getOneServiceSlot(serviceSlotId);

        if (serviceSlotVO == null) {
            errorMsgs.put("serviceSlotId", "查無此服務時段編號");

            model.addAttribute("errorMsgs", errorMsgs);

            return "frontend/serviceslot/select_page";
        }

        model.addAttribute("serviceSlotVO", serviceSlotVO);

        return "frontend/serviceslot/listOneServiceSlot";
    }

    // 依 serviceId 查詢該服務底下的所有時段
    @GetMapping("/service")
    public String listByServiceId(
            @RequestParam(value = "serviceId", required = false) String serviceIdStr,
            Model model) {

        Map<String, String> errorMsgs = new LinkedHashMap<>();

        Integer serviceId = parseIntegerValue(
                serviceIdStr,
                "serviceId",
                "服務編號",
                errorMsgs
        );

        if (!errorMsgs.isEmpty()) {
            model.addAttribute("errorMsgs", errorMsgs);
            return "frontend/serviceslot/select_page";
        }

        List<ServiceSlotVO> serviceSlotList = serviceSlotSvc.getByServiceId(serviceId);

        model.addAttribute("serviceSlotList", serviceSlotList);
        model.addAttribute("serviceId", serviceId);

        return "frontend/serviceslot/listServiceSlotByService";
    }

    // 進入新增頁
    @GetMapping("/add")
    public String addPage() {
        return "frontend/serviceslot/addServiceSlot";
    }

    // 新增服務時段
    @PostMapping("/insert")
    public String insert(
            @RequestParam(value = "serviceId", required = false) String serviceIdStr,
            @RequestParam(value = "startTime", required = false) String startTimeStr,
            @RequestParam(value = "endTime", required = false) String endTimeStr,
            @RequestParam(value = "slotStatus", required = false) String slotStatusStr,
            @RequestParam(value = "lockExpiresAt", required = false) String lockExpiresAtStr,
            Model model) {

        Map<String, String> errorMsgs = new LinkedHashMap<>();

        Integer serviceId = parseIntegerValue(serviceIdStr, "serviceId", "服務編號", errorMsgs);
        LocalDateTime startTime = parseDateTimeValue(startTimeStr, "startTime", "開始時間", errorMsgs);
        LocalDateTime endTime = parseDateTimeValue(endTimeStr, "endTime", "結束時間", errorMsgs);
        Byte slotStatus = parseByteValue(slotStatusStr, "slotStatus", "時段狀態", errorMsgs);
        LocalDateTime lockExpiresAt = parseDateTimeAllowEmptyValue(
                lockExpiresAtStr,
                "lockExpiresAt",
                "鎖定到期時間",
                errorMsgs
        );

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
            model.addAttribute("errorMsgs", errorMsgs);
            model.addAttribute("serviceSlotVO", serviceSlotVO);

            return "frontend/serviceslot/addServiceSlot";
        }

        serviceSlotVO = serviceSlotSvc.add(
                serviceId,
                startTime,
                endTime,
                slotStatus,
                lockExpiresAt
        );

        model.addAttribute("serviceSlotVO", serviceSlotVO);

        return "frontend/serviceslot/listOneServiceSlot";
    }

    // 進入修改頁
    @GetMapping("/edit")
    public String editPage(
            @RequestParam("serviceSlotId") Integer serviceSlotId,
            Model model) {

        ServiceSlotVO serviceSlotVO = serviceSlotSvc.getOneServiceSlot(serviceSlotId);

        model.addAttribute("serviceSlotVO", serviceSlotVO);

        return "frontend/serviceslot/update_service_slot_input";
    }

    // 修改服務時段
    @PostMapping("/update")
    public String update(
            @RequestParam(value = "serviceSlotId", required = false) String serviceSlotIdStr,
            @RequestParam(value = "serviceId", required = false) String serviceIdStr,
            @RequestParam(value = "startTime", required = false) String startTimeStr,
            @RequestParam(value = "endTime", required = false) String endTimeStr,
            @RequestParam(value = "slotStatus", required = false) String slotStatusStr,
            @RequestParam(value = "lockExpiresAt", required = false) String lockExpiresAtStr,
            Model model) {

        Map<String, String> errorMsgs = new LinkedHashMap<>();

        Integer serviceSlotId = parseIntegerValue(
                serviceSlotIdStr,
                "serviceSlotId",
                "服務時段編號",
                errorMsgs
        );

        Integer serviceId = parseIntegerValue(
                serviceIdStr,
                "serviceId",
                "服務編號",
                errorMsgs
        );

        LocalDateTime startTime = parseDateTimeValue(
                startTimeStr,
                "startTime",
                "開始時間",
                errorMsgs
        );

        LocalDateTime endTime = parseDateTimeValue(
                endTimeStr,
                "endTime",
                "結束時間",
                errorMsgs
        );

        Byte slotStatus = parseByteValue(
                slotStatusStr,
                "slotStatus",
                "時段狀態",
                errorMsgs
        );

        LocalDateTime lockExpiresAt = parseDateTimeAllowEmptyValue(
                lockExpiresAtStr,
                "lockExpiresAt",
                "鎖定到期時間",
                errorMsgs
        );

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
            model.addAttribute("errorMsgs", errorMsgs);
            model.addAttribute("serviceSlotVO", serviceSlotVO);

            return "frontend/serviceslot/update_service_slot_input";
        }

        serviceSlotVO = serviceSlotSvc.update(
                serviceSlotId,
                serviceId,
                startTime,
                endTime,
                slotStatus,
                lockExpiresAt
        );

        model.addAttribute("serviceSlotVO", serviceSlotVO);

        return "frontend/serviceslot/listOneServiceSlot";
    }

    // 刪除服務時段
    @PostMapping("/delete")
    public String delete(
            @RequestParam("serviceSlotId") Integer serviceSlotId) {

        serviceSlotSvc.delete(serviceSlotId);

        return "redirect:/serviceslot/list";
    }

    private Integer parseIntegerValue(
            String value,
            String paramName,
            String fieldName,
            Map<String, String> errorMsgs) {

        if (value == null || value.trim().isEmpty()) {
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

    private Byte parseByteValue(
            String value,
            String paramName,
            String fieldName,
            Map<String, String> errorMsgs) {

        if (value == null || value.trim().isEmpty()) {
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

    private LocalDateTime parseDateTimeValue(
            String value,
            String paramName,
            String fieldName,
            Map<String, String> errorMsgs) {

        if (value == null || value.trim().isEmpty()) {
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

    private LocalDateTime parseDateTimeAllowEmptyValue(
            String value,
            String paramName,
            String fieldName,
            Map<String, String> errorMsgs) {

        if (value == null || value.trim().isEmpty()) {
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
            return LocalDateTime.parse(
                    value,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
            );
        }

        if (value.length() == 19 && value.contains("T")) {
            return LocalDateTime.parse(
                    value,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
        }

        return LocalDateTime.parse(
                value,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }
}