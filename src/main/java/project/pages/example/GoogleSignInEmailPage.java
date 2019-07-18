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

public class GoogleSignInEmailPage extends AbstractPage {

    private final Logger log = Logger.getLogger(this.getClass());

    GoogleSignInEmailPage(final WebDriver driver) {
        super(driver);
        waitForPageToLoadAndVerifyBy(
                By.xpath(GoogleSignInPageLocators.SIGNIN_EMAIL_INPUT + "|" +
                        GoogleSignInPageLocators.COULD_NOT_FIND_ACCOUNT_NOTICE),
                ConfigManager.getWaitForPageUploadSec());
    }

    @FindBy(xpath = GoogleSignInPageLocators.SIGNIN_EMAIL_INPUT)
    private TextInput emailInput;

    @FindBy(xpath = GoogleSignInPageLocators.NEXT_BUTTON)
    private Button nextButton;

    @FindBy(xpath = GoogleSignInPageLocators.COULD_NOT_FIND_ACCOUNT_NOTICE)
    private WebElement couldNotFindAccNotice;

    public boolean isEmailInputDisplayed() {
        return isElementPresent(emailInput, 1);
    }

    public void printEmail(String email) {
        log.info("print email: " + email);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public GoogleSignInEmailPage clickNextButtonWrongEmail() {
        log.info("click Next button");
        nextButton.click();
        return new GoogleSignInEmailPage(getDriver());
    }

    public GoogleSignInPasswordPage clickNextButton() {
        log.info("click Next button");
        nextButton.click();
        return new GoogleSignInPasswordPage(getDriver());
    }

    public boolean isErrorNoticeDisplayed() {
        return isElementPresent(couldNotFindAccNotice, 1);
    }

    public boolean isErrorNoticeDisplayed(String errorText) {
        String xpath = "//*[text()=\"{t}\"]".replace("{t}", errorText);
        return isElementPresent(By.xpath(xpath), 1);
    }

}
