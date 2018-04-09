package gmail;

import driver.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobject.HomePage;
import pageobject.LoginPage;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import testng.GmailListener;

@Listeners(GmailListener.class)
public class SendEmailTest {

    private static final String USERNAME = "seleniumtests30";
    private static final String PASSWORD = "060788avavav";
    private static final String EMAIL_ADDRESS = "seleniumtests10@gmail.com";
    private static final String EMAIL_SUBJECT = "Test email subject";
    private static final String EMAIL_BODY = "Some test text";
    WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(String browser) throws Exception {
        driver = Driver.getInstance().open(browser);
        loginPage = new LoginPage();
        homePage = loginPage.login(USERNAME, PASSWORD);
    }

    @Test
    @Features("Send Email")
    @Description("Verify that sent email appears in Sent Mail folder")
    @TestCaseId("Project-3")
    public void verifySentEmailInSentMailFolder() {
        homePage.sendEmail(EMAIL_ADDRESS, EMAIL_SUBJECT, EMAIL_BODY);
        homePage.openSentMailFolder();
        String result = homePage.getLastSentEmailSubject().getText();

        Assert.assertEquals(EMAIL_SUBJECT, result,
               "Expected sent email's subject is '" + EMAIL_SUBJECT + "' but actual sent email's subject is '" + result + "'");
    }

    @Test(dependsOnMethods = "verifySentEmailInSentMailFolder")
    @Features("Send Email")
    @Description("Verify the ability to send emails")
    @TestCaseId("Project-4")
    public void verifySentEmailReceived() {
        homePage.logout();
        loginPage.switchAccount();
        loginPage.login(EMAIL_ADDRESS, PASSWORD);

        String result = homePage.getLastReceivedEmailSubject();

        Assert.assertEquals(EMAIL_SUBJECT, result,
                "Expected received email's subject is '" + EMAIL_SUBJECT + "' but actual received email's subject is '" + result + "'");
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        driver.close();
    }
}
