package com.jss.sdd.http;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolFactory {
	private static ThreadPoolExecutor sExecutorService = (ThreadPoolExecutor) Executors
			.newFixedThreadPool(10);
	
	public static void execute(Runnable runnable){
		sExecutorService.execute(runnable);
	}
	
}
