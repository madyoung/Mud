package com.mud.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesFileUtil {
	private static final String PATH_SEPARATOR = "/";

	public static Properties getPropertiesFromResource(String file, String charset) {
		Properties properties = new Properties();
		BufferedReader in = null;
		if (file.indexOf(PATH_SEPARATOR) != 0) {
			file = PATH_SEPARATOR + file;
		}
		try {
			in = new BufferedReader(new InputStreamReader(PropertiesFileUtil.class.getResourceAsStream(file), charset));
			properties.load(in);
		} catch (IOException e) {
			throw new IllegalStateException("read file fail.", e.getCause());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return properties;
	}

	public static Properties getPropertiesFromResource(String file) {
		Properties properties = new Properties();
		InputStream in = null;
		if (file.indexOf(PATH_SEPARATOR) != 0) {
			file = PATH_SEPARATOR + file;
		}
		try {
			in = PropertiesFileUtil.class.getResourceAsStream(file);
			properties.load(in);
		} catch (IOException e) {
			throw new IllegalStateException("read file fail.", e.getCause());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return properties;
	}

	public static Properties getPropertiesFromFile(String file) {
		Properties properties = new Properties();
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(file);
			properties.load(fileIn);
		} catch (IOException e) {
			throw new IllegalStateException("read file fail.", e.getCause());
		} finally {
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
				}
			}
		}
		return properties;
	}

	public static void setPropertiesToResource(Properties properties, String filename) {
		if (filename.indexOf(PATH_SEPARATOR) != 0) {
			filename = PATH_SEPARATOR + filename;
		}
		filename = PropertiesFileUtil.class.getResource(filename).getFile().replaceAll("%20", " ");
		setPropertiesToFile(properties, filename);
	}

	public static void setPropertiesToFile(Properties properties, String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					throw new IllegalArgumentException("create file fail.");
				}
			} catch (IOException e) {
				throw new IllegalArgumentException("create file fail.");
			}
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			properties.store(out, null);
		} catch (IOException e) {
			throw new IllegalStateException("write file fail.", e.getCause());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
