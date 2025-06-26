package com.Dashboard;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Dashbaord {
	WebDriver driver;

	public Dashbaord(WebDriver driver) {
		this.driver = driver;
	}

	public void openThemeDropdown() {
	try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// 🔄 Wait for modal to go if it appears
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modalSignature")));
			System.out.println("Modal closed before opening theme dropdown.");
		} catch (Exception e) {
			System.out.println("Modal might not be present or already closed.");
		}

		// ✅ Wait until toast disappears completely
		// Repeat check for 5 seconds max, polling every 500ms
		for (int i = 0; i < 10; i++) {
			if (driver.findElements(By.className("notyf__message")).isEmpty()) {
				break;
			}
			Thread.sleep(500);
		}

		// 🌗 Now click theme dropdown
		WebElement themeBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//li[contains(@class,'dropdown-style-switcher')]//a[contains(@class,'dropdown-toggle')]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", themeBtn); // safer than .click()
		System.out.println("Opened theme dropdown.");

	} catch (Exception e) {
		System.out.println("❌ Failed to open theme dropdown.");
		e.printStackTrace();
	}
}

	public void selectTheme(String theme) {
	for (int attempt = 1; attempt <= 2; attempt++) {
		try {
			openThemeDropdown();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			By themeLocator = By.xpath("//a[@class='dropdown-item waves-effect' and @data-theme='" + theme + "']");
			WebElement themeOption = wait.until(ExpectedConditions.elementToBeClickable(themeLocator));
			themeOption.click();

			System.out.println("Selected theme: " + theme);
			return;
		} catch (Exception e) {
			System.out.println("Attempt " + attempt + " failed to select theme: " + theme);
			if (attempt == 2) {
				e.printStackTrace();
			}
		}
	}
}



	// Convenience methods if needed
	public void selectDarkTheme() {
		selectTheme("dark");
	}

	public void selectSystemTheme() {
		selectTheme("system");
	}

	public void selectLightTheme() {
		selectTheme("light");
	}

}
