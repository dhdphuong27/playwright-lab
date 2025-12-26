package com.phuong.automation.pom.pages;
import com.phuong.automation.pom.pages.admin.LoginPage;
import com.phuong.automation.pom.pages.user.DashboardPage;
import com.phuong.automation.pom.pages.user.LoginUserPage;

public class BasePage {
    private LoginPage loginPage;
    private LoginUserPage loginUserPage;
    private DashboardPage dashboardPage;
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
    public DashboardPage getDashboardPage(){
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage();
        }
        return dashboardPage;
    }
}
