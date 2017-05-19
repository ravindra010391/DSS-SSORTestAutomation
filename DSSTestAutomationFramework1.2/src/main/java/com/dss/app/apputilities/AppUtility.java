package com.dss.app.apputilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.dss.app.coreutilities.CoreUtility;
import com.dss.p2p.pageobject.P2PHomePageObject;
import com.dss.p2p.pageobject.P2PLoginPageObject;
import com.dss.p2p.pageobject.P2PRegistrationPageObject;

public class AppUtility {

	public static Stack stack_Aol;
	public static Stack stack_Yahoo;
	public static Stack stack_Gmail;

	// This method will close the advertisement if it is displayed
	public static void closeAds(WebElement element, WebDriver driver) {

		try {
			CoreUtility.waitForElementPresent(element, driver);
			if (element != null) {
				CoreUtility.clickOnElement(element);
				// Log.info("Advertisement is present and closed");
			}
		} catch (Exception e) {
			// Log.warn("Advertisement is not displayed");
		}
	}

	// This method will generate random ISO email ID
	public static String emailGeneratorISO() {
		String emailId;
		Random random = new Random();
		int randomnum = random.nextInt(1000);

		String currentdate = getCurrentDate();

		emailId = "ISO" + "_" + currentdate + "_" + randomnum + "@SSOR.com";
		return emailId;
	}

	public static String passwordGenerator(int passcode){
		if(passcode == 1)
			return GlobalValues.PASSWORD1;
		else
			return GlobalValues.PASSWORD2;
		}
	
	public static String getCurrentDate() {

		String current_Date = null;
		DateFormat timeFormat = new SimpleDateFormat("ddMMYYYY");
		Date date = new Date();
		current_Date = timeFormat.format(date);

		return current_Date;

	}

	public static void deleteGmailCookies(WebDriver driver)
			throws MalformedURLException, IOException, InterruptedException {
		deleteAllCookies(driver, GlobalValues.SSO_GMAIL_URL_1);
		deleteAllCookies(driver, GlobalValues.SSO_GMAIL_URL_2);
	}

	public static void deleteFacebookCookies(WebDriver driver) {
		deleteAllCookies(driver, GlobalValues.SSO_FACEBOOK_URL);
	}

	public static void deleteYahooCookies(WebDriver driver) {
		deleteAllCookies(driver, GlobalValues.SSO_YAHOO_URL);
	}

	public static void deleteTwitterCookies(WebDriver driver) {
		deleteAllCookies(driver, GlobalValues.SSO_TWITTER_URL);
	}

	public static void deleteAOLCookies(WebDriver driver) {
		deleteAllCookies(driver, GlobalValues.SSO_AOL_URL);
	}

	public static void deleteAllCookies(WebDriver driver, String URL) {
		driver.manage().deleteAllCookies();
		driver.get(URL);
		driver.manage().deleteAllCookies();
	}

	public static void deleteTestDataFromP2P(List<String> allEmailId)
	 {

		boolean retry  = true;
		P2PLoginPageObject p2pLogIn;
		P2PHomePageObject p2pHome;
		P2PRegistrationPageObject p2pRegistration;
		PhantomJSDriver localdriver;

		try{
		System.setProperty("phantomjs.binary.path",
				GlobalValues.PHANTOMJS_DRIVER_PATH);
		localdriver = new PhantomJSDriver();
		localdriver.get(GlobalValues.P2P_STAGE_URL);
		p2pLogIn = new P2PLoginPageObject(localdriver);
		p2pHome = p2pLogIn.doP2PLogin(GlobalValues.P2P_STAGE_USERNAME,
				GlobalValues.P2P_STAGE_PASSWORD);
		p2pRegistration = p2pHome.clickRegistration();

		for (String email : allEmailId) {
			p2pRegistration.deleteEntry(email);
		}
		localdriver.close();
		
		}catch(Exception e){
			if(retry){
			deleteTestDataFromP2P(allEmailId); // Will call destroy method second time if there are failures during first time
			retry =  false;
			}
		}
		

	}

	public static void initAllSSOStacks() throws IOException {

		stack_Aol = SSOStacks.loadSSOStack("AOL");
		stack_Yahoo = SSOStacks.loadSSOStack("Yahoo");
		stack_Gmail = SSOStacks.loadSSOStack("Gmail");

	}

