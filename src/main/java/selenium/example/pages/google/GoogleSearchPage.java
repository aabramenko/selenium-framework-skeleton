package selenium.example.pages.google;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import selenium.core.TestRunParams;
import selenium.example.locators.GoogleStartPageLocators;
import selenium.example.pages.AbstractPage;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class GoogleSearchPage extends AbstractPage {

    private final Logger log = Logger.getLogger(this.getClass());

    public GoogleSearchPage(final WebDriver driver) {
        super(driver);
        waitForPageToLoadAndVerifyBy(By.xpath(GoogleStartPageLocators.GOOGLE_SEARCH_BUTON), TestRunParams.getWaitForPageUploadSec());
        waitUntilPageScriptsReady();
    }

    @FindBy(xpath = GoogleStartPageLocators.GOOGLE_SEARCH_BUTON)
    private Button googleSearchButton;

    @FindBy(xpath = GoogleStartPageLocators.SEARCH_LINE)
    private TextInput searchLine;

    @FindBy(xpath = GoogleStartPageLocators.SIGNIN_BUTTON)
    private Button signInButton;

    public GoogleResultsPage search(String text) {
        log.info("search: " + text);
        searchLine.sendKeys(text);
        searchLine.submit();
        return new GoogleResultsPage(getDriver());
    }

}
