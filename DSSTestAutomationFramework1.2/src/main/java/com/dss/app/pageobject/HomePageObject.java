package com.dss.app.pageobject;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.dss.app.coreutilities.CoreUtility;
import com.dss.app.coreutilities.Log;

/*
 * This class will contain all the locators of the HOME PAGE
 */
public class HomePageObject {

	private WebDriver driver;
	private Log Log;
	private ProfilePageObject profilepage;

	public HomePageObject(WebDriver driver, Log Log) {
		this.driver = driver;
		this.Log = Log;
		PageFactory.initElements(driver, this);
	}
	
	// Top Nav locators
	@FindBy(xpath = "html/body/header/div[1]/div[1]/a[2]")
	public  WebElement btn_LoginTopNav;

	@FindBy(xpath = "html/body/header/div[1]/div[1]/a[3]")
	private  WebElement btn_Subscribe;
	
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[3]/a")
	private WebElement link_Register;
	
	//Registration Panel Locators
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[1]/fieldset[1]/input")
	private  WebElement textBox_EmailId_RegistrationPanel;

	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[1]/fieldset[2]/input")
	private  WebElement textBox_Password_RegistrationPanel;
	
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[1]/fieldset[3]/input")
	private WebElement textbox_Zipcode_RegistrationPanel;
	
	@FindBy(xpath = ".//*[@id='reg-overlay']/div/div[2]/form/div[1]/button")
	private WebElement btn_RegisterForFree;
	
	// Login Panel locators					
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[1]/fieldset[1]/input")
	private  WebElement textBox_EmailId;

	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[1]/fieldset[2]/input")
	private  WebElement textBox_Password;

	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[1]/button")
	private  WebElement btn_Login;

	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[1]/div")
	private  WebElement btn_Cancel;

	// Advertise pop up close button
	@FindBy(xpath = ".//*[@id='checkm8Ad_4']")
	public WebElement btn_AdClose;

	// Navigation links for logged in users
	@FindBy(xpath = "html/body/header/div[1]/div[1]/a[4]")
	private  WebElement icon_User;

	@FindBy(xpath = "html/body/header/div[1]/div[1]/ul/li[1]/a")
	private  WebElement link_ProfilePage;

	@FindBy(xpath = "html/body/header/div[1]/div[1]/ul/li[2]/a")
	private  WebElement link_Newsletter;

	@FindBy(xpath = "html/body/header/div[1]/div[1]/ul/li[3]/a")
	private  WebElement link_SignOut;
	
	//Social media icons for SSO login
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[2]/div/div[1]")
	private WebElement icon_Facebook;
	
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[2]/div/div[2]")
	private WebElement icon_Twitter;
	
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[2]/div/div[3]")
	private WebElement icon_Yahoo;
	
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[2]/div/div[4]")
	private WebElement icon_Aol;
	
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[2]/div/div[5]")
	private WebElement icon_Gmail;
	
	// Intercept panel
	@FindBy(name = "zipCode")
	private WebElement textbox_Zipcode_InterceptPanel;
	
	@FindBy(xpath = "//*[@id='reg-overlay']/div/div[2]/form/div[1]/button" )
	private WebElement btn_Continue_InterceptPanel;
	
	@FindBy(name = "email")
	private WebElement textbox_Email_InterceptPanel;
		
	
	//Thank You Panel
	@FindBy(xpath = "//*[@id='reg-overlay']/div[2]/div[2]/div[2]/div")
	private WebElement btn_Continue_ThankYouPanel;	

	
	//-----------Public methods-------------------

	public HomePageObject clickOnLoginTopNav() {
		CoreUtility.highlightElement(btn_LoginTopNav, driver);
		CoreUtility.clickOnElement(btn_LoginTopNav);
		Log.info("Clicked on Login Button of Top NAV");
		return this;
	}

	public HomePageObject clickOnRegisterLinkOnLoginPanel() {
		CoreUtility.highlightElement(link_Register, driver);
		CoreUtility.clickOnElement(link_Register);
		Log.info("Clicked on Register link on Login Panel");
		return this;
	}
	public HomePageObject doLogin(String userName, String userPassword) {
		enterEmailIdonLoginPanel(userName);
		enterPasswordOnLoginPanel(userPassword);
		clickloginOnLoginPanel();
		return this;
	}

