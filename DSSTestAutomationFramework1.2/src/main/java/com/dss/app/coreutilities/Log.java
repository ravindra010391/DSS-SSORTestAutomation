package com.dss.app.coreutilities;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.dss.app.apputilities.GlobalValues;

/*
 * This class is to log the information in Log4J log file
 */

public class Log  {

	public Logger Log;
	
	
	/*public static void logInit(){
		//BasicConfigurator.configure();
		PropertyConfigurator.configure(GlobalValues.logConfigFilePath);
		Log = Logger.getLogger("FILE_LOGGER");
	}
*/
	



	public  Log(String logName) throws IOException{
		
		System.out.println("Logger created for: "+logName);
		FileAppender appeander=null ;
		ConsoleAppender console=null;
		
		Log = Logger.getLogger(logName);
		String PATTERN = "%d [%p|%c] %m%n";
		
		console = new ConsoleAppender(); 
		console.setLayout(new PatternLayout(PATTERN)); 
		console.setThreshold(org.apache.log4j.Level.INFO);
		console.activateOptions();
		//add appender to any Logger (here is root)
		//Logger.getRootLogger().addAppender(console);
		Log.addAppender(console);


		appeander = new FileAppender();
		appeander.setName("FileLogger");
		appeander.setFile(GlobalValues.tempLogFilePath + "\\"+ logName + ".log");
		appeander.setLayout(new PatternLayout(PATTERN));
		appeander.setThreshold(org.apache.log4j.Level.INFO);
		appeander.setAppend(true);
		appeander.activateOptions();
		//Logger.getRootLogger().addAppender(appeander);
		Log.addAppender(appeander);
		
		System.out.println("My logger name is :"+Log.getName());
	
	}

	public void destoryLogger() {
		Log.removeAllAppenders();
	}
	
	
	  public void startTestCase(String sTestCaseName){


	 	Log.info("----------------------------------------------------------------------------------------");

	 	Log.info("------------------------------ "+sTestCaseName+ " -------------------------------------");

	 	Log.info("----------------------------------------------------------------------------------------");

	 	}

	  public void endTestCase(String result){
		  if(result.equalsIgnoreCase("passed"))
			  Log.info("--------------------------------- "+"-P-A-S-S-E-D-"+"------------------------------------");
		  else if(result.equalsIgnoreCase("failed"))
			  Log.error("--------------------------------- "+"-F-A-I-L-E-D-"+"------------------------------------");
		  else if(result.equalsIgnoreCase("skipped"))
			  Log.error("--------------------------------- "+"-S-K-I-P-P-E-D-"+"------------------------------------");
		  
	 	Log.info("--------------------------------- "+"-E---N---D-"+"------------------------------------");

	 	}

	 
	  public void info(String message) {

	 		Log.info(message);

	 		}

	  public void warn(String message) {

	     Log.warn(message);

	 	}

	  public  void error(String message) {

	     Log.error(message);

	 	}
	  public void error(Throwable t){
		  Log.error("Error occurred while execution: ", t);
	  }

	  public  void fatal(String message) {

	     Log.fatal(message);

	 	}

	  public  void debug(String message) {

	     Log.debug(message);

	 	}
		
}

