package com.Layout;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QuoteDetailsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final By QUOTE_DETAILS_LINK = By.xpath("//li[@data-pagename='Quote Details']//a");
    private static final By ADD_ITEM_BUTTON = By.xpath("//button[normalize-space()='Add Item']");
    private static final By LOADER = By.id("loader");
    private static final By ITEM_ROW_SELECTOR = By.cssSelector("div.row.Descriptionclass");
    private static final By DESCRIPTION_INPUT = By.cssSelector("textarea.description-input");
    private static final By QUANTITY_INPUT = By.cssSelector("input.item-quantity");
    private static final By PRICE_INPUT = By.cssSelector("input.item-price-bind");
    private static final By LINE_TOTAL = By.cssSelector(".line-total");

    public QuoteDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillQuoteDetails() {
        List<ItemData> items = List.of(
            new ItemData("5 Inch Gutters", "184", "50"),
            new ItemData("2x3 Downspouts", "100", "50"),
            new ItemData("Mitter Hanger", "10", "50"),
            new ItemData("Hanger H11", "10", "20")
        );

        navigateToQuoteDetailsPage();
        waitForLoaderToDisappear();

        for (int i = 0; i < items.size(); i++) {
            if (i > 0) clickAddItemButton();
            updateItem(i, items.get(i));
        }

        System.out.println("✅ All items updated successfully.");
    }

    private void navigateToQuoteDetailsPage() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(QUOTE_DETAILS_LINK));
        clickElement(link);
        System.out.println("➡️ Navigated to Quote Details section.");
    }

    private void waitForLoaderToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADER));
    }

    private void clickAddItemButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(ADD_ITEM_BUTTON));
        clickElement(button);
        waitForLoaderToDisappear();
    }

    private void updateItem(int index, ItemData item) {
        WebElement row = getItemRow(index);

        setInputValue(row.findElement(DESCRIPTION_INPUT), item.description());
        setInputValue(row.findElement(QUANTITY_INPUT), item.quantity());
        setInputValue(row.findElement(PRICE_INPUT), item.price());

        verifyLineTotalUpdated(row);
        System.out.printf("✔️ Updated item %d: %s%n", index + 1, item.description());
    }

    private WebElement getItemRow(int index) {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ITEM_ROW_SELECTOR));
        if (index >= rows.size()) {
            throw new RuntimeException("❌ Row index out of bounds: " + index);
        }
        return rows.get(index);
    }

    private void setInputValue(WebElement element, String value) {
        scrollToElement(element);
        element.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        element.sendKeys(value);
        triggerChangeEvent(element);
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    private void clickElement(WebElement element) {
        scrollToElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void triggerChangeEvent(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", element);
    }

    private void verifyLineTotalUpdated(WebElement row) {
        WebElement total = row.findElement(LINE_TOTAL);
        wait.until(driver -> {
            String text = total.getText();
            return text != null && !text.isBlank() && !"$0".equals(text);
        });
    }

    // ✅ Declare ItemData as a record
    public record ItemData(String description, String quantity, String price) {}
}
