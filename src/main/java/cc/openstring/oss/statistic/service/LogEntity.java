package cc.openstring.oss.statistic.service;

import java.util.Date;
/**
 *  OSS system log entity
 * @Author Joker
 * @Date 2017年10月25日
 */
public class LogEntity {

	private String endPoint;
	private String bucketName;
	private String httpType;
	private String requestType;
	private String utilName;
	private String IP;
	private Integer stateCode;
	private Date date;
	private Long size;

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getHttpType() {
		return httpType;
	}

	public void setHttpType(String httpType) {
		this.httpType = httpType;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getUtilName() {
		return utilName;
	}

	public void setUtilName(String utilName) {
		this.utilName = utilName;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
