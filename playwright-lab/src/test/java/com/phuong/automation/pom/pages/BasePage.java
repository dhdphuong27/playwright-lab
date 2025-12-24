package com.phuong.automation.pom.pages;
import com.phuong.automation.pom.pages.admin.LoginPage;
import com.phuong.automation.pom.pages.user.LoginUserPage;

public class BasePage {
    private LoginPage loginPage;
    private LoginUserPage loginUserPage;

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    public LoginUserPage getLoginUserPage() {
        if (loginUserPage == null) {
            loginUserPage = new LoginUserPage();
        }
        return loginUserPage;
    }
}
