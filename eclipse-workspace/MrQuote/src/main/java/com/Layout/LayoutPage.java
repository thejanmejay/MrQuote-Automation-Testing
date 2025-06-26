package com.Layout;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LayoutPage {
	WebDriver driver;
	WebDriverWait wait;

	public LayoutPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	}

	// Method to navigate to a page by name
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

	// Method to navigate to Layout Page
	public void navigateToLayoutPage() {
		try {
			navigateToSectionByName("Layout Map");

			// Find the container that holds the dynamic ID
			WebElement cardBody = driver
					.findElement(By.xpath("//div[@class='card-body' and .//label[text()='Description']]"));

			// Extract the dynamic ID from editor or textarea
			WebElement textarea = cardBody.findElement(By.cssSelector("textarea.hidden-input"));
			String dynamicId = textarea.getAttribute("id").replace("hidden-input-", "");

			// Now build editor ID and locate elements dynamically
			WebElement quillEditor = driver.findElement(By.cssSelector("#snow-editor-" + dynamicId + " .ql-editor"));

			String content = "<p>This layout shows the <strong>inspection zones</strong>.</p>";

			// Update Quill editor
			((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = arguments[1];", quillEditor, content);
			System.out.println("Editor content updated.");

			// Update hidden textarea with plain text (for form submission)
			((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", textarea,
					"This layout shows the inspection zones.");
			System.out.println("Textarea value synced.");

			// Click Save button if available
			WebElement saveButton = driver.findElement(By.xpath("//button[contains(text(),'Save')]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);
			saveButton.click();
			System.out.println("Layout page saved successfully.");

		} catch (Exception e) {
			System.out.println("Error occurred while updating Layout page: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
