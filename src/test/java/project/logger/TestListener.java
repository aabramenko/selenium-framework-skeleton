package project.logger;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import project.cases.AbstractTest;
import project.core.ConfigManager;

public class TestListener implements ITestListener {

    private WebDriver driver;

    @Override
    public void onTestStart(ITestResult iTestResult) {
        Logger.printTestInfoHeader(iTestResult);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

        driver = ((AbstractTest) iTestResult.getInstance()).getDriver();

        if (driver != null) {

            if (ConfigManager.isHtmlOnSuccess()) {
                Logger.logPageSource(iTestResult, driver);
            }

            if (ConfigManager.isScreenOnSuccess()) {
                Logger.logScreenshot(iTestResult, driver);
            }

        }

        Logger.printTestInfoBottom(iTestResult);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

        driver = ((AbstractTest) iTestResult.getInstance()).getDriver();

        if (driver != null) {

            if (ConfigManager.isHtmlOnFailure()) {
                Logger.logPageSource(iTestResult, driver);
            }

            if (ConfigManager.isScreenOnFailure()) {
                Logger.logScreenshot(iTestResult, driver);
            }

            Logger.logCurrentUrl(driver);

        }

        Logger.printTestInfoBottom(iTestResult);
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
