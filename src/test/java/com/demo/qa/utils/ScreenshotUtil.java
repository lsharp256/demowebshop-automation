package com.demo.qa.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static void takeScreenshot(WebDriver driver, String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = "target/screenshots";
        String screenshotName = screenshotDir + "/" + testName + "_" + timestamp + ".png";

        try {
            Files.createDirectories(Paths.get(screenshotDir));
            Files.copy(src.toPath(), Paths.get(screenshotName));
            System.out.println("📸 Screenshot saved: " + screenshotName);
        } catch (IOException e) {
            System.out.println("❌ Failed to save screenshot: " + e.getMessage());
        }
    }
}
