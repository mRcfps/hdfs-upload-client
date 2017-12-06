package com.xl.client.bean;

public class Section {
	private int id = -1;
	private String sectionNum ;
	private String sectionName ;
	private String department;
	private String remarks;
	private String found;//创建人
	private String foundtime;//创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSectionNum() {
		return sectionNum;
	}
	public void setSectionNum(String sectionNum) {
		this.sectionNum = sectionNum;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFound() {
		return found;
	}
	public void setFound(String found) {
		this.found = found;
	}
	public String getFoundtime() {
		return foundtime;
	}
	public void setFoundtime(String foundtime) {
		this.foundtime = foundtime;
	}
	@Override
	public String toString() {
		return "Section [id=" + id + ", sectionNum=" + sectionNum + ", sectionName=" + sectionName + ", department="
				+ department + ", remarks=" + remarks + ", found=" + found + ", foundtime=" + foundtime + "]";
	}
	
	
	
}
