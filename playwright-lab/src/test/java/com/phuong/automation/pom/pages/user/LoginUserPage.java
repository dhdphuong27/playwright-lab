package com.phuong.automation.pom.pages.user;

import com.phuong.automation.managers.PageManager;
import com.phuong.automation.pom.pages.BasePage;
import com.phuong.automation.keywords.WebKeyword;
import org.testng.Assert;

public class LoginUserPage extends BasePage {
    private String url = "https://cms.anhtester.com/login";
    private String usernameInput = "//*[@id=\"email\"]";
    private String passwordInput = "//*[@id=\"password\"]";
    private String loginButton = "//button[normalize-space()='Login']";
    private String headerDashboardPageUser = "//h1[normalize-space()='Dashboard']";
    private String alertMessage = "[data-notify='message']"; //

    public LoginUserPage loginUser(String username, String password) {

        /*PageManager.getPage().navigate(url);
        PageManager.getPage().waitForLoadState();
        PageManager.getPage().locator(usernameInput).fill(username);
        PageManager.getPage().locator(passwordInput).fill(password);
        PageManager.getPage().locator(loginButton).click();*/
        WebKeyword.navigate(url);
        WebKeyword.fill(usernameInput,username);
        WebKeyword.fill(passwordInput,password);
        WebKeyword.click(loginButton);

        return this;
    }
    public LoginUserPage verifyLoginSuccess(){
        WebKeyword.waitForPageLoaded();
        Assert.assertTrue(WebKeyword.isVisible(headerDashboardPageUser), "FAIL. The button Clear Cache not visible.");
        return this;
    }
    public LoginUserPage verifyLoginFail(String message){
        WebKeyword.waitForPageLoaded();
        Assert.assertEquals(WebKeyword.textContent(alertMessage), message, "FAIL. The error message not match.");
        Assert.assertFalse(WebKeyword.isVisible(headerDashboardPageUser), "FAIL. The button Clear Cache visible.");
        return this;
    }

}
