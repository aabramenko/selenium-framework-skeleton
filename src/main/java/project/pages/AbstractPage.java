package project.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import project.core.Utils;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public abstract class AbstractPage {

    private WebDriver driver;

    final int WAIT_UNTIL_DISPLAYED = 20;

    final int WAIT_UNTIL_CLICKABLE = 20;

    private final Logger log = Logger.getLogger(this.getClass());

    public WebDriver getDriver() {
        return driver;
    }

    public AbstractPage(final WebDriver driver) {
        HtmlElementLoader.populatePageObject(this, driver);
        this.driver = driver;
    }

    // ------ waits and sleep -------

    public void waitUntilDisplayed(final WebElement element) {
        waitUntilDisplayed(element, WAIT_UNTIL_DISPLAYED);
    }

    public boolean waitUntilDisplayed(final WebElement element, final int timeout) {
        log.info("wait until element [" + element + "] is displayed");
        boolean isDisplayed;
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            isDisplayed = true;
        }
        catch (final TimeoutException e) {
            log.info("element [" + element + "] is NOT displayed after " + timeout + " seconds");
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public void waitUntilDisplayed(final Link link, int timeout) {
        waitUntilDisplayed(link.getWrappedElement(), timeout);
    }

    public void waitUntilDisplayed(final Select select, int timeout) {
        waitUntilDisplayed(select.getWrappedElement(), timeout);
    }

    public void waitUntilDisplayed(final Button button, int timeout) {
        waitUntilDisplayed(button.getWrappedElement(), timeout);
    }

    public void waitUntilDisplayed(final TextInput input, int timeout) {
        waitUntilDisplayed(input.getWrappedElement(), timeout);
    }

    public void waitUntilDisplayed(final By locator) {
        waitUntilDisplayed(locator, WAIT_UNTIL_DISPLAYED);
    }

    public boolean waitUntilDisplayed(final By locator, final int timeout) {
        boolean isDisplayed;
        log.info("wait until element [" + locator + "] is displayed");
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            isDisplayed = true;
        }
        catch (final TimeoutException e) {
            log.info("element [" + locator + "] is not displayed after " + timeout + " seconds");
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean waitUntilClickable(final By locator, int timeout) {
        log.info("wait until element [" + locator + "] is clickable");
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        boolean isClickable = false;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            isClickable = true;
        }
        catch (final TimeoutException e) {
            log.info("element [" + locator + "] is NOT clickable after " + timeout + " seconds");
            throw e;
        }
        return isClickable;
    }

    public void waitUntilClickable(final WebElement element) {
        waitUntilClickable(element, WAIT_UNTIL_CLICKABLE);
    }

    public boolean waitUntilClickable(final WebElement element, int timeout) {
        log.info("wait until element [" + element + "] is clickable");
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        boolean isClickable = false;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            isClickable = true;
        }
        catch (final TimeoutException e) {
            log.warn("element [" + element + "] is NOT clickable after " + timeout + " seconds");
            throw e;
        }
        return isClickable;
    }

    public void isClickable(WebElement element) {
        waitUntilClickable(element, 1);
    }

    public void waitUntilClickable(final Link element, int timeout) {
        waitUntilClickable(element.getWrappedElement(), timeout);
    }
    public void waitUntilClickable(final Button element, int timeout) {
        waitUntilClickable(element.getWrappedElement(), timeout);
    }

    public void waitUntilNotDisplayed(final By locator, final int timeout) {
        log.info("wait until element is NOT displayed: " + locator);
        try {
            new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.info("element [" + locator + "] is still displayed after " + timeout + " seconds");
        }
    }

    public void waitUntilNotDisplayed(final WebElement element, final int timeout) {
        log.info("wait until element is NOT displayed: " + element);
        try {
            new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            log.info("element [" + element + "] is still displayed after " + timeout + " seconds");
        }
    }

    public boolean isElementPresent(final By locator, final int timeout) {
        return waitUntilDisplayed(locator, timeout);
    }

    public boolean isElementPresent(final By locator) {
        return waitUntilDisplayed(locator, WAIT_UNTIL_DISPLAYED);
    }

    public boolean isElementPresent(final WebElement element, final int timeout) {
        return waitUntilDisplayed(element, timeout);
    }

    public boolean isElementPresent(final WebElement element) {
        return waitUntilDisplayed(element, WAIT_UNTIL_DISPLAYED);
    }

    public boolean isElementPresent(final Button element, final int timeout) {
        return waitUntilDisplayed(element.getWrappedElement(), timeout);
    }

    public boolean isElementPresent(final Link element, final int timeout) {
        return waitUntilDisplayed(element.getWrappedElement(), timeout);
    }

    public boolean isElementPresent(final Select element, int timeout) {
        return waitUntilDisplayed(element.getWrappedElement(), timeout);
    }

    public boolean isElementPresent(final TextInput element, int timeout) {
        return waitUntilDisplayed(element.getWrappedElement(), timeout);
    }

    // -------- Find element by -----------

    public WebElement findElement(final By locator, final Integer timeout) {
        try {
            return new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
        catch (TimeoutException e) {
            return null;
        }
    }

    public WebElement findElement(final By locator) {
        return findElement(locator, WAIT_UNTIL_DISPLAYED);
    }

    // ---- Verifying page content ----

    public void waitForPageToLoadAndVerifyBy(final By pageIdentifier) {
        waitForPageToLoadAndVerifyBy(pageIdentifier, WAIT_UNTIL_DISPLAYED);
    }

    public void waitForPageToLoadAndVerifyBy(final By pageIdentifier, int timeout) {
        final String pageName = Utils.removePackageNameFromPath(this.getClass().getName());
        log.info("Waiting for " + pageName + " page to load");

        if (isElementPresent(pageIdentifier, timeout)) {
            log.info(pageName + " page is open");
        } else {
            log.warn("the page " + pageName + " is not open properly");
        }
    }

    // ---- wait for page scripts to upload ----

    public void waitUntilPageScriptsReady() {
        log.info("wait until page scripts are ready");
        waitUntilJSReady();
        waitForJQueryLoad();
        waitForAngularLoad();
    }

    private void waitUntilJSReady() throws JavascriptException {
        WebDriver jsWaitDriver = getDriver();
        WebDriverWait wait = new WebDriverWait(jsWaitDriver, 15);
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;

        try {
            ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) jsWaitDriver)
                    .executeScript("return document.readyState").toString().equals("complete");
            boolean jsReady = (Boolean) jsExec.executeScript("return document.readyState").toString().equals("complete");
            if (!jsReady) {
                wait.until(jsLoad);
            }
        }
        catch (Exception e) {
            //
        }
    }

    public void waitForAngularLoad() throws JavascriptException {
        WebDriver jsWaitDriver = getDriver();
        WebDriverWait wait = new WebDriverWait(jsWaitDriver, 15);
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;

        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
        try {
            ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf(((JavascriptExecutor) driver)
                    .executeScript(angularReadyScript).toString());
            boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());
            if(!angularReady) {
                wait.until(angularLoad);
            }
        }
        catch (Exception e) {
            //
        }
    }

    private void waitForJQueryLoad() throws JavascriptException {
        WebDriver jsWaitDriver = getDriver();
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;
        WebDriverWait jsWait = new WebDriverWait(jsWaitDriver, 15);

        try {
            ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) jsWaitDriver)
                    .executeScript("return jQuery.active") == 0);
            boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");
            if(!jqueryReady) {
                jsWait.until(jQueryLoad);
            }
        }
        catch (Exception e) {
            //
        }
    }

    // ------ frames -------

    public void switchToFrame(final WebElement frame) {
        waitUntilDisplayed(frame);
        waitForFrameAndSwitch(frame);
        log.info("Switch to frame");
    }

    public void switchToDefaultContent() {
        log.info("Switch back to default content");
        getDriver().switchTo().defaultContent();
    }

    public void waitForFrameAndSwitch(final String frameId) {
        (new WebDriverWait(getDriver(), WAIT_UNTIL_DISPLAYED)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
    }

    private void waitForFrameAndSwitch(final WebElement frame) {
        (new WebDriverWait(getDriver(), WAIT_UNTIL_DISPLAYED)).until(new ExpectedCondition<WebDriver>() {
            public WebDriver apply(final WebDriver driver) {
                try {
                    return driver.switchTo().frame(frame);
                } catch (final NoSuchFrameException e) {
                    return null;
                } catch (final NoSuchElementException e) {
                    return null;
                }
            }
        });
    }

    public void switchFromFrameToDefault() {
        getDriver().switchTo().defaultContent();
    }

    // ------- alerts ------

    public void acceptAlert(int timeOutInSeconds, int sleepInMillis) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds, sleepInMillis);
        Alert alert = wait.until(alertIsPresent());
        if (alert != null) {
            alert.accept();
        }
    }

}