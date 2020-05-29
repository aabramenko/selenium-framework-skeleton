package project.cases;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import project.core.Dictionary;
import project.core.Utils;
import project.core.XmlManager;
import project.data.TestData;
import project.pages.google.GoogleResultsPage;
import project.pages.google.GoogleSearchPage;
import ru.yandex.qatools.htmlelements.annotations.Name;

@Name("Compare EUR currency rates from a bank and Google page")
@Test(groups = {Dictionary.REGRESSION, Dictionary.UI})
public class ValidateCurrencyRateTest extends BaseTest {

    GoogleSearchPage searchPage;
    GoogleResultsPage resultsPage;
    Document officialRates;
    int expected_delta_percent = 1;
    private final Logger log = Logger.getLogger(this.getClass());

    @BeforeClass
    void precondition() {
        officialRates = XmlManager.uploadXmlFromUrlToDocument(TestData.URL_XML_TODAYS_RATES_FROM_BANK);
    }

    @Parameters({ "currency_name" })
    @Test
    void tc_rate_01_search_EUR_rate_and_compare_with_official_one(@Optional("usd") String currency_name) {

        searchPage = openGoogleSearchPage();
        resultsPage = searchPage.search("euro rate to " + currency_name);

        Assert.assertTrue(
                resultsPage.isCurrencyConverterAreaDisplayed(),
                "Currency converter area is displayed"
        );

        Assert.assertEquals(
                resultsPage.getConverterAreaCurrencyFrom().toUpperCase(),
                TestData.CASE_02_EXPECTED_CURRENCY_FROM.toUpperCase(),
                "Name of currency 'From'"
        );

        String amountFromGoogleString = resultsPage.getConverterAreaAmountTo();
        String amountFromBankString = getCurrencyRateFromDocument(currency_name, officialRates);

        double actual_delta_percent = Utils.getDeltaPercent(
                Double.parseDouble(amountFromGoogleString),
                Double.parseDouble(amountFromBankString)
        );

        Assert.assertTrue(
                actual_delta_percent < expected_delta_percent,
                "Rates has difference not more than " + expected_delta_percent + "%"
        );
    }

}