	public void doRegistration(String username, String password, String zipcode){
		enterEmailIdonRegistrationPanel(username);
		enterPasswordOnRegistrationPanel(password);
		enterZipcodeOnRegistrationPanel(zipcode);
		clickOnRegisterButtonOnRegistrationPanel();
		
	}
	
	public HomePageObject doLogout(){
		clickOnUserIcon().clickOnLogoutLink();
		return this;
	}
	
	public ProfilePageObject gotoProfilePage() {

		profilepage = clickOnUserIcon().clickOnAccountLink();
		Log.info("Redirecting to Profile page");
		return profilepage;
	}
	
	public GigyaPageObject clickOnFacebookIcon(){
		CoreUtility.highlightElement(icon_Facebook, driver);
		CoreUtility.waitForElementPresent(icon_Facebook, driver);
		CoreUtility.clickOnElement(icon_Facebook);
		Log.info("Clicked on Social Media Icon: Facebook");
		return new GigyaPageObject(driver, Log);
	}
	
	public GigyaPageObject clickOnGmailIcon(){
		CoreUtility.highlightElement(icon_Gmail, driver);
		CoreUtility.waitForElementPresent(icon_Gmail, driver);
		CoreUtility.clickOnElement(icon_Gmail);
		Log.info("Clicked on Social Media Icon: Gmail");
		return new GigyaPageObject(driver, Log);
	}
	
	
	public void enterZipcodeOnInterceptPanel(String zipcode){
		CoreUtility.waitForElementPresent(textbox_Zipcode_InterceptPanel, driver);
		CoreUtility.highlightElement(textbox_Zipcode_InterceptPanel, driver);
		CoreUtility.enterData(zipcode, textbox_Zipcode_InterceptPanel);
		Log.info("Entered the zipcode");
	}
	
	public void clickOnContinueButtonOnInterceptPanel(){
		CoreUtility.waitForElementPresent(btn_Continue_InterceptPanel, driver);
		CoreUtility.highlightElement(btn_Continue_InterceptPanel, driver);
		CoreUtility.clickOnElement(btn_Continue_InterceptPanel);
		Log.info("Clicked on continue Button");
	}
	
	public void clickOnContinueButtonOnThankYouPanel(){
		CoreUtility.highlightElement(btn_Continue_ThankYouPanel, driver);
		CoreUtility.waitForElementPresent(btn_Continue_ThankYouPanel, driver);
		CoreUtility.clickOnElement(btn_Continue_ThankYouPanel);
		Log.info("Clicked On Continue Button On Thank You Panel");
	}
	
	public GigyaPageObject clickOnAOLIcon(){
		CoreUtility.waitForElementPresent(icon_Aol, driver);
		CoreUtility.highlightElement(icon_Aol, driver);
		CoreUtility.clickOnElement(icon_Aol);
		Log.info("Clicked on AOL Icon from Login Panel");
		return new GigyaPageObject(driver, Log);
	}

	public GigyaPageObject clickOnTwitterIcon() throws InterruptedException{
		CoreUtility.waitForElementPresent(icon_Twitter, driver);
		CoreUtility.highlightElement(icon_Twitter, driver);
		CoreUtility.clickOnElement(icon_Twitter);
		Log.info("Clicked on Twitter Icon from Login Panel");
		Thread.sleep(5000);
		return new GigyaPageObject(driver, Log);
	}
	public void enterEmailOnInterceptPanel(String email){
		CoreUtility.waitForElementPresent(textbox_Email_InterceptPanel, driver);
		CoreUtility.highlightElement(textbox_Email_InterceptPanel, driver);
		CoreUtility.enterData(email, textbox_Email_InterceptPanel);
		Log.info("Email is Entered on Intercept Panel");
	}
	
