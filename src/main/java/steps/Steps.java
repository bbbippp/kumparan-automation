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
public class Steps extends StepsUtils
{
    @Before
    public void establishedChromeDriver() {
        File app = new File("chromedriver");

        System.setProperty("webdriver.chrome.driver", app.getAbsolutePath());

        setDriver(new ChromeDriver());
        getDriver().manage().window().maximize();
    }

    @After
    public void killDriver()
    {
        getDriver().close();
    }

    @When("^User navigate to kumparan website on web browser$")
    public void userNavigateToKumparanWebsiteOnWebBrowser() throws Throwable
    {
        getDriver().get("https://kumparan.com");
    }

    @When("^User click Login with facebook button$")
    public void userClickLoginWithfacebookButton() throws Throwable
    {
        click(By.id("onesignal-popover-cancel-button"));
        waitUntilElementIsNotVisible(By.id("onesignal-popover-cancel-button"));
        click(By.xpath("//a[contains(@href,'login')]"));

        // Store the current window handle
        String winHandleBefore = getDriver().getWindowHandle();

        // Perform the click operation that opens new window
        click(By.xpath("//button[contains(@class,'btn-fb')]"));

        // Switch to new window opened
        for(String winHandle : getDriver().getWindowHandles()){
            getDriver().switchTo().window(winHandle);
        }

        // Perform the actions on new window
        sendKeys(By.name("email"),"rama.jackiee@gmail.com");
        sendKeys(By.name("pass"),"new280");
        click(By.name("login"));

        // Switch back to original browser (first window)
        getDriver().switchTo().window(winHandleBefore);

        // Continue with original browser (first window)
        findElement(By.xpath("//*[contains(text(), 'Create Story')]"),60).isDisplayed();
    }

    @When("^User click Login with google button$")
    public void userClickLoginWithgoogleButton() throws Throwable
    {
        click(By.id("onesignal-popover-cancel-button"));
        waitUntilElementIsNotVisible(By.id("onesignal-popover-cancel-button"));
        click(By.xpath("//a[contains(@href,'login')]"));

        // Store the current window handle
        String winHandleBefore = getDriver().getWindowHandle();

        // Perform the click operation that opens new window
        click(By.xpath("//button[contains(@class,'btn-gplus')]"));

        // Switch to new window opened
        for(String winHandle : getDriver().getWindowHandles()){
            getDriver().switchTo().window(winHandle);
        }

        // Perform the actions on new window
        sendKeys(By.id("identifierId"),"rama.jackiee@gmail.com");
        click(By.xpath("//span[contains(text(), 'Berikutnya')]"));
        sendKeys(By.xpath("//input[contains(@type, 'password')]"),"new280sunk677");
        click(By.xpath("//span[contains(text(), 'Berikutnya')]"));

        // Switch back to original browser (first window)
        getDriver().switchTo().window(winHandleBefore);

        // Continue with original browser (first window)
        findElement(By.xpath("//*[contains(text(), 'Create Story')]"),60).isDisplayed();
    }
}
