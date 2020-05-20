package project.cases;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import project.core.XmlManager;
import project.data.TestData;
import project.pages.google.GoogleSearchPage;

public class BaseTest extends AbstractTest {

    private Logger log = Logger.getLogger("");

    protected GoogleSearchPage openGoogleSearchPage() {
        log.info("open Google start page");
        getDriver().get(TestData.GOOGLE_START_PAGE);
        return new GoogleSearchPage(getDriver());
    }

    protected String getCurrencyRateFromDocument(String currencyName, Document doc) {
        log.info("getting rate of " + currencyName + " from document");
        String amount = XmlManager.getValueByXpath(
                doc,
                TestData.XML_XPATH_GET_RATE_BY_CURRENCY_NAME.replace("{NAME}", currencyName.toUpperCase())
        );
        log.info("Info from bank: " + currencyName + ": Rate = " + amount);
        return amount;
    }

}
