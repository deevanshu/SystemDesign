package com.systemdesign.ChainResponsibilityDesign_Pattern;

public abstract class LogProcessor {

	public static int INFO  = 1;
	public static int DEBUG = 2;
	public static int ERROR = 3;
	
	
	LogProcessor logProcessorObject;
	public LogProcessor(LogProcessor logProcessorObject) {
		
		this.logProcessorObject =  logProcessorObject;
	}
	
	public void log(int logLevel , String mess) {
		
		if(logProcessorObject!=null) {
			
			logProcessorObject.log(logLevel, mess);
		}
	}
	
	public abstract void print(String mess);
}
