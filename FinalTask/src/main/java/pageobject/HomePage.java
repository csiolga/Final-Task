package pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import waits.Wait;

public class HomePage extends Page {
    private static final String TITLE = "Inbox";

    @FindBy(xpath = "//a[contains(@href, 'SignOutOptions')]")
    private WebElement accountLink;

    @FindBy(xpath =  "//a[contains(text(), 'Sign out')]")
    private WebElement signOutLink;

    @FindBy(xpath = "//div[contains(text(), 'COMPOSE')]")
    private WebElement composeButton;

    @FindBy(xpath = "//textarea[@name='to']")
    private WebElement toInput;

    @FindBy(xpath = "//input[@name = 'subjectbox']")
    private WebElement subjectInput;

    @FindBy(xpath = "//div[@aria-label='Message Body']")
    private WebElement messageBodyInput;

    @FindBy(xpath = "//div[@role = 'button' and contains(text(), 'Send')]")
    private WebElement sendEmailButton;

    @FindBy(xpath = "//div[@role='alert']//div[@class='vh']")
    private WebElement sentEmailAlert;

    @FindBy(xpath = "//a[@title='Sent Mail']")
    private WebElement sentMailLink;

    @FindBy(xpath = "//div[@class='ae4 UI']//tr[1]//span[@class='bog']")
    private WebElement lastSentEmailSubject;

    @FindBy(xpath = "//tr[@class = 'zA zE'][1]//span[@class = 'bog']")
    private WebElement lastReceivedEmailSubject;

    @FindBy(xpath = "//tr[@class ='zA zE'][1]//div[@role = 'checkbox']")
    private WebElement lastReceivedEmailCheckbox;

    @FindBy(xpath = "//div[@aria-label='Delete']")
    private WebElement deleteButton;

    @FindBy(css = "span.J-Ke")
    private WebElement moreLink;

    @FindBy(xpath = "//a[contains(text(), 'Bin')]")
    private WebElement binFolderLink;

    @FindBy(xpath = "//div[@class='ae4 UI']//tr[1]//span[@class = 'bog']")
    private WebElement lastDeletedEmailSubject;

    public HomePage() {
        super(TITLE);
        PageFactory.initElements(driver, this);
    }

    public LoginPage logout() {
        accountLink.click();
        signOutLink.click();

        return new LoginPage();
    }

    public void sendEmail(String emailAddress, String subject, String messageBody) {
        composeButton.click();
        toInput.sendKeys(emailAddress);
        subjectInput.sendKeys(subject);
        messageBodyInput.sendKeys(messageBody);
        sendEmailButton.click();

        Wait.waitForElementIsDispalyed(sentEmailAlert);
    }

    public void openSentMailFolder() {
        sentMailLink.click();
    }

    public WebElement getLastSentEmailSubject() {
        return lastSentEmailSubject;
    }

    public String getLastReceivedEmailSubject(){
        return lastReceivedEmailSubject.getText();
    }

    public void deleteFirstEmail() {
        lastReceivedEmailCheckbox.click();
        Wait.waitForElementIsDispalyed(deleteButton);
        deleteButton.click();
    }

    public void openBinFolder() {
        moreLink.click();
        binFolderLink.click();
    }

    public String getLastDeletedEmailSubject() {
        return lastDeletedEmailSubject.getText();
    }

     @Override
     public boolean isDisplayed() {
        return driver.getTitle().startsWith(TITLE);
    }
}
