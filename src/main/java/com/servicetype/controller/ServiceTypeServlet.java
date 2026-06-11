package com.servicetype.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
	private ServiceTypeService serviceTypeSvc;
	
	@Override
    public void init() throws ServletException {
        WebApplicationContext ctx =
                WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

        serviceTypeSvc = ctx.getBean(ServiceTypeService.class);
    }
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if ("getAll".equals(action)) {
//            ServiceTypeService serviceTypeSvc = new ServiceTypeService();
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

//            ServiceTypeService serviceTypeSvc = new ServiceTypeService();
            ServiceTypeVO serviceTypeVO = serviceTypeSvc.findByPK(serviceTypeId);

            req.setAttribute("serviceTypeVO", serviceTypeVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/listOneServiceType.jsp");
            successView.forward(req, res);
        }
        
        if ("insert".equals(action)) {

            Map<String, String> errorMsgs = new LinkedHashMap<>();
            req.setAttribute("errorMsgs", errorMsgs);

            String typeName = req.getParameter("typeName");
            String description = req.getParameter("description");
            String typeModeStr = req.getParameter("typeMode");
            String imgURL = req.getParameter("imgURL");

            // 類型名稱驗證
            if (typeName == null || typeName.trim().length() == 0) {
                errorMsgs.put("typeName", "服務類型名稱請勿空白");
            } else if (typeName.trim().length() > 50) {
                errorMsgs.put("typeName", "服務類型名稱不可超過 50 字");
            }

            // 類型描述驗證
            if (description == null || description.trim().length() == 0) {
                errorMsgs.put("description", "服務類型描述請勿空白");
            } else if (description.trim().length() > 255) {
                errorMsgs.put("description", "服務類型描述不可超過 255 字");
            }

            // 類型狀態驗證
            Integer typeMode = null;
            if (typeModeStr == null || typeModeStr.trim().length() == 0) {
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

            // 預設圖片路徑驗證
            if (imgURL == null || imgURL.trim().length() == 0) {
                errorMsgs.put("imgURL", "圖片路徑請勿空白");
            } else if (imgURL.trim().length() > 255) {
                errorMsgs.put("imgURL", "圖片路徑不可超過 255 字");
            } else if (!imgURL.trim().matches("^/images/service/.+\\.(jpg|jpeg|png|gif|webp)$")) {
                errorMsgs.put("imgURL", "圖片路徑格式錯誤，例如：/images/service/walk.jpg");
            }

            // 保留使用者輸入的資料
            ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
            serviceTypeVO.setTypeName(typeName);
            serviceTypeVO.setDescrip(description);
            serviceTypeVO.setTypeMode(typeMode);
            serviceTypeVO.setImgURL(imgURL);

            // 有錯誤就回到新增頁
            if (!errorMsgs.isEmpty()) {
                req.setAttribute("serviceTypeVO", serviceTypeVO);

                RequestDispatcher failureView =
                        req.getRequestDispatcher("/frontend/servicetype/addServiceType.jsp");
                failureView.forward(req, res);
                return;
            }

            // 沒錯誤才新增
//            ServiceTypeService serTypeSvc = new ServiceTypeService();

            serviceTypeVO = serviceTypeSvc.add(
                    typeName.trim(),
                    description.trim(),
                    typeMode,
                    imgURL.trim()
            );

            System.out.println("新增後的 serviceTypeId = " + serviceTypeVO.getSvcTypeID());

            req.setAttribute("serviceTypeVO", serviceTypeVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/listOneServiceType.jsp");
            successView.forward(req, res);
            return;
        }
        
        if ("getOne_For_Update".equals(action)) {
            Integer serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));
            
//            ServiceTypeService serviceTypeSvc = new ServiceTypeService();
            ServiceTypeVO serviceTypeVO = serviceTypeSvc.findByPK(serviceTypeId);

            req.setAttribute("serviceTypeVO", serviceTypeVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/update_service_type_input.jsp");
            successView.forward(req, res);
            return;
        }
        if ("delete".equals(action)) {
            Integer serviceTypeId = Integer.valueOf(req.getParameter("serviceTypeId"));

//            ServiceTypeService serviceTypeSvc = new ServiceTypeService();
            serviceTypeSvc.delete(serviceTypeId);

            List<ServiceTypeVO> list = serviceTypeSvc.getAll();
            req.setAttribute("serviceTypeList", list);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/listAllServiceType.jsp");
            successView.forward(req, res);
            return;
        }
        
        if ("update".equals(action)) {

            Map<String, String> errorMsgs = new LinkedHashMap<>();
            req.setAttribute("errorMsgs", errorMsgs);

            String serviceTypeIdStr = req.getParameter("serviceTypeId");
            String typeName = req.getParameter("typeName");
            String description = req.getParameter("description");
            String typeModeStr = req.getParameter("typeMode");
            String imgURL = req.getParameter("imgURL");

            Integer serviceTypeId = null;
            Integer typeMode = null;

            // 1. 服務類型編號驗證
            if (serviceTypeIdStr == null || serviceTypeIdStr.trim().length() == 0) {
                errorMsgs.put("serviceTypeId", "服務類型編號遺失");
            } else {
                try {
                    serviceTypeId = Integer.valueOf(serviceTypeIdStr.trim());
                } catch (NumberFormatException e) {
                    errorMsgs.put("serviceTypeId", "服務類型編號格式錯誤");
                }
            }

            // 2. 類型名稱驗證
            if (typeName == null || typeName.trim().length() == 0) {
                errorMsgs.put("typeName", "服務類型名稱請勿空白");
            } else if (typeName.trim().length() > 50) {
                errorMsgs.put("typeName", "服務類型名稱不可超過 50 字");
            }

            // 3. 類型描述驗證
            if (description == null || description.trim().length() == 0) {
                errorMsgs.put("description", "服務類型描述請勿空白");
            } else if (description.trim().length() > 255) {
                errorMsgs.put("description", "服務類型描述不可超過 255 字");
            }

            // 4. 類型狀態驗證
            if (typeModeStr == null || typeModeStr.trim().length() == 0) {
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

            // 5. 圖片路徑驗證
            if (imgURL == null || imgURL.trim().length() == 0) {
                errorMsgs.put("imgURL", "圖片路徑請勿空白");
            } else if (imgURL.trim().length() > 255) {
                errorMsgs.put("imgURL", "圖片路徑不可超過 255 字");
            } else if (!imgURL.trim().matches("^/images/service/.+\\.(jpg|jpeg|png|gif|webp)$")) {
                errorMsgs.put("imgURL", "圖片路徑格式錯誤，例如：/images/service/walk.jpg");
            }

            // 6. 保留使用者輸入的資料
            ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
            serviceTypeVO.setSvcTypeID(serviceTypeId);
            serviceTypeVO.setTypeName(typeName);
            serviceTypeVO.setDescrip(description);
            serviceTypeVO.setTypeMode(typeMode);
            serviceTypeVO.setImgURL(imgURL);

            // 7. 有錯誤就回修改頁
            if (!errorMsgs.isEmpty()) {
                req.setAttribute("serviceTypeVO", serviceTypeVO);

                RequestDispatcher failureView =
                        req.getRequestDispatcher("/frontend/servicetype/update_service_type_input.jsp");
                failureView.forward(req, res);
                return;
            }

            // 8. 沒錯誤才更新
//            ServiceTypeService serviceTypeSvc = new ServiceTypeService();

            serviceTypeVO = serviceTypeSvc.update(
                    serviceTypeId,
                    typeName.trim(),
                    description.trim(),
                    typeMode,
                    imgURL.trim()
            );

            req.setAttribute("serviceTypeVO", serviceTypeVO);

            RequestDispatcher successView =
                    req.getRequestDispatcher("/frontend/servicetype/listOneServiceType.jsp");
            successView.forward(req, res);
            return;
        }
    }
}