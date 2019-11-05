package com.Utilities;

import java.util.ArrayList;
import java.util.List;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class TestStatistics extends TestBase implements ITestListener, IRetryAnalyzer {

	List<ITestNGMethod> passedtests = new ArrayList<ITestNGMethod>();
	List<ITestNGMethod> failedtests = new ArrayList<ITestNGMethod>();
	List<ITestNGMethod> skippedtests = new ArrayList<ITestNGMethod>();
	private int retryCount = 0;
	private static final int maxRetryCount = 0;

	public void onTestStart(ITestResult result) {

	}

	public void onTestSuccess(ITestResult result) {
		passedtests.add(result.getMethod());
		teardownTest(result);
	}

	public void onTestFailure(ITestResult result) {
		failedtests.add(result.getMethod());
		teardownTest(result);
	}

	public void onTestSkipped(ITestResult result) {
		skippedtests.add(result.getMethod());
		teardownTest(result);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onStart(ITestContext context) {
		context.getStartDate();
		Log.info("========TEST START========");
	}

	public void onFinish(ITestContext context) {
		context.getEndDate();
		Log.info("========TEST FINISH========");
	}

	private void teardownTest(ITestResult result) {
		String status = result.isSuccess() ? "SUCCESS" : "FAILURE";
		Log.info("======" + status + "======");
		Log.info("Test: " + result.getInstanceName() + "." + result.getName());
		if (status.equalsIgnoreCase("SUCCESS")) {
			Log.info("Passed Test Size Is: " + passedtests.size());
		} else if (status.equalsIgnoreCase("FAILURE")) {
			Log.info("Passed Test Size Is: " + failedtests.size());
		} else {
			Log.info("Passed Test Size Is: " + skippedtests.size());
		}
	}

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			retryCount++;
			return true;
		}
		return false;
	}

}