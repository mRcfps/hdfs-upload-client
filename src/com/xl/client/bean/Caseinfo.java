package com.xl.client.bean;

public class Caseinfo {

	private int id=-1;
	private String caseName;//案件名称
	private String caseNum;//案件编号
	private String userName;//所属用户
	private String mainParty;//主要当事人
	private String createdTime;//创建时间
	private String relatedCompany;//涉嫌单位
	private String relatedObject;//涉案物品
	private String trustee;//经办人
	private String inquireTime;//调查时间
	private String status;//案件状态	已分配 未分配
	private String remark;//备注
	private String label;//标签
	private String section; //科室
	private String department; //部门
	private String caseType;//案件类型
	
	
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMainParty() {
		return mainParty;
	}
	public void setMainParty(String mainParty) {
		this.mainParty = mainParty;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getRelatedCompany() {
		return relatedCompany;
	}
	public void setRelatedCompany(String relatedCompany) {
		this.relatedCompany = relatedCompany;
	}
	public String getRelatedObject() {
		return relatedObject;
	}
	public void setRelatedObject(String relatedObject) {
		this.relatedObject = relatedObject;
	}
	public String getTrustee() {
		return trustee;
	}
	public void setTrustee(String trustee) {
		this.trustee = trustee;
	}
	public String getInquireTime() {
		return inquireTime;
	}
	public void setInquireTime(String inquireTime) {
		this.inquireTime = inquireTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
}
