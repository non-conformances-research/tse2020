package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
	private InputStream inputStream;
	private Properties prop = new Properties();

	public PropertiesManager() {
		try {
			String propFileName = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("properties file not found");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return this.prop.getProperty(key);
	}

	public void close() {
		try {
			this.inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
