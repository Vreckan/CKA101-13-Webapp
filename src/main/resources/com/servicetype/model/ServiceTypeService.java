package com.servicetype.model;

import java.util.List;

public class ServiceTypeService {

    private ServiceTypeDao_interface dao;

    public ServiceTypeService() {
        dao = new ServiceTypeDAO();
    }
    
    public ServiceTypeVO getOneServiceType(Integer serviceTypeId) {
        return dao.findByPrimaryKey(serviceTypeId);
    }

    public List<ServiceTypeVO> getAll() {
        return dao.getAll();
    }
    public ServiceTypeVO addServiceType(String typeName, String description, Integer typeMode,String ImgURL) {

        ServiceTypeVO serviceTypeVO = new ServiceTypeVO();

        serviceTypeVO.setTypeName(typeName);
        serviceTypeVO.setDescription(description);
        serviceTypeVO.setTypeMode(typeMode);
        serviceTypeVO.setImgURL(ImgURL);

        dao.insert(serviceTypeVO);
  
        return serviceTypeVO;
    }

    public ServiceTypeVO updateServiceType(Integer serviceTypeId, String typeName, String description, Integer typeMode,String ImgURL) {

        ServiceTypeVO serviceTypeVO = new ServiceTypeVO();

        serviceTypeVO.setServiceTypeId(serviceTypeId);
        serviceTypeVO.setTypeName(typeName);
        serviceTypeVO.setDescription(description);
        serviceTypeVO.setTypeMode(typeMode);
        serviceTypeVO.setImgURL(ImgURL);

        dao.update(serviceTypeVO);

        return serviceTypeVO;
    }

    public void deleteServiceType(Integer serviceTypeId) {

        ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
        serviceTypeVO.setServiceTypeId(serviceTypeId);

        dao.delete(serviceTypeVO);
    }
    
}