package cc.openstring.oss.statistic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cc.openstring.oss.statistic.constant.Constant;

/**
 * Properties read and writer util
 */
public class PropertiesUtil {
	
	private Properties property = new Properties();
	private File file = null;
	public Map<String, String> toSaveMap = new HashMap<String, String>();
	
	/**
	 * PropertiesUtil
	 */
	public PropertiesUtil(String propertiesName) {
		final String filePath =  Constant.SOFT_CONFIG_PATH + propertiesName;
		file = new File(filePath);
		try {
			if (!file.exists()) {
				FileUtil.createFile(filePath);
			}
			FileInputStream fis = new FileInputStream(file);
			property.load(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * read all key and values
	 */
	public Map<String, String> getAllValues() throws Exception {
		FileInputStream fis = new FileInputStream(file);
		property.load(fis);
		Set<Object> keys = property.keySet();
		for (Iterator<Object> itr = keys.iterator(); itr.hasNext();) {
			String key = (String) itr.next();
			Object value = property.get(key);
			toSaveMap.put(key, String.valueOf(value));
		}
		fis.close();
		return toSaveMap;
	}

	/**
	 * check is weather exist file
	 */
	public void isExists(File properties) throws Exception {
		if (!properties.exists()) {
			properties.createNewFile();
		}
		property.setProperty("key", "values");
		FileOutputStream fis = new FileOutputStream(properties);
		property.store(fis, null);
		fis.close();
	}

	/**
	 *  read
	 */
	public String get(String key) {
		String values = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			property.load(fis);
			values = property.getProperty(key);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	/**
	 *  write
	 */
	public synchronized void set(String key, String value) {
		try {
			getAllValues();
			toSaveMap.put(key, value);
			property.putAll(toSaveMap);
			FileOutputStream fis = new FileOutputStream(file);
			property.store(fis, null);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
