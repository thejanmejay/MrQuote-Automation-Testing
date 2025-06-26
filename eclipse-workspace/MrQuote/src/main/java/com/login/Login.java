package com.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login {

    public void performLogin(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginButton")));
            loginBtn.click();
            WebElement loginInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("EmailOrMobile")));
            loginInput.sendKeys("jay@gmail.com");
            System.out.println("Username or email inserted.");

            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordInput")));
            passwordInput.sendKeys("Test@123");

            driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div/div/div/div[1]/form/div[4]/button")).click();
            try {
                WebElement ForgetMessage = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.name("Invalid credential.")));
                WebElement ForgetBtn = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Forgot Password?']")));
                WebElement EmailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
                WebElement SendEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Send Reset Link']")));

                if (ForgetMessage.isDisplayed()) {
                    ForgetBtn.click();
                    EmailInput.sendKeys("jay@gmail.com");
                    SendEmail.click();
                } else {
                    System.out.println("User redirected to the Dashboard.");
                }
            } catch (Exception e) {
                System.out.println("user is on the: " + driver.getCurrentUrl());
                System.out.println("Login successful, user redirected to dashboard.");
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
