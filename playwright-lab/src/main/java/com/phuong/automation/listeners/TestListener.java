package com.phuong.automation.listeners;

import com.microsoft.playwright.Page;
import com.phuong.automation.managers.PageManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Paths;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(">>> Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(">>> Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("!!! Test Failed: " + result.getName());

        // 1. Get the Page object directly from your PageManager
        // Since PageManager handles ThreadLocal, this gets the correct browser for this specific thread/test.
        Page page = PageManager.getPage();

        if (page != null) {
            try {
                // 2. Create a file path
                // Format: screenshots/testName_timestamp.png
                String testName = result.getName();
                String timestamp = String.valueOf(System.currentTimeMillis());
                String screenshotPath = "screenshots/" + testName + "_" + timestamp + ".png";

                // 3. Take the screenshot
                page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get(screenshotPath))
                        .setFullPage(true)); // capture full scrolling page

                System.out.println("Screenshot captured: " + screenshotPath);

            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            System.err.println("Page object was null. Cannot take screenshot.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(">>> Test Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("--- Suite Started: " + context.getName() + " ---");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("--- Suite Finished: " + context.getName() + " ---");
    }
}