	public static Map<String, ArrayList<String>> getTestCaseLevelSSOTestUsers(
			String testcaseName) {

		Map<String, ArrayList<String>> testCaseLevelSSOCredentials = new HashMap<String, ArrayList<String>>();

		testCaseLevelSSOCredentials.put("Gmail",
				SSOStacks.getIDFromStack(stack_Gmail));
		testCaseLevelSSOCredentials.put("Yahoo",
				SSOStacks.getIDFromStack(stack_Yahoo));
		testCaseLevelSSOCredentials.put("Aol",
				SSOStacks.getIDFromStack(stack_Aol));
		/*--------------------------------------------------------------*/
		if (testcaseName.toLowerCase().contains("ssotosso")) {

			if (testcaseName.toLowerCase().contains("yahoo")) {

				if (testcaseName.toLowerCase().contains("facebook")) {
					testCaseLevelSSOCredentials.put("Facebook",
							testCaseLevelSSOCredentials.get("Yahoo"));
					testCaseLevelSSOCredentials.put("Twitter",
							testCaseLevelSSOCredentials.get("Gmail"));
					System.out.println("yahoo and FB");
				} else {
					testCaseLevelSSOCredentials.put("Facebook",
							testCaseLevelSSOCredentials.get("Aol"));
					testCaseLevelSSOCredentials.put("Twitter",
							testCaseLevelSSOCredentials.get("Yahoo"));
					System.out.println("yahoo and twitter");
				}
			}

			else if (testcaseName.toLowerCase().contains("gmail")) {

				if (testcaseName.toLowerCase().contains("facebook")) {
					testCaseLevelSSOCredentials.put("Facebook",
							testCaseLevelSSOCredentials.get("Gmail"));
					testCaseLevelSSOCredentials.put("Twitter",
							testCaseLevelSSOCredentials.get("Yahoo"));
					System.out.println("gmail and FB");
				} else {
					testCaseLevelSSOCredentials.put("Facebook",
							testCaseLevelSSOCredentials.get("Yahoo"));
					testCaseLevelSSOCredentials.put("Twitter",
							testCaseLevelSSOCredentials.get("Gmail"));
					System.out.println("gmail and twitter");
				}
			}

			else {
				testCaseLevelSSOCredentials.put("Facebook",
						testCaseLevelSSOCredentials.get("Aol"));
				testCaseLevelSSOCredentials.put("Twitter",
						testCaseLevelSSOCredentials.get("Gmail"));
				if (testcaseName.toLowerCase().contains("Facebook")) {
					System.out.println("AOL and FB");
				} else {
					testCaseLevelSSOCredentials.put("Facebook",
							testCaseLevelSSOCredentials.get("Yahoo"));
					testCaseLevelSSOCredentials.put("Twitter",
							testCaseLevelSSOCredentials.get("Aol"));
					System.out.println("AOL and FB");
				}
			}

		} else {
			Random randomNumber = new Random();
			int swapCounter = randomNumber.nextInt(5);
			switch (swapCounter) {

			case 0:
				testCaseLevelSSOCredentials.put("Facebook",
						testCaseLevelSSOCredentials.get("Yahoo"));
				testCaseLevelSSOCredentials.put("Twitter",
						testCaseLevelSSOCredentials.get("Aol"));
				break;
			case 1:
				testCaseLevelSSOCredentials.put("Facebook",
						testCaseLevelSSOCredentials.get("Yahoo"));
				testCaseLevelSSOCredentials.put("Twitter",
						testCaseLevelSSOCredentials.get("Gmail"));
				break;
			case 2:
				testCaseLevelSSOCredentials.put("Facebook",
						testCaseLevelSSOCredentials.get("Gmail"));
				testCaseLevelSSOCredentials.put("Twitter",
						testCaseLevelSSOCredentials.get("Aol"));
				break;
			case 3:
				testCaseLevelSSOCredentials.put("Facebook",
						testCaseLevelSSOCredentials.get("Gmail"));
				testCaseLevelSSOCredentials.put("Twitter",
						testCaseLevelSSOCredentials.get("Yahoo"));
				break;
			case 4:
				testCaseLevelSSOCredentials.put("Facebook",
						testCaseLevelSSOCredentials.get("Aol"));
				testCaseLevelSSOCredentials.put("Twitter",
						testCaseLevelSSOCredentials.get("Yahoo"));
				break;
			case 5:
				testCaseLevelSSOCredentials.put("Facebook",
						testCaseLevelSSOCredentials.get("Aol"));
				testCaseLevelSSOCredentials.put("Twitter",
						testCaseLevelSSOCredentials.get("Gmail"));
				break;

			default:
				testCaseLevelSSOCredentials.put("Facebook",
						testCaseLevelSSOCredentials.get("Yahoo"));
				testCaseLevelSSOCredentials.put("Twitter",
						testCaseLevelSSOCredentials.get("Aol"));

			}

		}


		if (testCaseLevelSSOCredentials != null)
			System.out.println("child stack created");
		else
			System.out.println("child is null");
		return testCaseLevelSSOCredentials;

	}

	public static void destoryTestCaseLevelSSOTestUsers(
			Map<String, ArrayList<String>> testCaseLevelSSOCredentials)
		  {

		// deleting twitter and facebook as it is programmatically appended
		// depending upon the TC and we don't have a separate stack for it
		testCaseLevelSSOCredentials.remove("Facebook");
		testCaseLevelSSOCredentials.remove("Twitter");

		List<String> allSSOEmailids = new ArrayList<String>();
		System.out.println(testCaseLevelSSOCredentials.size());
		for (String key : testCaseLevelSSOCredentials.keySet()) {
			allSSOEmailids.add(testCaseLevelSSOCredentials.get(key).get(0));
		}

		System.out.println("calling delete p2p from distroy");

		deleteTestDataFromP2P(allSSOEmailids);

		stack_Aol.push(testCaseLevelSSOCredentials.get("Aol"));
		stack_Yahoo.push(testCaseLevelSSOCredentials.get("Yahoo"));
		stack_Gmail.push(testCaseLevelSSOCredentials.get("Gmail"));
		testCaseLevelSSOCredentials = null;
	}
}
