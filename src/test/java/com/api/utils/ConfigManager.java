package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties prop = new Properties();
	private static String path = "config" + File.separator + "config.properties";
	private static String env;

	static {
		env = System.getProperty("env", "qa");
		env = env.toLowerCase().trim();
		switch (env) {
		case "qa" -> path = "config" + File.separator + "config.qa.properties";

		case "dev" -> path = "config" + File.separator + "config.dev.properties";

		case "uat" -> path = "config" + File.separator + "config.uat.properties";

		default -> path = "config" + File.separator + "config.qa.properties";

		}

		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (input == null) {
			throw new RuntimeException("Cannot find the file at the path " + path);
		}
		try {

			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

}
