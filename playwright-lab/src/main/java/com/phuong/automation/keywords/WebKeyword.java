package com.phuong.automation.keywords;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import com.phuong.automation.managers.PageManager;
import com.phuong.automation.utils.LogUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.nio.file.Paths;

public class WebKeyword {

    public static final int STEP_TIME = 1;
    private static SoftAssert softAssert;

    public static SoftAssert getSoftAssert() {
        if (softAssert == null) {
            softAssert = new SoftAssert();
        }
        return softAssert;
    }

    public static void closeSoftAssert() {
        if (softAssert != null) {
            softAssert.assertAll();
        }
    }

    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // ---------- Navigation ----------

    public static void navigate(String url) {
        sleep(STEP_TIME);
        PageManager.getPage().navigate(url);
        PageManager.getPage().waitForLoadState();
        LogUtils.info("Navigate to URL: " + url);
    }

    public static void waitForPageLoaded() {
        sleep(STEP_TIME);
        PageManager.getPage().waitForLoadState();
        LogUtils.info("Wait for page loaded");
    }

    // ---------- Input / Actions ----------

    public static void fill(String locator, String value) {
        sleep(STEP_TIME);
        PageManager.getPage().locator(locator).fill(value);
        LogUtils.info("Fill text [" + value + "] into element " + locator);
    }

    public static void click(String locator) {
        sleep(STEP_TIME);
        PageManager.getPage().locator(locator).click();
        LogUtils.info("Click on element " + locator);
    }

    public static void click(Locator locator) {
        sleep(STEP_TIME);
        locator.click();
        LogUtils.info("Click on element " + locator);
    }

    public static void hover(String selector) {
        sleep(STEP_TIME);
        PageManager.getPage().locator(selector).hover();
        LogUtils.info("Hover on element " + selector);
    }

    public static void pressKey(String key) {
        sleep(STEP_TIME);
        PageManager.getPage().keyboard().press(key);
        LogUtils.info("Press key: " + key);
    }

    // ---------- Getters / Checks ----------

    public static String textContent(String locator) {
        sleep(STEP_TIME);
        String text = PageManager.getPage().locator(locator).textContent();
        LogUtils.info("Get textContent of " + locator + " => " + text);
        return text;
    }

    public static String innerText(String locator) {
        sleep(STEP_TIME);
        String result = PageManager.getPage().locator(locator).innerText();
        LogUtils.info("Get innerText of " + locator + " => " + result);
        return result;
    }

    public static boolean isVisible(String locator) {
        sleep(STEP_TIME);
        boolean result = PageManager.getPage().locator(locator).isVisible();
        LogUtils.info("Is element visible [" + locator + "] => " + result);
        return result;
    }

    public static boolean isChecked(String selector) {
        sleep(STEP_TIME);
        boolean result = PageManager.getPage().locator(selector).isChecked();
        LogUtils.info("Is checkbox checked [" + selector + "] => " + result);
        return result;
    }

    public static int countElement(String locator) {
        sleep(STEP_TIME);
        int count = PageManager.getPage().locator(locator).count();
        LogUtils.info("Count element " + locator + " => " + count);
        return count;
    }

    public static int getRowCount(String tableRowSelector) {
        sleep(STEP_TIME);
        int count = PageManager.getPage().locator(tableRowSelector).count();
        LogUtils.info("Row count for table selector " + tableRowSelector + " => " + count);
        return count;
    }

    // ---------- Finders ----------

    public static Locator find(String selector) {
        sleep(STEP_TIME);
        LogUtils.info("Find element " + selector);
        return PageManager.getPage().locator(selector);
    }

    public static Locator find(Locator parent, String selector) {
        sleep(STEP_TIME);
        LogUtils.info("Find child element " + selector + " from parent " + parent);
        return parent.locator(selector);
    }

    // ---------- Dialogs ----------

    public static void acceptDialog() {
        sleep(STEP_TIME);
        PageManager.getPage().onDialog(Dialog::accept);
        LogUtils.info("Accept dialog");
    }

    public static void acceptDialogWithText(String promptText) {
        sleep(STEP_TIME);
        PageManager.getPage().onDialog(dialog -> dialog.accept(promptText));
        LogUtils.info("Accept dialog with text: " + promptText);
    }

