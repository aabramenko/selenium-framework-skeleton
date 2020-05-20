package project.logger;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import project.cases.AbstractTest;
import project.core.TestRunParams;

public class TestListener implements ITestListener {

    private WebDriver driver;

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LogUtils.printTestInfoHeader(iTestResult);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        driver = ((AbstractTest) iTestResult.getInstance()).getDriver();
        if (driver != null) {
            if (TestRunParams.isHtmlOnSuccess()) {
                LogUtils.logPageSource(iTestResult, driver);
            }
            if (TestRunParams.isScreenOnSuccess()) {
                LogUtils.logScreenshot(iTestResult, driver);
            }
        }
        LogUtils.printTestInfoBottom(iTestResult);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        driver = ((AbstractTest) iTestResult.getInstance()).getDriver();
        if (driver != null) {
            LogUtils.logCurrentUrl(driver);
            if (TestRunParams.isHtmlOnFailure()) {
                LogUtils.logPageSource(iTestResult, driver);
            }
            if (TestRunParams.isScreenOnFailure()) {
                LogUtils.logScreenshot(iTestResult, driver);
            }
        }
        LogUtils.printTestInfoBottom(iTestResult);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        //
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        //
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        //
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        //
    }
}
