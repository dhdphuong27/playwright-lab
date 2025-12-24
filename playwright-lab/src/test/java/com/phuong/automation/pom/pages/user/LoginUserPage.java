package com.phuong.automation.pom.pages.user;

import com.phuong.automation.managers.PageManager;
import com.phuong.automation.pom.pages.BasePage;
import org.testng.Assert;

public class LoginUserPage extends BasePage {
    private String url = "https://cms.anhtester.com/login";
    private String usernameInput = "//*[@id=\"email\"]";
    private String passwordInput = "//*[@id=\"password\"]";
    private String loginButton = "//button[normalize-space()='Login']";
    private String headerDashboardPageUser = "//h1[normalize-space()='Dashboard']";
    public LoginUserPage loginUser(String username, String password) {
        //Should use WebKeyword here, but for learning purpose, use pure playwright first

        PageManager.getPage().navigate(url);
        PageManager.getPage().waitForLoadState();
        PageManager.getPage().locator(usernameInput).fill(username);
        PageManager.getPage().locator(passwordInput).fill(password);
        PageManager.getPage().locator(loginButton).click();

        return this;
    }
    public LoginUserPage verifyLoginSuccess(){
        PageManager.getPage().waitForLoadState();
        Assert.assertTrue(PageManager.getPage().isVisible(headerDashboardPageUser), "FAIL. The button Clear Cache not visible.");
        return this;
    }
}
