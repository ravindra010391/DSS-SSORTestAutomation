package com.dss.app.reporter;

import java.io.File;

import org.testng.ISuite;

import com.dss.app.apputilities.AppUtility;
import com.dss.app.apputilities.GlobalValues;
import com.dss.app.coreutilities.CoreUtility;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	static ExtentReports extent;
	
    final static String filePath = GlobalValues.ExtentReportPath;
    static String suiteName1 = null;
    public synchronized static ExtentReports getReporter(String suiteName) {
    	
  /*  	if(suiteName1.equals(null) || !suiteName1.equalsIgnoreCase(suiteName)){
    		System.out.println("Creatig report file getReporter called");
        	System.out.println("report of"+filePath+"\\"+suiteName+"AutomationReport.html");
            extent = new ExtentReports(filePath + File.separator +"Report"+suiteName+AppUtility.getCurrentDate()+".html", true, DisplayOrder.NEWEST_FIRST);
            extent.loadConfig(new File(GlobalValues.ExtentReportConfig));
            System.out.println(GlobalValues.ExtentReportConfig);
            suiteName1 = suiteName;
    		
    	}*/
        if (extent == null) {
        	System.out.println("Creatig report file getReporter called");
        	System.out.println("report of"+filePath+"\\"+suiteName+"AutomationReport.html");
            extent = new ExtentReports(filePath + File.separator +"Report"+suiteName+AppUtility.getCurrentDate()+".html", true, DisplayOrder.NEWEST_FIRST);
            extent.loadConfig(new File(GlobalValues.ExtentReportConfig));
            System.out.println(GlobalValues.ExtentReportConfig);
            ExtentTestManager.extent= extent;

        }
        System.out.println("my extent ===" +extent);
        
        return extent;
    }
    
    public synchronized static ExtentReports getReporter() {
    	System.out.println("getreporter() = extent => "+extent);
    	return extent;
    }
    
    public synchronized static void closeExtent(){
    	extent.close();
    	extent = null;
    	
    }
    

}
