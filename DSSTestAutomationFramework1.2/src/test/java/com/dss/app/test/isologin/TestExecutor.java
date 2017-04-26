package com.dss.app.test.isologin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import org.apache.http.util.Asserts;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.dss.app.apputilities.AppUtility;
import com.dss.app.apputilities.GlobalValues;
import com.dss.app.failedrerun.Retry;
import com.dss.app.reporter.ExtentTestManager;
import com.dss.app.test.base.BaseTest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.relevantcodes.extentreports.LogStatus;


public class TestExecutor extends BaseTest {
	static int i = 1;
	
	@Test(groups = {"Regression"}, enabled = false)
	public void ssotosso_yahootofacebook(){
		
		
		
		System.out.println("map gamail :"+testCaseLevelSSOCredentials.get("Gmail"));
		System.out.println("map yahoo :"+testCaseLevelSSOCredentials.get("Yahoo"));
		System.out.println("map aol :"+testCaseLevelSSOCredentials.get("Aol"));
		System.out.println("map twitter :"+testCaseLevelSSOCredentials.get("Twitter"));
		System.out.println("map facebook :"+testCaseLevelSSOCredentials.get("Facebook"));
	
		
		System.out.println("testcase 1 Failed");
		Assert.assertTrue(false);
	}
	
	@Test(groups = {"Regression"}, enabled = false)
	public void testcase2(){
		System.out.println("testcase 2 Failed");
		System.out.println("i===>"+i);
		if(i==1){i++;
		Assert.assertTrue(false);}
		else
			Assert.assertTrue(true);
		
		
		i++;
		
	}
		
	@Test(groups = {"Regression"}, enabled = false)
	public void isISOLoginSuccessful() throws InterruptedException, IOException {
		System.out.println("deleting test data ");
		//AppUtility.deleteTestDataFromP2P("gurimay12@yahoo.in");
		System.out.println("deleted");
		
		String username = "test1test2@gmail.com";
		String password = "tribune1";
		boolean isSuccess = false;
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on TopNav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Enter UserName and Password and Login");
		homepage.doLogin(username, password);
		Thread.sleep(3000);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto to Profile Page");
		profilepage= homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify User on Profile Page");
		isSuccess= profilepage.isExpectedUserLogged(username);
		
		//Profile Page Checks 
		String passwordUpdateMsg = profilepage.editPassword("tribune1", "tribune1");		
		String nameUpdateMsg= profilepage.editName("FirstName", "Lastname");
		String usernameUpdateMsg = profilepage.editUsername("NewUsername1");
				
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(isSuccess, true);
		softAssert.assertEquals(passwordUpdateMsg, GlobalValues.PASSWORD_SUCCESSFULLY_UPADATED_MSG);
	    softAssert.assertEquals(nameUpdateMsg, GlobalValues.NAME_SUCCESSFULLY_UPADATED_MSG);
		softAssert.assertEquals(usernameUpdateMsg, GlobalValues.USERNAME_SUCCESSFULLY_UPADATED_MSG);
		softAssert.assertAll();
	}


