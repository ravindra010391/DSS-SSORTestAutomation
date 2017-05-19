package com.dss.app.pageobject;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.dss.app.coreutilities.CoreUtility;
import com.dss.app.coreutilities.Log;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

public class GigyaPageObject {
	
	private WebDriver driver;
	private Log Log;
	
	public GigyaPageObject(WebDriver driver, Log Log){
		this.driver = driver;
		this.Log = Log;
		PageFactory.initElements(driver, this);
	}
	
	//Facebook Gigya locators
	@FindBy(id = "email")
	private WebElement textbox_Facebook_EmailID;

	@FindBy(id = "pass")
	private WebElement textbox_Facebook_Password;
	
	@FindBy(id = "u_0_2")
	private WebElement btn_Facebook_Login;
	
	
	//Gmail Gigya locators
	@FindBy(id = "identifierId")
	private WebElement textbox_Gmail_EmailID;
	
	@FindBy(xpath = "//*[@id='identifierNext']/content/span")
	private WebElement btn_Gmail_Next;
	
	@FindBy(name = "password")
	private WebElement textbox_Gmail_Password;
	
	@FindBy(className = "CwaK9")
	private WebElement btn_Gmail_SignIn;	
	
	
	//AOL locators
	@FindBy(name = "name")
	private WebElement textbox_AOL_Username;
	
	@FindBy(id = "submit")
	private WebElement btn_AOL_SignIN_AfterUsername;
	
	@FindBy(id = "pwdId")
	private WebElement textbox_AOL_Password;
	
	@FindBy(id = "aolsubmit")
	private WebElement btn_AOL_SignIN_AferPassword;
	
	@FindBy(id = "aolsubmit")
	private WebElement btn_AOL_Continue;
	
	//Twitter locators
	@FindBy(id = "username_or_email")
	private WebElement textbox_Twitter_Username;
	
	@FindBy(id = "password")
	private WebElement textbox_Twitter_Password;
	
	@FindBy(id = "allow")
	private WebElement btn_Twitter_AuthorizeApp;
	
	//Yahoo locators
	@FindBy(id = "login-username")
	private WebElement textbox_Yahoo_Username;
	
	@FindBy(id = "login-signin")
	private WebElement btn_Yahoo_Next;
	
	@FindBy(id = "login-passwd")
	private WebElement textbox_Yahoo_Password;
	
	@FindBy(id = "login-signin")
	private WebElement btn_Yahoo_SignIn;
	
	@FindBy(id  = "oauth2-agree")
	private WebElement btn_Yahoo_Agree;
		
	
	
	
	//--------------public methods------------------
	//Need to update the doLogin code as per Home Page 
	public HomePageObject doFacebookLogin(String username, String password) {
		String parentWindowHandle = CoreUtility.switchToWindow(driver, "facebook");
		enterFacebookUsername(username);
		enterFacebookPassword(password);
		clickOnFacebookLoginButton();
		Log.info("Facebook Login Successfull");
		waitForTheLoginAndswitchToParentWindow(driver, parentWindowHandle);
		
		return new HomePageObject(driver, Log);
	}
	
	
	public HomePageObject doGmailLogin(String username, String password) throws InterruptedException{
		String parentWindowHandle = CoreUtility.switchToWindow(driver, "gmail");
		enterGmailEmailID(username);
		clickOnNextButtonAfterEnteringGmailUsername();
		Thread.sleep(5000);
		enterGmailPassword(password);
		clickOnSingINButtonAfterEnteringPassword();
		waitForTheLoginAndswitchToParentWindow(driver, parentWindowHandle);
		Log.info("Gmail Login Successfull");
		
		return new HomePageObject(driver, Log);		
	}
	
	public HomePageObject doAOLLogin(String username, String password) throws InterruptedException{
		String parentWindowHandle = CoreUtility.switchToWindow(driver, "aol");
		enterAOLUsername(username);
		clickOnAOLSignINAfterEnteringUsername();
		enterAOLPassword(password);
		clickOnAOLSignINAfterEnteringPassword();
		clickOnAOLContinueButtonAfterEnteringCredentials();
		Log.info("AOL Login Successful");
		waitForTheLoginAndswitchToParentWindow(driver, parentWindowHandle);
		
		return new HomePageObject(driver, Log);
	}
	
	public HomePageObject doTwitterLogin(String username, String password) throws InterruptedException{
		String parentWindowHandle = CoreUtility.switchToWindow(driver, "twitter");
		enterTwitterUsername(username);
		enterTwitterPassword(password);
		clickOnTwitterAutorizeAppButton();
		Log.info("Twitter Login Successful");
		waitForTheLoginAndswitchToParentWindow(driver, parentWindowHandle);
		
		return new HomePageObject(driver, Log);
	}
	
