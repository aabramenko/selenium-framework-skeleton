package project.pages.example;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import project.core.ConfigManager;
import project.locators.example.GoogleSignInPageLocators;
import project.pages.AbstractPage;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class GoogleSignInPasswordPage extends AbstractPage {

    private final Logger log = Logger.getLogger(this.getClass());

    GoogleSignInPasswordPage(final WebDriver driver) {
        super(driver);
        waitForPageToLoadAndVerifyBy(
                By.xpath(GoogleSignInPageLocators.SIGNIN_PASSWORD_INPUT + "|" +
                        GoogleSignInPageLocators.WRONG_PASSWORD_NOTICE),
                ConfigManager.getWaitForPageUploadSec());
    }

    @FindBy(xpath = GoogleSignInPageLocators.SIGNIN_PASSWORD_INPUT)
    private TextInput passwordInput;

    @FindBy(xpath = GoogleSignInPageLocators.NEXT_BUTTON)
    private Button nextButton;

    @FindBy(xpath = GoogleSignInPageLocators.WRONG_PASSWORD_NOTICE)
    private WebElement wrongPasswordNotice;

    public boolean isPasswordInputDisplayed() {
        return isElementPresent(passwordInput, 1);
    }

    public void printPassword(String password) {
        log.info("print password");
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public GoogleSignInPasswordPage clickNextButtonWrongPassword() {
        log.info("click Next button");
        nextButton.click();
        return new GoogleSignInPasswordPage(getDriver());
    }

    public boolean isErrorNoticeDisplayed() {
        return isElementPresent(wrongPasswordNotice, 1);
    }

}
