package com.servicetype.model;

public class ServiceTypeVO implements java.io.Serializable {
	private Integer serviceTypeId;
	private String typeName;
	private String description;
	private Integer typeMode;
	private String imgURL;
	
	public ServiceTypeVO() {
		super();
	}
//	public ServiceTypeVO(String tName,String des,Integer tMode,String imgURL) {
////		this.serviceTypeId = serTid;
//		this.typeName = tName;
//		this.description = des;
//		this.typeMode = tMode;
//		this.imgURL = imgURL;
//	}
	public Integer getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTypeMode() {
		return typeMode;
	}
	public void setTypeMode(Integer typeMode) {
		this.typeMode = typeMode;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	
}
