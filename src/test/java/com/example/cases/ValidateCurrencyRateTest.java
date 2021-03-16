package com.example.cases;

import com.example.data.TestData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import selenium.core.Dictionary;
import selenium.core.Utils;
import selenium.core.XmlManager;
import selenium.example.pages.google.GoogleResultsPage;
import selenium.example.pages.google.GoogleSearchPage;
import ru.yandex.qatools.htmlelements.annotations.Name;

import static java.lang.String.format;

@Name("Compare EUR currency rates from a bank and Google page")
@Test(groups = {Dictionary.REGRESSION, Dictionary.UI})
public class ValidateCurrencyRateTest extends BaseTest {

//    1. search in the Google the phrase 'euro rate to %CURRENCY_NAME%',
//       where '%CURRENCY_NAME%' is a currency name (defined in the test suite)
//    2. get a rate value from the Google result page
//    3. get a rate for the same currencies from the xml document
//    4. compare the rates from the Google and from the xml.
//       Test is passed if the difference is less than %possible_delta_percent% percent

    GoogleSearchPage searchPage;
    GoogleResultsPage resultsPage;
    Document officialRates;
    int possible_delta_percent = 1;

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

        double actual_difference_percent = Utils.getDeltaPercent(
                Double.parseDouble(amountFromGoogleString),
                Double.parseDouble(amountFromBankString)
        );

        Assert.assertTrue(
                actual_difference_percent < possible_delta_percent,
                format("Difference between %s (from Google) and %s (from bank) " +
                         "is less than possible delta %d percent",
                        amountFromGoogleString,
                        amountFromBankString,
                        possible_delta_percent)
        );
    }

}
