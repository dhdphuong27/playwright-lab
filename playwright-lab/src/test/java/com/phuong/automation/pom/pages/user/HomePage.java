package com.phuong.automation.pom.pages.user;

import com.microsoft.playwright.Locator;
import com.phuong.automation.keywords.WebKeyword;

public class HomePage {

    private String divNewProduct = "#section_newest";
    private String divCard = "[data-slick-index]:visible";
    private String buttonWishList = "a[data-title='Add to wishlist']";

    public HomePage addFirstItemToWishlist(){
        WebKeyword.waitForPageLoaded();
        Locator section = WebKeyword.find(divNewProduct);
        Locator card = WebKeyword.find(section, divCard).first();
        Locator wishlistBtn = WebKeyword.find(card, buttonWishList);
        WebKeyword.click(wishlistBtn);

        return this;
    }
}
