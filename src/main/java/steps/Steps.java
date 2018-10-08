package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

/**
 * @author Ian Gumilang
 */
public class Steps extends StepsUtils
{
    private String expectedStoryTitle;
    private String expectedComment;
    private String winHandleBefore;

    @Before
    public void establishedChromeDriverAndGetPropertiesValue() {
        getPropertiesValue();

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

    @When("^User navigate to https://kumparan.com/ on web browser$")
    public void userNavigateToKumparanWebsiteOnWebBrowser()
    {
        getDriver().get("https://kumparan.com");
    }

    @When("^User click Login with Facebook button$")
    public void userClickLoginWithFacebookButton()
    {
        click(By.id("onesignal-popover-cancel-button"));
        waitUntilElementIsNotVisible(By.id("onesignal-popover-cancel-button"));
        click(By.xpath("//a[contains(@href,'login')]"));

        winHandleBefore = getDriver().getWindowHandle();

        // Perform the click operation that opens new window
        click(By.xpath("//button[contains(@class,'btn-fb')]"));
    }

    @When("^User filled facebook credentials to login$")
    public void userFilledFacebookCredentialsToLogin()
    {
        // Switch to new window opened
        for(String winHandle : getDriver().getWindowHandles()){
            getDriver().switchTo().window(winHandle);
        }

        // Perform the actions on new window
        sendKeys(By.name("email"), getFbUsername());
        sendKeys(By.name("pass"), getFbPassword());
        click(By.name("login"));

        // Switch back to original browser (first window)
        getDriver().switchTo().window(winHandleBefore);
    }

    @When("^User click Login with facebook button$")
    public void userLoginWithFacebook()
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
        sendKeys(By.name("email"),getFbUsername());
        sendKeys(By.name("pass"),getFbPassword());
        click(By.name("login"));

        // Switch back to original browser (first window)
        getDriver().switchTo().window(winHandleBefore);

        // Continue with original browser (first window)
        findElement(By.xpath("//*[contains(text(), 'Create Story')]"),60).isDisplayed();
    }

    @When("User click Login with Google button")
    public void userClickLoginWithGoogleButton()
    {
        click(By.id("onesignal-popover-cancel-button"));
        waitUntilElementIsNotVisible(By.id("onesignal-popover-cancel-button"));
        click(By.xpath("//a[contains(@href,'login')]"));

        // Store the current window handle
        winHandleBefore = getDriver().getWindowHandle();

        // Perform the click operation that opens new window
        click(By.xpath("//button[contains(@class,'btn-gplus')]"));
    }

    @When("User filled google credentials to login")
    public void userFilledGoogleCredentialsToLogin()
    {
        // Switch to new window opened
        for(String winHandle : getDriver().getWindowHandles()){
            getDriver().switchTo().window(winHandle);
        }

        // Perform the actions on new window
        sendKeys(By.id("identifierId"), getGoogleUsername());
        click(By.xpath("//span[contains(text(), 'Berikutnya')]"));
        sendKeys(By.xpath("//input[contains(@type, 'password')]"), getGooglePassword());
        click(By.xpath("//span[contains(text(), 'Berikutnya')]"));

        // Switch back to original browser (first window)
        getDriver().switchTo().window(winHandleBefore);
    }

    @Then("User logged in successfully to kumparan")
    public void userLoggedInSuccessfullyToKumparan()
    {
        // assert user is logged in
        findElement(By.xpath("//*[contains(text(), 'Create Story')]"),60).isDisplayed();
    }

//    @When("^User click Login with google button$")
//    public void userClickLoginWithgoogleButton()
//    {
//        click(By.id("onesignal-popover-cancel-button"));
//        waitUntilElementIsNotVisible(By.id("onesignal-popover-cancel-button"));
//        click(By.xpath("//a[contains(@href,'login')]"));
//
//        // Store the current window handle
//        String winHandleBefore = getDriver().getWindowHandle();
//
//        // Perform the click operation that opens new window
//        click(By.xpath("//button[contains(@class,'btn-gplus')]"));
//
//        // Switch to new window opened
//        for(String winHandle : getDriver().getWindowHandles()){
//            getDriver().switchTo().window(winHandle);
//        }
//
//        // Perform the actions on new window
//        sendKeys(By.id("identifierId"),GOOGLEUSERNAME);
//        click(By.xpath("//span[contains(text(), 'Berikutnya')]"));
//        sendKeys(By.xpath("//input[contains(@type, 'password')]"), GOOGLEPASSWORD);
//        click(By.xpath("//span[contains(text(), 'Berikutnya')]"));
//
//        // Switch back to original browser (first window)
//        getDriver().switchTo().window(winHandleBefore);
//
//        // Continue with original browser (first window)
//        findElement(By.xpath("//*[contains(text(), 'Create Story')]"),60).isDisplayed();
//    }

