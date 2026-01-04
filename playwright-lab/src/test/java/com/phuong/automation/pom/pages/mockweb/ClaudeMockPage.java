package com.phuong.automation.pom.pages.mockweb;

import com.phuong.automation.keywords.WebKeyword;
import com.phuong.automation.pom.pages.BasePage;
import org.testng.Assert;

public class ClaudeMockPage extends BasePage {
    private String url = "http://localhost:3000/";

    // Tabs
    private String tabBasics = "[data-testid='nav-basics']";
    private String tabInteractions = "[data-testid='nav-interactions']";
    private String tabTables = "[data-testid='nav-tables']";
    private String tabAsync = "[data-testid='nav-async']";
    private String tabAdvanced = "[data-testid='nav-advanced']";

    // Basics Tab
    private String inputUser = "[data-testid='username-input']";
    private String selectCountry = "[data-testid='country-select']";
    private String btnCustomDropdown = "[data-testid='custom-dropdown-btn']";
    private String inputSearchDropdown = "[data-testid='dropdown-search']";
    private String btnSubmit = "[data-testid='submit-btn']";
    private String msgFormAlert = "[data-testid='form-message']";

    // Drag & Drop Tab
    private String dragSource = "[data-testid='drag-item-item-1']"; // "Test Case 1"
    private String dropTarget = "[data-testid='drop-zone-done']";

    // Async Tab
    private String btnAsyncTrigger = "[data-testid='get-data-btn']";
    private String txtAsyncContent = "[data-testid='async-content']";

    // Advanced Tab
    private String btnConfirm = "[data-testid='confirm-btn']";
    private String btnPrompt = "[data-testid='prompt-btn']";
    private String inputFile = "[data-testid='file-input']";
    private String iframeSelector = "[data-testid='iframe-content']";

    public ClaudeMockPage open(){
        WebKeyword.navigate(url);
        return this;
    }

    public void navigateToTab(String tabName) {
        switch (tabName.toLowerCase()) {
            case "basics": WebKeyword.click(tabBasics); break;
            case "interactions": WebKeyword.click(tabInteractions); break;
            case "tables": WebKeyword.click(tabTables); break;
            case "async": WebKeyword.click(tabAsync); break;
            case "advanced": WebKeyword.click(tabAdvanced); break;
        }
    }

    public void fillForm(String user, String countryVal, String gender, String skill) {
        WebKeyword.fill(inputUser, user);
        WebKeyword.selectOptionByValue(selectCountry, countryVal);
        WebKeyword.click("[data-testid='gender-" + gender.toLowerCase() + "']");
        WebKeyword.check("[data-testid='skill-" + skill.toLowerCase() + "']", true);
        WebKeyword.click(btnSubmit);
    }
    public void selectFromCustomDropdown(String optionText) {
        WebKeyword.click(btnCustomDropdown);
        // Type to search (optional, but good practice based on your code)
        WebKeyword.fill(inputSearchDropdown, optionText);
        // Click the option
        WebKeyword.click("[data-testid='option-" + optionText + "']");
    }

    public void performDragAndDrop() {
        WebKeyword.dragAndDrop(dragSource, dropTarget);
    }

    public void handleAsyncWait() {
        WebKeyword.click(btnAsyncTrigger);
    }
    public void handleDialogs() {
        // Handle Confirm
        WebKeyword.click(btnConfirm);
        WebKeyword.acceptDialog();


        // Handle Prompt (Input text into alert)
        WebKeyword.click(btnPrompt);
        WebKeyword.acceptDialogWithText("PlaywrightUser");

    }
    public void interactWithIframe() {
        WebKeyword.interactWithIframe(iframeSelector, "#iframe-btn", "Iframe text");
    }

    // --- Verifications ---
    public String getSuccessMessage() {
        return WebKeyword.textContent(msgFormAlert);
    }

    public String getAsyncSuccessText() {
        WebKeyword.waitForVisibility(txtAsyncContent);
        return WebKeyword.textContent(txtAsyncContent);
    }

    public void verifyGenderSelected(String gender) {
        // Selector động dựa trên value: male/female
        String selector = "[data-testid='gender-" + gender.toLowerCase() + "']";

        // Kiểm tra xem radio button này có đang được check không
        WebKeyword.verifyChecked(selector, true);
    }

    public void verifySkillChecked(String skill, boolean shouldBeChecked) {
        String selector = "[data-testid='skill-" + skill.toLowerCase() + "']";
        WebKeyword.verifyChecked(selector, shouldBeChecked);
    }

    public void verifyCountrySelected(String countryValue) {
        // Verify native dropdown
        WebKeyword.verifySelectedValue(selectCountry, countryValue);
    }

    public void verifyCustomDropdownSelection(String expectedText) {
        // Verify text hiển thị trên nút bấm của custom dropdown
        WebKeyword.verifyElementText(btnCustomDropdown, expectedText);
    }

    public void verifyForm(String expectedText){
        Assert.assertTrue(getClaudeMockPage().getSuccessMessage().contains(expectedText));
    }


}
