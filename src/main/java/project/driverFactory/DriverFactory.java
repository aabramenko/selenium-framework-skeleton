package project.driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import project.core.ConfigManager;
import project.core.Dictionary;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import static project.core.ConfigManager.isSelenoid;

public class DriverFactory {

    private static Logger log = Logger.getLogger("");

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static DriverOptionsManager optionsManager = new DriverOptionsManager();
    private static RemoteWebDriver driver;
    private static String currentBrowserName;

    public static String getCurrentBrowserName() {
        return currentBrowserName;
    }

    public static void initiateDriver(String browserName) {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.SEVERE);

        if (ConfigManager.isGrid()) {

            if (browserName.equalsIgnoreCase(Dictionary.FIREFOX)) {
                initiateFirefoxRemoteDriver();
                currentBrowserName = Dictionary.FIREFOX;
            } else if (browserName.equalsIgnoreCase(Dictionary.CHROME)) {
                initiateChromeRemoteDriver();
                currentBrowserName = Dictionary.CHROME;
            } else if (browserName.equalsIgnoreCase("mix")) {
                if (DriverFactory.getCurrentThreadId()%2 == 0) {
                    initiateChromeRemoteDriver();
                    currentBrowserName = Dictionary.CHROME;
                } else {
                    initiateFirefoxRemoteDriver();
                    currentBrowserName = Dictionary.FIREFOX;
                }
            }

        } else {

            if (browserName.equalsIgnoreCase(Dictionary.FIREFOX)) {
                initiateFirefoxTLDriver();
                currentBrowserName = Dictionary.FIREFOX;
            } else if (browserName.equalsIgnoreCase(Dictionary.CHROME)) {
                initiateChromeTLDriver();
                currentBrowserName = Dictionary.CHROME;
            } else if (browserName.equalsIgnoreCase("mix")) {
                if (DriverFactory.getCurrentThreadId()%2 == 0) {
                    initiateChromeTLDriver();
                    currentBrowserName = Dictionary.CHROME;
                } else {
                    initiateFirefoxTLDriver();
                    currentBrowserName = Dictionary.FIREFOX;
                }
            }

        }
    }

    private static void initiateFirefoxTLDriver() {
        log.info("start " + Dictionary.FIREFOX + " browser");
        WebDriverManager.firefoxdriver().setup();
        tlDriver = ThreadLocal.withInitial(() -> new FirefoxDriver(optionsManager.getFirefoxOptions()));
    }

    private static void initiateChromeTLDriver() {
        log.info("start " + Dictionary.CHROME + " browser");
        WebDriverManager.chromedriver().setup();
        tlDriver = ThreadLocal.withInitial(() -> new ChromeDriver(optionsManager.getChromeOptions()));
    }

    private static void initiateFirefoxRemoteDriver() {
        log.info("start " + Dictionary.FIREFOX + " browser");
        try {
            driver = new RemoteWebDriver(new URL(ConfigManager.getGridHubUrl()), optionsManager.getFirefoxOptions());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (isSelenoid()) {
            driver.setFileDetector(new LocalFileDetector());
        }
    }

    private static void initiateChromeRemoteDriver() {
        log.info("start " + Dictionary.CHROME + " browser");
        try {
            driver = new RemoteWebDriver(new URL(ConfigManager.getGridHubUrl()), optionsManager.getChromeOptions());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (isSelenoid()) {
            driver.setFileDetector(new LocalFileDetector());
        }
    }

    public static WebDriver getDriver() {
        if (ConfigManager.isGrid()) {
            return driver;
        } else {
            return tlDriver.get();
        }
    }

    public static long getCurrentThreadId() {
        return Thread.currentThread().getId();
    }

    public static void closeBrowser() {
        getDriver().close();
    }

    public static String getRemoteSessionId() {
        return driver.getSessionId().toString();
    }

    public static void clearCookies() {
        log.info("clear cookies");
        getDriver().manage().deleteAllCookies();
    }

    public static void clearLocalStorage() {
        log.info("clear local storage");
        ((JavascriptExecutor) getDriver()).executeScript("localStorage.clear();");
    }

    public static void clearSessionStorage() {
        log.info("clear session storage");
        ((JavascriptExecutor) getDriver()).executeScript("sessionStorage.clear();");
    }

}