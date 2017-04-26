package com.dss.app.pageobject;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.dss.app.coreutilities.CoreUtility;
import com.dss.app.coreutilities.Log;

/*
 * This class will contain all the locators of the HOME PAGE
 */

public class ProfilePageObject {

	private WebDriver driver;
	private Log Log;

	public ProfilePageObject(WebDriver driver, Log Log) throws IOException {
		this.driver = driver;
		this.Log = Log;
		PageFactory.initElements(driver, this);
		// Log.logInit();
	}

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[1]/div[2]")
	private WebElement preferredEmail;

	@FindBy(xpath = "//*[@id='signup-login']/div/div/ul/li[3]/a")
	private WebElement topLogOutLink;

	// Password tab locators
	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[2]/button")
	private WebElement btn_EditPassword;

	@FindBy(name = "oldPassword")
	private WebElement textbox_OldPassword;

	@FindBy(name = "password")
	private WebElement textbox_NewPassword;

	@FindBy(name = "password2")
	private WebElement textbox_ReTypeNewPassword;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[2]/div[3]/form/button")
	private WebElement btn_SavePassword;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[2]/div[3]/form/a")
	private WebElement btn_CancelPassword;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[2]/div[5]")
	private WebElement label_PasswordSuccessfulUpdateMessage;

	// Name tab locators
	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[4]/button")
	private WebElement btn_EditName;

	@FindBy(name = "firstName")
	private WebElement textbox_FirstName;

	@FindBy(name = "lastName")
	private WebElement textbox_LastName;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[4]/div[3]/form/button")
	private WebElement btn_SaveName;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[4]/div[3]/form/a")
	private WebElement btn_CancelName;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[4]/div[5]")
	private WebElement label_NameUpdateSuccessMessage;

	@FindBy(xpath = "//*[@id='signup-login']/div/div/a[3]")
	private WebElement label_UserGreetingText;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[4]/div[2]")
	private WebElement label_CurrentFirstAndLastName;

	// Username tab locators
	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[3]/button")
	private WebElement btn_UsernameEdit;

	@FindBy(name = "userName")
	private WebElement textbox_Username;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[3]/div[3]/form/button")
	private WebElement btn_SaveUsername;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[3]/div[3]/form/a")
	private WebElement btn_CancelUsername;

	@FindBy(xpath = "//*[@id='content']/div/div[2]/div/div/div[3]/div[2]")
	private WebElement label_CurrentUsername;

	@FindBy(xpath = ".//*[@id='content']/div/div[2]/div/div/div[3]/div[@data-reg-role='done']")
	private WebElement label_UsernameUpdateSuccessMessage;

	
	
	// --------------Public Methods
	// ---------------------------------------------
	public boolean isExpectedUserLogged(String emailId) {

		boolean isSuccess = false;
		if (emailId.equalsIgnoreCase(getPreferredEmail())) {
			isSuccess = true;
			Log.info("Preferred Email matched with the User's Email ");
		}
		return isSuccess;
	}

	public String editPassword(String oldPassword, String newPassword) {
		Log.info("**Profile Page - Edit Password **");
		String passwordSuccessfulUpadteMessage = null;
		clickOnPasswordEditButton();
		enterOldPassword(oldPassword);
		enterNewPassword(newPassword);
		enterConfirmPassword(newPassword);
		passwordSuccessfulUpadteMessage = clickonPassswordSaveButton();

		return passwordSuccessfulUpadteMessage;
	}

	public String editName(String firstName, String lastName) {
		Log.info("**Profile Page - Edit Name **");
		String nameSuccessfulUpadteMessage = null;
		clickOnNameEditButton();
		enterFirstName(firstName);
		enterLastName(lastName);
		nameSuccessfulUpadteMessage = clickonNameSaveButton();

		// Verifing the Name after Update
		String currentName = getCurrentFirstAndLatName();
		if (currentName.equalsIgnoreCase(firstName + " " + lastName))
			Log.info("Your Name is Updated to : " + currentName);
		else
			Log.info("Failed to Update the Name on profile Page!!");

		// Verify the greeting text on Profile Page
		if (getCurrentUserGreetingText().equalsIgnoreCase(currentName))
			Log.info("Greeting Text Consist of the Updated Name ");
		else
			Log.info("Greeting Text does not contains Updated Name");

		return nameSuccessfulUpadteMessage;
	}

	public String editUsername(String username) {
		Log.info("**Profile Page - Edit Username**");
		String usernameSuccessfulUpadteMessage = null;
	
		clickOnUsernameEditButton();
		enterUserName(username);
		usernameSuccessfulUpadteMessage = clickOnSaveUsernameButton();

		// Verifing the Username after Update
		String currentUsername = getCurrentUsername();
		if (currentUsername.equalsIgnoreCase(currentUsername))
			Log.info("Updated Username to: " + currentUsername);
		else
			Log.error("failed to Update the Username!");

		// Verify the greeting text on Profile Page
		if (getCurrentFirstAndLatName().equalsIgnoreCase("")) {
			if (getCurrentUserGreetingText().equalsIgnoreCase(username)) {
				Log.info("Greeting Text Contains Updated Username. .");
			}
		} else {
			Log.info("Name is Present for Current User so Greeting Text does not contain the Username");
		}

		return usernameSuccessfulUpadteMessage;
	}

	// **********************Private Methods****************************
	private String getPreferredEmail() {
		CoreUtility.waitForElementPresent(preferredEmail, driver);
		Log.info("Getting 'Preferred Email' on Profile Page");
		return preferredEmail.getText();
	}

	private void clickOnPasswordEditButton() {
		CoreUtility.waitForElementPresent(btn_EditPassword, driver);
		CoreUtility.highlightElement(btn_EditPassword, driver);
		CoreUtility.clickOnElement(btn_EditPassword);
		Log.info("Clicked on Edit Password Button on Profile Page");
	}

