package com.phuong.automation.pom.testcases.mockweb;

import com.phuong.automation.common.BaseTest;

import org.testng.annotations.Test;


public class ClaudeMockPageTest extends BaseTest {
    @Test
    public void FillForm() {
        getClaudeMockPage().open();
       getClaudeMockPage().fillInputText("abc", "dhdongphuong27@gmail.com");
       
    }
}
