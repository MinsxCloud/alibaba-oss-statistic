package cc.openstring.oss.statistic.service;

public class LogStatisticsException extends Exception{
	
	private static final long serialVersionUID = -6086029597313847297L;
	
	public LogStatisticsException() {
		super();
	}

	public LogStatisticsException(String message) {
		super(message);
	}

	public LogStatisticsException(String message, Throwable cause) {
		super(message, cause);
	}

	protected LogStatisticsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	

}
