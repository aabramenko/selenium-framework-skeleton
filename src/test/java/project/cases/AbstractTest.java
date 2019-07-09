package project.cases;

import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;

import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import project.core.RunTimeDataStorage;
import project.core.ConfigManager;
import project.driverFactory.DriverFactory;
import project.core.Utils;

import static project.core.TestRunParams.*;
import static project.core.Utils.*;

public abstract class AbstractTest {

    private Logger log = Logger.getLogger("");

    SoftAssertions SoftAssertj;

    SoftAssert SoftAssert;

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeRun() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");

        ConfigManager.uploadRunConfigValues();
        ConfigManager.uploadEnvConfigValues(ConfigManager.getRunConfig());
        ConfigManager.uploadCredsValues(ConfigManager.getRunConfig());

        deleteTempFiles();
        createFolder(getPathToAllArtifactsFolders());
        createFolder(getPathToCurrentArtifactsFolder());

        RunTimeDataStorage.RunningTestStatistic.resetTestOrderNumber();

        log.info("=== Start url: " + ConfigManager.getStartUrl());
        log.info("=== OS: " + Utils.getCurrentOS());
        log.info("=== Grid?: " + ConfigManager.isGrid());
        log.info("=== Selenoid?: " + ConfigManager.isSelenoid());
        log.info("=== Headless?: " + ConfigManager.isHeadless());
    }

    @AfterSuite(alwaysRun = true)
    public void afterRun() {
        //
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        openBrowser();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowser();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestCase() {
        SoftAssertj = new SoftAssertions();
        SoftAssert = new SoftAssert();
        RunTimeDataStorage.RunningTestStatistic.incrementTestOrderNumber();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestCase(ITestResult result) {
        //
    }

    public void openBrowser() {
        DriverFactory.initiateDriver(ConfigManager.getBrowserName());
        driver = DriverFactory.getDriver();

        sleepMsec(2000);
        if (("" + getDriver()).toLowerCase().contains("null")) {
            sleepMsec(10000);
        }

        Utils.printDashedLine();
        log.info("Browser: " + DriverFactory.getCurrentBrowserName() + " | thread: " + DriverFactory.getCurrentThreadId());
        log.info("Driver: " + driver);
        Utils.printDashedLine();
        driver.manage().window().setSize(new Dimension(1366, 768));
    }

    public void closeBrowser() {
        if (getDriver() != null) {
            log.info("closing browser");
            try {
                DriverFactory.closeBrowser();
            }
            catch (WebDriverException e) {
                DriverFactory.closeBrowser();
            }
        }
    }

    public void log(String text) {
        log.info(text);
    }
}