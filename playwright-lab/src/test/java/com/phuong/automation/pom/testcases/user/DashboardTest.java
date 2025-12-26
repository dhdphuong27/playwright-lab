package com.phuong.automation.pom.testcases.user;

import com.phuong.automation.common.BaseTest;

import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

    @Test
    public void testTotalSummaryInUserDashboard() {
        getLoginUserPage().loginUser("customer@example.com", "123456");
        getDashboardPage().verifyNumProductInCart("0");
        getDashboardPage().verifyNumProductInWishList("0");
        getDashboardPage().verifyNumProductOrdered("5");

    }
}
