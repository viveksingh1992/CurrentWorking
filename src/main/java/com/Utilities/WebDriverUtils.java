package com.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverUtils extends TestBase {
	private static WebElement element = null;
	static String screenShotPath = "";
	public static WebDriver driver = null;
	public static String home = System.getProperty("user.home");
	public static String downloadPath = home + "\\Downloads";
	static boolean flag = false;

	private static String testName;

	public static String getTestName() {
		return testName;
	}

	public static void setTestName(String methodName) {
		testName = methodName;
	}

	public static void maximizeWindow() throws Exception {
		try {
			driver.manage().window().maximize();
			Log.info("Browser Window Maximized.");
		} catch (Exception ex) {
			Log.info("Failed to maximize Browser Window.");
		}
	}

	public static WebDriver openBrowser() throws Exception {
		String sBrowserName;

		try {
			testName = getTestName();
			sBrowserName = Browsers.get();
			BrowserEnum currentBrowser = BrowserEnum.valueOf(sBrowserName.toUpperCase());

			switch (currentBrowser) {

			case FIREFOX:
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions profile = new FirefoxOptions();
				profile.addPreference("browser.helperApps.neverAsk.openFile",
						"application/xls,text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				profile.addPreference("browser.helperApps.neverAsk.saveToDisk",
						"application/xls,text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				driver = new FirefoxDriver(profile);
				Log.info("Execution Browser is: " + sBrowserName);
				break;

			case CHROME:
				ChromeOptions options = new ChromeOptions();
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(options);
				Log.info("Execution Browser is: " + sBrowserName);
				break;

			case IE:
				WebDriverManager.iedriver().arch32().setup();
				driver = new InternetExplorerDriver();
				Log.info("Execution Browser is: " + sBrowserName);
				break;

			default:
				String errorMessage = "The browser '" + sBrowserName + "' is not valid.";
				throw new Exception(errorMessage);
			}
			maximizeWindow();

		} catch (Exception ex) {
			Log.info("Exception in finding browser is: " + ex);
		}
		return driver;
	}

	public static String currentURL() throws Exception {
		String URL = null;
		try {
			URL = driver.getCurrentUrl();
			Log.info("URL is: " + URL);
		} catch (Exception e) {
			Log.info("Failed to get current URL.");
			e.printStackTrace();
		}
		return URL;
	}

	public static boolean goToURL(String url) throws Exception {
		try {
			if (driver == null) {

				throw new Exception("Driver is null");
			}
			driver.get(url);
			Log.info("HomePage URL: " + url);
			flag = true;

		} catch (Exception e) {

			Log.error(e);
		}
		return flag;
	}

	public static boolean verifyPageTitle(String expectedTitle) throws Exception {
		boolean flag = false;
		String actualtitle = null;
		try {
			actualtitle = driver.getTitle();
			Log.info("Expected Title:" + expectedTitle);
			Log.info("Actual Title:" + expectedTitle);
			if (actualtitle.contentEquals(expectedTitle)) {
				flag = true;
				Log.info("Page Title Matched.");
			} else {
				Log.info("Page Title UnMatched.");
			}
		} catch (Exception e) {
			Log.info("Failed to aquire title.");
		}
		return flag;
	}

	public static WebElement findElement(String locatorString, String typeOfLocator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			if (typeOfLocator.equalsIgnoreCase("xpath")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorString)));
				highlightElement(element);
			} else if (typeOfLocator.equalsIgnoreCase("id")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorString)));
				highlightElement(element);
			} else if (typeOfLocator.equalsIgnoreCase("cssselector")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorString)));
				highlightElement(element);
			} else if (typeOfLocator.equalsIgnoreCase("linktext")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorString)));
				highlightElement(element);
			} else if (typeOfLocator.equalsIgnoreCase("name")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorString)));
				highlightElement(element);
			} else if (typeOfLocator.equalsIgnoreCase("classname")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorString)));
				highlightElement(element);
			} else if (typeOfLocator.equalsIgnoreCase("tagname")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorString)));
				highlightElement(element);
			} else if (typeOfLocator.equalsIgnoreCase("partiallinktext")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorString)));
				highlightElement(element);
			} else {
				Log.info("Element Not Found. Please check your Locator or the corresponding locator type.");
			}
		} catch (Exception e) {
			Log.info("Something wrong with element data passed because of which element not Found.");
			Log.error(e);
		}
		Log.info("Element Found using " + locatorString + " as a locator of type " + typeOfLocator);
		return element;
	}

	public static boolean moveToElement(WebElement ElementToBeHover) throws Exception {
		try {
			highlightElement(ElementToBeHover);
			Actions action = new Actions(driver);
			action.moveToElement(ElementToBeHover).build().perform();
			Log.info("Moved to element");
			flag = true;
		} catch (Exception e) {
			Log.info("Failed to move to element");
			Log.error(e);
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean isElementPresent(WebElement element) throws Exception {
		boolean flag = false;
		highlightElement(element);
		try {
			if (element.isDisplayed() || element.isEnabled())
				flag = true;
			Log.info("Element Is Present");
		} catch (NoSuchElementException e) {
			Log.info("Exception occured");
			Log.error(e);
			flag = false;
		} catch (StaleElementReferenceException e) {
			Log.info("Exception occured");
			Log.error(e);
			flag = false;
		}
		return flag;
	}

	public static boolean isElementVisible(String xpathString) throws Exception {
		boolean flag = false;
		try {
			WebElement toBeCheked = driver.findElement(By.xpath(xpathString));
			highlightElement(toBeCheked);
			if (toBeCheked.isDisplayed() || toBeCheked.isEnabled())
				flag = true;
			Log.info("Element Is Present");
		} catch (NoSuchElementException e) {
			Log.info("Exception occured");
			Log.error(e);
		} catch (StaleElementReferenceException e) {
			Log.info("Exception occured");
			Log.error(e);
		}
		return flag;
	}

	public static boolean isElementVisibleByID(String iDString) throws Exception {
		boolean flag = false;
		try {
			WebElement toBeCheked = driver.findElement(By.id(iDString));
			highlightElement(toBeCheked);
			if (toBeCheked.isDisplayed() || toBeCheked.isEnabled())
				flag = true;
			Log.info("Element Is Present");
		} catch (NoSuchElementException e) {
			Log.info("Exception occured");
			Log.error(e);
		} catch (StaleElementReferenceException e) {
			Log.info("Exception occured");
			Log.error(e);
		}
		return flag;
	}

	public static boolean areElementsVisible(String xpathString) {
		boolean flag = false;
		List<WebElement> toBeCheked = driver.findElements(By.xpath(xpathString));
		int sizeOfList = toBeCheked.size();
		try {
			for (int i = 0; i <= sizeOfList; i++) {
				if (toBeCheked.get(i).isDisplayed())
					Log.info("Elements are visible");
				flag = true;
			}
		} catch (NoSuchElementException e) {
			Log.info("Exception Occured");
			Log.error(e);
			flag = false;
		} catch (StaleElementReferenceException e) {
			Log.info("Exception Occured");
			Log.error(e);
			flag = false;
		}
		return flag;
	}

	public static boolean InputValues(WebElement FieldElement, String StringToBeEntered, String ElementName)
			throws Exception {
		try {
			highlightElement(FieldElement);
			FieldElement.clear();
			FieldElement.sendKeys(StringToBeEntered);
			tempTest.log(LogStatus.PASS, "Entered " + ElementName);
			Log.info("Entered " + ElementName);
			flag = true;
		} catch (Exception e) {
			tempTest.log(LogStatus.FAIL, "Failed to Enter " + ElementName);
			Log.info("Failed to Enter " + ElementName);
		}
		return flag;
	}

	public static boolean ClickAction(WebElement ElementToBeClicked, String ElementName) throws Exception {
		try {
			highlightElement(ElementToBeClicked);
			ElementToBeClicked.click();
			tempTest.log(LogStatus.PASS, "Clicked " + ElementName);
			Log.info("Clicked " + ElementName);
			flag = true;
		} catch (Exception e) {
			tempTest.log(LogStatus.FAIL, "Failed to Click " + ElementName);
			Log.info("Failed to Click " + ElementName);
		}
		return flag;
	}

	public static String getText(WebElement ElementForText) {

		String valueOfTheElement = null;
		try {
			highlightElement(ElementForText);
			valueOfTheElement = ElementForText.getText();
			Log.info("Text on the element found is: " + valueOfTheElement);
		} catch (Exception e) {
			Log.info("Something wrong with the element and the exception is: " + e);
		}
		return valueOfTheElement;
	}

	public static boolean TextChecker(WebElement ElementForText, String toCompare) throws Exception {
		boolean flag = false;
		String valueOfTheElement = null;
		highlightElement(ElementForText);
		valueOfTheElement = ElementForText.getText();
		Log.info("Actual Value: " + valueOfTheElement);
		Log.info("Expected Value: " + toCompare);
		try {
			if (valueOfTheElement.contentEquals(toCompare)) {
				Log.info("Actual value is equal to Exepcted value");
				flag = true;
				tempTest.log(LogStatus.PASS, "Condition Verifed successfully");
			} else {
				Log.info("Actual value is not equal to Exepcted value");
				tempTest.log(LogStatus.FAIL, "Actual value is not equal to Exepcted value");
			}
		} catch (Exception e) {
			Log.info("Actual value is not equal to Exepcted value");
			Log.error(e);
			tempTest.log(LogStatus.FAIL, e);
		}
		return flag;
	}

	public static boolean TextChecker(String actual, String expected) throws Exception {
		boolean flag = false;
		Log.info("Actual Value: " + actual);
		Log.info("Expected Value: " + expected);
		try {
			if (expected.contentEquals(actual)) {
				Log.info("Actual value is equal to Exepcted value");
				flag = true;
				tempTest.log(LogStatus.PASS, "Condition Verifed successfully");
			} else {
				Log.info("Actual value is not equal to Exepcted value");
				tempTest.log(LogStatus.FAIL, "Actual value is not equal to Exepcted value");
			}
		} catch (Exception e) {
			Log.info("Actual value is not equal to Exepcted value");
			Log.error(e);
			tempTest.log(LogStatus.FAIL, e);
		}
		return flag;
	}

	public static boolean TextCheckerPartialCheck(WebElement ElementForText, String toCompare) throws Exception {
		boolean flag = false;
		String valueOfTheElement = null;
		highlightElement(ElementForText);
		valueOfTheElement = ElementForText.getText();
		Log.info("Actual Value: " + valueOfTheElement);
		Log.info("Expected Value: " + toCompare);
		try {
			if (valueOfTheElement.contains(toCompare)) {
				Log.info("Actual value is equal to Exepcted value");
				flag = true;
				tempTest.log(LogStatus.PASS, "Condition Verifed successfully");
			} else {
				Log.info("Actual value is not equal to Exepcted value");
				tempTest.log(LogStatus.FAIL, "Actual value is not equal to Exepcted value");
			}
		} catch (Exception e) {
			Log.info("Actual value is not equal to Exepcted value");
			Log.error(e);
			tempTest.log(LogStatus.FAIL, e);
		}
		return flag;
	}

	public static boolean compareInCount(WebElement ElementForText, String xPath) throws Exception {
		boolean flag = false;
		List<WebElement> list = driver.findElements(By.xpath(xPath));
		int sizeOfList = list.size();
		int number = Integer.parseInt(ElementForText.getText());
		try {
			if (sizeOfList == number) {
				Log.info("Count is equal");
				flag = true;
			}
		} catch (Exception e) {
			Log.info("Exception occured");
			Log.error(e);
		}
		return flag;
	}

	public static int getElementCount(String xPath) {
		List<WebElement> list = driver.findElements(By.xpath(xPath));
		int sizeOfList = list.size();
		return sizeOfList;
	}

	public static boolean compareCount(int defaultCount, int afterCount) {
		boolean flag = false;
		if (defaultCount < afterCount) {
			flag = true;
		}
		return flag;
	}

	public static void highlightElement(WebElement element) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: black; border: 2px solid red;");
		} catch (Exception e) {
			tempTest.log(LogStatus.FAIL, e.getMessage() + test.addScreenCapture(TakeScreenShot()));
		}
	}

	public static boolean textCompareOnVisibility(String xPath, String toCompare) throws Exception {
		String value = null;
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xPath))));
			Log.info("Element Found");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e);
			Log.info("Exception occured");
		}
		WebDriverUtils.highlightElement(element);
		value = element.getText();
		if (value.length() > 0) {
			Log.info("Found Value: " + value);
		} else {
			Log.info("Not text value present.");
		}
		if (value.contentEquals(toCompare)) {
			flag = true;
			Log.info("Text is matched.");
		} else
			Log.info("Text is not matching.");
		return flag;
	}

	public static String TakeScreenShot() throws IOException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-h-mm-ss");
		String CurrentDate = sdf.format(date);
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			screenShotPath = System.getProperty("user.dir") + "\\" + "ScreenShotsAndReport\\" + CurrentDate + ".jpeg";
			FileUtils.copyFile(scrFile, new File(screenShotPath));
			String base = System.getProperty("user.dir") + "/ScreenShotsAndReport";
			screenShotPath = new File(base).toURI().relativize(new File(screenShotPath).toURI()).getPath();
			Log.info(Environment.ReadExcelData("Global_Validater", 5, 1));
		} catch (FileNotFoundException fnfe) {
			Log.info("In takeScreenShot " + fnfe.getMessage());
			Log.info(Environment.ReadExcelData("Global_Validater", 6, 1));
			Log.info("File not found" + fnfe);
		} catch (IOException e) {
			Log.info("In takeScreenShot " + e.getMessage());
			Log.info(Environment.ReadExcelData("Global_Validater", 6, 1));
		} catch (Exception e) {
			Log.info("In takeScreenShot " + e.getMessage());
			Log.info(Environment.ReadExcelData("Global_Validater", 6, 1));
		}
		return screenShotPath;
	}

	public static LocalDateTime getCurrentTimeUsingCalendar() {
		LocalDateTime formattedDate = null;
		try {
			formattedDate = LocalDateTime.now();
			String date = formattedDate.toString();
			Log.info(date);
		} catch (Exception e) {
			Log.info("Exception occured");
			Log.error(e);
			e.printStackTrace();
		}

		return formattedDate;
	}

	public static void selectOptionByValue(WebElement element, String valueOfOption) {
		try {
			Select option = new Select(element);
			option.selectByValue(valueOfOption);
			Log.info("Option selected: " + valueOfOption);
		} catch (Exception e) {
			Log.info("Failed to select option " + valueOfOption);
			Log.error(e);
			e.printStackTrace();
		}
	}

	public static void selectOptionFromListByValue(String xPath, String valueToSelect) {
		try {
			List<WebElement> toBeWorked = driver.findElements(By.xpath(xPath));
			int sizeOfList = toBeWorked.size();
			for (int i = 0; i <= sizeOfList; i++) {
				if (toBeWorked.get(i).getText().contains(valueToSelect)) {
					toBeWorked.get(i).click();
					Log.info(valueToSelect + "Found And Clicked");
				} else {
					Log.info(valueToSelect + "Not Found");
				}
			}
		} catch (Exception e) {
			Log.info("Exception occured");
			Log.error(e);
		}
	}

	public static void switchToWindow() {
		try {
			String parentWindow = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			for (String windowHandle : handles) {
				if (!windowHandle.equals(parentWindow)) {
					driver.switchTo().window(windowHandle);
				}
			}
			Log.info("Switch to window achieved");
		} catch (Exception e) {
			Log.info("Switch to window failed");
			e.printStackTrace();
		}
	}

	public static void switchToiFrame(int index) {
		try {
			driver.switchTo().frame(index);
			Log.info("Switch to iFrame achieved");
		} catch (Exception e) {
			Log.info("Switch to iFrame failed");
			e.printStackTrace();
		}
	}

	public static void returnToWebPageFromiFrame() {
		try {
			driver.switchTo().defaultContent();
			Log.info("Switch to web page from iFrame achieved");
		} catch (Exception e) {
			Log.info("Switch to web page from iFrame failed");
			e.printStackTrace();
		}
	}

	public static void clearTextFieldUsingJS(String forElement) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('" + forElement + "').value = ''");
			Log.info("Field cleared");
		} catch (Exception e) {
			Log.info("Field clearance failed");
			Log.error(e);
			e.printStackTrace();
		}
	}

	public static void inputValuesUsingJS(String forElement, String toEnter) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('" + forElement + "').value = '" + toEnter + "'");
			Log.info("Inserted value in the field");
		} catch (Exception e) {
			Log.info("Value insertion failed");
			Log.error(e);
			e.printStackTrace();
		}
	}

	public static void closeCurrentWindow() {
		try {
			String parentWindow = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			for (String windowHandle : handles) {
				if (!windowHandle.equals(parentWindow)) {
					driver.close();
					driver.switchTo().window(windowHandle);
				}
			}
			Log.info("Current Window Closed Successfully");
		} catch (Exception e) {
			Log.info("Closing current window failed");
			Log.error(e);
			e.printStackTrace();
		}
	}

	public static boolean alertTextChecker(String alertText) {
		boolean flag = false;
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		try {
			if (alertMessage.contentEquals(alertText)) {
				Log.info("Alert Message Actual is: " + alertMessage);
				Log.info("Alert Message Expected is: " + alertText);
				flag = true;
			}
			Log.info("Alert text matched");
		} catch (Exception e) {
			Log.info("Alert text not matched");
			Log.error(e);
			e.printStackTrace();
		}
		alert.accept();
		return flag;
	}

	public static void checkPageIsReady() throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long wt = 3000;
		try {
			for (int i = 0; i < 30; i++) {
				try {
					Thread.sleep(wt);
				} catch (InterruptedException e) {
					Log.info("Page is not Ready to work with yet.");
				}

				if (js.executeScript("return document.readyState").toString().equals("complete")) {
					Log.info("Page is ready to work with");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Page is taking too long to load.");
		}
	}

	public static void taskdelay(String filename) {
		long wt = 2000;
		for (int i = 0; i < 120; i++) {
			try {
				Thread.sleep(wt);
			} catch (InterruptedException e) {
			}
			if (WebDriverUtils.delay_downloaded_File(filename)) {
				break;
			}
		}
	}

	public static void delete_File(String fileName) {
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();
		try {
			for (int i = 0; i < dirContents.length; i++) {
				if (dirContents[i].getName().contains(fileName)) {
					dirContents[i].delete();
					break;
				}
			}
			tempTest.log(LogStatus.PASS, "\"" + fileName + "\" Old File Deleted successfully");
			Log.info(fileName + " File Deleted successfully");
		} catch (Exception e) {
			tempTest.log(LogStatus.FAIL, "\"" + fileName + "\" Old File Deletion failed");
			Log.info(fileName + " File Deletion failed");
		}
	}

	public static boolean verify_downloaded_File(String fileName) {
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();
		try {
			for (int i = 0; i < dirContents.length; i++) {
				if (dirContents[i].getName().contains(fileName)) {
					tempTest.log(LogStatus.PASS, "\"" + fileName + "\" New File Downloaded successfully");
					Log.info(fileName + " File downloaded successfully");
					return true;
				}
			}
		} catch (Exception e) {
			tempTest.log(LogStatus.FAIL, "\"" + fileName + "\" New File Download failed");
			Log.info(fileName + " File downloading failed");
			Log.error(e);
		}
		return false;
	}

	public static boolean delay_downloaded_File(String fileName) {
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();
		try {
			for (int i = 0; i < dirContents.length; i++) {
				if (dirContents[i].getName().contains(fileName)) {
					return true;
				}
			}
		} catch (Exception e) {

		}
		return false;
	}

	public static boolean verify_createdExcel_File(String fileName) {
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();
		try {
			for (int i = 0; i < dirContents.length; i++) {
				if (dirContents[i].getName().contains(fileName)) {
					tempTest.log(LogStatus.PASS, "\"" + fileName + "\" New File Downloaded successfully");
					return true;
				}
			}
			Log.info("File downloaded successfully");
		} catch (Exception e) {
			tempTest.log(LogStatus.FAIL, "\"" + fileName + "\" New File Download failed");
			Log.info("File Download Failed");
			Log.error(e);
		}
		return false;
	}

	public static String findElementLocator(String generalTextLocator, String TextToBeMatched) {
		String locator = null;
		WebElement list = driver.findElement(By.xpath(generalTextLocator));
		List<WebElement> names = list.findElements(By.tagName("tr"));
		int count = names.size();
		for (int i = 1; i < count; i++) {
			if (names.get(i).getText().contains(TextToBeMatched)) {
				locator = "//*[@id='body:dealerUserSrhForm:userSearchResults:tbody_element']/tr[" + (i + 1)
						+ "]/td[7]/a[1]";
				break;
			}
		}
		return locator;
	}

	public static String dynamicXpathLocator(String blockLocator, String xpathFirstPart, String xpathSecondPart) {
		String locator = null;
		List<WebElement> blockElement = driver.findElements(By.xpath(blockLocator));
		int sizeOfBlockElementList = blockElement.size();
		forlop: for (int i = 1; i <= sizeOfBlockElementList * 2; i = i + 2) {
			try {
				if (driver.findElement(By.xpath(xpathFirstPart + i + xpathSecondPart)).isDisplayed()) {
					locator = xpathFirstPart + i + xpathSecondPart;
				}
			} catch (Exception e) {
				continue forlop;
			}
		}
		return locator;
	}

	public static void clickActionWithElementCheck(WebElement toBeChecked, WebElement toBeClicked) {
		try {
			if (toBeChecked.isDisplayed()) {
				toBeChecked.click();
				Log.info("Clicked on the element");
			} else {
				toBeClicked.click();
				Log.info("Clicked on the element");
			}
		} catch (Exception e) {
			Log.info("Exception occured");
			Log.error(e);
		}

	}

	public static boolean checkIfFieldIsEmpty(WebElement toBeChecked) {
		if (toBeChecked.getAttribute("value").isEmpty()) {
			Log.info("Field is empty");
			return true;
		} else {
			Log.info("Field is not empty");
			return false;
		}

	}

	public static boolean isElementDisabled(WebElement toBeChecked) {
		boolean flag = false;
		try {

			if (toBeChecked.isEnabled()) {
				flag = true;
				Log.info("Element is enabled to work with.");
			} else
				Log.info("Element is not enabled to work with.");
		} catch (Exception e) {
			Log.info("Exception occured");
			Log.error(e);
		}
		return flag;
	}

	public static String getTextFieldValue(WebElement toBeChecked) {
		String valueInsideTextField = null;
		try {
			valueInsideTextField = toBeChecked.getAttribute("value");
			Log.info("Vlaue Found: " + valueInsideTextField);
		} catch (Exception e) {
			Log.error(e);
			Log.info("Issues with the text or element of the text");
			e.printStackTrace();
		}
		return valueInsideTextField;
	}

	public static void getImageUrl() {
		List<WebElement> allImages = driver.findElements(By.tagName("img"));
		try {
			for (WebElement imageFromList : allImages) {
				String ImageUrl = imageFromList.getAttribute("src");
				Log.info(ImageUrl);
			}
			Log.info("List Of Images Found");
		} catch (Exception e) {
			Log.info("Not able to find images");
			Log.error(e);
			e.printStackTrace();
		}
	}

	public static List<String> getVINlist(String xpathForTheVINList) {
		List<WebElement> allVINs = driver.findElements(By.xpath(xpathForTheVINList));
		List<String> VINs = new ArrayList<String>();
		try {
			for (WebElement vinFromList : allVINs) {
				String VINList = vinFromList.getText();
				VINs.add(VINList);
			}
			Log.info("VIN list creted");
		} catch (Exception e) {
			Log.error(e);
			Log.info("Failed to create VIN list");
			e.printStackTrace();
		}
		return VINs;
	}

	public static WebElement dynamicElement(String xpath) {
		WebElement elementToBeFound = null;
		try {
			List<WebElement> buttons = driver.findElements(By.xpath(xpath));
			elementToBeFound = buttons.get(0);
			Log.info("Dynamic Element Found");
		} catch (Exception e) {
			Log.info("Dynamic Element not Found");
			Log.error(e);
			e.printStackTrace();
		}
		return elementToBeFound;
	}

	public static boolean VerifycheckboxS(String webElement) throws Exception {

		boolean flag = false;
		WebElement checkbox = null;
		try {
			checkbox = driver.findElement(By.id(webElement));
			Log.info("Checkbox Element Found");
		} catch (Exception e) {
			Log.info("Checkbox Element not Found");
			e.printStackTrace();
		}
		highlightElement(checkbox);
		if (checkbox.isSelected()) {
			Log.info("The checkbox is selected");
			flag = true;
		} else {
			Log.info("The checkbox is not selected");
		}
		return flag;
	}

	public static String splitStringByNeglecting(String toSplit, String onTheBasisOf, String neglect) {
		String value = "";
		String S = toSplit;
		String[] words = S.split(onTheBasisOf);

		for (String w : words) {
			if (!w.contentEquals(neglect))
				value = w;
		}
		return value;
	}

	public enum BrowserEnum {
		FIREFOX, CHROME, IE
	}

}
