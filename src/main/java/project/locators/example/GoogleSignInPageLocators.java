package project.locators.example;

public class GoogleSignInPageLocators {

    public static final String SIGNIN_EMAIL_INPUT = "//input[@id='identifierId']";

    public static final String SIGNIN_PASSWORD_INPUT = "//*[@id='password']//input[@name='password']";

    public static final String NEXT_BUTTON = "//*[text()='Next']";

    public static final String COULD_NOT_FIND_ACCOUNT_NOTICE = "//*[text()=\"Couldn't find your Google Account\"]";

    public static final String WRONG_PASSWORD_NOTICE = "//*[text()='Wrong password. Try again or click Forgot password to reset it.']";

}