	public GigyaPageObject clickOnYahooIcon(){
		CoreUtility.waitForElementPresent(icon_Yahoo, driver);
		CoreUtility.highlightElement(icon_Yahoo, driver);
		CoreUtility.clickOnElement(icon_Yahoo);
		Log.info("Clicked on Yahoo Icon from Login Panel");
		return new GigyaPageObject(driver, Log);
	}


	// ****************PRIVATE METHODS*******************************
	private HomePageObject enterEmailIdonLoginPanel(String emailId) {
		CoreUtility.highlightElement(textBox_EmailId, driver);
		CoreUtility.waitForElementPresent(textBox_EmailId,
				driver);
		CoreUtility.enterData(emailId, textBox_EmailId);
		Log.info("Entered Email id: "+emailId);
		return this;
	}

	private HomePageObject enterPasswordOnLoginPanel(String password) {
		CoreUtility.waitForElementPresent(textBox_Password,
				driver);
		CoreUtility.highlightElement(textBox_Password, driver);
		CoreUtility.enterData(password, textBox_Password);
		Log.info("Entered Password");
		return this;
	}

	private HomePageObject clickloginOnLoginPanel() {
		CoreUtility.waitForElementPresent(btn_Login, driver);
		CoreUtility.highlightElement(btn_Login, driver);
		CoreUtility.clickOnElement(btn_Login);
		Log.info("Clicked on Login button at the login panel");
		return this;
	}
	
	
	private HomePageObject enterEmailIdonRegistrationPanel(String emailId) {
		CoreUtility.highlightElement(textBox_EmailId_RegistrationPanel, driver);
		CoreUtility.waitForElementPresent(textBox_EmailId_RegistrationPanel,
				driver);
		CoreUtility.enterData(emailId, textBox_EmailId_RegistrationPanel);
		Log.info("Entered Email id: "+emailId);
		return this;
	}

	private HomePageObject enterPasswordOnRegistrationPanel(String password) {
		CoreUtility.waitForElementPresent(textBox_Password_RegistrationPanel,
				driver);
		CoreUtility.highlightElement(textBox_Password_RegistrationPanel, driver);
		CoreUtility.enterData(password, textBox_Password_RegistrationPanel);
		Log.info("Entered Password");
		return this;
	}

	private HomePageObject enterZipcodeOnRegistrationPanel(String zipcode) {
		CoreUtility.waitForElementPresent(textbox_Zipcode_RegistrationPanel,
				driver);
		CoreUtility.highlightElement(textbox_Zipcode_RegistrationPanel, driver);
		CoreUtility.enterData(zipcode, textbox_Zipcode_RegistrationPanel);
		Log.info("Entered Zipcode");
		return this;
	}

	private HomePageObject clickOnRegisterButtonOnRegistrationPanel() {
		CoreUtility.waitForElementPresent(btn_RegisterForFree, driver);
		CoreUtility.highlightElement(btn_RegisterForFree, driver);
		CoreUtility.clickOnElement(btn_RegisterForFree);
		Log.info("Clicked on 'Register For Free' button at the login panel");
		return this;
	}

	private HomePageObject clickCancel() {
		CoreUtility.clickOnElement(btn_Cancel);
		Log.info("Clicked on Cancel button at the login panel");
		return this;
	}

	private HomePageObject clickOnUserIcon() {
		CoreUtility.highlightElement(icon_User, driver);
		CoreUtility.clickOnElement(icon_User);
		Log.info("Clicked on User Account menu");
		return this;
	}
	
	private HomePageObject clickOnLogoutLink() {
		CoreUtility.highlightElement(link_SignOut, driver);
		CoreUtility.clickOnElement(link_SignOut);
		Log.info("Clicked on sign Out link");
		return this;
	}


	private ProfilePageObject clickOnAccountLink(){
		CoreUtility.waitForElementPresent(link_ProfilePage,
				driver);
		CoreUtility.highlightElement(link_ProfilePage, driver);
		CoreUtility.clickOnElement(link_ProfilePage);
		Log.info("Clicked on Profile link of account menu drop-down");
		return new ProfilePageObject(driver,Log);
	}

	
}
