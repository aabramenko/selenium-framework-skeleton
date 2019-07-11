package project.cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import project.core.Dictionary;
import project.data.TestData;
import project.pages.example.GoogleResultsPage;
import project.pages.example.GoogleStartPage;
import ru.yandex.qatools.htmlelements.annotations.Name;

@Name("Validate currency rate")
@Test(groups = {"regression"})
public class ValidateCurrencyRateTest extends CommonActionsTest {

    GoogleStartPage startPage;
    GoogleResultsPage resultsPage;

    @Test
    public void tc_rate_01_open_google_start_page() {

        startPage = openGoogleStartPage();

        SoftAssert.assertTrue(
                startPage.isGoogleSearchButtonDisplayed(),
                "'Google Search' button is displayed"
        );

        SoftAssert.assertTrue(
                startPage.isSearchLineDisplayed(),
                "Search Line is displayed"
        );

        SoftAssert.assertAll();
    }

    @Test(dependsOnMethods = "tc_rate_01_open_google_start_page")
    public void tc_rate_02_search_current_rate() {

        resultsPage = startPage.search(TestData.CASE_02_TEXT_EURO_RATE_TO_USD);

        Assert.assertTrue(
                resultsPage.getNumberOfResultsOnPage() > 0,
                "Results page contains search results"
        );

        Assert.assertTrue(
                resultsPage.isCurrencyConverterAreaDisplayed(),
                "Currency converter area is displayed"
        );

        Assert.assertEquals(
                resultsPage.getConverterAreaCurrencyFrom().toUpperCase(),
                TestData.CASE_02_EXPECTED_CURRENCY_FROM.toUpperCase(),
                "Name of currency 'From'"
        );

        Assert.assertEquals(
                resultsPage.getConverterAreaCurrencyTo().toUpperCase(),
                TestData.CASE_02_EXPECTED_CURRENCY_TO.toUpperCase(),
                "Name of currency 'To'"
        );

    }

    @Test(dependsOnMethods = "tc_rate_02_search_current_rate")
    public void tc_rate_03_compare_with_data_from_bank() {

        String amountFromGoogle = resultsPage.getConverterAreaAmountTo();

        String amountFromBank = getCurrencyAmountFromBank(Dictionary.USD);

        Assert.assertEquals(
                amountFromGoogle,
                amountFromBank,
                "Rate"
        );
    }

}
