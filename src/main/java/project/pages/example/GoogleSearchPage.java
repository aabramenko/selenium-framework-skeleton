package project.pages.example;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import project.core.ConfigManager;
import project.locators.example.GoogleResultsPageLocators;
import project.locators.example.GoogleStartPageLocators;
import project.pages.AbstractPage;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class GoogleSearchPage extends AbstractPage {

    private final Logger log = Logger.getLogger(this.getClass());

    public GoogleSearchPage(final WebDriver driver) {
        super(driver);
        waitForPageToLoadAndVerifyBy(By.xpath(GoogleStartPageLocators.GOOGLE_SEARCH_BUTON), ConfigManager.getWaitForPageUploadSec());
        waitUntilPageScriptsReady();
    }

    @FindBy(xpath = GoogleStartPageLocators.GOOGLE_SEARCH_BUTON)
    private Button googleSearchButton;

    @FindBy(xpath = GoogleStartPageLocators.SEARCH_LINE)
    private TextInput searchLine;

    @FindBy(xpath = GoogleStartPageLocators.SIGNIN_BUTTON)
    private Button signInButton;

    public boolean isGoogleSearchButtonDisplayed() {
        return isElementPresent(googleSearchButton, 1);
    }

    public boolean isSearchLineDisplayed() {
        return isElementPresent(searchLine, 1);
    }

    public GoogleResultsPage search(String text) {
        log.info("search: " + text);
        searchLine.sendKeys(text);
        searchLine.submit();
        waitUntilDisplayed(By.xpath(GoogleResultsPageLocators.FIRST_RESULT), 5);
        return new GoogleResultsPage(getDriver());
    }

    public boolean isSignInButtonDisplayed() {
        return isElementPresent(signInButton, 1);
    }

    public GoogleSignInEmailPage clickSignInButton() {
        log.info("click Sign In button");
        signInButton.click();
        return new GoogleSignInEmailPage(getDriver());
    }

}
