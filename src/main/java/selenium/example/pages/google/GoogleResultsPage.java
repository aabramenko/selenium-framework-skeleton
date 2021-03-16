package selenium.example.pages.google;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.core.TestRunParams;
import selenium.core.Utils;
import selenium.example.locators.GoogleResultsPageLocators;
import selenium.example.pages.AbstractPage;
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
        Long startTime = System.currentTimeMillis();
        waitForPageToLoadAndVerifyBy(By.xpath(GoogleResultsPageLocators.SINGLE_RESULT), TestRunParams.getWaitForPageUploadSec());
        waitUntilPageScriptsReady();
        Long finishTime = System.currentTimeMillis();
        Utils.savePageUploadTime(Utils.removePackages(this.getClass().getName()), startTime, finishTime);
    }

    public boolean isCurrencyConverterAreaDisplayed() {
        return isElementPresent(currencyConverterArea, 1);
    }

    public String getConverterAreaCurrencyFrom() {
        String currency = currencyFromSelector.getFirstSelectedOption().getText();
        log.info("Currency From = " + currency);
        return currency;
    }

    public String getConverterAreaAmountTo() {
        String amount = amountToInput.getText();
        log.info("Google Page: Rate = " + amount);
        return amount;
    }

}
