package project.cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import project.data.TestUsers;
import project.models.User;
import project.pages.example.GoogleSignInEmailPage;
import project.pages.example.GoogleStartPage;
import ru.yandex.qatools.htmlelements.annotations.Name;

@Name("Try to login with wrong email")
@Test(groups = {"regression"})
public class WrongCredsTest extends CommonActionsTest {

    GoogleStartPage startPage;
    GoogleSignInEmailPage signInEmailPage;

    @Test
    void tc_wrngcrds_01_submit_wrong_email() {

        User userBadEmail = TestUsers.getUserBadEmail();

        startPage = openGoogleStartPage();
        signInEmailPage = startPage.clickSignInButton();

        Assert.assertTrue(
                signInEmailPage.isEmailInputDisplayed(),
                "Email input is displayed"
        );

        signInEmailPage.printEmail(userBadEmail.getEmail());
        signInEmailPage = signInEmailPage.clickNextButtonWrongEmail();

        SoftAssert.assertTrue(
                signInEmailPage.isErrorNoticeDisplayed(),
                "'Could not find your account' notification is displayed"
        );

        SoftAssert.assertTrue(
                signInEmailPage.isEmailInputDisplayed(),
                "Email input is still displayed"
        );

        SoftAssert.assertAll();
    }

}
