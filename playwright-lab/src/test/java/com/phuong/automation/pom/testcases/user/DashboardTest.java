package com.phuong.automation.pom.testcases.user;

import com.phuong.automation.common.BaseTest;

import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

    @Test
    public void testTotalSummaryInUserDashboard() {
        getLoginUserPage().loginUser("customer@example.com", "123456");
        getDashboardPage().closePopup();
        getDashboardPage().verifyNumProductInCart(0);
        getDashboardPage().verifyNumProductInWishList(0);
        getDashboardPage().verifyNumProductOrdered(5);

    }
    @Test
    public void testPurchaseHistory(){
        getLoginUserPage().loginUser("customer@example.com", "123456");
        getDashboardPage().closePopup();
        getDashboardPage().clickMenuItem("Purchase History");
        getDashboardPage().verifyNumProductInPurchaseHistory(6);
    }
    @Test
    public void testSupportTicket(){
        getLoginUserPage().loginUser("customer@example.com", "123456");
        getDashboardPage().closePopup();
        getDashboardPage().clickMenuItem("Support Ticket");
        getDashboardPage()
                .createTicket("Ticketabc","Descriptionasdoljisa","test.jpg")
                .verifyTicketSentSuccess("Ticket has been sent successfully");
    }
}
