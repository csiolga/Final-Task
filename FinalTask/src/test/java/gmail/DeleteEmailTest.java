package gmail;

import config.Configuration;
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

import java.io.IOException;

@Listeners(GmailListener.class)
public class DeleteEmailTest {

    private static final String USERNAME = Configuration.getValue("gmail.username");
    private static final String PASSWORD = Configuration.getValue("gmail.password");
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeClass
    @Parameters({"browser"})
    public void setup(String browser) throws IOException {
        driver = Driver.getInstance().open(browser);
        loginPage = new LoginPage();
        homePage = loginPage.login(USERNAME, PASSWORD);
    }

    @AfterClass
    public void teardown() {
        driver.close();
    }

    @Test
    @Features("Delete Email")
    @Description("Verify that deleted email is listed in Trash")
    @TestCaseId("Project-4")
    public void verifyDeletedEmailInBinFolder() {
        String emailSubject = homePage.getLastReceivedEmailSubject();

        homePage.deleteFirstEmail();
        homePage.openBinFolder();
        String result = homePage.getLastDeletedEmailSubject();

        Assert.assertEquals(emailSubject, result,
                "Expected deleted email subject is '" + emailSubject + "' but actual deleted email subject is '" + result + "'");
    }
}
