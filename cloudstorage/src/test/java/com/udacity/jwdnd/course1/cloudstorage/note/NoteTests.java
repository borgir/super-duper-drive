package com.udacity.jwdnd.course1.cloudstorage.note;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.concurrent.TimeUnit;


/**
 * Tests for Note Creation, Viewing, Editing, and Deletion.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    private NotePage notePage;

    private LoginPage loginPage;

    private SignupPage signupPage;

    private HomePage homePage;




    /**
     * Included the TestInstance anotation above so that this could be an instance method.
     * Reason: needed to perform the signupUser() just once before the tests begin.
     */
    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.signupPage = new SignupPage(driver);
        this.notePage = new NotePage(driver);
        this.loginPage = new LoginPage(driver);
        this.homePage = new HomePage(driver);
        signupUser();
    }




    /**
     *
     */
    @BeforeEach
    public void beforeEach() {
        authenticateUser();
    }




    /**
     *
     */
    @AfterEach
    public void afterEach() {
        this.homePage.logOutUser();
    }




    /**
     *
     */
    @AfterAll
    public void afterAll() {
        if (driver != null) {
            driver.quit();
        }
    }




    /**
     * Tests if a note is successfully created, and verifies if it is displayed on the note's list.
     * @throws InterruptedException
     */
    @Test
    @Order(1)
    public void shouldCreateNote() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        this.notePage.clickNotesTab();

        TimeUnit.SECONDS.sleep(1);

        this.notePage.clickAddNoteModalButton();

        TimeUnit.SECONDS.sleep(1);

        String newNoteTitle = "Must Test SDD";
        String newNoteDescription = "Must test SDD with Junit and Selenium.";

        this.notePage.addNote(driver, newNoteTitle, newNoteDescription);

        TimeUnit.SECONDS.sleep(1);

        this.notePage.clickNotesTab();

        TimeUnit.SECONDS.sleep(1);

        WebElement noteTitleElement = this.notePage.getNoteElementFromDom(driver, ".list-note-title");
        WebElement noteDescriptionElement = this.notePage.getNoteElementFromDom(driver, ".list-note-description");

        Assertions.assertEquals(newNoteTitle, noteTitleElement.getText());
        Assertions.assertEquals(newNoteDescription, noteDescriptionElement.getText());

    }




    /**
     * Tests if an existing note is successfully edited and verifies if the changes are displayed on the note's list.
     * @throws InterruptedException
     */
    @Test
    @Order(2)
    public void shouldUpdateNote() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        this.notePage.clickNotesTab();

        TimeUnit.SECONDS.sleep(1);

        this.notePage.clickEditNoteButton();

        TimeUnit.SECONDS.sleep(1);

        String updatedNoteTitle = "V2 - Must Test SDD";
        String updatedNoteDescription = "V2 - Must test SDD with Junit and Selenium.";

        this.notePage.addNote(driver, updatedNoteTitle, updatedNoteDescription);

        TimeUnit.SECONDS.sleep(1);

        this.notePage.clickNotesTab();

        TimeUnit.SECONDS.sleep(1);

        WebElement updatedNoteTitleElement = this.notePage.getNoteElementFromDom(driver, ".list-note-title");
        WebElement updatedNoteDescriptionElement = this.notePage.getNoteElementFromDom(driver, ".list-note-description");

        Assertions.assertEquals(updatedNoteTitle, updatedNoteTitleElement.getText());
        Assertions.assertEquals(updatedNoteDescription, updatedNoteDescriptionElement.getText());

    }




    /**
     * Tests if an existing note is successfully deleted and verifies that the note is no longer displayed on the note's list.
     * @throws InterruptedException
     */
    @Test
    @Order(3)
    public void shouldDeleteNote() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        this.notePage.clickNotesTab();

        TimeUnit.SECONDS.sleep(1);

        this.notePage.clickDeleteNoteButton();

        TimeUnit.SECONDS.sleep(1);

        // removes the delete confirmation popup
        Alert alert = driver.switchTo().alert();
        alert.accept();

        Assertions.assertFalse(isElementPresent(".list-note-title"));

        Assertions.assertFalse(isElementPresent(".list-note-description"));

    }




    /**
     * Checks if an HTML element exists on DOM
     * @param selector
     * @return
     */
    private boolean isElementPresent(String selector){
        try{
            this.notePage.getNoteElementFromDom(driver, ".list-note-title");
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }




    /**
     * Signs up a user on the SDD application
     */
    public void signupUser() {
        driver.get("http://localhost:" + this.port + "/signup");
        signupPage.insertUserData("Ricardo", "Miguel", "rm", "lkdjf3");
    }



    
    /**
     * Authenticates a user on the SDD application
     */
    public void authenticateUser() {
        driver.get("http://localhost:" + this.port + "/login");
        loginPage.authenticateUser("rm", "lkdjf3");
    }

}
