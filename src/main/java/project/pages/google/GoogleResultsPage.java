package project.pages.google;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.core.TestRunParams;
import project.locators.GoogleResultsPageLocators;
import project.pages.AbstractPage;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class GoogleResultsPage extends AbstractPage {

    private final Logger log = Logger.getLogger(this.getClass());

    @FindBy(xpath = GoogleResultsPageLocators.CURRENCY_CONVERTER_AREA)
    private WebElement currencyConverterArea;

    @FindBy(xpath = GoogleResultsPageLocators.CONVERTER_AREA_CURRENCY_FROM_SELECT)
    private Select currencyFromSelector;

    @FindBy(xpath = GoogleResultsPageLocators.CONVERTER_AREA_CURRENCY_TO_SELECT)
    private Select currencyToSelector;

    @FindBy(xpath = GoogleResultsPageLocators.CONVERTER_AREA_AMOUNT_TO_INPUT)
    private TextInput amountToInput;

    GoogleResultsPage(final WebDriver driver) {
        super(driver);
        waitForPageToLoadAndVerifyBy(By.xpath(GoogleResultsPageLocators.RESULT_STATISTICS), TestRunParams.getWaitForPageUploadSec());
        waitUntilPageScriptsReady();
    }

    public int getNumberOfResultsOnPage() {
        int num = getDriver().findElements(By.xpath(GoogleResultsPageLocators.SINGLE_RESULT)).size();
        log.info("Page contains " + num + " results");
        return num;
    }

    public boolean isCurrencyConverterAreaDisplayed() {
        return isElementPresent(currencyConverterArea, 1);
    }

    public String getConverterAreaCurrencyFrom() {
        String currency = currencyFromSelector.getFirstSelectedOption().getText();
        log.info("Currency From = " + currency);
        return currency;
    }

    public String getConverterAreaCurrencyTo() {
        String currency = currencyToSelector.getFirstSelectedOption().getText();
        log.info("Currency To = " + currency);
        return currency;
    }

    public String getConverterAreaAmountTo() {
        String amount = amountToInput.getText();
        log.info("Google Page: Rate = " + amount);
        return amount;
    }

    public GoogleResultsPage waitUntilResultsDisplayed(int timeoutSec) {
        log.info("wait until results are displayed");
        WebDriverWait wait = new WebDriverWait(getDriver(), timeoutSec);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(GoogleResultsPageLocators.SINGLE_RESULT), 0));
        return new GoogleResultsPage(getDriver());
    }

}
