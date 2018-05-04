package pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import waits.Wait;

public class LoginPage extends Page {
    private static final String TITLE = "Gmail";

    @FindBy(id = "identifierId")
    private WebElement usernameInput;

    @FindBy(id = "identifierNext")
    private WebElement nextButton;

    @FindBy(xpath = "//div[@id = 'identifierNext']")
    private WebElement switchAccountNextButton;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordInput;

    @FindBy(id = "passwordNext")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@aria-label = 'Switch account']")
    private WebElement switchAccountButton;

    @FindBy(id = "identifierLink")
    private WebElement useAnotherAccountLink;

    public LoginPage() {
        super(TITLE);
        PageFactory.initElements(driver, this);
    }

    public HomePage login(String username, String password) {
        usernameInput.sendKeys(username);
        nextButton.click();
        Wait.waitForElementIsDispalyed(passwordInput);

        passwordInput.sendKeys(password);
        Wait.waitForElementIsDispalyed(loginButton);
        loginButton.click();

        Wait.waitForTitleChange(TITLE);

        return new HomePage();
    }

    public void switchAccount() {
        switchAccountButton.click();

        Wait.waitForElementIsDispalyed(useAnotherAccountLink);

        useAnotherAccountLink.click();
    }
}
