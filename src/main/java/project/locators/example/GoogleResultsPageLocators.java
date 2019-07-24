package project.locators.example;

public class GoogleResultsPageLocators {

    public static final String RESULT_STATS_AREA = "//*[@id='resultStats']";

    public static final String SINGLE_RESULT = "//*[text()='Web results']/..//a/h3";

    public static final String CURRENCY_CONVERTER_AREA = "//*[contains(@aria-label, 'onverter')][contains(@aria-label, 'urrency')]";

    public static final String CONVERTER_AREA_CURRENCY_FROM_SELECT = "//table//tr[1]/td//select[@aria-label='Currency Type']";

    public static final String CONVERTER_AREA_CURRENCY_TO_SELECT = "//table//tr[3]/td//select[@aria-label='Currency Type']";

    public static final String CONVERTER_AREA_AMOUNT_TO_INPUT = "//tr[3]//input[@aria-label='Currency Amount Field']";

    public static final String FIRST_RESULT = "//h2[text()='Web results']/../div/*[@class='g'][1]";

}