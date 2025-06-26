package com.Dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class EstimatorFilter {
    WebDriver driver;

    public EstimatorFilter(WebDriver driver) {
        this.driver = driver;
    }

    // Apply a single estimator filter
    public void applySingleFilter(String estimatorName) {
        try {
            WebElement dropdown = driver.findElement(By.id("estimatorFilter"));
            Select select = new Select(dropdown);
            select.selectByVisibleText(estimatorName);
            System.out.println("Applied estimator filter: " + estimatorName);
        } catch (Exception e) {
            System.out.println("Failed to apply filter: " + estimatorName);
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
            System.out.println("Failed to click Clear All button.");
            e.printStackTrace();
        }
    }

    // Apply multiple estimator filters sequentially
    public void applyMultipleEstimators() {
        String[] estimators = { "Jay Singh", "Janmejay Singh"};

        for (String estimator : estimators) {
            applySingleFilter(estimator);
            clickClearAll();
        }
    }
}
