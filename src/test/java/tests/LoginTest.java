package tests;

import Utilities.MyRetry;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobjects.LoginPage;

import java.time.Duration;

public class LoginTest extends BaseClass {

    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);
    WebDriver driver;
    LoginPage loginPage;

    @BeforeClass()
    @Parameters("browser")
    public void setup(String browser) {

        driver = getDriver(browser);
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test(priority=1, retryAnalyzer = MyRetry.class)
    public void loginPageLogoTest() {
        loginPage = new LoginPage(driver);
        Assert.assertEquals(loginPage.isLogoDisplayed(), true);
    }

    @Test(priority=2, groups = {"functional"})
    public void loginPageUrlTest(){
        Assert.assertEquals(loginPage.getCurrentUrl(),"https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test(priority=3, dependsOnMethods = {"loginPageUrlTest"})
    public void loginPageTitleTest() {

        loginPage.setUsername("Admin");
        loginPage.setPassword("admin123");
        loginPage.clickLogin();
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
    }

    @AfterClass
    public void teardown() {
        teardown(driver);
    }

}
