package com.phuong.automation.pom.testcases.user;

import com.phuong.automation.common.BaseTest;
import com.phuong.automation.pom.pages.user.LoginUserPage;
import org.testng.annotations.Test;

public class LoginUserTest extends BaseTest {
    LoginUserPage page;

    @Test
    public void testLoginSuccess() {
        page = new LoginUserPage();
        page.loginUser("customer@example.com", "123456")
                .verifyLoginSuccess();
    }

    @Test
    public void testLoginFailWithEmailInvalid() {
        page = new LoginUserPage();
        page.loginUser("customer123@example.com", "123456")
                .verifyLoginFail("Invalid login credentials");
    }
}
