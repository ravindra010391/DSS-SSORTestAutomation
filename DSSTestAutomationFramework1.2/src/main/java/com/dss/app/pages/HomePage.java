package com.dss.app.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.dss.app.coreutilities.CoreUtility;
import com.dss.app.coreutilities.Log;
import com.dss.app.pageobject.HomePageObject;

/*
 * This class will contain all the functionalities of the HOME PAGE
 */

public class HomePage {

	private WebDriver driver;
	private ProfilePage profilepage;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, HomePageObject.class);
		Log.logInit();
	}

	public HomePage clickOnLoginTopNav() {
		CoreUtility.highlightElement(HomePageObject.btn_LoginTopNav, driver);
		CoreUtility.clickOnElement(HomePageObject.btn_LoginTopNav);
		Log.info("Clicked on Login Button of Top NAV");
		return this;
	}

	public void doLogin(String userName, String userPassword) {
		enterEmailId(userName);
		enterPassword(userPassword);
		clicklogin();

	}

	public ProfilePage gotoProfilePage() {

		profilepage = clickOnUserIcon().clickOnAccountLink();
		Log.info("Redirecting to Profile page");
		return profilepage;
	}
	
	

	// ****************PRIVATE METHODS*******************************
	private HomePage enterEmailId(String emailId) {
		CoreUtility.highlightElement(HomePageObject.textBox_EmailId, driver);
		CoreUtility.waitForElementPresent(HomePageObject.textBox_EmailId,
				driver);
		CoreUtility.enterData(emailId, HomePageObject.textBox_EmailId);
		Log.info("Entered Email id");
		return this;
	}

	private HomePage enterPassword(String password) {
		CoreUtility.waitForElementPresent(HomePageObject.textBox_Password,
				driver);
		CoreUtility.highlightElement(HomePageObject.textBox_Password, driver);
		CoreUtility.enterData(password, HomePageObject.textBox_Password);
		Log.info("Entered Password");
		return this;
	}

	private HomePage clicklogin() {
		CoreUtility.waitForElementPresent(HomePageObject.btn_Login, driver);
		CoreUtility.highlightElement(HomePageObject.btn_Login, driver);
		CoreUtility.clickOnElement(HomePageObject.btn_Login);
		Log.info("Clicked on Login button at the login panel");
		return this;
	}

	private HomePage clickCancel() {
		CoreUtility.clickOnElement(HomePageObject.btn_Cancel);
		Log.info("Clicked on Cancel button at the login panel");
		return this;
	}

	private HomePage clickOnUserIcon() {
		CoreUtility.highlightElement(HomePageObject.icon_User, driver);
		CoreUtility.clickOnElement(HomePageObject.icon_User);
		Log.info("Clicked on User Account menu");
		return this;
	}

	private ProfilePage clickOnAccountLink() {
		CoreUtility.waitForElementPresent(HomePageObject.link_ProfilePage,
				driver);
		CoreUtility.highlightElement(HomePageObject.link_ProfilePage, driver);
		CoreUtility.clickOnElement(HomePageObject.link_ProfilePage);
		Log.info("Clicked on Profile link of account menu drop-down");
		return new ProfilePage(driver);
	}

}
