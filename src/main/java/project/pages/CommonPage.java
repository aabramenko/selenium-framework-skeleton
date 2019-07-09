package project.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import project.core.ConfigManager;
import project.locators.CommonLocators;
import ru.yandex.qatools.htmlelements.element.Button;

public class CommonPage extends AbstractPage {

    private final Logger log = Logger.getLogger(this.getClass());

    private final int WAIT_UNTIL_CLICKABLE = 20;

    private final int WAIT_UNTIL_DISPLAYED = 20;

    public CommonPage(final WebDriver driver) {
        super(driver);
        waitForPageToLoadAndVerifyBy(By.xpath("//html/body/header"), ConfigManager.getWaitForPageUploadSec());
    }

    @FindBy(xpath = CommonLocators.ATTACH_TO_OBJECT_BUTTON)
    private Button attachButton;

    @FindBy(xpath = CommonLocators.ATTACH_MODAL_FILES_TAB)
    private WebElement attachModalFilesTab;

    @FindBy(xpath = CommonLocators.ATTACH_MODAL_SELECT_BUTTON)
    private WebElement attachModalSelectButton;

    @FindBy(xpath = CommonLocators.SELECT_FILE_BUTTON)
    private Button selectFileButton;

    @FindBy(xpath = CommonLocators.GUEST_WARNIN_MESSAGE)
    private WebElement warningArea;

    @FindBy(xpath = CommonLocators.WORKFLOWS_PAGE_ICON)
    private WebElement workflowsIcon;

}
