package com.servicetype.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.servicetype.model.ServiceTypeService;
import com.servicetype.model.ServiceTypeVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/servicetype/servicetype.do")
public class ServiceTypeServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if ("getAll".equals(action)) {
            ServiceTypeService serviceTypeSvc = new ServiceTypeService();
            List<ServiceTypeVO> list = serviceTypeSvc.getAll();

            req.setAttribute("serviceTypeList", list);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/listAllServiceType.jsp");

            successView.forward(req, res);
            return;
        }

        if ("getOne_For_Display".equals(action)) {
        	Map<String,String> errorMsgs =  new HashMap<>();
        	req.setAttribute("errorMsgs", errorMsgs);
        	
        	String str = req.getParameter("serviceTypeId");
        	
        	if(str == null || str.trim().length()==0) {
        		errorMsgs.put("serviceTypeId", "請輸入服務類型編號");
        	}
        	
        	Integer serviceTypeId = null;
        	
        	if(errorMsgs.isEmpty()) {
        		try {
        			serviceTypeId=Integer.valueOf(str.trim());
        			}catch (NumberFormatException e){
        				errorMsgs.put("serviceTypeId", "服務類型編號只能輸入數字");
        			}
        		}
        	
        	if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView =
                    req.getRequestDispatcher("/frontend/servicetype/select_page.jsp");
                failureView.forward(req, res);
                return;
            }
        	
            serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));

            ServiceTypeService serviceTypeSvc = new ServiceTypeService();
            ServiceTypeVO serviceTypeVO = serviceTypeSvc.findByPK(serviceTypeId);

            req.setAttribute("serviceTypeVO", serviceTypeVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/listOneServiceType.jsp");
            successView.forward(req, res);
        }
        
        if("insert".equals(action)) {
        	Map<String,String>errorMsgs = new LinkedHashMap<>();
        	req.setAttribute("errorMsgs", errorMsgs);
            String typeName = req.getParameter("typeName");
            
            if (typeName == null || typeName.trim().length() == 0) {
                errorMsgs.put("typeName", "服務類型名稱請勿空白");
            }

            String description = req.getParameter("description");
            if (description == null || description.trim().length() == 0) {
                errorMsgs.put("description", "服務類型描述請勿空白");
            }

            Integer typeMode = null;
            try {
                typeMode = Integer.valueOf(req.getParameter("typeMode"));
            } catch (Exception e) {
                errorMsgs.put("typeMode", "請選擇服務類型狀態");
            }

            String imgURL = req.getParameter("imgURL");
            if (imgURL == null || imgURL.trim().length() == 0) {
                errorMsgs.put("imgURL", "圖片路徑請勿空白");
            }
            
            ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
            serviceTypeVO.setTypeName(typeName);
            serviceTypeVO.setDescrip(description);
            serviceTypeVO.setTypeMode(typeMode);
            serviceTypeVO.setImgURL(imgURL);
            
            if (!errorMsgs.isEmpty()) {
                req.setAttribute("serviceTypeVO", serviceTypeVO);
                RequestDispatcher failureView =
                        req.getRequestDispatcher("/frontend/servicetype/addServiceType.jsp");
                failureView.forward(req, res);
                return;
            }
        	ServiceTypeService serTypeSvc = new ServiceTypeService();
            serviceTypeVO = serTypeSvc.add(
                    typeName,
                    description,
                    typeMode,
                    imgURL
            );
            //測試用
            System.out.println("新增後的 serviceTypeId = " + serviceTypeVO.getSvcTypeID());
            req.setAttribute("serviceTypeVO", serviceTypeVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/listOneServiceType.jsp");
            successView.forward(req, res);
            return;
        }
        
        if ("getOne_For_Update".equals(action)) {
            Integer serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));
            
            ServiceTypeService serviceTypeSvc = new ServiceTypeService();
            ServiceTypeVO serviceTypeVO = serviceTypeSvc.findByPK(serviceTypeId);

            req.setAttribute("serviceTypeVO", serviceTypeVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/update_service_type_input.jsp");
            successView.forward(req, res);
            return;
        }
        if ("delete".equals(action)) {
            Integer serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));

            ServiceTypeService serviceTypeSvc = new ServiceTypeService();
            serviceTypeSvc.delete(serviceTypeId);

            List<ServiceTypeVO> list = serviceTypeSvc.getAll();
            req.setAttribute("serviceTypeList", list);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/listAllServiceType.jsp");
            successView.forward(req, res);
            return;
        }
        
        if ("update".equals(action)) {
        	
        	Integer serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));
            String typeName = req.getParameter("typeName");
            String description = req.getParameter("description");
            Integer typeMode = Integer.valueOf(req.getParameter("typeMode"));
            String imgURL = req.getParameter("imgURL");

            ServiceTypeService serviceTypeSvc = new ServiceTypeService();

            ServiceTypeVO serviceTypeVO = serviceTypeSvc.update(
                    serviceTypeId,
                    typeName,
                    description,
                    typeMode,
                    imgURL
            );

            req.setAttribute("serviceTypeVO", serviceTypeVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/listOneServiceType.jsp");
            successView.forward(req, res);
            return;
        }
    }
}