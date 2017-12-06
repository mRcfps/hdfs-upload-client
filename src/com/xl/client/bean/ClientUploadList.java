package com.xl.client.bean;

import java.util.Date;

public class ClientUploadList {

	private Integer id;
	private Integer uid;
	private Integer evId;
	private Float evSize;
	private Date finishTime;
	private Integer upStatus;
	private Integer isDel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getEvId() {
		return evId;
	}

	public void setEvId(Integer evId) {
		this.evId = evId;
	}

	public Float getEvSize() {
		return evSize;
	}

	public void setEvSize(Float evSize) {
		this.evSize = evSize;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getUpStatus() {
		return upStatus;
	}

	public void setUpStatus(Integer upStatus) {
		this.upStatus = upStatus;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}
