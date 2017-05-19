package com.dss.app.test.base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.dss.app.apputilities.AppUtility;
import com.dss.app.apputilities.Config;
import com.dss.app.apputilities.SauceREST;
import com.dss.app.coreutilities.CoreUtility;
import com.dss.app.coreutilities.Log;
import com.dss.app.pageobject.GigyaPageObject;
import com.dss.app.pageobject.HomePageObject;
import com.dss.app.pageobject.ProfilePageObject;
import com.dss.app.reporter.ExtentTestManager;
import com.dss.app.reporter.ExtentManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest{

	protected WebDriver driver;

	protected ExtentTest logger;
	protected Log Log;
	protected HomePageObject homepage;
	protected ProfilePageObject profilepage;
	protected GigyaPageObject gigyapage;
	
	protected SauceREST SauceREST;
	protected String jobID;
	protected String url;
	protected Map<String, ArrayList<String>> testCaseLevelSSOCredentials;
	public static String currentSuiteName;
	
	
	@BeforeSuite(alwaysRun = true)
	public void suiteSetUp(ITestContext context) throws IOException {
		System.out.println("Before suite");
		currentSuiteName = context.getCurrentXmlTest().getSuite().getName();
		ExtentManager.getReporter(currentSuiteName);
		AppUtility.initAllSSOStacks();

	}

	@AfterSuite(alwaysRun = true)
	public void suiteTearDown() throws IOException {
		System.out.println("After suite");
		//CoreUtility.cleanAllTempLogFile();
		ExtentManager.getReporter().close();
		
	}
	
	@Parameters({ "browser"})
	@BeforeTest
	public void testSetUp(String browser) throws IOException {
		System.out.println("Before TEst");
		
		SauceREST = new SauceREST();
		
	}
	
	@Parameters({"browser"})
	@BeforeClass(alwaysRun = true)
	public void classSetup(String browser) throws IOException{
		System.out.println("Enter Inside class before");
		String logName = browser+Thread.currentThread().getId()+"log";
		Log= new Log(logName);
		System.out.println("Log at before class"+Log);
		System.out.println("Exit Inside class before");
	}
	
	
	@AfterClass(alwaysRun =  true)
	public void classTeardown(ITestContext context) throws IOException{
		System.out.println("After Test");
        String fromFile = Log.Log.getName()+".log";
        String toFile =  "Log" + currentSuiteName +AppUtility.getCurrentDate()+".log";
               
        System.out.println("From file: "+fromFile);
        CoreUtility.copyDataFromTempLogFileToMainLogFile(fromFile,toFile);

		System.out.println("Class After method start");
		Log.destoryLogger();
		Log=null;
		System.out.println("Class After method end");
	}
	

	@AfterTest
	public void testTearDown() throws IOException {
	
		
	}

	@Parameters({ "browser", "platform", "url" })
	@BeforeMethod(alwaysRun = true)
	public void methodSetUp(String browser, String platform, String url, Method method, ITestContext testContext) throws IOException, InterruptedException {
		
		System.out.println("deleting test data ");
		//AppUtility.deleteTestDataFromP2P("gurimay12@yahoo.in");
		System.out.println("deleted");
		this.url = url;
		//driver = new Config().selectBrowserOnLocal(browser, platform);
		driver = new Config(Log).selectBrowserOnSauceLab(browser, platform, method);
		jobID =  (((RemoteWebDriver) driver).getSessionId()).toString();
		
		ExtentTestManager.startTest(CoreUtility.capsFirstLetter(CoreUtility.getMarketName(url))+" - "+ CoreUtility.capsFirstLetter(browser) + " - "+ CoreUtility.capsFirstLetter(method.getName()));
		ExtentTestManager.getTest().assignCategory(CoreUtility.capsFirstLetter(CoreUtility.getMarketName(url)), CoreUtility.capsFirstLetter(browser));

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		getURL(url);
		
		homepage = getHomePageObject();	
		Log.startTestCase(method.getName());
		Log.info("Test: "+testContext.getName());
		Log.info("Browser: "+browser);
		Log.info("Platform: "+platform);
		Log.info("URL: "+url);
		testCaseLevelSSOCredentials = AppUtility.getTestCaseLevelSSOTestUsers(method.getName());
		Log.info("Test Data / SSO Users for Test Case is Created ");		
		
	}

	@AfterMethod(alwaysRun = true)
	public void methodTearDown(ITestResult result) throws IOException, InterruptedException {
		System.out.println("After Method");
		String testStatus = "null";
		if (result.getStatus() == ITestResult.FAILURE) {
			testStatus = "Failed";
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
            SauceREST.jobFailed(jobID);
            Log.error(result.getThrowable());
            
        } else if (result.getStatus() == ITestResult.SKIP) {
        	testStatus = "Skipped";
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
            Log.error(result.getThrowable());
            
        } else  if (result.getStatus() == ITestResult.SUCCESS){
        	testStatus = "Passed";
            ExtentTestManager.getTest().log(LogStatus.PASS, result.getName());
            SauceREST.jobPassed(jobID);
        }
		String click_url = "<a target='_blank'"+ "href='https://saucelabs.com/beta/tests/"+ jobID +"/metadata#9'>Screenshot and Screencast of the Test</a>";
		ExtentTestManager.getTest().log(LogStatus.INFO, click_url);
        
        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());        
        ExtentManager.getReporter().flush();
        Log.endTestCase(testStatus);    
		driver.quit();
		AppUtility.destoryTestCaseLevelSSOTestUsers(testCaseLevelSSOCredentials);	
		
	}


	
	public void getURL(String url){
		driver.get(url);
		CoreUtility.handleAlert(driver, "accept");
	}

	public HomePageObject getHomePageObject(){
		HomePageObject homepage =  new HomePageObject(driver, Log);
		AppUtility.closeAds(homepage.btn_AdClose, driver);
		return homepage;
	}

	
	
	

}
