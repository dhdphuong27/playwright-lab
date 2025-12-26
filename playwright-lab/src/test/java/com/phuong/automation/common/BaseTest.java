package com.phuong.automation.common;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.phuong.automation.constants.AppConfig;
import com.phuong.automation.keywords.WebKeyword;
import com.phuong.automation.managers.PageManager;
import com.phuong.automation.pom.pages.BasePage;
import com.phuong.automation.utils.LogUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest extends BasePage {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    @BeforeClass
    public void testBeforeClass() {
        LogUtils.info("Before Class");
    }
    @AfterClass
    public void testAfterClass() {
        LogUtils.info("After Class");
    }
    @BeforeMethod
    public void setUp() {
        //Not using Factory Pattern here and create browser directly
        playwright = Playwright.create(); //Create
        PageManager.setPlaywright(playwright);
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)); //Launch
        PageManager.setBrowser(browser);
        browserContext = PageManager.getBrowser().newContext();
        PageManager.setBrowserContext(browserContext);
        page = PageManager.getBrowserContext().newPage();
        //page.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(AppConfig.TIMEOUT_PAGE_LOAD));
        page.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(30));
        PageManager.setPage(page);

    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        WebKeyword.closeSoftAssert();
        try {
            PageManager.closePage();
        } catch (Exception e) {
            System.out.println("closePage fail: " + e.getMessage());
        }
        try {
            PageManager.closeBrowserContext();
        } catch (Exception e) {
            System.out.println("closeContext fail: " + e.getMessage());
        }
        try {
            PageManager.closeBrowser();
        } catch (Exception e) {
            System.out.println("closeBrowser fail: " + e.getMessage());
        }
        try {
            PageManager.closePlaywright();
        } catch (Exception e) {
            System.out.println("closePlaywright fail: " + e.getMessage());
        }
    }
}
