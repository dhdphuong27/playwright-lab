package com.phuong.automation.keywords;

import com.microsoft.playwright.Locator;
import com.phuong.automation.managers.PageManager;
import com.phuong.automation.utils.LogUtils;
import org.testng.asserts.SoftAssert;

public class WebKeyword {
    public static final int STEP_TIME = 2;
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
    public static void navigate(String url) {
        sleep(STEP_TIME);
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
        PageManager.getPage().locator(locator).click();
        LogUtils.info("Click on element " + locator);
    }
    public static void click(Locator locator) {
        locator.click();
    }
    public static void waitForPageLoaded(){
        PageManager.getPage().waitForLoadState();
    }
    public static String textContent(String locator){
        String text = PageManager.getPage().locator(locator).textContent();
        LogUtils.info("Get text of element " + locator + " ==> " + text);
        return text;
    }
    public static boolean isVisible(String locator){
        boolean result = PageManager.getPage().locator(locator).isVisible();
        LogUtils.info("Check if element " + locator + " is visible ==> " + result);
        return result;
    }

    public static Locator find(String selector) {
        return PageManager.getPage().locator(selector);
    }

    public static Locator find(Locator parent, String selector) {
        return parent.locator(selector);
    }
    public static String innerText(String locator) {
        String result = PageManager.getPage().locator(locator).innerText();
        LogUtils.info("Get inner text of element " + locator + " ==> " + result);
        return result;
    }

}
