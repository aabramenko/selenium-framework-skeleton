package project.cases;

import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import project.core.ConfigManager;
import project.core.CredsManager;
import project.core.RunTimeDataStorage;
import project.core.Utils;
import project.driverFactory.DriverFactory;
import project.models.Driver;

import static project.core.TestRunParams.getPathToAllArtifactsFolders;
import static project.core.TestRunParams.getPathToCurrentArtifactsFolder;
import static project.core.Utils.createFolder;
import static project.core.Utils.deleteTempFiles;

public abstract class AbstractTest {

    private Logger log = Logger.getLogger("");

    private Driver classDriver;

    SoftAssertions softAssertj;

    SoftAssert softAssert;

    public WebDriver getDriver() {
        if (classDriver != null) {
            return classDriver.getDriver();
        }
        else {
            return null;
        }
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeRun() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");

        RunTimeDataStorage.Statistic.resetCaseOrderNumber();

        ConfigManager.uploadRunConfigValues();
        ConfigManager.uploadEnvConfigValues();
        ConfigManager.uploadDbConfigValues();
        CredsManager.uploadCredsValues();

        deleteTempFiles();
        createFolder(getPathToAllArtifactsFolders());
        createFolder(getPathToCurrentArtifactsFolder());

        log.info("=== OS: " + Utils.getCurrentOS());
        log.info("=== Grid?: " + ConfigManager.isGrid());
        log.info("=== Selenoid?: " + ConfigManager.isSelenoid());
        log.info("=== Headless?: " + ConfigManager.isHeadless());
    }

    @AfterSuite(alwaysRun = true)
    public void afterRun() {
        //
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestCase() {
        softAssertj = new SoftAssertions();
        softAssert = new SoftAssert();
        RunTimeDataStorage.Statistic.incrementCaseOrderNumber();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestCase(ITestResult result) {
        //
    }

    void openBrowser() {
        classDriver = DriverFactory.getNewDriverInstance(ConfigManager.getBrowserName());
        Utils.printDashedLine();
        log.info("Browser: " + classDriver.getBrowserName() + " | thread: " + Utils.getCurrentThreadId());
        log.info("Driver: " + classDriver.getDriver());
        Utils.printDashedLine();
        classDriver.getDriver().manage().window().setSize(new Dimension(1366, 768));
    }

    void closeBrowser() {
        if (classDriver.getDriver() != null) {
            log.info("closing browser");
            Utils.printDashedLine();
            log.info("Browser: " + classDriver.getBrowserName() + " | thread: " + Utils.getCurrentThreadId());
            log.info("Driver: " + classDriver.getDriver());
            Utils.printDashedLine();
            try {
                classDriver.getDriver().quit();
                Utils.sleepMsec(500);
            }
            catch (WebDriverException e) {
                //
            }
        }
    }
}