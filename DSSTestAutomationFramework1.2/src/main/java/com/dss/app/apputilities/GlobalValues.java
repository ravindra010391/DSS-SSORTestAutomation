package com.dss.app.apputilities;

import java.io.File;

public interface GlobalValues {
	
	public static String baseDirectory=System.getProperty("user.dir");
	public static final String excelPath =baseDirectory  + "/TestData.xlsx";
	public static final String tempLogFilePath= baseDirectory+"\\TempLogFiles";
	
	public static final String windowsChromeDriverPath = baseDirectory+ "\\src\\test\\resources\\chromedriver.exe";
	public static final String windowsFirefoxDriverPath = baseDirectory+ "\\src\\test\\resources\\geckodriver.exe";
	public static final String PHANTOMJS_DRIVER_PATH = baseDirectory+ "\\src\\test\\resources\\phantomjs.exe";
	
	//Sauce Lab config details
	public static final String SAUCE_USERNAME = "ravindra.mandage3"; 
	public static final String SAUCE_ACCESS_KEY = "5cb0d0d2-2d72-492a-9050-15126373cf7d"; 
	public static final String SAUCE_URL = "http://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
	public static final String SAUCE_MAC_VERSION = "macOS 10.12";
	public static final String SAUCE_WINDOWS_VERSION = "windows 7";
	
	//Extent report Config Details
	public static final String ExtentReportPath = baseDirectory;
	public static final String ExtentReportConfig = baseDirectory+File.separator +"src" +File.separator + "main" +File.separator +"java"+File.separator +"com"+File.separator + "dss"+File.separator +"app"+File.separator +"reporter"+File.separator + "ExtentConf.xml";
	
	//Social Sites URL
	public static final String SSO_GMAIL_URL_1 = "https://mail.google.com/";
	public static final String SSO_GMAIL_URL_2 = "https://accounts.google.com/";
	public static final String SSO_YAHOO_URL = "http://www.yahoo.com";
	public static final String SSO_AOL_URL = "https://my.screenname.aol.com/";
	public static final String SSO_FACEBOOK_URL = "http://www.facebook.com";
	public static final String SSO_TWITTER_URL = "http://www.twitter.com";
	
	//P2P Config Details
	public static final String P2P_STAGE_URL = "https://core.p2p.tribstage.com";
	public static final String P2P_STAGE_USERNAME = "DSStester";
	public static final String P2P_STAGE_PASSWORD = "Tribune1";
	
	//Global Credential Stack Test data Sheet
	public static final String GLOBAL_STACK_TEST_DATA_SHEET = GlobalValues.baseDirectory + File.separator + "\\EmailIDTestData.xlsx";
	
	//Failed test case retry count
	public static final int MAX_RETRY_ON_FAILED_TESTS = 1;
	
	
	//Profile Page Success Messages
	public static final String PREFERRED_EMAIL_SUCCESSFULLY_UPADATED_MSG = "Your email has been updated.";
	public static final String NAME_SUCCESSFULLY_UPADATED_MSG = "Your name has been updated.";
	public static final String PASSWORD_SUCCESSFULLY_UPADATED_MSG = "Your password has been updated.";
	public static final String USERNAME_SUCCESSFULLY_UPADATED_MSG	 = "Your username has been updated.";
	
	
	
	
}
