package project.models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Driver {

    private RemoteWebDriver driver;

    private String browserName;

    public Driver(
            final RemoteWebDriver driver,
            final String browserName) {
        this.driver = driver;
        this.browserName = browserName;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getBrowserName() {
        return browserName;
    }

    public String getRemoteSessionId() {
        return driver.getSessionId().toString();
    }
}
