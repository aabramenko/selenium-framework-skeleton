package project.pages.example;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import project.core.ConfigManager;
import project.locators.example.GoogleResultsPageLocators;
import project.pages.AbstractPage;

public class GoogleResultsPage extends AbstractPage {

    private final Logger log = Logger.getLogger(this.getClass());

    @FindBy(xpath = GoogleResultsPageLocators.CURRENCY_CONVERTER_AREA)
    private WebElement currencyConverterArea;

    @FindBy(xpath = GoogleResultsPageLocators.CONVERTER_AREA_CURRENCY_FROM)
    private WebElement currencyFrom;

    @FindBy(xpath = GoogleResultsPageLocators.CONVERTER_AREA_CURRENCY_TO)
    private WebElement currencyTo;

    @FindBy(xpath = GoogleResultsPageLocators.CONVERTER_AREA_AMOUNT_TO)
    private WebElement amountTo;

    GoogleResultsPage(final WebDriver driver) {
        super(driver);
        waitForPageToLoadAndVerifyBy(By.xpath(GoogleResultsPageLocators.RESULT_STATS_AREA), ConfigManager.getWaitForPageUploadSec());
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
        String currency = currencyFrom.getText();
        log.info("Currency From = " + currency);
        return currency;
    }

    public String getConverterAreaCurrencyTo() {
        String currency = currencyTo.getText();
        log.info("Currency To = " + currency);
        return currency;
    }

    public String getConverterAreaAmountTo() {
        String amount = amountTo.getText();
        log.info("Amount To = " + amount);
        return amount;
    }

}