	@Test(enabled = false)
	public void SSO_Login_With_Facebook_First_Time_Login() throws InterruptedException, IOException{
		String username = "guri10febf@gmail.com"; // US based account
		String password = "tribune1";
		String zipcode = "60601";
		boolean isSuccess = false;
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Facebook Icon on Login Panel");
		gigyapage = homepage.clickOnFacebookIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Facebook Login ");
		homepage = gigyapage.doFacebookLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Enter Zip Code on Intercept Panel");	
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Thank You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify the User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS, "First Time SSO Login with Facebook PASSED");
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Delete Browser Session/History");
		AppUtility.deleteFacebookCookies(driver);
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Enter URL for Second Time SSO Facebook Login");
		getURL(url);
		homepage = getHomePageObject();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Facebook Icon on Login Panel");
		gigyapage = homepage.clickOnFacebookIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Facebook Login ");
		homepage = gigyapage.doFacebookLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify the User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS, "Second Time SSO Login with Facebook PASSED");
		
		Assert.assertEquals(isSuccess, true);
		
		
	}
	
	
	@Test( groups = "Regression", enabled = false)
	public void SSO_Login_With_Gmail_First_Time_Login() throws InterruptedException, IOException{
		String username = testCaseLevelSSOCredentials.get("Gmail").get(0);
		String password = testCaseLevelSSOCredentials.get("Gmail").get(1);
		String zipcode = "60601";
		boolean isSuccess = false;
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Gmail Icon on Login Panel");
		gigyapage = homepage.clickOnGmailIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Gmail Login");
		homepage = gigyapage.doGmailLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Enter Zip Code on Intecept Panel");
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button On Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Thnak You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		 		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS, "First Time SSO Login with Gmail PASSED");
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Delete Browser Session/History");
		AppUtility.deleteGmailCookies(driver);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Open URL for Second Time SSO Login");
		getURL(url);
		homepage = getHomePageObject();
				
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Gmail Icon on Login Panel");
		gigyapage = homepage.clickOnGmailIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Gmail Login ");
		homepage = gigyapage.doGmailLogin(username, password);
	
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS, "Second Time SSO Login with Gmail PASSED");
		
		Assert.assertEquals(isSuccess, true);
		
	}
	
	@Test(enabled = false)
	public void SSO_Login_With_AOL_First_Time_Login() throws InterruptedException, IOException{
		String username = "ssortest@aol.com";
		String password = "tribune1";
		String zipcode = "60601";
		boolean isSuccess = false;
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on AOL icon on Login Panel");
		gigyapage = homepage.clickOnAOLIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform AOL Login ");
		homepage = gigyapage.doAOLLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Enter Zip Code on intercept Panel");
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Thank You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS, "First Time SSO Login Using AOL is Successful");
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Delete Browser Session/History");
		AppUtility.deleteAOLCookies(driver);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Open URL for Second Time SSO Login");
		getURL(url);
		homepage = getHomePageObject();
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on AOL icon on Login Panel");
		gigyapage = homepage.clickOnAOLIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform AOL Login ");
		homepage = gigyapage.doAOLLogin(username, password);
			ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS, "Second Time SSO Login Using AOL is Successful");
		
		Assert.assertEquals(isSuccess, true);
		
		
		
	}
	
	@Test(enabled = false) 
	public void SSO_Login_With_Twitter_First_Time_Login() throws InterruptedException, IOException{
		String username = "tribunedss1@yahoo.com";
		String password = "Tribune01";
		String zipcode = "60601";
		boolean isSuccess = false;
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Twitter Icon on Login Panel");
		gigyapage = homepage.clickOnTwitterIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Twitter Login");
		homepage = gigyapage.doTwitterLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Enter Email ID on Intercept Panel");
		homepage.enterEmailOnInterceptPanel(username);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Enter Zip Code on Intercept Panel");
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Thank You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify user on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS, "First Time SSO Login Using Twitter is Successful");
		
		/*ExtentTestManager.getTest().log(LogStatus.INFO, "Delete Browser Session/History");
		AppUtility.deleteTwitterCookies(driver);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Open URL for Second Time SSO - Twitter Login");*/
		
		/*LocalStorage local = ((ChromeDriver) driver).getLocalStorage();
		local.clear();*/
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript(String.format("window.localStorage.clear();"));
		
		getURL(url);
		homepage = getHomePageObject();
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Twitter Icon on Login Panel");
		gigyapage = homepage.clickOnTwitterIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Twitter Login");
		homepage = gigyapage.doTwitterLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify user on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.INFO, "First Time SSO Login Using Twitter is Successful");
		
		Assert.assertEquals(isSuccess, true);
		
	}

	
	@Test(enabled = false)
	public void SSO_Login_With_Yahoo_First_Time_Login() throws InterruptedException, IOException{
		String username = "gurimay12@yahoo.in";
		String password = "tribune1";
		String zipcode = "60601";
		boolean isSuccess = false;
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Social Icon Yahoo on Login Panel");
		gigyapage = homepage.clickOnYahooIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Yahoo Login");
		homepage = gigyapage.doYahooLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Enter Zip Code on Intercept Panel ");
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Thank You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify the User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS, "First Time SSO Login Using Yahoo is Successful");
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Delete Browser Session/History");
		AppUtility.deleteYahooCookies(driver);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Open URL for Second Time SSO - Yahoo Login");
		getURL(url);
		homepage = getHomePageObject();
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Social Icon Yahoo on Login Panel");
		gigyapage = homepage.clickOnYahooIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Yahoo Login");
		homepage = gigyapage.doYahooLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify the User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.INFO, "Second Time SSO Login Using Yahoo is Successful");
		
		Assert.assertEquals(isSuccess, true);	

	}
	
	
	
	
	@Test(groups = {"Regression"}, enabled = true)
	public void Linking_ISOtoSSO_ISOtoYahoo() throws InterruptedException, IOException{
		
		homepage.clickOnLoginTopNav();
		homepage.clickOnRegisterButtonOnLoginPanel();
		homepage.doRegistration("tribunedss21@yahoo.com", "tribune1","60601");
		homepage.doLogout();
		homepage.clickOnLoginTopNav();
		gigyapage = homepage.clickOnYahooIcon();
		homepage = gigyapage.doYahooLogin("tribunedss21@yahoo.com","tribune1");
		homepage.doLogin("tribunedss21@yahoo.com ", "tribune1");
		profilepage = homepage.gotoProfilePage();
		
	}
	
	
}
