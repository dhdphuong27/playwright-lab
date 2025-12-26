package com.phuong.automation.pom.pages.user;

import com.microsoft.playwright.Locator;
import com.phuong.automation.keywords.WebKeyword;
import com.phuong.automation.managers.PageManager;
import com.phuong.automation.pom.pages.BasePage;
import com.phuong.automation.utils.LogUtils;

public class DashboardPage extends BasePage {
    private String buttonCloseSubscribe = "button[data-key=\"website-popup\"]";
    private String divTotalCartProduct = "//div[normalize-space()='in your cart']/preceding-sibling::div";
    private String divTotalWishlistProduct = "//div[normalize-space()='in your wishlist']/preceding-sibling::div";
    private String divTotalOrderedProduct = "//div[normalize-space()='you ordered']/preceding-sibling::div";
    public void verifyNumProductInCart(String number){
        WebKeyword.getSoftAssert().assertEquals(WebKeyword.innerText(divTotalCartProduct)
                .replaceAll("[^0-9]", ""), number);
    }
    public void verifyNumProductInWishList(String number){
        WebKeyword.getSoftAssert().assertEquals(WebKeyword.innerText(divTotalWishlistProduct)
                .replaceAll("[^0-9]", ""), number);
    }
    public void verifyNumProductOrdered(String number){
        WebKeyword.getSoftAssert().assertEquals(WebKeyword.innerText(divTotalOrderedProduct)
                .replaceAll("[^0-9]", ""), number);
    }

}
