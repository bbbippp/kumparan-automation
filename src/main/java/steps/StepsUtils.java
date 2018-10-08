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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Ian Gumilang
 */
public class StepsUtils
{
    private WebDriver driver;
    private String googleUsername;
    private String googlePassword;
    private String fbUsername;
    private String fbPassword;

    public WebDriver getDriver()
    {
        return driver;
    }

    public void setDriver(WebDriver driver)
    {
        this.driver = driver;
    }

    public String getGoogleUsername()
    {
        return googleUsername;
    }

    public void setGoogleUsername(String googleUsername)
    {
        this.googleUsername = googleUsername;
    }

    public String getGooglePassword()
    {
        return googlePassword;
    }

    public void setGooglePassword(String googlePassword)
    {
        this.googlePassword = googlePassword;
    }

    public String getFbUsername()
    {
        return fbUsername;
    }

    public void setFbUsername(String fbUsername)
    {
        this.fbUsername = fbUsername;
    }

    public String getFbPassword()
    {
        return fbPassword;
    }

    public void setFbPassword(String fbPassword)
    {
        this.fbPassword = fbPassword;
    }

    public void getPropertiesValue(){

        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "config.properties";
            input = StepsUtils.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            prop.load(input);

            this.setGoogleUsername(prop.getProperty("googleusername"));
            this.setGooglePassword(prop.getProperty("googlepassword"));
            this.setFbUsername(prop.getProperty("fbusername"));
            this.setFbPassword(prop.getProperty("fbpassword"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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

    public void refreshCurrentPage()
    {
        driver.navigate().refresh();
    }
}
