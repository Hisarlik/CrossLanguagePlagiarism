package com.menta.NLP.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationGetProperties {

	InputStream inputStream;
	public  Properties prop = null;
 
	
	// Lazy Initialization (If required then only)
		public Properties getInstance(String propFileName) {
			if (prop == null) {
				// Thread Safe. Might be costly operation in some case
				synchronized (Properties.class) {
					if (prop == null) {
						prop = new Properties();
					}
				}
			}
			try {
				//String propFileName = "config.properties";
	 
				inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	 
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
				}

				inputStream.close();

			} catch (Exception e) {
				System.out.println("Exception: " + e);
			} 
			return prop;
		}
	
}