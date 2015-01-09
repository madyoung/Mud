package com.mud.resources;

import java.util.Properties;

public class ConfigConstants {

	public static int THREAD_SIZE;

	public static void init(Properties properties) {
		THREAD_SIZE = Integer.parseInt(properties.getProperty("thread_size"));
	}
}
