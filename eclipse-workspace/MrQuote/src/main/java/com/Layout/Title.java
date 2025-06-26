package com.Layout;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Title {
    WebDriver driver;
    WebDriverWait wait;

    public Title(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method to fill out and submit the title section
    public void TitlePage() {
        try {
            // Use correct locator for QuoteName (Assuming it's an <input>)
            WebElement QuoteName = driver.findElement(By.id("quotetype")); // Updated from tagName to id
            WebElement Date = driver.findElement(By.id("date"));
            WebElement PrimaryImage = driver.findElement(By.id("file-input"));
            WebElement SecondaryImage = driver.findElement(By.id("certification-logo-input"));
            WebElement FirstName = driver.findElement(By.id("FirstName"));
            WebElement LastName = driver.findElement(By.id("LastName"));
            WebElement Email = driver.findElement(By.id("add-user-email1"));
            WebElement Phone = driver.findElement(By.id("add-user-contact1"));
            WebElement Company = driver.findElement(By.id("CompanyName"));
            WebElement Address = driver.findElement(By.id("Address"));
            WebElement City = driver.findElement(By.id("City"));
            WebElement State = driver.findElement(By.id("State"));
            WebElement ZipCode = driver.findElement(By.id("ZipCode"));
            WebElement SaveButton = driver.findElement(By.id("saveBtnForEdit"));

            // Fill values
            QuoteName.clear();
            QuoteName.sendKeys("New Quote");
            Date.sendKeys("06-25-2025");

            PrimaryImage.sendKeys("C:\\Users\\JanmejaySingh\\Downloads\\primary.jpg");
            SecondaryImage.sendKeys("C:\\Users\\JanmejaySingh\\Downloads\\secondary.jpg");

            FirstName.sendKeys("Janmejay");
            LastName.sendKeys("Singh");
            Email.sendKeys("janmejay@gnxtsystems.com");
            Phone.sendKeys("6262173362");
            Company.sendKeys("GNXT Systems");
            Address.sendKeys("Homes 121");
            City.sendKeys("Noida");
            State.sendKeys("U.P");
            ZipCode.sendKeys("20131");

            // Scroll and click Save
         // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", SaveButton);

            // ⏳ Wait for any toast/modal/loader to disappear before clicking
            try {
            	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("notyf__message")));
            } catch (Exception e) {
            	System.out.println("No toast found or already gone.");
            }

            try {
            	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modalSignature")));
            } catch (Exception e) {
            	System.out.println("No modal found or already gone.");
            }

            try {
            	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));
            } catch (Exception e) {
            	System.out.println("Loader not present.");
            }

            // ✅ JavaScript click to avoid intercept
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", SaveButton);
            System.out.println("Clicked Save using JavaScriptExecutor.");


            System.out.println("Quote form submitted and page loaded.");
        } catch (Exception e) {
            System.out.println("Error occurred while filling the page title form: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
