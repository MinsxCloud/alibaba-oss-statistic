package cc.openstring.oss.statistic.service;

import java.text.SimpleDateFormat;
import java.util.Locale;
/**
 * OSS system log entity util
 * @Author Joker
 * @Date 2017年10月25日
 */
public class LogEntityUtil {
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MMMMMM/yyyy:HH:mm:ss",Locale.US);
	
	public static LogEntity getLogEntityByLine(String line) throws Exception {
		String[] params = line.split("\"");
		String[] currentContent = params[0].split(" ");
		LogEntity logEntity = new LogEntity();
		logEntity.setBucketName(params[17]);
		logEntity.setEndPoint(params[7]);
		logEntity.setHttpType(params[1].split(" ")[0]);
		logEntity.setIP(currentContent[1]);
		logEntity.setRequestType(params[15]);
		logEntity.setUtilName(params[5]);
		logEntity.setDate(SDF.parse(currentContent[4].substring(1, currentContent[4].length())));
		currentContent= params[2].split(" ");
		logEntity.setSize(Long.valueOf(currentContent[2]));
		logEntity.setStateCode(Integer.parseInt(currentContent[1]));
		return logEntity;
	}

}
