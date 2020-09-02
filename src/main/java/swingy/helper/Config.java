package swingy.helper;

import swingy.Exceptions.PropertiesException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	private static final Properties config = new Properties();
	private static int villainsCount;
	private static int maxLvl;
	private static GameMode mode;

	public static Properties getConfig() {
		return config;
	}

	static {
		try {
			File configFile = findConfigFile();
			InputStream input1 = new FileInputStream(configFile);
			config.load(input1);
			villainsCount = Integer.parseInt(config.getProperty("villain.amount"));
			if (villainsCount <= 0)
				throw new PropertiesException("No villains!");
			if (villainsCount >= 50)
				throw new PropertiesException("To much villains!");
			maxLvl = Integer.parseInt(config.getProperty("maxLvl"));
			if (maxLvl < 0)
				throw new PropertiesException("Maximum level cannot be negative!");
		} catch (IOException | PropertiesException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	private static File findConfigFile() {
		String dir = System.getProperty("user.dir");
		if ("target".equals(dir.substring(dir.lastIndexOf('/') + 1))) {
			return new File("classes/application.properties");
		} else {
			return new File("target/classes/application.properties");
		}
	}

	public static int getVillainsCount() {
		return villainsCount;
	}

	public static int getMaxLvl() {
		return maxLvl;
	}

	public static GameMode getMode() {
		return mode;
	}

	public static void setMode(GameMode mode) {
		Config.mode = mode;
	}
}