    @When("^User can see list of Trending Stories on the left side of the page$")
    public void userCanSeeListOfTrendingStoriesOnTheLeftSideOfThePage()
    {
        findElement(By.xpath("//*[contains(text(),'TRENDING STORIES')]")).isDisplayed();
    }

    @When("^User click one of the Trending Stories$")
    public void userClickOneOfTheTrendingStories()
    {
        List<WebElement> storiesWe =  getDriver().findElements(By.xpath("//div[contains(@class,'sidebar-stick')]//div[@class='media']//div[@class='media']"));

        // get first story title
        expectedStoryTitle = storiesWe.get(0).getText();

        // click first story
        storiesWe.get(0).click();
    }

    @When("^User taken to the stories page that user clicked before$")
    public void userTakenToTheStoriesPageThatuserClickedBefore()
    {
        // assert that the title is the title we clicked before
        String actualStoryTitle = findElement(By.xpath("//h1[contains(@class, 'marg-ver-none')]")).getText();
        Assert.assertThat("Title do not match", expectedStoryTitle, Matchers.containsString(actualStoryTitle));

        String story = findElement(By.xpath("//div[@data-contents='true']")).getText();
        Assert.assertThat(story, not(isEmptyString()));
    }

    @When("^User fill the comments field and click Post button$")
    public void userFillTheCommentsFieldAndClickPostButton() throws Throwable
    {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);

        // i added datetime so the comment will be unique each time i run the test
        expectedComment = "What a cool story! " + datetime;
        sendKeys(By.id("newCommentTextArea"),expectedComment);

        Thread.sleep(2000);
        click(By.xpath("//span[contains(text(), 'Post')]"));
        Thread.sleep(4000);
    }

    @When("^User refresh the page$")
    public void userRefreshCurrentPage() throws Throwable
    {
        refreshCurrentPage();
        Thread.sleep(4000);
    }

    @Then("User can see comment is posted")
    public void userCanSeeCommentIsPosted()
    {
        findElement(By.xpath(String.format("//*[contains(text(), '%s')]",expectedComment))).isDisplayed();
    }

    @When("^User see trending stories$")
    public void userSeeTrendingStories()
    {
        findElement(By.xpath("//*[contains(text(),'TRENDING STORIES')]")).isDisplayed();

        List<WebElement> storiesWe =  getDriver().findElements(By.xpath("//div[contains(@class,'sidebar-stick')]//div[@class='media']//div[@class='media']"));

        expectedStoryTitle = storiesWe.get(0).getText();

        // click first story
        storiesWe.get(0).click();

        // assert redirected to stories clicked
        String actualStoryTitle = findElement(By.xpath("//h1[contains(@class, 'marg-ver-none')]")).getText();
        Assert.assertThat("Title do not match", expectedStoryTitle, Matchers.containsString(actualStoryTitle));

        String story = findElement(By.xpath("//div[@data-contents='true']")).getText();
        Assert.assertThat(story, not(isEmptyString()));
    }

    @When("^User post comment on story$")
    public void userPostCommentOnStory() throws Throwable
    {
        userSeeTrendingStories();

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);

        // i added datetime so the comment will be unique each time i run the test
        expectedComment = "What a cool story! " + datetime;
        sendKeys(By.id("newCommentTextArea"),expectedComment);

        Thread.sleep(2000);
        click(By.xpath("//span[contains(text(), 'Post')]"));
        Thread.sleep(4000);
        refreshCurrentPage();
        Thread.sleep(4000);
        findElement(By.xpath(String.format("//*[contains(text(), '%s')]",expectedComment))).isDisplayed();
    }

    @Given("^User logged in to kumparan$")
    public void userLoggedInToKumparan() throws Throwable
    {
        userNavigateToKumparanWebsiteOnWebBrowser();
        userLoginWithFacebook();
    }
}
