package com.Actions;

import com.PageObjects.BookingPage;
import com.Utilities.Environment;
import com.Utilities.Log;
import com.Utilities.TestBase;
import com.Utilities.WebDriverUtils;
import com.relevantcodes.extentreports.LogStatus;

public class EuropeBestSellerActions extends TestBase {

	static boolean status = false;

	public static boolean europeBestSellerNavigation() throws Exception {
		if (!HomePageActions.homePageNavigation()) {
			status = false;
		}
		if (!WebDriverUtils.ClickAction(BookingPage.europBestSellerslink(), "Europe Best Seller Link")) {
			status = false;
		}
		WebDriverUtils.checkPageIsReady();
		if (WebDriverUtils.TextChecker(BookingPage.europeanBestsellersHeader(), "Europe - European Bestsellers")
				&& WebDriverUtils.verifyPageTitle(
						"Europe - European Bestsellers Vacations Packages | Custom Vacation Packages to Europe - European Bestsellers | Multi-City Trips to Europe - European Bestsellers | Tripmasters")) {
			Log.info(Environment.ReadExcelData("Global_Validater", 1, 1));
			status = true;
			test.appendChild(tempTest);
			tempTest.log(LogStatus.PASS, "Assert Passed");
		} else {
			Log.info(Environment.ReadExcelData("Global_Validater", 2, 1));
			status = false;
			test.appendChild(tempTest);
			tempTest.log(LogStatus.FAIL, "Assert failed");
		}
		return status;
	}

}
