package com.Layout;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Inspection {
    private WebDriver driver;
    private WebDriverWait wait;

    
    
    // Element Locators
    private static final By SECTION_CONTAINER = By.id("sections-container");
    private static final By DELETE_BUTTON = By.xpath("//button[@class='btn delete-item px-2']");
    private static final By DELETE_CONFIRM_BUTTON = By.id("confirmDelete");
    private static final By ADD_ITEM_BUTTON = By.xpath("//button[contains(@class,'add-item')]");
    private static final By INSPECTION_ITEMS = By.cssSelector("div.items-container.option1 > div.item");
    private static final By ITEMS_CONTAINER = By.cssSelector("div.items-container.option1");
    private static final By SAVE_BUTTON = By.id("saveBtnForEdit");
    private static final By SUCCESS_ALERT = By.xpath("//div[contains(@class,'alert-success')]");
    private static final By STYLE_CHANGE_LINK = By.xpath("//a[normalize-space()='Change']");
    private static final By RADIO_OPTION_2 = By.xpath("//input[@type='radio' and @id='option2']");
    private static final By STYLE_SAVE_BUTTON = By.id("saveButton");
    // Local image paths
    private static final String[] DEFAULT_IMAGES = {
        "C:\\Users\\JanmejaySingh\\Downloads\\10.jpg",
        "C:\\Users\\JanmejaySingh\\Downloads\\9.jpg",
        "C:\\Users\\JanmejaySingh\\Downloads\\8.jpg",
        "C:\\Users\\JanmejaySingh\\Downloads\\7.jpg",
        "C:\\Users\\JanmejaySingh\\Downloads\\6.jpg"
    };

    public Inspection(WebDriver driver) {
        this.driver = driver;
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
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
    private void verifyOnInspectionPage() {
        if (!driver.getCurrentUrl().toLowerCase().contains("editlayoutinspection")) {
            throw new RuntimeException("Not on inspection page. Current URL: " + driver.getCurrentUrl());
        }
    }


    public void performInspection() {
        try {
            navigateToInspection();
            verifyOnInspectionPage();
            deleteDefaultItem();
            uploadImagesWithDescriptions(DEFAULT_IMAGES);
            saveInspection();
            changeSectionStyle();
            System.out.println("✅ Inspection automation completed successfully.");
        } catch (Exception e) {
            System.out.println("❌ Error during inspection automation: " + e.getMessage());
            throw new RuntimeException("Inspection failed", e);
        }
    }

    private void navigateToInspection() {
        try {
            navigateToSectionByName("Inspection");

            // Updated to check correct URL fragment
            wait.until(d -> {
                String currentUrl = d.getCurrentUrl().toLowerCase();
                boolean onInspectionPage = currentUrl.contains("editlayoutinspection");
                boolean sectionVisible = d.findElements(SECTION_CONTAINER).size() > 0;

                return onInspectionPage && sectionVisible;
            });
            waitForPageLoad();
            System.out.println("✔️ Successfully loaded Inspection page");
        } catch (Exception e) {
            System.out.println("❌ Failed to navigate to Inspection page");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            throw new RuntimeException("Navigation failed", e);
        }
    }


    

    private void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    private void deleteDefaultItem() {
        try {
            System.out.println("🗑️ Attempting to delete default item...");
            List<WebElement> deleteButtons = driver.findElements(DELETE_BUTTON);

            if (!deleteButtons.isEmpty()) {
                WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(deleteButtons.get(0)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton); // safer click

                WebElement confirmDeleteBtn = wait.until(ExpectedConditions.elementToBeClickable(DELETE_CONFIRM_BUTTON));
                confirmDeleteBtn.click();

                // Wait until no items remain
                wait.until(d -> driver.findElement(ITEMS_CONTAINER).findElements(By.cssSelector("div")).isEmpty());
                System.out.println("✅ Default item deleted.");
            } else {
                System.out.println("ℹ️ No default item found to delete.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not delete default item.");
            throw new RuntimeException(e);
        }
    }


    private void uploadImagesWithDescriptions(String[] imagePaths) {
        // Ensure at least one item exists
        if (driver.findElements(INSPECTION_ITEMS).size() == 0) {
            addNewItem(0);
        }

        for (int i = 0; i < imagePaths.length; i++) {
            System.out.println("📁 Processing image " + (i + 1) + " of " + imagePaths.length);
            if (i != 0) addNewItem(i);
            uploadImage(i, imagePaths[i]);
            addDescription(i);
            System.out.println("✅ Uploaded: " + imagePaths[i]);
        }
    }

    private void addNewItem(int index) {
        try {
            WebElement addButton = wait.until(ExpectedConditions.presenceOfElementLocated(ADD_ITEM_BUTTON));
            
            // Scroll into view and wait until visible + clickable
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addButton);
            wait.until(ExpectedConditions.visibilityOf(addButton));
            wait.until(ExpectedConditions.elementToBeClickable(addButton));
            
            // Perform reliable click
            new Actions(driver).moveToElement(addButton).pause(300).click().perform();

            // Extra wait in case of delayed rendering
            wait.until(driver -> {
                List<WebElement> items = driver.findElements(INSPECTION_ITEMS);
                return items.size() > index && items.get(index).isDisplayed();
            });

            System.out.println("➕ Item added at index: " + index);

        } catch (Exception e) {
            throw new RuntimeException("Failed to add new item at index " + index, e);
        }
    }

    private void uploadImage(int index, String filePath) {
    	String inputId = "file-input-0-" + index;
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(inputId)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('type','file');", input);
        input.sendKeys(filePath);

        // Wait for any upload indicator to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'upload-progress')]")));
    }

    private void addDescription(int index) {
        String editorXPath = "(//div[contains(@class,'ql-editor')])[" + (index + 1) + "]";
        WebElement editor = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(editorXPath)));
        String description = "Inspection detail for image " + (index + 1);

        ((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = '<p>" + description + "</p>';", editor);

        String hiddenInputId = "quill-input-0-" + index;
        WebElement hiddenInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(hiddenInputId)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", hiddenInput, description);
    }

    private void saveInspection() {
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        clickWithScroll(saveButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_ALERT));
        System.out.println("💾 Inspection saved successfully.");
    }

    private void changeSectionStyle() {
        clickWithScroll(wait.until(ExpectedConditions.elementToBeClickable(STYLE_CHANGE_LINK)));
        clickWithScroll(wait.until(ExpectedConditions.elementToBeClickable(RADIO_OPTION_2)));
        System.out.println("🎨 Radio option 'option2' selected.");

        // background scroll off to insure button is in view
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				wait.until(ExpectedConditions.elementToBeClickable(STYLE_SAVE_BUTTON));
        clickWithScroll(wait.until(ExpectedConditions.elementToBeClickable(STYLE_SAVE_BUTTON)));
        System.out.println("✅ Section style changed.");
    }

    private void clickWithScroll(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        new Actions(driver).moveToElement(element).pause(200).click().perform();
    }
}
