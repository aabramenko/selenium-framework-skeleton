package project.cases;

import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;

import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import project.core.CredsManager;
import project.core.RunTimeDataStorage;
import project.core.ConfigManager;
import project.core.Utils;
import project.driverFactory.NewDriverFactory;
import project.models.Driver;

import static project.core.TestRunParams.*;
import static project.core.Utils.*;

public abstract class AbstractTest {

    private Logger log = Logger.getLogger("");

    SoftAssertions SoftAssertj;

    SoftAssert SoftAssert;

    private Driver classDriver;

    public WebDriver getDriver() {
        return classDriver.getDriver();
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeRun() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");

        ConfigManager.uploadRunConfigValues();
        ConfigManager.uploadEnvConfigValues();
        CredsManager.uploadCredsValues();

        deleteTempFiles();
        createFolder(getPathToAllArtifactsFolders());
        createFolder(getPathToCurrentArtifactsFolder());

        RunTimeDataStorage.RunningTestStatistic.resetTestOrderNumber();

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

    private void openBrowser() {
        classDriver = NewDriverFactory.getNewDriverInstance(ConfigManager.getBrowserName());
        Utils.printDashedLine();
        log.info("Browser: " + classDriver.getBrowserName() + " | thread: " + Utils.getCurrentThreadId());
        log.info("Driver: " + classDriver.getDriver());
        Utils.printDashedLine();
        classDriver.getDriver().manage().window().setSize(new Dimension(1366, 768));
    }

    private void closeBrowser() {
        if (classDriver.getDriver() != null) {
            log.info("closing browser");
            try {
                classDriver.getDriver().quit();
            }
            catch (WebDriverException e) {
                //
            }
        }
    }
}