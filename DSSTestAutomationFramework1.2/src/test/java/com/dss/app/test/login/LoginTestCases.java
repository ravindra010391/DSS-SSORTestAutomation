package com.dss.app.test.login;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.dss.app.apputilities.AppUtility;
import com.dss.app.apputilities.GlobalValues;
import com.dss.app.reporter.ExtentTestManager;
import com.dss.app.test.base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTestCases extends BaseTest {

	@Test(groups = { "Regression" }, enabled = true)
	public void isISORegistrationSuccessful(){
		
		String emailId = AppUtility.emailGeneratorISO();
		boolean isSuccess = false;
		SoftAssert softAssert = new SoftAssert();
		
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Register Button on Login Panel");
		homepage.clickOnRegisterLinkOnLoginPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Registration");
		homepage.doRegistration(emailId, AppUtility.passwordGenerator(1), "60601");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Thank You Button on Thank You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Navigate to the Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify Email on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(emailId);
		softAssert.assertTrue(isSuccess);
		

		
		// Profile Page Checks
		
		//Edit Password
		ExtentTestManager.getTest().log(LogStatus.INFO,"PROFILE PAGE CHECKS");
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit Password on Profile Page");
		String passwordUpdateMsg = profilepage.editPassword("tribune1",	"tribune1");
		softAssert.assertEquals(passwordUpdateMsg, GlobalValues.PASSWORD_SUCCESSFULLY_UPADATED_MSG);
		
		// Edit Name
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit First and Last Name on Profile Page");
		String nameUpdateMsg = profilepage.editName("FirstName", "Lastname");
		softAssert.assertEquals(nameUpdateMsg,	GlobalValues.NAME_SUCCESSFULLY_UPADATED_MSG);
		
		//Edit Username
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit Username on Profile Page");
		String usernameUpdateMsg = profilepage.editUsername("NewUsername1111");
		softAssert.assertEquals(usernameUpdateMsg, GlobalValues.USERNAME_SUCCESSFULLY_UPADATED_MSG);
	
/*		// Linking Facebook Account Form Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO,"Link Facebook Account from Profile Page And Verify FB User in Preferred Email");
		gigyapage = profilepage.clickOnProfileFacebookGigya();
		gigyapage.doFacabookLoginProfilePage(testCaseLevelSSOCredentials.get("Facebook").get(0), testCaseLevelSSOCredentials.get("Facebook").get(1));
		boolean isFacebookSSOLinkedSuccess = profilepage.checkProfileLinkingSuccess(testCaseLevelSSOCredentials.get("Facebook").get(0), "Facebook");
		softAssert.assertTrue(isFacebookSSOLinkedSuccess);
	
		//Disconnect Linked Facebook Account
		ExtentTestManager.getTest().log(LogStatus.INFO, "Disconnect Linked Facebook Account from Profile Page");
		boolean isFacebookSSODisconnectedSuccess = profilepage.disconnectProfileLinkedSSO("Facebook");
		softAssert.assertTrue(isFacebookSSODisconnectedSuccess);
		
*/		ExtentTestManager.getTest().log(LogStatus.INFO,"Goto Home Page and Log Out the User");
		driver.navigate().back();
		homepage.doLogout();
		
		softAssert.assertAll();
		

	
	}
	
	@Test(groups = { "Regression" }, enabled = true)
	public void isISOLoginSuccessful() throws InterruptedException, IOException {

		String username = AppUtility.emailGeneratorISO();
		String password = AppUtility.passwordGenerator(1);
		boolean isSuccess = false;
		boolean isGmailSSOLinkedSuccess = false;
		boolean isGmailSSODisconnectedSuccess = false;
		
		SoftAssert softAssert = new SoftAssert();
		
		//Registering New User and Logging Out 
		ExtentTestManager.getTest().log(LogStatus.INFO, "Register New User");
		homepage.clickOnLoginTopNav();
		homepage.clickOnRegisterLinkOnLoginPanel();
		homepage.doRegistration(username, AppUtility.passwordGenerator(1), "60601");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Log Out the Current User");
		homepage.doLogout();

		//Performiong ISO login
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on TopNav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Enter UserName and Password and Login");
		homepage.doLogin(username, password);
		Thread.sleep(3000);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto to Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		softAssert.assertTrue(isSuccess);
		
		if(isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS,"Expected User is logged In");			
		
		// Profile Page Checks
		
		//Edit Password
		ExtentTestManager.getTest().log(LogStatus.INFO,"PROFILE PAGE CHECKS");
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit Password on Profile Page");
		String passwordUpdateMsg = profilepage.editPassword("tribune1",	"tribune1");
		softAssert.assertEquals(passwordUpdateMsg, GlobalValues.PASSWORD_SUCCESSFULLY_UPADATED_MSG);
		
		// Edit Name
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit First and Last Name on Profile Page");
		String nameUpdateMsg = profilepage.editName("FirstName", "Lastname");
		softAssert.assertEquals(nameUpdateMsg,	GlobalValues.NAME_SUCCESSFULLY_UPADATED_MSG);
		
		//Edit Username
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit Username on Profile Page");
		String usernameUpdateMsg = profilepage.editUsername("NewUsername1111");
		softAssert.assertEquals(usernameUpdateMsg, GlobalValues.USERNAME_SUCCESSFULLY_UPADATED_MSG);

		// ----------------------------------------------
		
/*		// Linking Gmail Account Form Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO,"Link Gmail Account from Profile Page And Verify FB User in Preferred Email");
		gigyapage = profilepage.clickOnProfileGmailGigya();
		profilepage = gigyapage.doGmailLoginProfilePage(
				testCaseLevelSSOCredentials.get("Gmail").get(0),
				testCaseLevelSSOCredentials.get("Gmail").get(1));
	
		isGmailSSOLinkedSuccess = profilepage.checkProfileLinkingSuccess(testCaseLevelSSOCredentials.get("Gmail").get(0), "Gmail");
		softAssert.assertTrue(isGmailSSOLinkedSuccess);
		
		//Disconnect Linked Gmail Account
		ExtentTestManager.getTest().log(LogStatus.INFO, "Disconnect Linked Gmail Account from Profile Page");
		isGmailSSODisconnectedSuccess = profilepage.disconnectProfileLinkedSSO("Gmail");
		softAssert.assertTrue(isGmailSSODisconnectedSuccess);
	
*/		
		ExtentTestManager.getTest().log(LogStatus.INFO,"Goto Home Page and Log Out the User");
		driver.navigate().back();
		homepage.doLogout();
		
		softAssert.assertAll();
	}
	
	
	@Test(groups = { "Regression" }, enabled = false)
	public void SSO_Login_With_Gmail_First_Time_Login()
			throws InterruptedException, IOException {
		String username = testCaseLevelSSOCredentials.get("Gmail").get(0);
		String password = testCaseLevelSSOCredentials.get("Gmail").get(1);
		String zipcode = "60601";
		boolean isSuccess = false;
		SoftAssert softAssert =  new SoftAssert();

		//First Time SSO Login Using Gmail
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Gmail Icon on Login Panel");
		gigyapage = homepage.clickOnGmailIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Gmail Login");
		homepage = gigyapage.doGmailLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Enter Zip Code on Intecept Panel");
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Continue Button On Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Continue Button on Thnak You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS,	"First Time SSO Login with Gmail PASSED");
		softAssert.assertTrue(isSuccess);

/*		
		// Clearing all Browser Data for Second Time SSO Login
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Delete Browser Session/History");
		AppUtility.deleteGmailCookies(driver);
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Open URL for Second Time SSO Login");
		getURL(url);
		homepage = getHomePageObject();

		// Second Time SSO Login Using Gmail
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Gmail Icon on Login Panel");
		gigyapage = homepage.clickOnGmailIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Gmail Login ");
		homepage = gigyapage.doGmailLogin(username, password);

		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS, "Second Time SSO Login with Gmail PASSED");
		softAssert.assertTrue(isSuccess);
		*/
		
		
		//Profile Page Basic checks
		
		// Edit Name
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit First and Last Name on Profile Page");
		String nameUpdateMsg = profilepage.editName("FirstName", "Lastname");
		softAssert.assertEquals(nameUpdateMsg,	GlobalValues.NAME_SUCCESSFULLY_UPADATED_MSG);
		
		//Edit Username
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit Username on Profile Page");
		String usernameUpdateMsg = profilepage.editUsername("Username1");
		softAssert.assertEquals(usernameUpdateMsg, GlobalValues.USERNAME_SUCCESSFULLY_UPADATED_MSG);
		
		// Linking Facebook Account from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO,"Link Facebook Account from Profile Page And Verify FB User in Preferred Email");
		gigyapage = profilepage.clickOnProfileFacebookGigya();		
		Thread.sleep(3000);
		profilepage = gigyapage.doFacabookLoginProfilePage(
				testCaseLevelSSOCredentials.get("Facebook").get(0),
				testCaseLevelSSOCredentials.get("Facebook").get(1));
		boolean isFacebookSSOLinkedSuccess = profilepage.checkProfileLinkingSuccess(testCaseLevelSSOCredentials.get("Facebook").get(0), "facebook");
		softAssert.assertTrue(isFacebookSSOLinkedSuccess);

		//Disconnect Linked Facebook from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO, "Disconnect Linked Facebook Account from Profile Page");
		boolean isTwitterSSODisconnectedSuccess = profilepage.disconnectProfileLinkedSSO("Facebook");
		softAssert.assertTrue(isTwitterSSODisconnectedSuccess);
		
		softAssert.assertAll();
	}

	@Test(groups = { "Regression" }, enabled = false)
	public void SSO_Login_With_Yahoo_First_Time_Login()
			throws InterruptedException, IOException {
		String username = testCaseLevelSSOCredentials.get("Yahoo").get(0);
		String password = testCaseLevelSSOCredentials.get("Yahoo").get(1);
		String zipcode = "60601";
		boolean isSuccess = false;
		SoftAssert softAssert =  new SoftAssert();
		
		// First Time SSO Login Using Yahoo
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Social Icon Yahoo on Login Panel");
		gigyapage = homepage.clickOnYahooIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Yahoo Login");
		homepage = gigyapage.doYahooLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Enter Zip Code on Intercept Panel ");
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO,"Click on Continue Button on Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Continue Button on Thank You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify the User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS,	"First Time SSO Login Using Yahoo is Successful");
		softAssert.assertTrue(isSuccess);
		
		// Clearing Browser History 
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Delete Browser Session/History");
		AppUtility.deleteYahooCookies(driver);
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Open URL for Second Time SSO - Yahoo Login");
		getURL(url);
		homepage = getHomePageObject();

		//Second Time SSO Login Using Same Yahoo User
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Social Icon Yahoo on Login Panel");
		gigyapage = homepage.clickOnYahooIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Yahoo Login");
		homepage = gigyapage.doYahooLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify the User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.INFO,	"Second Time SSO Login Using Yahoo is Successful");
		softAssert.assertTrue(isSuccess);
		
		//Profile Page Basic checks
		
		// Edit Name
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit First and Last Name on Profile Page");
		String nameUpdateMsg = profilepage.editName("FirstName", "Lastname");
		softAssert.assertEquals(nameUpdateMsg,	GlobalValues.NAME_SUCCESSFULLY_UPADATED_MSG);
		
		//Edit Username
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit Username on Profile Page");
		String usernameUpdateMsg = profilepage.editUsername("Username1");
		softAssert.assertEquals(usernameUpdateMsg, GlobalValues.USERNAME_SUCCESSFULLY_UPADATED_MSG);
		
		// Linking AOL Account from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO,"Link Yahoo Account from Profile Page And Verify User in Preferred Email");
		gigyapage = profilepage.clickOnProfileYahooGigya();
		profilepage = gigyapage.doYahooLoginProfilePage(
				testCaseLevelSSOCredentials.get("Aol").get(0),
				testCaseLevelSSOCredentials.get("Aol").get(1));
		boolean isYahooSSOLinkedSuccess = profilepage.checkProfileLinkingSuccess(testCaseLevelSSOCredentials.get("Aol").get(0), "Aol");
		softAssert.assertTrue(isYahooSSOLinkedSuccess);

		//Disconnect Linked Gmail from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO, "Disconnect Linked Aol Account from Profile Page");
		boolean isTwitterSSODisconnectedSuccess = profilepage.disconnectProfileLinkedSSO("Aol");
		softAssert.assertTrue(isTwitterSSODisconnectedSuccess);
		
		softAssert.assertAll();	

	}

	@Test(groups = { "Regression" }, enabled = false)
	public void SSO_Login_With_AOL_First_Time_Login()
			throws InterruptedException, IOException {
		String username = testCaseLevelSSOCredentials.get("Aol").get(0);
		String password = testCaseLevelSSOCredentials.get("Aol").get(1);
		String zipcode = "60601";
		boolean isSuccess = false;
		SoftAssert softAssert =  new SoftAssert();

		//First Time SSO Login Using AOL User
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Login on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on AOL icon on Login Panel");
		gigyapage = homepage.clickOnAOLIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform AOL Login ");
		homepage = gigyapage.doAOLLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO,"Enter Zip Code on intercept Panel");
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Continue Button on Thank You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS,	"First Time SSO Login Using AOL is Successful");
		softAssert.assertTrue(isSuccess);

		// Cleating Browser History for Second Time SSO Login
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Delete Browser Session/History");
		AppUtility.deleteAOLCookies(driver);
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Open URL for Second Time SSO Login");
		getURL(url);
		homepage = getHomePageObject();

		// Second Time SSO Login Using Aol User
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Login on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on AOL icon on Login Panel");
		gigyapage = homepage.clickOnAOLIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform AOL Login ");
		homepage = gigyapage.doAOLLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS,	"Second Time SSO Login Using AOL is Successful");
		softAssert.assertTrue(isSuccess);
		
		//Profile Page Basic checks
		
		// Edit Name
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit First and Last Name on Profile Page");
		String nameUpdateMsg = profilepage.editName("FirstName", "Lastname");
		softAssert.assertEquals(nameUpdateMsg,	GlobalValues.NAME_SUCCESSFULLY_UPADATED_MSG);
		
		//Edit Username
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit Username on Profile Page");
		String usernameUpdateMsg = profilepage.editUsername("Username1");
		softAssert.assertEquals(usernameUpdateMsg, GlobalValues.USERNAME_SUCCESSFULLY_UPADATED_MSG);
		
		// Linking Yahoo Account from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO,"Link Yahoo Account from Profile Page And Verify User in Preferred Email");
		gigyapage = profilepage.clickOnProfileYahooGigya();
		profilepage = gigyapage.doYahooLoginProfilePage(
				testCaseLevelSSOCredentials.get("Yahoo").get(0),
				testCaseLevelSSOCredentials.get("Yahoo").get(1));
		boolean isYahooSSOLinkedSuccess = profilepage.checkProfileLinkingSuccess(testCaseLevelSSOCredentials.get("Yahoo").get(0), "Yahoo");
		softAssert.assertTrue(isYahooSSOLinkedSuccess);

		//Disconnect Linked Facebook from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO, "Disconnect Linked Yahoo Account from Profile Page");
		boolean isTwitterSSODisconnectedSuccess = profilepage.disconnectProfileLinkedSSO("Yahoo");
		softAssert.assertTrue(isTwitterSSODisconnectedSuccess);
		
		softAssert.assertAll();

	}


	
	@Test(groups = { "Regression" }, enabled = false)
	public void SSO_Login_With_Facebook_First_Time_Login()
			throws InterruptedException, IOException {
		String username =testCaseLevelSSOCredentials.get("Facebook").get(0);
		String password = testCaseLevelSSOCredentials.get("Facebook").get(1);
		String zipcode = "60601";
		boolean isSuccess = false;
		SoftAssert softAssert = new SoftAssert();
		
		//First Time Login Using SSO - Facebook
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Facebook Icon on Login Panel");
		gigyapage = homepage.clickOnFacebookIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO,"Perform Facebook Login ");
		homepage = gigyapage.doFacebookLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO,"Enter Zip Code on Intercept Panel");
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO,"Click on Continue Button on Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO,"Click on Continue Button on Thank You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,"Verify the User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS,	"First Time SSO Login with Facebook PASSED");
		softAssert.assertTrue(isSuccess);

		//Deleting all Browser History 
		ExtentTestManager.getTest().log(LogStatus.INFO, "Delete Browser Session/History");
		AppUtility.deleteFacebookCookies(driver);

		//Second Time SSO Login Using Same Facebook User
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Enter URL for Second Time SSO Facebook Login");
		getURL(url);
		homepage = getHomePageObject();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Facebook Icon on Login Panel");
		gigyapage = homepage.clickOnFacebookIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Perform Facebook Login ");
		homepage = gigyapage.doFacebookLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify the User on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS,	"Second Time SSO Login with Facebook PASSED");
		softAssert.assertTrue(isSuccess);
		
		//Profile Page Basic checks
		
		// Edit Name
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit First and Last Name on Profile Page");
		String nameUpdateMsg = profilepage.editName("FirstName", "Lastname");
		softAssert.assertEquals(nameUpdateMsg,	GlobalValues.NAME_SUCCESSFULLY_UPADATED_MSG);
		
		//Edit Username
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit Username on Profile Page");
		String usernameUpdateMsg = profilepage.editUsername("Username1");
		softAssert.assertEquals(usernameUpdateMsg, GlobalValues.USERNAME_SUCCESSFULLY_UPADATED_MSG);
		
		// Linking Twitter Account from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO,"Link Twitter Account from Profile Page And Verify FB User in Preferred Email");
		gigyapage = profilepage.clickOnProfileTwitterGigya();
		profilepage = gigyapage.doTwitterLoginProfilePage(
				testCaseLevelSSOCredentials.get("Twitter").get(0),
				testCaseLevelSSOCredentials.get("Twitter").get(1));
		boolean isTwitterSSOLinkedSuccess = profilepage.checkProfileLinkingSuccess(testCaseLevelSSOCredentials.get("Twitter").get(0), "Twitter");
		softAssert.assertTrue(isTwitterSSOLinkedSuccess);

		//Disconnect Linked Twitter from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO, "Disconnect Linked Twitter Account from Profile Page");
		boolean isTwitterSSODisconnectedSuccess = profilepage.disconnectProfileLinkedSSO("Twitter");
		softAssert.assertTrue(isTwitterSSODisconnectedSuccess);
		
		softAssert.assertAll();

	}

	@Test(groups = { "Regression" }, enabled = false)
	public void SSO_Login_With_Twitter_First_Time_Login() throws InterruptedException, IOException {
		String username = testCaseLevelSSOCredentials.get("Twitter").get(0);
		String password = testCaseLevelSSOCredentials.get("Twitter").get(0);
		String zipcode = "60601";
		boolean isSuccess = false;
		SoftAssert softAssert =  new SoftAssert();
		
		// First Time SSO Login Using Twitter
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Twitter Icon on Login Panel");
		gigyapage = homepage.clickOnTwitterIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Twitter Login");
		homepage = gigyapage.doTwitterLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Enter Email ID on Intercept Panel");
		homepage.enterEmailOnInterceptPanel(username);
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Enter Zip Code on Intercept Panel");
		homepage.enterZipcodeOnInterceptPanel(zipcode);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Continue Button on Intercept Panel");
		homepage.clickOnContinueButtonOnInterceptPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Continue Button on Thank You Panel");
		homepage.clickOnContinueButtonOnThankYouPanel();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify user on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.PASS,	"First Time SSO Login Using Twitter is Successful");
		softAssert.assertTrue(isSuccess);
				
		// Cleating Browser History for Second Time SSO Login
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Delete Browser Session/History");
		AppUtility.deleteAOLCookies(driver);
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Open URL for Second Time SSO Login");
		getURL(url);
		homepage = getHomePageObject();

		// Second Time SSO Login Using Twitter
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Login Button on Top Nav");
		homepage.clickOnLoginTopNav();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Click on Twitter Icon on Login Panel");
		gigyapage = homepage.clickOnTwitterIcon();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Perform Twitter Login");
		homepage = gigyapage.doTwitterLogin(username, password);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Goto Profile Page");
		profilepage = homepage.gotoProfilePage();
		ExtentTestManager.getTest().log(LogStatus.INFO,	"Verify user on Profile Page");
		isSuccess = profilepage.isExpectedUserLogged(username);
		if (isSuccess)
			ExtentTestManager.getTest().log(LogStatus.INFO,	"First Time SSO Login Using Twitter is Successful");
		softAssert.assertTrue(isSuccess);
		
		//Profile Page Basic checks
		
		// Edit Name
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit First and Last Name on Profile Page");
		String nameUpdateMsg = profilepage.editName("FirstName", "Lastname");
		softAssert.assertEquals(nameUpdateMsg,	GlobalValues.NAME_SUCCESSFULLY_UPADATED_MSG);
		
		//Edit Username
		ExtentTestManager.getTest().log(LogStatus.INFO,"Edit Username on Profile Page");
		String usernameUpdateMsg = profilepage.editUsername("Username1");
		softAssert.assertEquals(usernameUpdateMsg, GlobalValues.USERNAME_SUCCESSFULLY_UPADATED_MSG);
		
		// Linking Gmail Account from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO,"Link Yahoo Account from Profile Page And Verify User in Preferred Email");
		gigyapage = profilepage.clickOnProfileYahooGigya();
		profilepage = gigyapage.doYahooLoginProfilePage(
				testCaseLevelSSOCredentials.get("Gmail").get(0),
				testCaseLevelSSOCredentials.get("Gmail").get(1));
		boolean isYahooSSOLinkedSuccess = profilepage.checkProfileLinkingSuccess(testCaseLevelSSOCredentials.get("Gmail").get(0), "Gmail");
		softAssert.assertTrue(isYahooSSOLinkedSuccess);

		//Disconnect Linked Gmail from Profile Page
		ExtentTestManager.getTest().log(LogStatus.INFO, "Disconnect Linked Gmail Account from Profile Page");
		boolean isTwitterSSODisconnectedSuccess = profilepage.disconnectProfileLinkedSSO("Gmail");
		softAssert.assertTrue(isTwitterSSODisconnectedSuccess);
		
		softAssert.assertAll();
	}

	
/*	
	@Test(groups = { "Regression" }, enabled = false)
	public void Linking_ISOtoSSO_ISOtoYahoo() throws InterruptedException,
			IOException {

		homepage.clickOnLoginTopNav();
		homepage.clickOnRegisterLinkOnLoginPanel();
		homepage.doRegistration("tribunedss21@yahoo.com", "tribune1", "60601");
		homepage.doLogout();
		homepage.clickOnLoginTopNav();
		gigyapage = homepage.clickOnYahooIcon();
		homepage = gigyapage.doYahooLogin("tribunedss21@yahoo.com", "tribune1");
		homepage.doLogin("tribunedss21@yahoo.com ", "tribune1");
		profilepage = homepage.gotoProfilePage();

	}
*/
}
