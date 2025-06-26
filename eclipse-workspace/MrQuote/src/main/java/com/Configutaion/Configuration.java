package com.Configutaion;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Configuration {
    WebDriver driver;

    public Configuration(WebDriver driver) {
        this.driver = driver;
    }

    public void setupConfiguration() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Click on Configuration menu to expand
            WebElement configButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@class='menu-link menu-toggle']//div[text()='Configuration']")));
            configButton.click();
            System.out.println("Configuration button clicked.");

            // Step 2: Click on Account Settings menu item (the <a> tag, not the <div>)
            WebElement accountSettingBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/Layout/ReportLayout/ReportLayoutList']")));
            accountSettingBtn.click();
            System.out.println("Account Settings button clicked.");

        } catch (Exception e) {
            System.out.println("Error occurred while setting up configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
