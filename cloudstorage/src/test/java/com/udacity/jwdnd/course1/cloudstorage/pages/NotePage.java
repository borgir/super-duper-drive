package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NotePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "btn-note-modal")
    private WebElement btnNoteModal;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "btn-add-note")
    private WebElement btnAddNote;

    @FindBy(className = "edit-note")
    private WebElement btnEditNote;

    @FindBy(className = "delete-note")
    private WebElement btnDeleteNote;


    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public WebElement getNoteElementFromDom(WebDriver driver, String cssSelector) {
        return driver.findElement(By.cssSelector(".list-note-item " + cssSelector));
    }


    public void addNote(WebDriver driver, String noteTitle, String noteDescription) {
        this.noteTitle.clear();
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.clear();
        this.noteDescription.sendKeys(noteDescription);
        this.btnAddNote.click();
    }


    public void clickNotesTab() {
        this.navNotesTab.click();
    }


    public void clickAddNoteModalButton() {
        this.btnNoteModal.click();
    }


    public void clickEditNoteButton() {
        this.btnEditNote.click();
    }


    public void clickDeleteNoteButton() {
        this.btnDeleteNote.click();
    }

}