    public static void acceptNextAlert() {
        sleep(STEP_TIME);
        PageManager.getPage().onceDialog(Dialog::accept);
        LogUtils.info("Accept next alert");
    }

    // ---------- Checkbox / Select ----------

    public static void check(String selector, boolean shouldCheck) {
        sleep(STEP_TIME);
        Locator loc = PageManager.getPage().locator(selector);
        if (shouldCheck) {
            if (!loc.isChecked()) loc.check();
            LogUtils.info("Check checkbox " + selector);
        } else {
            if (loc.isChecked()) loc.uncheck();
            LogUtils.info("Uncheck checkbox " + selector);
        }
    }

    public static void selectOptionByLabel(String selector, String label) {
        sleep(STEP_TIME);
        PageManager.getPage().locator(selector)
                .selectOption(new SelectOption().setLabel(label));
        LogUtils.info("Select option by label [" + label + "] on " + selector);
    }

    public static void selectOptionByValue(String selector, String value) {
        sleep(STEP_TIME);
        PageManager.getPage().locator(selector).selectOption(value);
        LogUtils.info("Select option by value [" + value + "] on " + selector);
    }

    public static void selectCustomDropdown(
            String dropdownTrigger,
            String optionListSelector,
            String desiredOptionText
    ) {
        sleep(STEP_TIME);
        click(dropdownTrigger);
        PageManager.getPage()
                .locator(optionListSelector)
                .filter(new Locator.FilterOptions().setHasText(desiredOptionText))
                .click();

        LogUtils.info("Select custom dropdown option [" + desiredOptionText + "]");
    }

    // ---------- Advanced ----------

    public static void dragAndDrop(String source, String target) {
        sleep(STEP_TIME);
        PageManager.getPage().locator(source)
                .dragTo(PageManager.getPage().locator(target));
        LogUtils.info("Drag from " + source + " to " + target);
    }

    public static void interactWithIframe(String iframeSelector, String elementInsideFrame, String text) {
        sleep(STEP_TIME);
        FrameLocator frame = PageManager.getPage().frameLocator(iframeSelector);
        frame.locator(elementInsideFrame).click();
        LogUtils.info("Interact with Iframe [" + iframeSelector + "]");
    }

    public static void waitForVisibility(String selector) {
        sleep(STEP_TIME);
        PageManager.getPage().locator(selector)
                .waitFor(new Locator.WaitForOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        LogUtils.info("Wait for visibility of element " + selector);
    }

    public static void uploadFile(String locator, String filePath) {
        sleep(STEP_TIME);
        PageManager.getPage().locator(locator)
                .setInputFiles(Paths.get(filePath));
        LogUtils.info("Upload file [" + filePath + "] to " + locator);
    }

    // --- 1. VERIFY CHECKBOX / RADIO BUTTON (Verify State) ---
    public static void verifyChecked(String selector, boolean expectedState) {
        // Chờ element hiện hữu trước khi check state
        waitForVisibility(selector);

        boolean actualState = PageManager.getPage().locator(selector).isChecked();
        Assert.assertEquals(actualState, expectedState,
                "Lỗi Verify Checkbox/Radio: Trạng thái của [" + selector + "] không đúng!");
    }

    // --- 2. VERIFY DROPDOWN (Verify Selected Value) ---
    // Dành cho Native Select (<select><option>)
    public static void verifySelectedValue(String selector, String expectedValue) {
        waitForVisibility(selector);

        // inputValue() sẽ lấy value của option đang được chọn
        String actualValue = PageManager.getPage().locator(selector).inputValue();
        Assert.assertEquals(actualValue, expectedValue,
                "Lỗi Verify Dropdown: Giá trị đã chọn không khớp!");
    }

    public static void verifyElementText(String selector, String expectedText) {
        waitForVisibility(selector);

        String actualText = PageManager.getPage().locator(selector).textContent().trim();
        // Dùng contains vì đôi khi text có khoảng trắng thừa hoặc icon
        Assert.assertTrue(actualText.contains(expectedText),
                "Lỗi Verify Text: Mong đợi [" + expectedText + "] nhưng thấy [" + actualText + "]");
    }
}
