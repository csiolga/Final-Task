package testng;

import driver.Driver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

public class GmailListener implements ITestListener {

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] captureScreenshot() {
        return ((TakesScreenshot) Driver.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Environment", type = "plain/text")
    private String getEnvironmentInfo() {
        return Driver.getInstance().getInfo();
    }

    public void onTestStart(ITestResult iTestResult) {

    }

    public void onTestSuccess(ITestResult iTestResult) {

    }

    public void onTestFailure(ITestResult iTestResultr) {
        captureScreenshot();
        getEnvironmentInfo();
    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }
}
