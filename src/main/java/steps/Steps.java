package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
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
public class Steps
{
    private WebDriver driver;

    @Before
    public void establishedChromeDriver() {
        File app = new File("chromedriver");

        System.setProperty("webdriver.chrome.driver", app.getAbsolutePath());

        driver= new ChromeDriver();
        driver.manage().window().maximize();
    }

    @When("^User navigate to kumparan website on web browser$")
    public void userNavigateToKumparanWebsiteOnWebBrowser() throws Throwable
    {
        driver.get("https://kumparan.com");
    }

    @When("^User click Login with Google button$")
    public void userClickLoginWithGoogleButton() throws Throwable
    {
        click(By.id("onesignal-popover-cancel-button"));
        waitUntilElementIsNotVisible(By.id("onesignal-popover-cancel-button"));
        click(By.xpath("//a[contains(@href,'login')]"));

        // Store the current window handle
        String winHandleBefore = driver.getWindowHandle();

        // Perform the click operation that opens new window
        click(By.xpath("//button[contains(@class,'btn-fb')]"));

        // Switch to new window opened
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // Perform the actions on new window
        sendKeys(By.name("email"),"rama.jackiee@gmail.com");
        sendKeys(By.name("pass"),"new280");
        click(By.name("login"));

        // Switch back to original browser (first window)
        driver.switchTo().window(winHandleBefore);

        // Continue with original browser (first window)
        findElement(By.xpath("//*[contains(text(), 'Create Story')]"),60).isDisplayed();
    }

    @After
    public void killDriver()
    {
        driver.close();
    }

    private WebElement findElement(final By locator)
    {
        return findElement(locator,10);
    }

    private WebElement findElement(final By locator, long timeoutInSeconds)
    {
        WebElement we =  (new WebDriverWait(driver, timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));

        return we;
    }

    private void click(final By locator)
    {
        findElement(locator, 10).click();
    }

    private void click(final By locator, long timeoutInSeconds)
    {
        findElement(locator, timeoutInSeconds).click();
    }

    private void sendKeys(final By locator, CharSequence... keysToSend)
    {
        findElement(locator, 10).sendKeys(keysToSend);
    }

    private void sendKeys(final By locator, long timeoutInSeconds, CharSequence... keysToSend)
    {
        findElement(locator, timeoutInSeconds).sendKeys(keysToSend);
    }

    private void waitUntilElementIsNotVisible(final By locator)
    {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("onesignal-popover-cancel-button")));
    }
}
