package com.phuong.automation.keywords;

import com.phuong.automation.managers.PageManager;
import com.phuong.automation.utils.LogUtils;

public class WebKeyword {
    public static void navigate(String url) {
        PageManager.getPage().navigate(url);
        PageManager.getPage().waitForLoadState();
        LogUtils.info("Navigate to URL: " + url);
        //ExtentTestManager.logMessage(Status.INFO, "Navigate to URL: " + url);
    }
    public static void fill(String locator, String value) {
        PageManager.getPage().locator(locator).fill(value);
        LogUtils.info("Fill text " + value + " on element " + locator);
    }
    public static void click(String locator) {
        PageManager.getPage().click(locator);
    }
}
