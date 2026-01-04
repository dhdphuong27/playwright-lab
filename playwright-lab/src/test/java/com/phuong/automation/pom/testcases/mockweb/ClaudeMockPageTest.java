package com.phuong.automation.pom.testcases.mockweb;

import com.microsoft.playwright.Locator;
import com.phuong.automation.common.BaseTest;

import com.phuong.automation.listeners.TestListener;
import com.phuong.automation.managers.PageManager;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import org.testng.Assert;

@Listeners(TestListener.class)
public class ClaudeMockPageTest extends BaseTest {
    @Test(priority = 1)
    void testFormAndCustomDropdown() {
        getClaudeMockPage().open();
        getClaudeMockPage().navigateToTab("basics");

        // 1. Handle Custom Div Dropdown (Search & Select)
        getClaudeMockPage().selectFromCustomDropdown("Playwright");

        // 2. Fill standard form
        getClaudeMockPage().fillForm("Tester01", "vn", "Female", "Java");

        getClaudeMockPage().verifyGenderSelected("Female");

        // Verify Checkbox (Verify state)
        getClaudeMockPage().verifySkillChecked("Java", true);
        getClaudeMockPage().verifySkillChecked("Playwright", false);

        // Verify Native Dropdown (Verify selected value)
        getClaudeMockPage().verifyCountrySelected("vn");

        // Verify Custom Dropdown (Verify text hiển thị)
        getClaudeMockPage().verifyCustomDropdownSelection("Playwright");

        // 3. Assert Success
        getClaudeMockPage().verifyForm("submitted successfully");
    }

    @Test(priority = 2)
    void testDragAndDrop() {
        getClaudeMockPage().open();
        getClaudeMockPage().navigateToTab("interactions");

        getClaudeMockPage().performDragAndDrop();

        // Verify it landed in the second list
        // In your React code, the item moves to a new div.
        // We verify that the "Done" zone now contains the text "Test Case 1"
        Locator doneZone = PageManager.getPage().locator("[data-testid='drop-zone-done']");
        Assert.assertTrue(doneZone.textContent().contains("Test Case 1"));
    }

    @Test(priority = 3)
    void testAsyncWait() {
        getClaudeMockPage().open();
        getClaudeMockPage().navigateToTab("async");

        // Click button that takes 3 seconds
        getClaudeMockPage().handleAsyncWait();

        // This assertion will wait automatically for the element to attach
        String result = getClaudeMockPage().getAsyncSuccessText();
        Assert.assertTrue(result.contains("Data loaded successfully"));
    }

    @Test(priority = 4)
    void testTablePagination() {
        getClaudeMockPage().open();
        getClaudeMockPage().navigateToTab("tables");

        // Logic: We want to find "Employee 15". It is not on Page 1.
        // We need to click "Next" until we find it.
        boolean found = false;

        // Limit loop to avoid infinite running
        for(int i=0; i<5; i++) {
            if(PageManager.getPage().locator("text=Employee 15").isVisible()) {
                found = true;
                break;
            }
            // Click Next if button is enabled
            Locator nextBtn = PageManager.getPage().locator("[data-testid='next-btn']");
            if(!nextBtn.isDisabled()) {
                nextBtn.click();
            }
        }
        Assert.assertTrue(found, "Employee 15 should be found via pagination");
    }

    @Test(priority = 5)
    void testDialog() {
        getClaudeMockPage().open();
        getClaudeMockPage().navigateToTab("advanced");

        getClaudeMockPage().handleDialogs();
    }

    @Test(priority = 6)
    void testIframe(){
        getClaudeMockPage().open();
        getClaudeMockPage().navigateToTab("advanced");
        getClaudeMockPage().interactWithIframe();
    }
}
