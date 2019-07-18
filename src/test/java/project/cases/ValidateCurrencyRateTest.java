package project.cases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import project.core.Dictionary;
import project.data.TestData;
import project.pages.example.GoogleResultsPage;
import project.pages.example.GoogleSearchPage;
import ru.yandex.qatools.htmlelements.annotations.Name;

@Name("Validate currency rate")
@Test(groups = {"regression", "ui"})
public class ValidateCurrencyRateTest extends CommonActionsTest {

    GoogleSearchPage searchPage;
    GoogleResultsPage resultsPage;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        openBrowser();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowser();
    }

    @Test
    public void tc_rate_01_open_google_start_page() {

        searchPage = openGoogleSearchPage();

        softAssert.assertTrue(
                searchPage.isGoogleSearchButtonDisplayed(),
                "'Google Search' button is displayed"
        );

        softAssert.assertTrue(
                searchPage.isSearchLineDisplayed(),
                "Search Line is displayed"
        );

        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "tc_rate_01_open_google_start_page")
    public void tc_rate_02_search_current_rate() {

        resultsPage = searchPage.search(TestData.CASE_02_TEXT_EURO_RATE_TO_USD);

        Assert.assertTrue(
                resultsPage.getNumberOfResultsOnPage() > 0,
                "Results page contains search results"
        );

        Assert.assertTrue(
                resultsPage.isCurrencyConverterAreaDisplayed(),
                "Currency converter area is displayed"
        );

        Assert.assertEquals(
                resultsPage.getConverterAreaSelectorCurrencyFromText().toUpperCase(),
                TestData.CASE_02_EXPECTED_CURRENCY_FROM.toUpperCase(),
                "Name of currency 'From'"
        );

        Assert.assertEquals(
                resultsPage.getConverterAreaCurrencyToText().toUpperCase(),
                TestData.CASE_02_EXPECTED_CURRENCY_TO.toUpperCase(),
                "Name of currency 'To'"
        );
    }

    @Test(dependsOnMethods = "tc_rate_02_search_current_rate")
    public void tc_rate_03_compare_with_data_from_bank() {

        String amountFromGoogle = resultsPage.getConverterAreaAmountToText();

        String amountFromBank = getCurrencyAmountFromBank(Dictionary.USD);

        Assert.assertEquals(
                amountFromGoogle,
                amountFromBank,
                "Rate"
        );
    }

}
