package com.service.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.model.ServiceService;
import com.service.model.ServiceVO;
import com.servicetype.model.ServiceTypeService;
import com.servicetype.model.ServiceTypeVO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceApiController {

    private final ServiceService serviceSvc;
    private final ServiceTypeService serviceTypeSvc;

    public ServiceApiController(ServiceService serviceSvc, ServiceTypeService serviceTypeSvc) {
        this.serviceSvc = serviceSvc;
        this.serviceTypeSvc = serviceTypeSvc;
    }

    // 查全部服務
    @GetMapping("/services")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<ServiceDTO> list = serviceSvc.getAll()
                .stream()
                .map(this::toServiceDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    // 查單一服務
    @GetMapping("/services/{serviceId}")
    public ResponseEntity<?> getOneService(@PathVariable Integer serviceId) {
        ServiceVO serviceVO = serviceSvc.getOneService(serviceId);

        if (serviceVO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("查無此服務"));
        }

        return ResponseEntity.ok(toServiceDTO(serviceVO));
    }

    // 依服務類型查服務
    @GetMapping("/services/by-type/{serviceTypeId}")
    public ResponseEntity<List<ServiceDTO>> getServicesByType(@PathVariable Integer serviceTypeId) {
        List<ServiceDTO> list = serviceSvc.getServicesByServiceTypeId(serviceTypeId)
                .stream()
                .map(this::toServiceDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    // 新增服務
    @PostMapping("/services")
    public ResponseEntity<?> insertService(@RequestBody ServiceRequest request) {
        Map<String, String> errors = validateServiceRequest(request);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorMapResponse(errors));
        }

        ServiceVO serviceVO = serviceSvc.add(
                request.serviceTypeId,
                request.memberId,
                request.serviceName.trim(),
                request.description.trim(),
                request.hourlyRate,
                request.status.byteValue(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toServiceDTO(serviceVO));
    }

    // 修改服務
    @PutMapping("/services/{serviceId}")
    public ResponseEntity<?> updateService(
            @PathVariable Integer serviceId,
            @RequestBody ServiceRequest request) {

        Map<String, String> errors = validateServiceRequest(request);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorMapResponse(errors));
        }

        ServiceVO oldService = serviceSvc.getOneService(serviceId);

        if (oldService == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("查無此服務，無法修改"));
        }

        ServiceVO serviceVO = serviceSvc.update(
                serviceId,
                request.serviceTypeId,
                request.memberId,
                request.serviceName.trim(),
                request.description.trim(),
                request.hourlyRate,
                request.status.byteValue()
        );

        return ResponseEntity.ok(toServiceDTO(serviceVO));
    }

    // 刪除服務
    @DeleteMapping("/services/{serviceId}")
    public ResponseEntity<?> deleteService(@PathVariable Integer serviceId) {
        ServiceVO oldService = serviceSvc.getOneService(serviceId);

        if (oldService == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("查無此服務，無法刪除"));
        }

        serviceSvc.delete(serviceId);

        return ResponseEntity.noContent().build();
    }

	//    // 查全部服務類型，給 Vue 下拉選單用
	//    @GetMapping("/service-types")
	//    public ResponseEntity<List<ServiceTypeDTO>> getAllServiceTypes() {
	//        List<ServiceTypeDTO> list = serviceTypeSvc.getAll()
	//                .stream()
	//                .map(this::toServiceTypeDTO)
	//                .collect(Collectors.toList());
	//
	//        return ResponseEntity.ok(list);
	//    }

    private Map<String, String> validateServiceRequest(ServiceRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();

        if (request == null) {
            errors.put("request", "請提供服務資料");
            return errors;
        }

        if (request.serviceTypeId == null || request.serviceTypeId <= 0) {
            errors.put("serviceTypeId", "請選擇服務類型");
        }

        if (request.memberId == null || request.memberId <= 0) {
            errors.put("memberId", "會員編號必須大於 0");
        }

        if (request.serviceName == null || request.serviceName.trim().isEmpty()) {
            errors.put("serviceName", "服務名稱請勿空白");
        } else if (request.serviceName.trim().length() > 50) {
            errors.put("serviceName", "服務名稱不可超過 50 個字");
        }

        if (request.description == null || request.description.trim().isEmpty()) {
            errors.put("description", "服務描述請勿空白");
        }

        if (request.hourlyRate == null) {
            errors.put("hourlyRate", "每小時費率必須是整數");
        } else if (request.hourlyRate < 0) {
            errors.put("hourlyRate", "每小時費率不可小於 0");
        }

        if (request.status == null) {
            errors.put("status", "請選擇服務狀態");
        } else if (request.status != 0 && request.status != 1) {
            errors.put("status", "服務狀態只能是 0 或 1");
        }

        return errors;
    }

    private ServiceDTO toServiceDTO(ServiceVO serviceVO) {
        ServiceDTO dto = new ServiceDTO();

        dto.serviceId = serviceVO.getServiceId();
        dto.serviceTypeId = serviceVO.getServiceTypeId();
        dto.memberId = serviceVO.getMemberId();
        dto.serviceName = serviceVO.getServiceName();
        dto.description = serviceVO.getDescription();
        dto.hourlyRate = serviceVO.getHourlyRate();
        dto.status = serviceVO.getStatus();

        if (serviceVO.getCreatedAt() != null) {
            dto.createdAt = serviceVO.getCreatedAt().toString();
        }

        return dto;
    }

    private ServiceTypeDTO toServiceTypeDTO(ServiceTypeVO serviceTypeVO) {
        ServiceTypeDTO dto = new ServiceTypeDTO();

        dto.svcTypeID = serviceTypeVO.getSvcTypeID();
        dto.typeName = serviceTypeVO.getTypeName();
        dto.descrip = serviceTypeVO.getDescrip();
        dto.typeMode = serviceTypeVO.getTypeMode();
        dto.imgURL = serviceTypeVO.getImgURL();

        return dto;
    }

    public static class ServiceRequest {
        public Integer serviceTypeId;
        public Integer memberId;
        public String serviceName;
        public String description;
        public Integer hourlyRate;
        public Integer status;
    }

    public static class ServiceDTO {
        public Integer serviceId;
        public Integer serviceTypeId;
        public Integer memberId;
        public String serviceName;
        public String description;
        public Integer hourlyRate;
        public Byte status;
        public String createdAt;
    }

    public static class ServiceTypeDTO {
        public Integer svcTypeID;
        public String typeName;
        public String descrip;
        public Integer typeMode;
        public String imgURL;
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    public static class ErrorMapResponse {
        public Map<String, String> errors;

        public ErrorMapResponse(Map<String, String> errors) {
            this.errors = errors;
        }
    }
}