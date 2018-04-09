package gmail;

import driver.Driver;
import file.FileRead;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
public class LogoutTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(String browser) throws Exception {
        driver = Driver.getInstance().open(browser);
        loginPage = new LoginPage();
    }

    @AfterMethod
    public void switchAccount() {
        loginPage.switchAccount();
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        driver.close();
    }

    @Test(dataProvider = "credentials")
    @Features("Login")
    @Description("Logout from gmail")
    @TestCaseId("Project-1")
    public void logout(String username, String password) {
        homePage = loginPage.login(username, password);

        homePage.logout();

        Assert.assertTrue(loginPage.isDisplayed(), "Home page is not displayed");
    }

    @DataProvider(name = "credentials")
    public  Object[][] getFromFile() throws IOException {
        return FileRead.getCredentials();
    }
}
