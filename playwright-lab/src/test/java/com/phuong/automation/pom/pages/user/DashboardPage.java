package com.phuong.automation.pom.pages.user;

import com.microsoft.playwright.Locator;
import com.phuong.automation.keywords.WebKeyword;
import com.phuong.automation.managers.PageManager;
import com.phuong.automation.pom.pages.BasePage;
import com.phuong.automation.utils.LogUtils;
import org.testng.Assert;

public class DashboardPage extends BasePage {
    private String buttonCloseSubscribe = "button[data-key=\"website-popup\"]";
    private String buttonAcceptCookie = "button[class*='aiz-cookie-accept']";
    private String divTotalCartProduct = "//div[normalize-space()='in your cart']/preceding-sibling::div";
    private String divTotalWishlistProduct = "//div[normalize-space()='in your wishlist']/preceding-sibling::div";
    private String divTotalOrderedProduct = "//div[normalize-space()='you ordered']/preceding-sibling::div";
    private String rowPurchaseHistory = "table[class*='aiz-table'] >> tr" ;
    private String buttonCreateTicket = "div[data-target='#ticket_modal']";
    private String inputSubject = "input[placeholder='Subject']";
    private String inputDescription = "textarea[placeholder='Type your reply']";
    //private String inputAttachment = "input[name='attachments']";
    private String buttonBrowse = "button:has-text('Browse')";
    private String linkUploadNew = "a:has-text('Upload New')";
    private String linkSelectFile = "a:has-text('Select File')";
    private String buttonUploadNew = ".uppy-Dashboard-dropFilesTitle >> input[type='file']";
    private String buttonAddFile = "button:has-text('Add File')";
    private String divFirstImage = ".aiz-uploader-select >> nth=0";
    private String buttonSendTicket = "button:has-text('Send Ticket')";
    private String alertMessage = "//div[@role='alert']";

    public DashboardPage closePopup(){
        WebKeyword.click(buttonCloseSubscribe);
        WebKeyword.click(buttonAcceptCookie);
        return this;
    }

    public DashboardPage verifyNumProductInCart(int number){
        String value = WebKeyword.innerText(divTotalCartProduct)
                .replaceAll("[^0-9]", "");
        WebKeyword.getSoftAssert().assertEquals(Integer.parseInt(value), number);
        return this;
    }
    public DashboardPage verifyNumProductInWishList(int number){
        String value = WebKeyword.innerText(divTotalWishlistProduct)
                .replaceAll("[^0-9]", "");
        WebKeyword.getSoftAssert().assertEquals(Integer.parseInt(value), number);
        return this;
    }
    public DashboardPage verifyNumProductOrdered(int number){
        String value = WebKeyword.innerText(divTotalOrderedProduct)
                .replaceAll("[^0-9]", "");
        WebKeyword.getSoftAssert().assertEquals(Integer.parseInt(value), number);
        return this;
    }
    public DashboardPage clickMenuItem(String menuName){
        String selector = String.format(".aiz-side-nav-link:has-text('%s'):visible", menuName);
        WebKeyword.click(selector);
        return this;
    }
    public DashboardPage verifyNumProductInPurchaseHistory(int number){
        int value = WebKeyword.countElement(rowPurchaseHistory);
        WebKeyword.getSoftAssert().assertEquals(number, value);
        return this;
    }
    public DashboardPage createTicket(String subject, String description, String fileName) {
        WebKeyword.click(buttonCreateTicket);
        WebKeyword.fill(inputSubject,subject);
        WebKeyword.fill(inputDescription, description);

        /*WebKeyword.click(buttonBrowse);
        WebKeyword.click(linkUploadNew);
        String projectPath = System.getProperty("user.dir");
        String fullPath = projectPath + "/src/test/resources/test-data/" + fileName;
        WebKeyword.uploadFile(buttonUploadNew,fullPath);
        WebKeyword.sleep(1);
        WebKeyword.click(linkSelectFile);
        WebKeyword.click(divFirstImage);
        WebKeyword.click(buttonAddFile);*/

        WebKeyword.click(buttonSendTicket);
        return this;
    }
    public DashboardPage verifyTicketSentSuccess(String message){
        Assert.assertEquals(WebKeyword.textContent(alertMessage), message);
        return this;
    }
}