	public HomePageObject doYahooLogin(String username, String password) throws InterruptedException{
		String parentWindowHandle = CoreUtility.switchToWindow(driver, "yahoo");
		enterYahooUsername(username);
		clickOnYahooNextButtonAfterEnteringUsername();
		enterYahooPassword(password);
		clickOnYahooSingInButtonAfterEnteringPassword();
		clickOnYahooAgreeButtonAfterEnteringCredentials();
		waitForTheLoginAndswitchToParentWindow(driver, parentWindowHandle);
		Log.info("Yahoo Login Successful");
		
		return new HomePageObject(driver, Log);		
	}
	

	public ProfilePageObject doFacabookLoginProfilePage(String username, String password)  {
		doFacebookLogin(username, password);
		return new ProfilePageObject(driver, Log);
		
	}
	public ProfilePageObject doTwitterLoginProfilePage(String username, String password) throws IOException, InterruptedException{
		doTwitterLogin(username, password);
		return new ProfilePageObject(driver, Log);
		
	}
	public ProfilePageObject doYahooLoginProfilePage(String username, String password) throws IOException, InterruptedException{
		doYahooLogin(username, password);
		return new ProfilePageObject(driver, Log);
		
	}
	public ProfilePageObject doGmailLoginProfilePage(String username, String password) throws IOException, InterruptedException{
		doGmailLogin(username, password);
		return new ProfilePageObject(driver, Log);
		
	}
	public ProfilePageObject doAOLLoginProfilePage(String username, String password) throws IOException, InterruptedException{
		doAOLLogin(username, password);
		return new ProfilePageObject(driver, Log);
		
	}
	
	
	
	//---------------private methods----------------
	
	//Will wait if it takes time to login after entering valid credentials
	private void waitForTheLoginAndswitchToParentWindow(WebDriver driver, String parentWindowHandle){
	try{
		int attempts = 1;		
		while(attempts <3){
			if(driver.getWindowHandles().size() == 1){
				driver.switchTo().window(parentWindowHandle);
				Log.info("Switched to Parent Window");
				break;
			}else{
				Thread.sleep(5000);
				attempts++;
			}
		}
	}catch(Exception e){System.out.println("Exception occured While switching to the Parent Window");}
	}
	
	private void enterFacebookUsername(String username){
		CoreUtility.waitForElementPresent(textbox_Facebook_EmailID, driver);
		CoreUtility.highlightElement(textbox_Facebook_EmailID, driver);
		CoreUtility.enterData(username, textbox_Facebook_EmailID);
		Log.info("Entered facebook username: "+username);
	}
	
	private void enterFacebookPassword(String password){
		CoreUtility.waitForElementPresent(textbox_Facebook_Password, driver);
		CoreUtility.highlightElement(textbox_Facebook_Password, driver);
		CoreUtility.enterData(password, textbox_Facebook_Password);
		Log.info("Entered Facebook Password");
	}
	
	private void clickOnFacebookLoginButton(){
		CoreUtility.highlightElement(btn_Facebook_Login, driver);
		CoreUtility.clickOnElement(btn_Facebook_Login);
		Log.info("Clicked on facebook Login Button");
		
	}

	
	private void enterGmailEmailID(String username){
		CoreUtility.waitForElementPresent(textbox_Gmail_EmailID, driver);
		CoreUtility.highlightElement(textbox_Gmail_EmailID, driver);
		CoreUtility.enterData(username, textbox_Gmail_EmailID);
		Log.info("Entered Gmail Email ID: "+username);
	}
	
	private void clickOnNextButtonAfterEnteringGmailUsername(){
		CoreUtility.waitForElementPresent(btn_Gmail_Next, driver);
		CoreUtility.highlightElement(btn_Gmail_Next, driver);
		CoreUtility.clickOnElement(btn_Gmail_Next);
		Log.info("Clicked on Next Button ");
	}
	
	private void enterGmailPassword(String password){
		CoreUtility.waitForElementPresent(textbox_Gmail_Password, driver);
		CoreUtility.highlightElement(textbox_Gmail_Password, driver);
		CoreUtility.enterData(password, textbox_Gmail_Password);
		Log.info("Entered Gmail Email ID ");
	}
	
	private void clickOnSingINButtonAfterEnteringPassword(){
		CoreUtility.waitForElementPresent(btn_Gmail_SignIn, driver);
		CoreUtility.highlightElement(btn_Gmail_SignIn, driver);
		CoreUtility.clickOnElement(btn_Gmail_SignIn);
		Log.info("Clicked on Sign IN Button ");
	}
	
