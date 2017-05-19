package com.dss.app.coreutilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.xml.XmlSuite;

import com.dss.app.apputilities.GlobalValues;

/*
  * This class contains all the core methods 
  * Which will be accessed through all the classes
  * These are generic methods 
 */

public class CoreUtility {

	// This method will click on the element passed as a parameter
	public static void clickOnElement(WebElement element) {

		element.click();
	}

	// This method will enter the Test Data in the textbox element
	public static void enterData(String data, WebElement element) {
		element.clear();
		element.sendKeys(data);

	}

	// This method will retrieve the label of the the given element
	public static String getText(WebElement element) {
		return element.getText();
	}

	// This method will check if element is present or not
	public static boolean isPresent(WebElement element) {
		
		if (element.isDisplayed()) {
			return true;
		} else {
			//log.debug("Web element not found");
			return false;
		}
	}

	// This method will wait till element is present
	public static WebElement waitForElementPresent(WebElement element,
			WebDriver driver) {
		WebElement visibleElement = null;

		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			visibleElement = wait.until(ExpectedConditions
					.visibilityOf(element));
			
		} catch (Exception e) {
			System.out.println("Web element not found ");
		}
		return visibleElement;
	}

	// This method will perform mentioned action if alert is present
	public static void handleAlert(WebDriver driver, String action) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			if (action.equalsIgnoreCase("accept")) {
				alert.accept();
				//System.out.println("accepted");
			} else {
				alert.dismiss();
			//System.out.println("dismissed");
			}

		} catch (Exception e) {
			//System.out.println("catched alert");
		}
	}

	// This method will capture the screenshot of the screen
	public static String captureScreen(WebDriver driver, String screenshotName)
			throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		java.io.File source = ts.getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir") + "//TempErrorSreenshot//"
				+ screenshotName + ".png";
		java.io.File destination = new java.io.File(dest);
		org.apache.commons.io.FileUtils.copyFile(source, destination);
		return dest;

	}

	// This method will highlight the webelement
	public static void highlightElement(WebElement element, WebDriver driver) {

		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
				element);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		js.executeScript(
				"arguments[0].setAttribute('style','border: solid 2px white');",
				element);
*/
	}

	// This method will create a physical XML file
	public static synchronized void createPhysicalXMLFileOfSuite(XmlSuite suite,
			String suiteName) throws IOException {

		File file = new File(suiteName);
		System.out.println("file" + file);
		FileWriter writer = new FileWriter(file);
		writer.write(suite.toXml());
		writer.close();
	}

	
	public static synchronized void copyDataFromTempLogFileToMainLogFile(String fromFile, String toFile) throws IOException{      
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader br = null;
        BufferedReader r = null;
        RandomAccessFile oldFile=null;
        
        try{
            br = new BufferedReader(new FileReader(GlobalValues.tempLogFilePath + File.separator + fromFile));
            
            String s1 =null;
            

            while ((s1 = br.readLine()) != null){                         
                list.add(s1);        
            }
            }
        catch (IOException e){
            e.printStackTrace();
     
        }finally{
        	  br.close();              
              oldFile = new RandomAccessFile (GlobalValues.tempLogFilePath + File.separator + fromFile,"rw"); 
              oldFile.setLength(0); 
                 
        }


        BufferedWriter writer=null;
        writer = new BufferedWriter(new FileWriter(GlobalValues.baseDirectory + File.separator + toFile, true));
       
        String listWord;              
        for (int i = 0; i< list.size(); i++){
            listWord = list.get(i);
            writer.write(listWord);
            writer.write("\n");
        }

        writer.close();  
 
        
        
       /* 
        try{
        Path path = Paths.get(GlobalValues.tempLogFilePath+"\\"+fromFile);
        Files.delete(path);
        }catch (Exception e) {
			e.printStackTrace();
		}
        File threadlogFile = new File(GlobalValues.logFilePath+"\\"+fromFile);
        if(threadlogFile.exists())
        	threadlogFile.delete();
       */
    }
	
public static String getMarketName(String marketURL) {
		
		String[] URL = marketURL.split("\\.");
		
		return URL[1];
		
	}

public static String capsFirstLetter(String string) {
	
	String firstLetterCaps = string.substring(0, 1).toUpperCase() + string.substring(1);
	
	return firstLetterCaps;
	
}


public static void cleanAllTempLogFile() throws IOException {
	
	FileUtils.cleanDirectory(new File(GlobalValues.tempLogFilePath));
	
}

public static String switchToWindow(WebDriver driver, String token_url){

	int attempts = 0;
	String parentWindowHandle = driver.getWindowHandle();
	try{	
	Set<String> allWindowHandles = driver.getWindowHandles();
	
	for(String eachWindow : allWindowHandles){
		driver.switchTo().window(eachWindow);
		if(driver.getCurrentUrl().contains(token_url)){
			break;
		}
	}
	}catch(Exception e){
		//Will handle exception in case Social media window gets closed late/while getting URL 
		if(attempts <2){
		switchToWindow( driver, token_url);
		attempts++;
		}
	}
	return parentWindowHandle;
}

public static void switchToParentWindow(WebDriver driver){
	driver.switchTo().defaultContent();
}

}
