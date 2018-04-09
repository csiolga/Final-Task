package pageobject;

import driver.Driver;
import org.openqa.selenium.WebDriver;

abstract class Page {
    private final String TITLE;
    WebDriver driver = Driver.getInstance().getDriver();
    Page(String title) {
        TITLE = title;
    }

    public boolean isDisplayed() {
        return driver.getTitle().contentEquals(TITLE);
    }
}
