package com.systemdesign.ChainResponsibilityDesign_Pattern;

public class ErrorLogProcessor extends LogProcessor{

	public ErrorLogProcessor(LogProcessor logProcessorObject) {
		super(logProcessorObject);
	}

	public void log(int logLevel , String mess) {
		
		if(logLevel== ERROR) {
			
			print(mess);
		}
		else {
			
		//	logProcessorObject.log(logLevel , mess);
			super.log(logLevel , mess);
		}
	}
	
	@Override
	public void print(String mess) {
		
		System.out.println("ERROR :" + mess);
	}

}
