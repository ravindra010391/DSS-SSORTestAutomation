package com.dss.app.apputilities;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.dss.app.coreutilities.Log;

public class Config {

	private WebDriver driver;
	private Log Log;

	public Config(Log Log) {
		this.Log = Log;
	}

	// This method will select the browser based on the parameter passed and
	// returns the driver of the browser
	public WebDriver selectBrowserOnLocal(String browser, String platform) {

		switch (browser.toLowerCase()) {
		case "chrome":

			if (platform.equalsIgnoreCase("windows")) {
				System.setProperty("webdriver.chrome.driver",
						GlobalValues.windowsChromeDriverPath);
				driver = new ChromeDriver();
			} else if (platform.equalsIgnoreCase("mac")) {
				Log.info("Mac Chrome browser is selected ");
			} else if (platform.equalsIgnoreCase("android")) {
				Log.info("Android Chrome browser is selected ");
			} else {
			}
			break;

		case "firefox":
			if (platform.equalsIgnoreCase("windows")) {
				System.setProperty("webdriver.gecko.driver",
						GlobalValues.windowsFirefoxDriverPath);
				driver = new FirefoxDriver();
				// Log.info("Windows Firefox browser is selected ");
			} else if (platform.equalsIgnoreCase("mac")) {
				Log.info("Mac Firefox browser is selected ");
			} else
				Log.error("Invalid platform is selected - Please select platform as a [Windows/Mac");
			break;

		case "ie":
			if (platform.equalsIgnoreCase("windows")) {
				Log.info("Windows Internet Explorer browser is selected ");
			} else
				Log.error("Invalid platform is selected - Please select platform as a [Windows]");
			break;

		case "safari":
			if (platform.equalsIgnoreCase("mac")) {
				Log.info("Mac Safari browser is selected ");
			} else if (platform.equalsIgnoreCase("iPhone")) {
				Log.info("iPhone Safari browser is selected ");
			} else
				Log.error("Invalid platform is selected - Please select platform as a [Mac/iPhone]");
			break;

		default:
			Log.error("Invalid platform is selected");

		}
		return driver;
	}

	public synchronized WebDriver selectBrowserOnSauceLab(String browser,
			String platform, Method method) throws MalformedURLException {
		
		if (platform.equalsIgnoreCase("Windows")) {
			platform = GlobalValues.SAUCE_WINDOWS_VERSION;
		} else if (platform.equalsIgnoreCase("Mac")) {
			platform = GlobalValues.SAUCE_MAC_VERSION;
		
		}

		DesiredCapabilities caps = null;
		
		switch (browser.toLowerCase()) {
					
		case "chrome":
			System.out.println("Chrome init");
			caps = DesiredCapabilities.chrome();
			caps.setCapability("locationContextEnabled",false);
			caps.setCapability("version", "latest");
			if (platform.contains("windows"))
				caps.setCapability("platform", platform);
			else if (platform.contains("OS")) 
				caps.setCapability("platform", platform);
			caps.setCapability("name",
					platform + " " + browser + " : " + method.getName());	
			driver = new RemoteWebDriver(new URL(GlobalValues.SAUCE_URL), caps);
			System.out.println("After Driver initialization chrome");
			break;

		case "firefox":
			System.out.println("Firefox init");
			caps = DesiredCapabilities.firefox();
			caps.setCapability("locationContextEnabled",false);
			caps.setCapability("version", "52");
		
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			caps.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, false);
			
			// Below code is to handle the Unsecure Password Exception on FF
			caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			caps.setCapability(FirefoxDriver.PROFILE, profile);
			
			if (platform.contains("windows"))
				caps.setCapability("platform", platform);
			else if (platform.contains("OS")) 
				caps.setCapability("platform", platform);
			caps.setCapability("name",
					platform + " " + browser + " : " + method.getName());
			driver = new RemoteWebDriver(new URL(GlobalValues.SAUCE_URL), caps);
			System.out.println("After Driver initialization ff");
			break;
			
		case "internetexplorer":
			System.out.println("IE init");
			caps = DesiredCapabilities.internetExplorer();
			caps.setCapability("version", "latest");

			if (platform.contains("windows"))
				caps.setCapability("platform", platform);
			else 
				System.out.println("Invalid platform for IE browser");
			
			caps.setCapability("name",
					platform + " " + browser + " : " + method.getName());
			driver = new RemoteWebDriver(new URL(GlobalValues.SAUCE_URL), caps);
			System.out.println("After Driver initialization IE");
			break;
			
		case "safari":
			System.out.println("Init safari");
			caps = DesiredCapabilities.safari();
			caps.setCapability("version", "latest");

			if (platform.contains("OS "))
				caps.setCapability("platform", platform);
			else 
				System.out.println("Invalid platform for SAFARI browser");
			
			caps.setCapability("name",
					platform + " " + browser + " : " + method.getName());
			driver = new RemoteWebDriver(new URL(GlobalValues.SAUCE_URL), caps);
			System.out.println("After Driver initialization safari");
			break;

		default:
			System.out.println("Invalid browser or platform");

		}

		return driver;
	}

}
