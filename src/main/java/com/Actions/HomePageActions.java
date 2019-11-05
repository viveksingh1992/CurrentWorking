package com.Actions;

import com.Utilities.Environment;
import com.Utilities.Log;
import com.Utilities.TestBase;
import com.Utilities.WebDriverUtils;
import com.relevantcodes.extentreports.LogStatus;

public class HomePageActions extends TestBase {

	static boolean status = false;

	public static boolean homePageNavigation() throws Exception {
		if (!WebDriverUtils.goToURL(Environment.ReadExcelData("URL", 1, 0))) {
			status = false;
		}
		WebDriverUtils.checkPageIsReady();
		if (WebDriverUtils.TextChecker(WebDriverUtils.currentURL(), Environment.ReadExcelData("URL", 1, 0))) {
			Log.info(Environment.ReadExcelData("Global_Validater", 1, 1));
			status = true;
			test.appendChild(tempTest);
			tempTest.log(LogStatus.PASS, "Assert Passed");
		} else {
			Log.info(Environment.ReadExcelData("Global_Validater", 2, 1));
			test.appendChild(tempTest);
			tempTest.log(LogStatus.FAIL, "Assert failed");
		}
		return status;
	}
}
