package com.Layout;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LayoutCreation {
    WebDriver driver;

    public LayoutCreation(WebDriver driver) {
        this.driver = driver;
    }

    // Method to create a layout
    public void createLayout() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Click "Create Layout" button

            WebElement layoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Create Layout')]")));
            layoutButton.click();
            System.out.println("Create Layout button clicked.");

            // Step 2: Enter Layout Name
            WebElement layoutNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("layoutName")));
            layoutNameInput.clear();
            layoutNameInput.sendKeys("Layout Test - 27/06/2025");
            System.out.println("Layout name entered: " + layoutNameInput.getAttribute("value"));

            // Step 3: Click Save
            WebElement saveButton = driver.findElement(By.id("DisableSave"));
            saveButton.click();
            System.out.println("Save button clicked.");

           /* 
            // Step 5: Click Close button
            WebElement closeButton = driver.findElement(
                By.xpath("//button[@data-bs-dismiss='modal' and contains(text(), 'Close')]")
            );
            closeButton.click();
            System.out.println("Modal closed after layout creation.");
            */

        } catch (Exception e) {
            System.out.println("Error occurred while creating layout: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
