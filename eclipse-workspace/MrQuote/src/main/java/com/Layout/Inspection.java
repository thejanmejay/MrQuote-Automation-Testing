package com.Layout;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
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
	private static final By SAVE_BUTTON = By.xpath("//div[@class='col-12 d-flex justify-content-end']//button[@type='submit'][normalize-space()='Save']");
	private static final By SUCCESS_ALERT = By.xpath("//div[contains(@class,'alert-success')]");
	private static final By STYLE_CHANGE_LINK = By.xpath("//a[normalize-space()='Change']");
	private static final By RADIO_OPTION_2 = By.xpath("//input[@type='radio' and @id='option2']");
	private static final By STYLE_SAVE_BUTTON = By.id("saveButton");
	// Local image paths
	private static final String[] DEFAULT_IMAGES = { "C:\\Users\\JanmejaySingh\\Downloads\\10.jpg",
			"C:\\Users\\JanmejaySingh\\Downloads\\9.jpg", "C:\\Users\\JanmejaySingh\\Downloads\\8.jpg",
			"C:\\Users\\JanmejaySingh\\Downloads\\7.jpg", "C:\\Users\\JanmejaySingh\\Downloads\\6.jpg" };

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
		wait.until(
				driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
	}

	private void deleteDefaultItem() {
		try {
			System.out.println("🗑️ Attempting to delete default item...");
			List<WebElement> deleteButtons = driver.findElements(DELETE_BUTTON);

			if (!deleteButtons.isEmpty()) {
				WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(deleteButtons.get(0)));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton); // safer click

				WebElement confirmDeleteBtn = wait
						.until(ExpectedConditions.elementToBeClickable(DELETE_CONFIRM_BUTTON));
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
		// Clear existing items
		// deleteDefaultItem();

		// Upload first image
		if (imagePaths.length > 0) {
			addNewItem(0);
			uploadImage(0, imagePaths[0]);
			addDescription(0);
			System.out.println("🖼️ First image uploaded successfully");
		}

		// Upload remaining images with more robust waiting
		for (int i = 1; i < imagePaths.length; i++) {
			try {
				System.out.println("\n🔄 Processing image " + (i + 1) + "/" + imagePaths.length);

				// Add new item with retry logic
				int retries = 0;
				while (retries < 3) {
					try {
						addNewItem(i);
						break;
					} catch (StaleElementReferenceException e) {
						retries++;
						System.out.println("⚠️ Stale element, retrying (" + retries + "/3)");
						if (retries == 3)
							throw e;
					}
				}

				uploadImage(i, imagePaths[i]);
				addDescription(i);
				System.out.println("✅ Successfully processed image " + (i + 1));
			} catch (Exception e) {
				System.out.println("❌ Failed to process image " + (i + 1));
				throw new RuntimeException("Image processing failed at index " + i, e);
			}
		}
	}

	private void addNewItem(int index) {
		try {
			int currentCount = driver.findElements(INSPECTION_ITEMS).size();
			System.out.println("➕ Attempting to add item #" + (currentCount + 1));

			WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_ITEM_BUTTON));

			 // ✅ Scroll to bottom of page to ensure Add button is visible
	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	        Thread.sleep(500);  // Let scroll finish

	        // ✅ Ensure button is visible and clickable
	        wait.until(ExpectedConditions.visibilityOf(addButton));
	        wait.until(ExpectedConditions.elementToBeClickable(addButton));

	        // Optional fallback if normal click fails
	        try {
	            addButton.click();
	        } catch (ElementClickInterceptedException e) {
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
	        }

			// Wait for the new item to appear and be fully rendered
			wait.until(d -> {
				List<WebElement> items = d.findElements(INSPECTION_ITEMS);
				return items.size() == currentCount + 1;
			});

			// Additional validation for the new item
			validateItemAppearance(currentCount);
			System.out.println("🆕 New item added at index " + index);

			System.out.println("✅ Successfully added item #" + (currentCount + 1));
		} catch (Exception e) {
			System.out.println("❌ Failed to add new item at index " + index);
			throw new RuntimeException("Failed to add new item", e);
		}
	}

	private void uploadImage(int index, String filePath) {
	try {
		validateItemAppearance(index);
		WebElement itemContainer = driver.findElements(INSPECTION_ITEMS).get(index);

		System.out.println("Uploading image to item #" + index + " from path: " + filePath);
		File file = new File(filePath);
		System.out.println("File exists: " + file.exists());

		WebElement input = wait.until(d -> {
			List<WebElement> inputs = itemContainer.findElements(By.xpath(".//input[@type='file']"));
			return inputs.isEmpty() ? null : inputs.get(0);
		});

		((JavascriptExecutor) driver).executeScript(
			"arguments[0].style.visibility='visible'; arguments[0].style.height='1px'; arguments[0].style.width='1px';",
			input);

		for (int retry = 0; retry < 3; retry++) {
			try {
				input.sendKeys(file.getAbsolutePath());
				break;
			} catch (ElementNotInteractableException e) {
				System.out.println("⚠️ Retry " + (retry + 1) + ": Element not interactable for file input.");
				Thread.sleep(500);
				if (retry == 2) throw e;
			}
		}

		// Debug preview wait
		WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		boolean uploaded = longWait.until(d -> {
			try {
				By previewImg = By.xpath(".//img[starts-with(@id, 'preview-img-') and contains(@id, '-" + index + "')]");
				WebElement preview = itemContainer.findElement(previewImg);

				return preview.isDisplayed() && preview.getAttribute("src").startsWith("data:image/");
			} catch (Exception ex) {
				return false;
			}
		});

		if (uploaded) {
			System.out.println("🖼️ Successfully uploaded image to item #" + index);
		} else {
			System.out.println("❌ Image preview not found after upload attempt");
		}

		scrollToBottom();
		Thread.sleep(300);
	} catch (Exception e) {
		System.out.println("❌ Failed to upload image to item #" + index);
		throw new RuntimeException("Image upload failed for index " + index, e);
	}
}
	private void scrollToBottom() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	private void addDescription(int index) {
		try {
			// Wait for the editor to be available
			WebElement editor = wait.until(d -> {
			    WebElement container = d.findElements(INSPECTION_ITEMS).get(index);
			    return container.findElement(By.cssSelector("div.ql-editor"));
			});


			String description = "Inspection detail for image " + (index + 1);

			// Clear existing content first
			((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = '';", editor);

			// Add new content
			((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = '<p>" + description + "</p>';",
					editor);

			// Update hidden input
			String hiddenInputId = "quill-input-0-" + index;
			WebElement hiddenInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(hiddenInputId)));
			((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", hiddenInput, description);

			System.out.println("📝 Added description to item #" + (index + 1));
		} catch (Exception e) {
			System.out.println("❌ Failed to add description to item #" + (index + 1));
			throw new RuntimeException("Description addition failed", e);
		}
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

	private void validateItemAppearance(int index) {
		WebElement item = driver.findElements(INSPECTION_ITEMS).get(index);
		wait.until(d -> {
			return item.isDisplayed() && item.getSize().getHeight() > 0 && item.getSize().getWidth() > 0;
		});
	}

}
