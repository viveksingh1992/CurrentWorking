package com.PageObjects;

import org.openqa.selenium.WebElement;

import com.Utilities.WebDriverUtils;

public class BookingPage {

	public static WebElement leavingTextbox() throws Exception {
		return WebDriverUtils.findElement("//input[@id='qLeaveNA']", "Xpath");
	}

	public static WebElement goingToTextbox() throws Exception {
		return WebDriverUtils.findElement("//input[contains(@id,'qNACity')]", "Xpath");
	}

	public static WebElement goingToTextboxSecond() throws Exception {
		return WebDriverUtils.findElement("//*[@id='qNACity2']", "Xpath");
	}

	public static WebElement addCityBtn() throws Exception {
		return WebDriverUtils.findElement("//span[contains(.,'Add City')]", "Xpath");
	}

	public static WebElement noMoreCitiesBtn() throws Exception {
		return WebDriverUtils.findElement("//span[contains(.,'No more cities. Continue')]", "Xpath");
	}

	public static WebElement continueBtn() throws Exception {
		return WebDriverUtils.findElement("//div[@class='dvContinueButton']//span[contains(.,'Continue')]", "Xpath");
	}

	public static WebElement paymentContinueBtn() throws Exception {
		return WebDriverUtils.findElement("//a[contains(@id,'PriceValidation')]", "Xpath");
	}

	public static WebElement validatingPricesPopupText() throws Exception {
		return WebDriverUtils.findElement("//div[@id='divLoading']//div[contains(.,'Validating prices...')]", "Xpath");
	}

	public static WebElement tripInclusionDetailsHeader() throws Exception {
		return WebDriverUtils.findElement("//span[contains(.,'TRIP INCLUSION DETAILS')]", "Xpath");
	}

	public static WebElement EditContinueBtn() throws Exception {
		return WebDriverUtils.findElement("//div[a[contains(.,'Edit Itinerary')]]//a[contains(@id,'Continue')]",
				"Xpath");
	}

	public static WebElement FirstTravelerFirstNameTxtbox() throws Exception {
		return WebDriverUtils.findElement(
				"//div[div[span[contains(.,'Traveler 1:')]]]//input[contains(@placeholder,'First and Middle Name')]",
				"Xpath");
	}

	public static WebElement FirstTravelerLastNameTxtbox() throws Exception {
		return WebDriverUtils.findElement(
				"//div[div[span[contains(.,'Traveler 1:')]]]//input[contains(@placeholder,'Last Name')]", "Xpath");
	}

	public static WebElement SecondTravelerFirstNameTxtbox() throws Exception {
		return WebDriverUtils.findElement(
				"//div[div[span[contains(.,'Traveler 2:')]]]//input[contains(@placeholder,'First and Middle Name')]",
				"Xpath");
	}

	public static WebElement SecondTravelerLastNameTxtbox() throws Exception {
		return WebDriverUtils.findElement(
				"//div[div[span[contains(.,'Traveler 2:')]]]//input[contains(@placeholder,'Last Name')]", "Xpath");
	}

	public static WebElement FirstTravelerGenderDropdown() throws Exception {
		return WebDriverUtils.findElement(
				"//div[div[div[span[contains(.,'Traveler 1:')]]]]//div[contains(@class,'Gender')]//select", "Xpath");
	}

	public static WebElement FirstTravelerDOB() throws Exception {
		return WebDriverUtils.findElement(
				"//div[div[div[span[contains(.,'Traveler 1:')]]]]//input[contains(@id,'txtPAX_DOB')]", "Xpath");
	}

	public static WebElement SecondTravelerGenderDropdown() throws Exception {
		return WebDriverUtils.findElement(
				"//div[div[div[span[contains(.,'Traveler 2:')]]]]//div[contains(@class,'Gender')]//select", "Xpath");
	}

	public static WebElement SecondTravelerDOB() throws Exception {
		return WebDriverUtils.findElement(
				"//div[div[div[span[contains(.,'Traveler 2:')]]]]//input[contains(@id,'txtPAX_DOB')]", "Xpath");
	}

	public static WebElement FormContinueBtn() throws Exception {
		return WebDriverUtils.findElement("//div[a[img[@name='continue_btn']]]//a", "Xpath");
	}

	public static WebElement USAndCanadaCreditCardsTxt() throws Exception {
		return WebDriverUtils.findElement("//strong[contains(.,'We only accept US and Canada credit cards')]", "Xpath");
	}

	public static WebElement europBestSellerslnk() throws Exception {
		return WebDriverUtils.findElement(
				"//div[a[div[contains(@class,'dvEachHighImg')]/following-sibling::h3[contains(.,'European BestSellers')]]]//a",
				"Xpath");
	}

	public static WebElement europBestSellerslink() throws Exception {
		return WebDriverUtils.findElement(
				"//div[a[div[contains(@class,'dvEachHighImg')]/following-sibling::h3[contains(.,'European BestSellers')]]]//a",
				"Xpath");
	}

	public static WebElement europeanBestsellersHeader() throws Exception {
		return WebDriverUtils.findElement(
				"//div[contains(@class,'pageTitle')]/div[contains(.,'Europe - European Bestsellers')]", "Xpath");
	}
}
