package cc.openstring.oss.statistic.main;

import java.text.SimpleDateFormat;
import java.util.Map;

import cc.openstring.oss.statistic.service.LogStatistics;
import cc.openstring.oss.statistic.util.PropertiesUtil;

/**
 * OSS statistic main entry
 * @Author Joker
 * @Date 2017年10月25日
 */
public class Application {

	private final static String START_DATE = "start";
	private final static String END_DATE = "end";
	private final static String IP = "ip";
	private final static String HELP = "help";

	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static PropertiesUtil PROPERTIES_UTIL = new PropertiesUtil("config.properties");

	public static void main(String[] args) {
		try {
			if (args.length==1&&args[0].equalsIgnoreCase(HELP)) {
				System.out.println("Software documentation\r\n" + 
						"OSS Statistic is used to help you make statistics of specifying IP's OSS usage charges\r\n" + 
						"\r\n" + 
						"Software running environment\r\n" + 
						"1.You need to install Java more than 8 of the environment\r\n" + 
						"2.You need to configure ../bin to the system environment variable\r\n" + 
						"\r\n" + 
						"Parameter description\r\n" + 
						"[start] start date time like : 2017-10-13 00:00:00\r\n" + 
						"[end] end date time like : 2017-10-14 00:00:00\r\n" + 
						"[ip] You need to make statistics of whitch IP's OSS usage\r\n" + 
						"\r\n" + 
						"Sample description\r\n" + 
						"ost start 2017-10-13 00:00:00 end 2017-10-13 23:00:00 ip 120.212.99.23\r\n" + 
						"\r\n" + 
						"Other description\r\n" + 
						"You can not specify the start time and end time, the default will \r\n" + 
						"make statistics of the day OSS usage, you can also not specify IP, the default \r\n" + 
						"will  make statistics of all the OSS usage\r\n" + 
						"\r\n" + 
						"If your service PC and OSS are in an intranet, it is recommended to configure \r\n" + 
						"the OSS intranet address(EndPoint) in ../bin/config/config.properties");
			}else {
				LogStatistics logStatistics = new LogStatistics(PROPERTIES_UTIL.get("logPath"),
						PROPERTIES_UTIL.get("bucketName"), PROPERTIES_UTIL.get("endPoint"),
						PROPERTIES_UTIL.get("accessKeyId"), PROPERTIES_UTIL.get("accessKeySecret"));
				
				for (int i = 0; i < args.length; i++) {
					if (args[i].equalsIgnoreCase(START_DATE) && i < args.length - 2) {
						logStatistics.setStartDate(SDF.parse(args[i + 1] + " " + args[i + 2]));
					} else if (args[i].equalsIgnoreCase(END_DATE) && i < args.length - 2) {
						logStatistics.setEndDate(SDF.parse(args[i + 1] + " " + args[i + 2]));
					} else if (args[i].equalsIgnoreCase(IP) && i < args.length - 1) {
						logStatistics.setIp(args[i + 1]);
					}
				}
				
				System.out.println("start make statistics of oss using, please wait......");
				System.out.println("--------------------------------------------------");
				Map<String, Object> result = logStatistics.getAllStatistics();
				System.out.println("--------------------------------------------------");
				System.out.println("finished statistics , result :");
				System.out.println("--------------------------------------------------");
				result.entrySet().forEach(entrySet -> {
					if ("flowTotalCost".equals(entrySet.getKey()) || "requestTotalCost".equals(entrySet.getKey())) {
						System.out.println(entrySet.getKey() + " = " + entrySet.getValue() + " (RMB)");
					} else if ("requestTimes".equals(entrySet.getKey())) {
						System.out.println(entrySet.getKey() + " = " + entrySet.getValue() + " (TIMES)");
					} else if ("flow".equals(entrySet.getKey())) {
						System.out.println(entrySet.getKey() + " = " + entrySet.getValue() + " (BIT)");
					} else {
						System.out.println(entrySet.getKey() + " = " + entrySet.getValue());
					}
				});
				System.out.println(String.format("totalCost = %s (RMB)",
						(Double) result.get("flowTotalCost") + (Double) result.get("requestTotalCost")));
				System.out.println("--------------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