	private void enterOldPassword(String oldPassword) {
		CoreUtility.waitForElementPresent(textbox_OldPassword, driver);
		CoreUtility.highlightElement(textbox_OldPassword, driver);
		CoreUtility.enterData(oldPassword, textbox_OldPassword);
		Log.info("Entered Old Password: " + oldPassword);
	}

	private void enterNewPassword(String newPassword) {
		CoreUtility.waitForElementPresent(textbox_NewPassword, driver);
		CoreUtility.highlightElement(textbox_NewPassword, driver);
		CoreUtility.enterData(newPassword, textbox_NewPassword);
		Log.info("Entered New Password: " + newPassword);
	}

	private void enterConfirmPassword(String newPassword) {
		CoreUtility.waitForElementPresent(textbox_ReTypeNewPassword, driver);
		CoreUtility.highlightElement(textbox_ReTypeNewPassword, driver);
		CoreUtility.enterData(newPassword, textbox_ReTypeNewPassword);
		Log.info("Re-Entered New Password: " + newPassword);
	}

	private String clickonPassswordSaveButton() {
		String passwordSuccessfulUpadteMessage = null;
		CoreUtility.waitForElementPresent(btn_SavePassword, driver);
		CoreUtility.highlightElement(btn_SavePassword, driver);
		CoreUtility.clickOnElement(btn_SavePassword);
		Log.info("Clicked on Save Password Button on Profile Page");

		CoreUtility.waitForElementPresent(
				label_PasswordSuccessfulUpdateMessage, driver);

		if (CoreUtility.waitForElementPresent(
				label_PasswordSuccessfulUpdateMessage, driver) != null) {
			passwordSuccessfulUpadteMessage = label_PasswordSuccessfulUpdateMessage
					.getText();
			Log.info("Password Upadate Success Message: "
					+ passwordSuccessfulUpadteMessage);
		}

		return passwordSuccessfulUpadteMessage;
	}

	// ----------------
	private void clickOnNameEditButton() {
		CoreUtility.waitForElementPresent(btn_EditName, driver);
		CoreUtility.highlightElement(btn_EditName, driver);
		CoreUtility.clickOnElement(btn_EditName);
		Log.info("Clicked on Edit Name Button on Profile Page");
	}

	private void enterFirstName(String firstName) {
		CoreUtility.waitForElementPresent(textbox_FirstName, driver);
		CoreUtility.highlightElement(textbox_FirstName, driver);
		CoreUtility.enterData(firstName, textbox_FirstName);
		Log.info("Entered First Name: " + firstName);
	}

	private void enterLastName(String lastName) {
		CoreUtility.waitForElementPresent(textbox_LastName, driver);
		CoreUtility.highlightElement(textbox_LastName, driver);
		CoreUtility.enterData(lastName, textbox_LastName);
		Log.info("Entered Last Name: " + lastName);
	}

	private String clickonNameSaveButton() {
		String nameSuccessfulUpadteMessage = null;
		CoreUtility.waitForElementPresent(btn_SaveName, driver);
		CoreUtility.highlightElement(btn_SaveName, driver);
		CoreUtility.clickOnElement(btn_SaveName);
		Log.info("Clicked on Save Name Button on Profile Page");

		if (CoreUtility.waitForElementPresent(label_NameUpdateSuccessMessage,
				driver) != null) {
			nameSuccessfulUpadteMessage = label_NameUpdateSuccessMessage
					.getText();
			Log.info("Name Update Success Message: "
					+ nameSuccessfulUpadteMessage);
		}

		return nameSuccessfulUpadteMessage;

	}

	private String getCurrentUserGreetingText() {
		CoreUtility.waitForElementPresent(label_UserGreetingText, driver);
		CoreUtility.highlightElement(label_UserGreetingText, driver);
		return label_UserGreetingText.getAttribute("data-reg-username");
	}

	private String getCurrentFirstAndLatName() {
		CoreUtility
				.waitForElementPresent(label_CurrentFirstAndLastName, driver);
		CoreUtility.highlightElement(label_CurrentFirstAndLastName, driver);
		return label_CurrentFirstAndLastName.getText();

	}

	// --------------
	private void clickOnUsernameEditButton() {
		CoreUtility.waitForElementPresent(btn_UsernameEdit, driver);
		CoreUtility.highlightElement(btn_UsernameEdit, driver);
		CoreUtility.clickOnElement(btn_UsernameEdit);
		Log.info("Clicked on Edit UserName Button on Profile Page");
	}

	private void enterUserName(String username) {
		CoreUtility.waitForElementPresent(textbox_Username, driver);
		CoreUtility.highlightElement(textbox_Username, driver);
		CoreUtility.enterData(username, textbox_Username);
		Log.info("Entered UserName: " + username);
	}

	private String clickOnSaveUsernameButton() {
		String usernameSuccessfulUpadteMessage = null;
		CoreUtility.waitForElementPresent(btn_SaveUsername, driver);
		CoreUtility.highlightElement(btn_SaveUsername, driver);
		CoreUtility.clickOnElement(btn_SaveUsername);
		Log.info("Clicked on Save UserName Button on Profile Page");
		
		// Temparary fix
		usernameSuccessfulUpadteMessage = driver
				.findElement(
						By.xpath("//*[@id='content']/div/div[2]/div/div/div[3]/div[5]"))
				.getText();
		Log.info("Username Update Success Msg: "
				+ usernameSuccessfulUpadteMessage);

		return usernameSuccessfulUpadteMessage;
	}

	private String getCurrentUsername() {
		CoreUtility.waitForElementPresent(label_CurrentUsername, driver);
		return label_CurrentUsername.getText();
	}
	
	
}
