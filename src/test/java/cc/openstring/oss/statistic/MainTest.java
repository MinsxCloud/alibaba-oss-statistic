package cc.openstring.oss.statistic;

import java.text.SimpleDateFormat;
import java.util.Map;

import cc.openstring.oss.statistic.service.LogStatistics;


public class MainTest {
	
	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		try {
			LogStatistics logStatistics = new LogStatistics("oss-log","rtc-hospital","http://oss-cn-shanghai.aliyuncs.com","LTAIczLFMZV7mdDU","9z04n82aNQpphfTXFeLWgPJI2T9wlq");
			logStatistics.setIp("139.219.99.239");
			logStatistics.setStartDate(SDF.parse("2017-10-17 00:00:00"));
			logStatistics.setEndDate(SDF.parse("2017-10-17 23:00:00"));
			Map<String, Object> result = logStatistics.getAllStatistics();
			result.entrySet().forEach(entrySet->{
				System.out.println(entrySet.getKey()+" = "+entrySet.getValue());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
