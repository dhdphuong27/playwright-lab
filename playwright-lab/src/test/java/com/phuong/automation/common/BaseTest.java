package com.phuong.automation.common;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.phuong.automation.keywords.WebKeyword;
import com.phuong.automation.managers.PageManager;
import com.phuong.automation.pom.pages.BasePage;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.List;

public class BaseTest extends BasePage {

    // Không cần khai báo biến class level ở đây nếu đã dùng PageManager hoàn toàn
    // Nhưng giữ lại để tiện gọi nếu cần debug cục bộ

    @BeforeMethod
    @Parameters({"browser"})
    public void createDriver(@Optional("chrome") String browserName) {
        // 1. Khởi tạo Playwright
        Playwright playwright = Playwright.create();
        PageManager.setPlaywright(playwright);

        // 2. Khởi tạo Browser (Tùy chọn browser dựa trên tham số XML nếu cần)
        Browser browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setChannel("chrome") // Hoặc dùng tham số browserName để switch case
                        .setArgs(List.of("--start-maximized")));
        PageManager.setBrowser(browser);

        // 3. Khởi tạo Context
        BrowserContext browserContext = PageManager.getBrowser()
                .newContext(new Browser.NewContextOptions().setViewportSize(null));
        PageManager.setBrowserContext(browserContext);

        // 4. Khởi tạo Page
        Page page = PageManager.getBrowserContext().newPage();
        PageManager.setPage(page);

        // 5. Cấu hình Page
        page.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(30000)); // 30s thay vì 30ms
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver(ITestResult result) {
        // Xử lý SoftAssert nếu có
        WebKeyword.closeSoftAssert();

        // Đóng theo thứ tự ngược lại: Page -> Context -> Browser -> Playwright
        // Hàm closeAll() trong PageManager của bạn đã bao gồm logic này,
        // nhưng ta nên gọi từng cái để đảm bảo clear ThreadLocal sạch sẽ.

        try {
            // Có thể thêm logic chụp ảnh màn hình khi fail ở đây
            // if (result.getStatus() == ITestResult.FAILURE) { ... }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Quan trọng: Phải đóng tất cả để giải phóng Thread
            PageManager.closeAll();
        }
    }
}