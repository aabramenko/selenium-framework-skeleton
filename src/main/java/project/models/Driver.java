package project.models;

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

    public RemoteWebDriver getDriver() {
        return driver;
    }

    public String getBrowserName() {
        return browserName;
    }

}
