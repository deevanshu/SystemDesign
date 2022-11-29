package com.systemdesign.ChainResponsibilityDesign_Pattern;

public class Main {

	public static void main(String args[]) {
		
		LogProcessor logProcessorObject = new InfoLogProcessor( new ErrorLogProcessor ( new DebugLogProcessor( null)));
		
		
		logProcessorObject.log(LogProcessor.ERROR,  "Error Occured");
		logProcessorObject.log(LogProcessor.DEBUG,  "Debug Needed");
		logProcessorObject.log(LogProcessor.INFO ,  "Info Needed");
		
	}
}
