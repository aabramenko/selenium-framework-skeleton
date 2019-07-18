package project.driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import project.core.ConfigManager;
import project.core.Dictionary;
import project.core.Utils;
import project.models.Driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class DriverFactory {

    private static Logger log = Logger.getLogger("");

    static DriverOptionsManager optionsManager = new DriverOptionsManager();

    public static Driver createNewDriverInstance(String browserParameter) {

        RemoteWebDriver driver = null;
        String browserName = null;

        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.SEVERE);

        if (ConfigManager.isGrid()) {

            if (browserParameter.equalsIgnoreCase(Dictionary.FIREFOX)) {
                driver = initiateFirefoxGridDriver();
                browserName = Dictionary.FIREFOX;
            } else if (browserParameter.equalsIgnoreCase(Dictionary.CHROME)) {
                driver = initiateChromeGridDriver();
                browserName = Dictionary.CHROME;
            } else if (browserParameter.equalsIgnoreCase(Dictionary.MIX)) {
                if (Utils.getCurrentThreadId() % 2 == 0) {
                    driver = initiateChromeGridDriver();
                    browserName = Dictionary.CHROME;
                } else {
                    driver = initiateFirefoxGridDriver();
                    browserName = Dictionary.FIREFOX;
                }
            }
        }

        else {

            if (browserParameter.equalsIgnoreCase(Dictionary.FIREFOX)) {
                driver = initiateFirefoxDriver();
                browserName = Dictionary.FIREFOX;
            } else if (browserParameter.equalsIgnoreCase(Dictionary.CHROME)) {
                driver = initiateChromeDriver();
                browserName = Dictionary.CHROME;
            } else if (browserParameter.equalsIgnoreCase(Dictionary.MIX)) {
                if (Utils.getCurrentThreadId() % 2 == 0) {
                    driver = initiateChromeDriver();
                    browserName = Dictionary.CHROME;
                } else {
                    driver = initiateFirefoxDriver();
                    browserName = Dictionary.FIREFOX;
                }
            }
        }

        return new Driver(driver, browserName);
    }

    private static RemoteWebDriver initiateFirefoxDriver() {
        log.info("start " + Dictionary.FIREFOX + " browser");
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(optionsManager.getFirefoxOptions());
    }

    private static RemoteWebDriver initiateChromeDriver() {
        log.info("start " + Dictionary.CHROME + " browser");
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(optionsManager.getChromeOptions());
    }

    private static RemoteWebDriver initiateFirefoxGridDriver() {
        log.info("start " + Dictionary.FIREFOX + " browser");
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(ConfigManager.getGridHubUrl()), optionsManager.getFirefoxOptions());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (ConfigManager.isSelenoid()) {
            driver.setFileDetector(new LocalFileDetector());
        }
        return driver;
    }

    private static RemoteWebDriver initiateChromeGridDriver() {
        log.info("start " + Dictionary.CHROME + " browser");
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(ConfigManager.getGridHubUrl()), optionsManager.getChromeOptions());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (ConfigManager.isSelenoid()) {
            driver.setFileDetector(new LocalFileDetector());
        }
        return driver;
    }

}