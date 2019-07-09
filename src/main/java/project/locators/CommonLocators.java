package project.locators;

public class CommonLocators {

    public static final String COMMON_NAV_PANEL = "//*[@id='project-navbar-collapse']";

    public static final String LOGGED_USERNAME_LINK = "//li[@class='dropdown']/a";

    public static final String USER_AVATAR_IMG = "//a/img[@class='img-circle']";

    public static final String APPS_PAGE_ICON = "//*[text()='Apps']/../../a";

    public static final String COMPS_PAGE_ICON = "//*[text()='Comparisons']/../../a";

    public static final String FILES_PAGE_ICON = "//*[text()='Files']/../../a";

    public static final String NOTES_PAGE_ICON = "//*[text()='Notes']/../../a";

    public static final String EXPERTS_PAGE_ICON = "//*[text()='Experts']/../../a";

    public static final String CHALLS_PAGE_ICON = "//*[text()='Challenges']/../../a";

    public static final String DISCS_PAGE_ICON = "//*[text()='Discussions']/../../a";

    public static final String OVERVIEW_PAGE_ICON = "//*[text()='Overview']/../../a";

    public static final String SPACES_PAGE_ICON = "//*[text()='Spaces']/../../a";

    public static final String WORKFLOWS_PAGE_ICON = "//*[text()='Workflows']/../../a";

    public static final String MAIN_LOGO = "//a[@class='navbar-brand']/img";

    public static final String SPACES_ICON_ACTIVE = "//*[@id='project-navbar-collapse']//li[@class='active']/a[contains(@href, 'spaces')]";

    public static final String ATTACH_TO_OBJECT_BUTTON = "//button[contains(text(), 'ttach to Discu')] | //button[contains(text(), 'ttach to Note')]";

    public static final String ATTACH_MODAL_FILES_TAB = "//*[contains(@id, 'modal')][contains(@style, 'display')]//li//span[text()='Files']";

    public static final String ATTACH_MODAL_FILE_LINK_TEMPLATE = "//*[contains(@id, 'modal')][contains(@style, 'display')]//span[text()='{FILE_NAME}']";

    public static final String ATTACH_MODAL_FILE_CHECKBOX_TEMPLATE = "//*[contains(@id, 'modal')][contains(@style, 'display')]//span[text()='{FILE_NAME}']/../..//input[@type='checkbox']";

    public static final String ATTACH_MODAL_SELECT_BUTTON = "//*[contains(@id, 'modal')][contains(@style, 'display')]//button/span[text()='Select']";

    public static final String SELECT_FILE_BUTTON = "//button[contains(text(), 'Select file')]";

    public static final String TURBOLINKS_PROGRESS_BAR = "//*[@class='turbolinks-progress-bar']";

    public static final String GUEST_WARNIN_MESSAGE = "//div[contains(@class, 'alert alert-warning')]";

}