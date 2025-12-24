package tests;

import Utilities.MyRetry;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobjects.LoginPage;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeClass()
    @Parameters("browser")
    public void setup(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless=new");
            driver = new ChromeDriver(chromeOptions);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Invalid browser parameter");
        }
        loginPage = new LoginPage(driver);
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test(priority = 1, retryAnalyzer = MyRetry.class)
    public void loginPageLogoTest() {
        Assert.assertEquals(loginPage.isLogoDisplayed(), true);
    }

    @Test(priority = 2)
    public void loginPageUrlTest() {
        Assert.assertEquals(loginPage.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test(priority = 3, dependsOnMethods = {"loginPageUrlTest"})
    public void loginPageTitleTest() {

        loginPage.setUsername("Admin");
        loginPage.setPassword("admin123");
        loginPage.clickLogin();
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }

}
