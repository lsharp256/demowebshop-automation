package com.demo.qa.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup() throws IOException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Create a unique user data directory for each test session
        Path tempDir = Files.createTempDirectory("webdriver-user-data");
        options.addArguments("user-data-dir=" + tempDir.toAbsolutePath().toString());

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://demowebshop.tricentis.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}