package com.serviceslot.model;

import java.util.List;

public interface ServiceSlotDAO_interface {

    public void insert(ServiceSlotVO svcS);

    public void delete(Integer serviceSlotId);

    public void update(ServiceSlotVO svcS);

    public ServiceSlotVO getOne(Integer serviceSlotId);

    public List<ServiceSlotVO> getAll();

    public List<ServiceSlotVO> getByServiceId(Integer serviceId);
}