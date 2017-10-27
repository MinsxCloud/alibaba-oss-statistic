package cc.openstring.oss.statistic.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

/**
 * OSS system log statistics service
 * @Author Joker
 * @Date 2017年10月25日
 */
public class LogStatistics {

	private String ip;
	private String logPath;
	private Date startDate;
	private Date endDate;
	private String bucketName;
	private String endPoint;
	private String accessKeyId;
	private String accessKeySecret;

	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public LogStatistics() {
		initialDate();
	}

	public LogStatistics(String logPath, String bucketName, String endPoint, String accessKeyId,
			String accessKeySecret) {
		this.logPath = logPath;
		this.bucketName = bucketName;
		this.endPoint = endPoint;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		initialDate();
	}

	/**
	 * Statistics of OSS usage</br>
	 * if without giving start date or end date,it will only count today usage
	 * 
	 * @return requestTimes : total request times </br>
	 *         flow : total download file size </br>
	 *         requestTotalCost : total request cost </br>
	 *         flowTotalCost : total download cost
	 * @throws Exception
	 *             Statistics exception include OSS and data parse exception
	 */
	public Map<String, Object> getAllStatistics() throws Exception {
		checkParameters();
		Map<String, Object> result = new HashMap<>();
		OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
		Map<String, Object> currentResult = new HashMap<>();
		Long requestTimes = 0L, flow = 0L;
		Double requestTotalCost = 0.0, flowTotalCost = 0.0;
		final int maxKeys = 200;
		String nextMarker = null;
		ObjectListing objectListing =null;
		do {
		    objectListing = ossClient.listObjects(new ListObjectsRequest(bucketName).withMarker(nextMarker).withPrefix(logPath).withMaxKeys(maxKeys));
		    List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
		    for (OSSObjectSummary s : sums) {
		    	String objectURI = s.getKey();
				Date objectDate = SDF.parse(
						objectURI.substring(objectURI.indexOf(bucketName) + bucketName.length(), objectURI.length() - 5));
				if (objectDate.after(endDate))
					break;
				if (objectDate.after(startDate) && objectDate.before(endDate)) {
					System.out.println(objectURI);
					currentResult = getOneLogStatistics(ossClient, objectURI);
					requestTimes += (Long) currentResult.get("requestTimes");
					flow += (Long) currentResult.get("flow");
					requestTotalCost += (Double) currentResult.get("requestTotalCost");
					flowTotalCost += (Double) currentResult.get("flowTotalCost");
				}
		    }
		    nextMarker = objectListing.getNextMarker();
		} while (objectListing.isTruncated());
		
		result.put("requestTimes", requestTimes);
		result.put("flow", flow);
		result.put("requestTotalCost", requestTotalCost);
		result.put("flowTotalCost", flowTotalCost);
		return result;
	}

	/**
	 * Statistics of one log data
	 */
	private Map<String, Object> getOneLogStatistics(OSSClient ossClient, String logURI) throws Exception {
		Map<String, Object> result = new HashMap<>();
		OSSObject ossObject = ossClient.getObject(bucketName, logURI);
		InputStream content = ossObject.getObjectContent();
		if (content != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(content, "GBK"));
			String line;
			LogEntity logEntity;
			Long requestTimes = 0L, flow = 0L;
			Double requestTotalCost = 0.0, flowTotalCost = 0.0;
			while (true) {
				line = reader.readLine();
				if (line != null) {
					if (ip == null || line.contains(ip)) {
						logEntity = LogEntityUtil.getLogEntityByLine(line);
						if (logEntity.getDate().after(startDate) && logEntity.getDate().before(endDate)) {
							requestTimes++;
							flow += logEntity.getSize();
							if (isIdleTime(logEntity.getDate())) {
								flowTotalCost += (logEntity.getSize() / (1024 * 1024 * 1024.0)) * 0.25;
							} else {
								flowTotalCost += (logEntity.getSize() / (1024 * 1024 * 1024.0)) * 0.5;
							}
						}
					}
				} else {
					break;
				}
			}
			content.close();
			requestTotalCost = (requestTimes / 10000.0) * 0.01;
			result.put("requestTimes", requestTimes);
			result.put("flow", flow);
			result.put("requestTotalCost", requestTotalCost);
			result.put("flowTotalCost", flowTotalCost);
		}
		return result;
	}

	/**
	 * Check whether parameters is valid
	 */
	public void checkParameters() throws LogStatisticsException {
		if (this.logPath == null) {
			throw new LogStatisticsException("logPath can't be null");
		}
		if (this.bucketName == null) {
			throw new LogStatisticsException("Bucket can't be null");
		}
		if (this.endPoint == null) {
			throw new LogStatisticsException("endPoint can't be null");
		}
		if (this.accessKeyId == null) {
			throw new LogStatisticsException("accessKeyId can't be null");
		}
		if (this.accessKeySecret == null) {
			throw new LogStatisticsException("accessKeySecret can't be null");
		}
	}

	/**
	 * Judge whether it is idle time
	 */
	@SuppressWarnings("deprecation")
	public Boolean isIdleTime(Date date) {
		Boolean isIdle = false;
		if (0 <= date.getHours() && date.getHours() < 8) {
			isIdle = true;
		}
		return isIdle;
	}

	/**
	 * Initial start and end date
	 */
	@SuppressWarnings("deprecation")
	public void initialDate() {
		Date startDate = new Date();
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		this.startDate = startDate;
		this.endDate = new Date();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

}
