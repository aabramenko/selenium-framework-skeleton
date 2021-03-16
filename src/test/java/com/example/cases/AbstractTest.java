package com.example.cases;

import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import selenium.core.*;
import selenium.driverFactory.DriverFactory;

import static selenium.core.TestRunParams.getPathToAllArtifactsFolders;
import static selenium.core.TestRunParams.getPathToCurrentArtifactsFolder;

public abstract class AbstractTest {

    private final Logger log = Logger.getLogger("");
    protected SoftAssertions softAssertj;
    protected SoftAssert softAssert;
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite(alwaysRun = true)
    protected void beforeAll() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        RunTimeDataStorage.CasesStatistics.resetCaseOrderNumber();
        RunTimeDataStorage.PagesLoadStatistics.initiatePageUploadingTimeArrayList();
        TestRunParams.uploadDefaultRunParameters();
        TestRunParams.uploadDbConfigValues();
        CredentialsManager.uploadCredsValues();
        Utils.deleteTempFiles();
        Utils.createFolder(getPathToAllArtifactsFolders());
        Utils.createFolder(getPathToCurrentArtifactsFolder());

        log.info("=== OS: " + Utils.getCurrentOS());
        log.info("=== Browser: " + TestRunParams.getDefinedBrowserName());
        log.info("=== Grid?: " + TestRunParams.isGrid());
        log.info("=== Hub Host: " + TestRunParams.getHubHost());
        log.info("=== Headless?: " + TestRunParams.isHeadless());
        log.info("=== Video?: " + TestRunParams.isVideo());
        log.info("=== Version: " + Constants.VERSION);
    }

    @AfterSuite(alwaysRun = true)
    protected void afterAll() {
        Utils.printPageUploadTimeStatistics();
        Utils.createFilePageUploadTimeStatistics();
    }

    @BeforeClass(alwaysRun = true)
    protected void beforeClass() {
        openBrowser();
    }

    @AfterClass(alwaysRun = true)
    protected void afterClass() {
        closeBrowser(getDriver());
    }

    private void closeBrowser(WebDriver driver) {
        log.info("closing browser");
        driver.quit();
    }

    private void openBrowser() {
        driver = DriverFactory.initiateDriver(TestRunParams.getDefinedBrowserName());
        Utils.printDashedLine();
        log.info("Browser: " + TestRunParams.getDefinedBrowserName() + " | thread: " + Thread.currentThread().getId());
        log.info("Driver: " + driver);
        Utils.printDashedLine();
        driver.manage().window().setSize(new Dimension(1366, 768));
    }

    @BeforeMethod(alwaysRun = true)
    protected void beforeTestCase() {
        softAssertj = new SoftAssertions();
        softAssert = new SoftAssert();
        RunTimeDataStorage.CasesStatistics.incrementCaseOrderNumber();
    }

    @AfterMethod(alwaysRun = true)
    protected void afterTestCase(ITestResult result) {
        //
    }


}