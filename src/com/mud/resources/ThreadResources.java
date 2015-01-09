package com.mud.resources;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Deprecated
public class ThreadResources {
	private static ScheduledExecutorService executor;

	public static void init() {
		executor = Executors.newScheduledThreadPool(ConfigConstants.THREAD_SIZE);
	}

	public static void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		executor.scheduleAtFixedRate(command, initialDelay, period, unit);
	}

	public static void schedule(Runnable command, long delay, TimeUnit unit) {
		executor.schedule(command, delay, unit);
	}

	public static void destroy() {
		executor.shutdown();
	}

}
