package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;

import java.time.Duration;
import java.util.Objects;

public class LoginPage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//*[@name='username']")
    WebElement txt_username;

    @FindBy(xpath = "//*[@name='password']")
    WebElement txt_password;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btn_login;

    @FindBy(xpath = "//img[@alt='company-branding']")
    WebElement logo;

//    By txt_username = By.xpath("//*[@name='username']");
//    By txt_password = By.xpath("//*[@name='password']");
//    By btn_login = By.xpath("//button[@type='submit']");

    public void setUsername(String username) {
        txt_username.sendKeys(username);
    }

    public void setPassword(String password) {
        txt_password.sendKeys(password);
    }

    public void clickLogin() {
        Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofMillis(1000))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
       fluentWait.until(ExpectedConditions.elementToBeClickable(btn_login)).click();

    }

    public boolean isLogoDisplayed() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
        webDriverWait.until(ExpectedConditions.visibilityOf(logo));
        return logo.isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
