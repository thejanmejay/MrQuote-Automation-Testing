package com.Dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class StatusFilter {
    WebDriver driver;

    public StatusFilter(WebDriver driver) {
        this.driver = driver;
    }

    // Applies a single filter status
    public void applySingleFilter(String status) {
        try {
            WebElement dropdown = driver.findElement(By.id("statusFilter"));
            Select select = new Select(dropdown);
            select.selectByVisibleText(status);
            System.out.println("Applied status filter: " + status);
        } catch (Exception e) {
            System.out.println("Failed to apply filter: " + status);
            e.printStackTrace();
        }
    }

    // Clicks the Clear All button
    public void clickClearAll() {
        try {
            WebElement clearBtn = driver.findElement(By.id("clearAllBtn")); // Assuming it's an ID
            clearBtn.click();
            System.out.println("Clicked Clear All.");
        } catch (Exception e) {
            System.out.println("Failed to click Clear All button.");
            e.printStackTrace();
        }
    }

    // Applies and clears filters in sequence
    public void applyAllStatuses() {
        String[] statuses = {
            "All Status", "Requested", "Assigned",
            "Completed", "Draft", "Provided", "Approved",
            "Revised", "Declined", "Expired", "Cancelled"
        };

        for (String status : statuses) {
            applySingleFilter(status);
            clickClearAll();
        }
    }
}
