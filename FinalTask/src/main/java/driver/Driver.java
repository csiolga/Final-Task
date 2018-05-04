package driver;

import config.Configuration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Driver {
    private static final String DESTINATION_URL = Configuration.getValue("gmail.url");
    private static final String USERNAME = Configuration.getValue("cloud.username");
    private static final String ACCESS_KEY = Configuration.getValue("cloud.accessKey");
    private static final String CLOUD_DOMAIN = Configuration.getValue("cloud.domain");
    public static final String CLOUD_URL = "https://" + USERNAME + ":" + ACCESS_KEY + CLOUD_DOMAIN;
    private static final String GRID_URL = Configuration.getValue("grid.url");
    private DesiredCapabilities desiredCaps;
    private Capabilities cap;
    private static Driver instance = null;
    private WebDriver driver;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date;
    private static final int RUN_METHOD = 1; //1 = locally, 2 = cloud, 3 = selenium grid


    private Driver() {}

    public static Driver getInstance() {
        if (instance == null) {
            synchronized (Driver.class) {
                if (instance == null) {
                    instance = new Driver();
                }
            }
        }
        return instance;
    }

    private WebDriver createLocalDriver (String browser) {
        if(browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");
            driver = new ChromeDriver();
        } else if(browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "src/test/drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        } else {
            throw new RuntimeException("Incorrect browser name");
        }

        return driver;
    }

    private WebDriver createCloudDriver (String browser) throws MalformedURLException {
        if (browser.equalsIgnoreCase("firefox")) {
            desiredCaps = DesiredCapabilities.firefox();
        } else if (browser.equalsIgnoreCase("chrome")) {
            desiredCaps = DesiredCapabilities.chrome();
        } else {
            throw new RuntimeException("Incorrect browser name");
        }

        desiredCaps.setCapability("platform", "Windows 10");
        driver = new RemoteWebDriver(new URL(CLOUD_URL), desiredCaps);

        return driver;
    }

    private WebDriver createGridDriver(String browser) throws MalformedURLException{
        if(browser.equalsIgnoreCase("chrome")) {
            driver = new RemoteWebDriver(new URL(GRID_URL), DesiredCapabilities.chrome());
        } else if(browser.equalsIgnoreCase("firefox")) {
            driver = new RemoteWebDriver(new URL(GRID_URL), DesiredCapabilities.firefox());
        }  else {
            throw new RuntimeException("Incorrect browser name");
        }

        return driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriver open(String browser) throws IOException {
       switch (RUN_METHOD) {
            case 1:
                createLocalDriver(browser);
                break;
            case 2:
                createCloudDriver(browser);
                break;
            case 3:
                createGridDriver(browser);
                break;
            default:
                throw new RuntimeException("Incorrect run method");
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(DESTINATION_URL);
        return driver;
    }

    public void close() {
        getDriver().quit();
    }

    public String getInfo() {
        cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        String platform = cap.getPlatform().toString();
        String browserVersion = cap.getVersion().toString();
        date = new Date();

        return "Browser: " + browserName + "\nBrowser version: " + browserVersion + "\nPlatform: " + platform
                + "\nDate: " + dateFormat.format(date);
    }
}
