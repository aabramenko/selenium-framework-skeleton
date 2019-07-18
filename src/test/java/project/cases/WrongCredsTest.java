package project.cases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import project.data.TestUsers;
import project.models.User;
import project.pages.example.GoogleSignInEmailPage;
import project.pages.example.GoogleSearchPage;
import ru.yandex.qatools.htmlelements.annotations.Name;

@Name("Try to login with wrong email")
@Test(groups = {"regression", "ui"})
public class WrongCredsTest extends CommonActionsTest {

    GoogleSearchPage searchPage;
    GoogleSignInEmailPage signInEmailPage;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        openBrowser();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowser();
    }

    @Test
    void tc_wrngcrds_01_submit_wrong_email() {

        User userBadEmail = TestUsers.getUserBadEmail();

        searchPage = openGoogleSearchPage();
        signInEmailPage = searchPage.clickSignInButton();

        Assert.assertTrue(
                signInEmailPage.isEmailInputDisplayed(),
                "Email input is displayed"
        );

        signInEmailPage.printEmail(userBadEmail.getEmail());
        signInEmailPage = signInEmailPage.clickNextButtonWrongEmail();

        softAssert.assertTrue(
                signInEmailPage.isErrorNoticeDisplayed(),
                "'Could not find your account' notification is displayed"
        );

        softAssert.assertTrue(
                signInEmailPage.isEmailInputDisplayed(),
                "Email input is still displayed"
        );

        softAssert.assertAll();
    }

}