	private void enterAOLUsername(String username){
		CoreUtility.waitForElementPresent(textbox_AOL_Username, driver);
		CoreUtility.highlightElement(textbox_AOL_Username, driver);
		CoreUtility.enterData(username, textbox_AOL_Username);
		Log.info("Entered AOL Username: "+username);
	}
	
	private void clickOnAOLSignINAfterEnteringUsername(){
		CoreUtility.waitForElementPresent(btn_AOL_SignIN_AfterUsername, driver);
		CoreUtility.highlightElement(btn_AOL_SignIN_AfterUsername, driver);
		CoreUtility.clickOnElement(btn_AOL_SignIN_AfterUsername);
		Log.info("Clicked on Sign IN Button after Username");	
	}
		
	private void enterAOLPassword(String password){
		CoreUtility.waitForElementPresent(textbox_AOL_Password, driver);
		CoreUtility.highlightElement(textbox_AOL_Password, driver);
		CoreUtility.enterData(password, textbox_AOL_Password);
		Log.info("Entered AOL Password");
	}
	
	private void clickOnAOLSignINAfterEnteringPassword(){
		CoreUtility.waitForElementPresent(btn_AOL_SignIN_AferPassword, driver);
		CoreUtility.highlightElement(btn_AOL_SignIN_AferPassword, driver);
		CoreUtility.clickOnElement(btn_AOL_SignIN_AferPassword);
		Log.info("Clicked on Sign IN Button after Password");	
	}
	
	private void clickOnAOLContinueButtonAfterEnteringCredentials(){
		CoreUtility.waitForElementPresent(btn_AOL_Continue, driver);
		CoreUtility.highlightElement(btn_AOL_Continue, driver);
		CoreUtility.clickOnElement(btn_AOL_Continue);
		Log.info("Clicked on Continue Button after Entering AOL Credentials");	
	}
	
	private void enterTwitterUsername(String username) throws InterruptedException{
		CoreUtility.waitForElementPresent(textbox_Twitter_Username, driver);
		CoreUtility.highlightElement(textbox_Twitter_Username, driver);
		//CoreUtility.enterData(username, textbox_Twitter_Username); //clear() is not working
		textbox_Twitter_Username.sendKeys(username);
		Log.info("Entered Twitter Username: "+username);
	}
	
	private void enterTwitterPassword(String password){
		CoreUtility.waitForElementPresent(textbox_Twitter_Password, driver);
		CoreUtility.highlightElement(textbox_Twitter_Password, driver);
		CoreUtility.enterData(password, textbox_Twitter_Password);
		Log.info("Entered Twitter Password");
	}
	
	private void clickOnTwitterAutorizeAppButton(){
		CoreUtility.waitForElementPresent(btn_Twitter_AuthorizeApp, driver);
		CoreUtility.highlightElement(btn_Twitter_AuthorizeApp, driver);
		CoreUtility.clickOnElement(btn_Twitter_AuthorizeApp);
		Log.info("Clicked on Autorize App Button ");
	}
	
	
	private void enterYahooUsername(String username){
		CoreUtility.waitForElementPresent(textbox_Yahoo_Username, driver);
		CoreUtility.highlightElement(textbox_Yahoo_Username, driver);
		CoreUtility.enterData(username, textbox_Yahoo_Username);	
		Log.info("Entered Yahoo Username: "+username);
	}
	
	private void clickOnYahooNextButtonAfterEnteringUsername(){
		CoreUtility.waitForElementPresent(btn_Yahoo_Next, driver);
		CoreUtility.highlightElement(btn_Yahoo_Next, driver);
		CoreUtility.clickOnElement(btn_Yahoo_Next);
		Log.info("Clicked on Next Button ");
	}
	
	private void enterYahooPassword(String password){
		CoreUtility.waitForElementPresent(textbox_Yahoo_Password, driver);
		CoreUtility.highlightElement(textbox_Yahoo_Password, driver);
		CoreUtility.enterData(password, textbox_Yahoo_Password);	
		Log.info("Entered Yahoo Password");
	}
	
	private void clickOnYahooSingInButtonAfterEnteringPassword(){
		CoreUtility.waitForElementPresent(btn_Yahoo_SignIn, driver);
		CoreUtility.highlightElement(btn_Yahoo_SignIn, driver);
		CoreUtility.clickOnElement(btn_Yahoo_SignIn);
		Log.info("Clicked on Sign IN Button ");
	}
	
	private void clickOnYahooAgreeButtonAfterEnteringCredentials(){
		CoreUtility.waitForElementPresent(btn_Yahoo_Agree, driver);
		CoreUtility.highlightElement(btn_Yahoo_Agree, driver);
		CoreUtility.clickOnElement(btn_Yahoo_Agree);
		Log.info("Clicked on Agree Button ");
	}
		
}
