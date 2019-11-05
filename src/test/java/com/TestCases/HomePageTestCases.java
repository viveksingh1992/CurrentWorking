package com.TestCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.Actions.HomePageActions;
import com.Utilities.Environment;
import com.Utilities.Log;
import com.Utilities.TestBase;
import com.Utilities.TestStatistics;
import com.Utilities.WebDriverUtils;

@Listeners(TestStatistics.class)
public class HomePageTestCases extends TestBase {

	@Test(retryAnalyzer = TestStatistics.class)
	public static void homePageNavigationTest() throws Exception {
		try {
			Log.startTestCase("HomePageNavigation Test");
			tempTest = report.startTest("HomePageNavigation Test");
			boolean status = HomePageActions.homePageNavigation();
			Assert.assertTrue(status);
			Log.endTestCase("HomePageNavigation Test");
		} catch (Exception e) {
			WebDriverUtils.TakeScreenShot();
			Log.error(e);
			Log.info(Environment.ReadExcelData("Global_Validater", 2, 1));
			Log.endTestCase("HomePageNavigation Test");
			Assert.fail(e.getMessage());
		}
	}

}
