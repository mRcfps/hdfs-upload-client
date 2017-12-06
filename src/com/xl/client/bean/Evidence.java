package com.xl.client.bean;

public class Evidence {

	private int id = -1;
	private int caseID = -1;// 所属案件id
	private String evName;// 证据名称
	private String comment;// 注释
	private String evAdmin;// 管理人
	private String dirPath;// 文件夹路径
	private String evSize;// 证据大小
	private int emlCount = -1;// Eml统计
	private int docCount = -1;// Doc统计
	private int pdfCount = -1;// Pdf统计
	private int zipCount = -1;// Zip统计
	private int rarCount = -1;// Rar统计
	private String percent;// 处理进度
	private String finished;// 导入完成
	private String status;// 任务是否最终完成，完成后清除workboard计时器
	private String mailbox;// mail
	private String mailBox;
	private String dealinfo;
	private String addTime;
	private String currFlag;
	private String finishTime;
	private Integer uptype;// 上传类型
	private Integer isdel;// 是否标记为删除
	private int indexFlag=-1;// 是否已建立索引
	private int evType =-1;//上传数据的类型
	private Integer dataTypes;//数据来源
	private String uploadNum;//上传数目
	private String successNum;//上传成功数目
	private String errorNum;//上传失败数目

	public String getUploadNum() {
		return uploadNum;
	}

	public void setUploadNum(String uploadNum) {
		this.uploadNum = uploadNum;
	}

	public String getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(String successNum) {
		this.successNum = successNum;
	}

	public String getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(String errorNum) {
		this.errorNum = errorNum;
	}

	public Integer getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(Integer dataTypes) {
		this.dataTypes = dataTypes;
	}

	public int getEvType() {
		return evType;
	}

	public void setEvType(int evType) {
		this.evType = evType;
	}

	public int getIndexFlag() {
		return indexFlag;
	}

	public void setIndexFlag(int indexFlag) {
		this.indexFlag = indexFlag;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCaseID() {
		return caseID;
	}

	public void setCaseID(int caseID) {
		this.caseID = caseID;
	}

	public String getEvName() {
		return evName;
	}

	public void setEvName(String evName) {
		this.evName = evName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEvAdmin() {
		return evAdmin;
	}

	public void setEvAdmin(String evAdmin) {
		this.evAdmin = evAdmin;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getEvSize() {
		return evSize;
	}

	public void setEvSize(String evSize) {
		this.evSize = evSize;
	}

	public int getEmlCount() {
		return emlCount;
	}

	public void setEmlCount(int emlCount) {
		this.emlCount = emlCount;
	}

	public int getDocCount() {
		return docCount;
	}

	public void setDocCount(int docCount) {
		this.docCount = docCount;
	}

	public int getPdfCount() {
		return pdfCount;
	}

	public void setPdfCount(int pdfCount) {
		this.pdfCount = pdfCount;
	}

	public int getZipCount() {
		return zipCount;
	}

	public void setZipCount(int zipCount) {
		this.zipCount = zipCount;
	}

	public int getRarCount() {
		return rarCount;
	}

	public void setRarCount(int rarCount) {
		this.rarCount = rarCount;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getFinished() {
		return finished;
	}

	public void setFinished(String finished) {
		this.finished = finished;
	}

	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	public String getMailBox() {
		return mailBox;
	}

	public String getDealinfo() {
		return dealinfo;
	}

	public void setDealinfo(String dealinfo) {
		this.dealinfo = dealinfo;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getCurrFlag() {
		return currFlag;
	}

	public void setCurrFlag(String currFlag) {
		this.currFlag = currFlag;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getUptype() {
		return uptype;
	}

	public void setUptype(Integer uptype) {
		this.uptype = uptype;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

}
