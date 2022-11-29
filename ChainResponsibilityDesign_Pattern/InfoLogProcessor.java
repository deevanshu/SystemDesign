package com.systemdesign.ChainResponsibilityDesign_Pattern;

public class InfoLogProcessor extends LogProcessor{

	public InfoLogProcessor(LogProcessor logProcessorObject) {
		super(logProcessorObject);
	}

	public void log(int logLevel , String mess) {
		
		if(logLevel==INFO) {
			
			print(mess);
		}
		else {
			
			logProcessorObject.log(logLevel , mess);
		}
	}
	
	@Override
	public void print(String mess) {
		
		System.out.println("INFO :" + mess);
	}
}
