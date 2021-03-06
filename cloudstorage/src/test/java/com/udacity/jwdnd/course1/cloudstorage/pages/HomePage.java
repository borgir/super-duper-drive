package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {

    @FindBy(id = "btn-logout")
    private WebElement btnLogout;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logOutUser() {
        btnLogout.click();
    }

}
