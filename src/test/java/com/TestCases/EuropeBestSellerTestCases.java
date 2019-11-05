package com.TestCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.Actions.EuropeBestSellerActions;
import com.Utilities.*;

@Listeners(TestStatistics.class)
public class EuropeBestSellerTestCases extends TestBase {

	@Test(retryAnalyzer = TestStatistics.class)
	public static void europeBestSellerNavigationTestCase() throws Exception {
		try {
			Log.startTestCase("europeBestSellerNavigation Test");
			tempTest = report.startTest("europeBestSellerNavigation Test");
			boolean status = EuropeBestSellerActions.europeBestSellerNavigation();
			Assert.assertTrue(status);
			Log.endTestCase("europeBestSellerNavigation Test");
		} catch (Exception e) {
			WebDriverUtils.TakeScreenShot();
			Log.error(e);
			Log.info(Environment.ReadExcelData("Global_Validater", 2, 1));
			Log.endTestCase("europeBestSellerNavigation Test");
			Assert.fail(e.getMessage());
		}
	}
}
