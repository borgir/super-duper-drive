package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class CredentialsPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "btn-credential-modal")
    private WebElement btnCredentialModal;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "btn-add-credentials")
    private WebElement btnAddCredentials;

    @FindBy(className = "edit-credential")
    private WebElement btnEditCredential;

    @FindBy(className = "delete-credential")
    private WebElement btnDeleteCredential;


    public CredentialsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void clickCredentialsTab() {
        this.navCredentialsTab.click();
    }


    public void clickAddCredentialsModalButton() {
        this.btnCredentialModal.click();
    }


    public void addCredentials(WebDriver driver, String url, String username, String password) {
        this.credentialUrl.clear();
        this.credentialUrl.sendKeys(url);
        this.credentialUsername.clear();
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.clear();
        this.credentialPassword.sendKeys(password);
        this.btnAddCredentials.click();
    }


    public WebElement getCredentialsElementFromDom(WebDriver driver, String cssSelector) {
        return driver.findElement(By.cssSelector(".list-credential-item " + cssSelector));
    }


    public void clickEditCredentialButton() {
        this.btnEditCredential.click();
    }


    public void clickDeleteCredentialButton() {
        this.btnDeleteCredential.click();
    }


    public WebElement getCredentialElementFromDom(WebDriver driver, String cssSelector) {
        return driver.findElement(By.cssSelector(".list-credential-item " + cssSelector));
    }

}
