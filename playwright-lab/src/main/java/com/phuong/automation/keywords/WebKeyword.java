package com.phuong.automation.keywords;

import com.microsoft.playwright.Locator;
import com.phuong.automation.managers.PageManager;
import com.phuong.automation.utils.LogUtils;
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
    public static void navigate(String url) {
        sleep(STEP_TIME);
        PageManager.getPage().navigate(url);
        PageManager.getPage().waitForLoadState();
        LogUtils.info("Navigate to URL: " + url);
        //ExtentTestManager.logMessage(Status.INFO, "Navigate to URL: " + url);
    }
    public static void fill(String locator, String value) {
        sleep(STEP_TIME);
        PageManager.getPage().locator(locator).fill(value);
        LogUtils.info("Fill text " + value + " on element " + locator);
    }
    public static void fillId(String id, String value) {
        sleep(STEP_TIME);
        PageManager.getPage().getByTestId(id).fill(value);
        LogUtils.info("Fill text " + value + " on element " + id);
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
    public static void waitForPageLoaded(){

        PageManager.getPage().waitForLoadState();
    }
    public static String textContent(String locator){
        sleep(STEP_TIME);
        String text = PageManager.getPage().locator(locator).textContent();
        LogUtils.info("Get text of element " + locator + " ==> " + text);
        return text;
    }
    public static boolean isVisible(String locator){
        sleep(STEP_TIME);
        boolean result = PageManager.getPage().locator(locator).isVisible();
        LogUtils.info("Check if element " + locator + " is visible ==> " + result);
        return result;
    }

    public static Locator find(String selector) {
        sleep(STEP_TIME);
        return PageManager.getPage().locator(selector);
    }

    public static Locator find(Locator parent, String selector) {
        sleep(STEP_TIME);
        return parent.locator(selector);
    }
    public static String innerText(String locator) {
        sleep(STEP_TIME);
        String result = PageManager.getPage().locator(locator).innerText();
        LogUtils.info("Get inner text of element " + locator + " ==> " + result);
        return result;
    }

    public static int countElement(String locator){
        sleep(STEP_TIME);
        LogUtils.info("Get count of element " + locator);
        return PageManager.getPage().locator(locator).count();
    }

    public static void uploadFile(String locator, String filePath) {
        try {
            PageManager.getPage().locator(locator).setInputFiles(Paths.get(filePath));

            LogUtils.info(
                    "Upload file " + filePath + " to " + locator
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
