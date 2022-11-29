package com.systemdesign.ChainResponsibilityDesign_Pattern;

public class DebugLogProcessor extends LogProcessor{

	public DebugLogProcessor(LogProcessor logProcessorObject) {
		super(logProcessorObject);
	}

	public void log(int logLevel , String mess) {
		
		if(logLevel== DEBUG) {
			
			print(mess);
		}
		else {
			
			super.log(logLevel , mess);
		}
	}
	
	@Override
	public void print(String mess) {
		
		System.out.println("DEBUG :" + mess);
	}
}
