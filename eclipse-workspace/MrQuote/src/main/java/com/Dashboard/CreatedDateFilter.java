package com.Dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CreatedDateFilter {
    WebDriver driver;

    public CreatedDateFilter(WebDriver driver) {
        this.driver = driver;
    }

    // Apply a single filter
    public void applySingleFilter(String filterOptionText) {
        try {
            WebElement dropdown = driver.findElement(By.id("createdDateFilter"));
            Select select = new Select(dropdown);
            select.selectByVisibleText(filterOptionText);
            System.out.println("Applied filter: " + filterOptionText);
        } catch (Exception e) {
            System.out.println("Failed to apply created date filter: " + filterOptionText);
            e.printStackTrace();
        }
    }

    // Click "Clear All" button
    public void clickClearAll() {
        try {
            WebElement clearBtn = driver.findElement(By.id("clearAllBtn"));
            clearBtn.click();
            System.out.println("Clicked Clear All.");
        } catch (Exception e) {
            System.out.println("Clear All button not found or failed to click.");
            e.printStackTrace();
        }
    }

    // Apply all filters one by one and clear after each
    public void applyAllDateFilters() {
        String[] filterOptions = {
            "Last 24 hours",
            "Last 7 days",
            "Last 30 days",
            "Older (than 30 days)"
        };

        for (String option : filterOptions) {
            applySingleFilter(option);
            clickClearAll();
        }
    }
}
