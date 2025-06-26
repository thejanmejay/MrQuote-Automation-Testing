package com.Layout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Introduction {
	WebDriver driver;
	WebDriverWait wait;

	public Introduction(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	public void navigateToSectionByName(String sectionName) {
		try {
			String xpath = "//a[.//span[normalize-space()='" + sectionName + "']]";
			WebElement sectionLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", sectionLink);
			System.out.println("Redirected to " + sectionName + " page.");
		} catch (Exception e) {
			System.out.println("Section link for '" + sectionName + "' not found.");
			e.printStackTrace();
		}
	}

	// Method to introduce the layout creation process and edit Quill editor content
	public void Intro() {
		try {
			navigateToSectionByName("Introduction");

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("snow-editor")));

			WebElement quillEditor = driver.findElement(By.cssSelector(".ql-editor"));
			String content = "<p> Hello <strong>Customer</strong>, welcome to <em>Mr Quote</em>.</p> </br> <p>We are excited to assist you with your needs.</p> </br> <p>Best regards,</p> <p><strong>Your Company Name</strong></p>";
			((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = arguments[1];", quillEditor, content);
			System.out.println("Editor content updated via JavaScript.");

			String[] tokens = { "Account Name", "Customer First Name", "Customer Last Name", "Customer Full Name",
					"Customer Address" };

			for (String token : tokens) {
				try {
					// Reopen dropdown before each token
					WebElement insertTokenBtn = wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Insert token')]")));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", insertTokenBtn);
					insertTokenBtn.click();
					System.out.println("Insert token dropdown opened for: " + token);

					// Wait briefly for dropdown to fully open
					Thread.sleep(500); // Required after re-clicking dropdown

					wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.dropdown-menu.show")));

					// Ensure any loading or notification is gone
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("notyf__message")));

					// Build and wait for token element
					String tokenXpath = String.format(
							"//ul[contains(@class,'dropdown-menu') and contains(@class,'show')]//a[@data-token='%s']",
							token);
					WebElement tokenElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tokenXpath)));

					// Click via Actions
					new Actions(driver).moveToElement(tokenElement).pause(200).click().perform();
					System.out.println("Token '" + token + "' inserted.");

					Thread.sleep(500); // small delay between tokens

				} catch (Exception tokenEx) {
					System.out.println("⚠️ Failed to insert token: " + token + " | " + tokenEx.getMessage());
				}
			}

			// Save Introduction
			WebElement saveButton = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id='LayoutIntroduction']/div[2]/div/div[2]/div/button")));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);
			saveButton.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("notyf__message")));
			System.out.println("✅ Introduction saved successfully.");

		} catch (Exception e) {
			System.out.println("❌ Error occurred during introduction: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
