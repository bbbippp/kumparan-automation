package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

/**
 * @author Ian Gumilang
 */
public class StepsUtils
{
    private WebDriver driver;

    public WebDriver getDriver()
    {
        return driver;
    }

    public void setDriver(WebDriver driver)
    {
        this.driver = driver;
    }

    public WebElement findElement(final By locator)
    {
        return findElement(locator,10);
    }

    public WebElement findElement(final By locator, long timeoutInSeconds)
    {
        WebElement we =  (new WebDriverWait(driver, timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));

        return we;
    }

    public void click(final By locator)
    {
        findElement(locator, 10).click();
    }

    public void click(final By locator, long timeoutInSeconds)
    {
        findElement(locator, timeoutInSeconds).click();
    }

    public void sendKeys(final By locator, CharSequence... keysToSend)
    {
        findElement(locator, 10).sendKeys(keysToSend);
    }

    public void sendKeys(final By locator, long timeoutInSeconds, CharSequence... keysToSend)
    {
        findElement(locator, timeoutInSeconds).sendKeys(keysToSend);
    }

    public void waitUntilElementIsNotVisible(final By locator)
    {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("onesignal-popover-cancel-button")));
    }
}
