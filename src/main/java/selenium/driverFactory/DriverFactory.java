package selenium.driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import selenium.core.Dictionary;
import selenium.core.TestRunParams;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class DriverFactory {

    private static Logger log = Logger.getLogger("");
    static DriverOptionsManager optionsManager = new DriverOptionsManager();

    public static WebDriver initiateDriver(String browserName) {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.SEVERE);

        ThreadLocal<WebDriver> tlDriver = null;
        RemoteWebDriver remoteDriver = null;

        boolean useRemoteDriver = TestRunParams.isGrid();

        if (browserName.equalsIgnoreCase(Dictionary.FIREFOX)) {
            if (useRemoteDriver) {
                remoteDriver = getFirefoxRemoteDriver();
            }
            else {
                tlDriver = getFirefoxTLDriver();
            }
        } else if (browserName.equalsIgnoreCase(Dictionary.CHROME)) {
            if (useRemoteDriver) {
                remoteDriver = getChromeRemoteDriver();
            }
            else {
                tlDriver = getChromeTLDriver();
            }
        } else {
            log.error("'" + browserName + "' IS NOT A PROPER BROWSER NAME!");
        }

        if (useRemoteDriver) {
            return remoteDriver;
        } else {
            return tlDriver.get();
        }
    }

    private static ThreadLocal<WebDriver> getFirefoxTLDriver() {
        log.info("initiate " + Dictionary.FIREFOX + " browser");
        WebDriverManager.firefoxdriver().setup();
        return ThreadLocal.withInitial(() -> new FirefoxDriver(optionsManager.getFirefoxOptions()));
    }

    private static ThreadLocal<WebDriver> getChromeTLDriver() {
        log.info("initiate " + Dictionary.CHROME + " browser");
        WebDriverManager.chromedriver().setup();
        return ThreadLocal.withInitial(() -> new ChromeDriver(optionsManager.getChromeOptions()));
    }

    private static RemoteWebDriver getFirefoxRemoteDriver() {
        log.info("initiate " + Dictionary.FIREFOX + " browser");
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL("http://" + TestRunParams.getHubHost() +
                    ":4444/wd/hub"),
                    optionsManager.getFirefoxOptions());
            driver.setFileDetector(new LocalFileDetector());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    private static RemoteWebDriver getChromeRemoteDriver() {
        log.info("initiate " + Dictionary.CHROME + " browser");
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL("http://" + TestRunParams.getHubHost() +
                    ":4444/wd/hub"),
                    optionsManager.getChromeOptions());
            driver.setFileDetector(new LocalFileDetector());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

}