package com.Dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SidebarToggle {
    WebDriver driver;

    public SidebarToggle(WebDriver driver) {
        this.driver = driver;
    }

    // Step 1: Click the sidebar toggle button to close
    public void toggleSidebarOff() {
        try {
            WebElement toggleBtn = driver.findElement(
                By.xpath("//a[contains(@class, 'layout-menu-toggle') and contains(@class, 'menu-link')]")
            );
            toggleBtn.click();
            System.out.println("Sidebar closed by toggle button.");
        } catch (Exception e) {
            System.out.println("Failed to toggle sidebar OFF.");
            e.printStackTrace();
        }
    }

    // Step 2: Hover over the logo to show sidebar temporarily
    public void hoverOnSidebarLogo() {
        try {
            WebElement logo = driver.findElement(By.xpath("//img[@src='/img/logo/drop.png']"));
            Actions actions = new Actions(driver);
            actions.moveToElement(logo).perform();
            System.out.println("Hovered over logo to open sidebar temporarily.");
        } catch (Exception e) {
            System.out.println("Failed to hover over sidebar logo.");
            e.printStackTrace();
        }
    }

    // Step 3: Click toggle again to open permanently
    public void toggleSidebarOn() {
        try {
            WebElement toggleBtn = driver.findElement(
                By.xpath("//a[contains(@class, 'layout-menu-toggle') and contains(@class, 'menu-link')]")
            );
            toggleBtn.click();
            System.out.println("Sidebar opened permanently by toggle button.");
        } catch (Exception e) {
            System.out.println("Failed to toggle sidebar ON.");
            e.printStackTrace();
        }
    }

    // Complete flow: OFF → Hover → ON
    public void executeSidebarToggleFlow() {
        try {
            toggleSidebarOff();
            hoverOnSidebarLogo();
            Thread.sleep(1000); // Wait to simulate user pause on hover
            toggleSidebarOn();
            System.out.println("Sidebar toggle flow executed successfully.");
        } catch (Exception e) {
            System.out.println("Error during sidebar toggle flow.");
            e.printStackTrace();
        }
    }
}
