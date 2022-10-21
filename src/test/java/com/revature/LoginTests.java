package com.revature;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests {
    // AAA
    // Arrange: setup whatever is necessary for you to perform the actions
    // Act: perform the actions to elicit a result
    // Assert: compare the result (actual) with what you expect

    public WebDriver driver;

    @BeforeClass
    public void setupBeforeClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod // The typical purpose of a method annotated with @BeforeMethod is to set up whatever is required for running the test case
    public void setup() {
        /*
            Arrange
         */
        driver = new ChromeDriver();
    }

    @AfterMethod // The typical purpose of a method annotated with @AfterMethod is to clean up whatever resources we were using for
    // the test case that was just executed
    public void quitOutOfWebDriver() {
        // Cleanup
        driver.quit();
    }

    @Test(groups = { "group1" })
    public void testValidLogin() throws InterruptedException {
        /*
            Act
         */
        // 1. Go to the login page
        driver.get("http://127.0.0.1:5500/login.html");

        WebElement usernameInputElement = driver.findElement(By.id("username"));
        WebElement passwordInputElement = driver.findElement(By.id("password"));
        WebElement submitButtonElement = driver.findElement(By.xpath("//button[text()='submit']"));

        // 2. enter a valid username
        usernameInputElement.sendKeys("username");

        // 3. enter a valid password
        passwordInputElement.sendKeys("password");

        // 4. click on login button
        submitButtonElement.click();

        /*
            Assert
         */
        Thread.sleep(1000); // wait for 1 second before checking for a result (in case the result
        // takes some time)

        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals(actualUrl, "http://127.0.0.1:5500/homepage.html");
    }

    @Test(groups = { "group1" })
    public void testValidUsernameInvalidPassword() throws InterruptedException {
        /*
            Act
         */
        driver.get("http://127.0.0.1:5500/login.html");

        WebElement usernameInputElement = driver.findElement(By.id("username"));
        WebElement passwordInputElement = driver.findElement(By.id("password"));
        WebElement submitButtonElement = driver.findElement(By.xpath("//button[text()='submit']"));

        usernameInputElement.sendKeys("username");
        passwordInputElement.sendKeys("test123");
        submitButtonElement.click();

        /*
            Assert
         */
        Thread.sleep(1000);

        String actualMessage = driver.switchTo().alert().getText();

        Assert.assertEquals(actualMessage, "invalid login");
    }

}
