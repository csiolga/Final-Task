package waits;

import driver.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {
    private static WebDriver driver = Driver.getInstance().getDriver();

    public static void waitForElementIsDispalyed(WebElement element) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForTitleChange(String title) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.not(ExpectedConditions.titleIs(title)));
    }

    public static void waitForAttributeValueChange(WebElement element, String attribute, String value) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.attributeContains(element, attribute, value));
    }
}
