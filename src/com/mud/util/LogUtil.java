/**
 * 
 */
package com.mud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author chenzhiwei
 * @since 2014-12-9 下午3:41:34
 */
public class LogUtil {

	public static final Logger email = LoggerFactory.getLogger("email");

	public static final String SUBJECT = "subject";
	private static final String DEFAULT_SUBJECT = "Mud Server Notify.";

	public static void email(String subject, String content, Object[] params) {
		MDC.put(SUBJECT, subject);
		content += "\nHost:";
		content += ServerInfoUtil.getHostName();
		content += "\nPath:";
		content += ServerInfoUtil.getServerPath();
		email.error(content, params);
	}

	public static void email(String subject, String content, Throwable throwable) {
		if (subject == null) {
			subject = DEFAULT_SUBJECT;
		}
		MDC.put(SUBJECT, subject);
		content += "\nHost:";
		content += ServerInfoUtil.getHostName();
		content += "\nPath:";
		content += ServerInfoUtil.getServerPath();
		email.error(content, throwable);
	}
}
