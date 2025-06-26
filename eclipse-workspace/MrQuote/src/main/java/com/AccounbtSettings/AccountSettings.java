package com.AccounbtSettings;

import org.openqa.selenium.WebDriver;

public class AccountSettings {
WebDriver driver;

	public AccountSettings(WebDriver driver) {
		this.driver = driver;
	}

	// Method to navigate to Account Settings
	public void navigateToAccountSettings() {
		try {
			System.out.println("Navigated to Account Settings.");
		} catch (Exception e) {
			System.out.println("Error occurred while navigating to Account Settings: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
