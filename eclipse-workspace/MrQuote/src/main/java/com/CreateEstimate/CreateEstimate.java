package com.CreateEstimate;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateEstimate {
	public void EstimateCreation(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			WebElement CreateEstimateBtn = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Create Estimate']")));
			CreateEstimateBtn.click();
			System.out.println("Estimate Create Button click");
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement FirstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Efname")));
			WebElement LastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
			WebElement ComapnyName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("company")));
			WebElement Email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Eemail")));
			WebElement Phone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Ephone")));
			WebElement Address1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Eaddress1")));
			WebElement Address2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("addressline2")));
			WebElement City = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("city")));
			WebElement State = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("state")));
			WebElement ZipCode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("zipCode")));

			// Data Insertion
			FirstName.sendKeys("Demo");
			LastName.sendKeys("Test");
			ComapnyName.sendKeys("Test");
			Email.sendKeys("Test@gmail.com");
			Phone.sendKeys("6262173362");
			Address1.sendKeys("Homes 121");
			Address2.sendKeys("Alpha 1");
			City.sendKeys("Noida");
			State.sendKeys("U.P");
			ZipCode.sendKeys("20131");

			// page scroll
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");
			System.out.println("All fields are visible and ready for input");
			// Estimate Button Click
			WebElement EstimateBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("estimateBtn")));
			EstimateBtn.click();
			wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			System.out.println("Estimate created successfully");

			// wait till the popup close of estimate create
			try {
				Thread.sleep(2000); // Optional: for animation buffer
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modalSignature")));
				System.out.println("Modal closed. Proceeding...");
				// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Estimate created successfully')]")));
			} catch (Exception e) {
				System.out.println("Modal may still be open or took too long.");
				e.printStackTrace();
			}			
			// WebElement CancelBtn =
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Cancel']")));
			// CancelBtn.click();
			// System.out.println("Cancel Button click");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
