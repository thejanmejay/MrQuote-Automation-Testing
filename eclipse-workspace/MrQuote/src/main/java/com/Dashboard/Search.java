package com.Dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Search {
WebDriver driver;

	public Search(WebDriver driver) {
		this.driver = driver;
	}

	public void performSearch() {
		try {
			// Locate the search input field
			WebElement searchInput = driver.findElement(By.id("searchInput"));
			searchInput.sendKeys("Estimate");
			System.out.println("Search query entered: ");			
			WebElement clearButton = driver.findElement(By.id("clearAllBtn"));
			clearButton.click();
			
			System.out.println("Search functionality checked.");
		} catch (Exception e) {
			System.out.println("Error occurred while performing search: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
