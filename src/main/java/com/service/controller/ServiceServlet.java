package com.service.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        
        if (action == null) {
            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/service/select_page.jsp");
            successView.forward(req, res);
            return;
        }

        if ("getAll".equals(action)) {
            ServiceService serviceSvc = new ServiceService();

            List<ServiceVO> list = serviceSvc.getAll();

            req.setAttribute("serviceList", list);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/service/listAllService.jsp");

            successView.forward(req, res);
            return;
        }

        if ("getOne_For_Display".equals(action)) {
            Integer serviceId = Integer.valueOf(req.getParameter("serviceId"));

            ServiceService serviceSvc = new ServiceService();

            ServiceVO serviceVO = serviceSvc.getOneService(serviceId);

            req.setAttribute("serviceVO", serviceVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/service/listOneService.jsp");

            successView.forward(req, res);
            return;
        }

        if ("insert".equals(action)) {
            Integer serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));
            Integer memberId = Integer.valueOf(req.getParameter("memberId"));
            String serviceName = req.getParameter("serviceName");
            String description = req.getParameter("description");
            Integer hourlyRate = Integer.valueOf(req.getParameter("hourlyRate"));
            Byte status = Byte.valueOf(req.getParameter("status"));

            ServiceService serviceSvc = new ServiceService();

            ServiceVO serviceVO = serviceSvc.add(
                    serviceTypeId,
                    memberId,
                    serviceName,
                    description,
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

            ServiceService serviceSvc = new ServiceService();

            ServiceVO serviceVO = serviceSvc.getOneService(serviceId);

            req.setAttribute("serviceVO", serviceVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/service/update_service_input.jsp");

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

            ServiceService serviceSvc = new ServiceService();

            ServiceVO serviceVO = serviceSvc.update(
                    serviceId,
                    serviceTypeId,
                    memberId,
                    serviceName,
                    description,
                    hourlyRate,
                    status
            );

            req.setAttribute("serviceVO", serviceVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/service/listOneService.jsp");

            successView.forward(req, res);
            return;
        }

        if ("delete".equals(action)) {
            Integer serviceId = Integer.valueOf(req.getParameter("serviceId"));

            ServiceService serviceSvc = new ServiceService();

            serviceSvc.delete(serviceId);

            List<ServiceVO> list = serviceSvc.getAll();

            req.setAttribute("serviceList", list);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/service/listAllService.jsp");

            successView.forward(req, res);
            return;
        }
        
        if ("getServices_By_ServiceType".equals(action)) {

            Integer serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));

            ServiceService serviceSvc = new ServiceService();
            List<ServiceVO> serviceList = serviceSvc.getServicesByServiceTypeId(serviceTypeId);

            req.setAttribute("serviceList", serviceList);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/service/listServicesByType.jsp");
            successView.forward(req, res);
            return;
        }
        if ("toServiceSelectPage".equals(action)) {

            System.out.println("進入 toServiceSelectPage");

            ServiceTypeService serviceTypeSvc = new ServiceTypeService();
            List<ServiceTypeVO> list = serviceTypeSvc.getAll();

            System.out.println("服務類型數量 = " + list.size());

            req.setAttribute("serviceTypeList", list);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/service/select_page.jsp");
            successView.forward(req, res);
            return;
        }
    }
}