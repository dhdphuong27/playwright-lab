package com.phuong.automation.pom.pages.mockweb;

import com.phuong.automation.keywords.WebKeyword;
import com.phuong.automation.pom.pages.BasePage;

public class ClaudeMockPage extends BasePage {
    private String url = "http://localhost:3000/";
    //dùng CSS selector vì webkeyword dùng hàm locator
    private static String inputUsername =   "data-testid=username-input";
    private static String inputEmail =      "data-testid=email-input";
    private static String selectCountry =   "data-testid=country-select";
    private static String buttonDropdown =  "data-testid=custom-dropdown-trigger";
    private static String radioMale =       "data-testid=radio-male";
    private static String radioFemale =     "data-testid=radio-female";
    private static String ratioOther =      "data-testid=ratio-other";
    private static String checkboxJava =    "data-testid=checkbox-java";
    private static String checkboxJS =      "data-testid=checkbox-javascript";
    private static String checkboxPython =  "data-testid=checkbox-python";
    private static String checkboxAuto =    "data-testid=checkbox-automation";
    private static String inputBirthday =   "data-testid=date-picker";
    private static String inputUpload =     "data-testid=file-upload";
    private static String submitButton =    "data-testid=submit-button";

    public ClaudeMockPage open(){
        WebKeyword.navigate(url);
        return this;
    }

    public ClaudeMockPage fillInputText(String username, String email){
        WebKeyword.fill(inputUsername, username);
        WebKeyword.fill(inputEmail, email);
        WebKeyword.sleep(10);
        return this;
    }
    public ClaudeMockPage selectCountry(){
        return this;
    }



}
