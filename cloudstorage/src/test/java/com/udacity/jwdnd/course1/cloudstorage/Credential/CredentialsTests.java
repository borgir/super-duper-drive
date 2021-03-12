package com.udacity.jwdnd.course1.cloudstorage.Credential;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.concurrent.TimeUnit;


/**
 * Tests for Credential Creation, Viewing, Editing, and Deletion.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    private LoginPage loginPage;

    private SignupPage signupPage;

    private CredentialsPage credentialsPage;

    private HomePage homePage;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private EncryptionService encryptionService;




    /**
     * Included the TestInstance anotation above so that this could be an instance method.
     * Reason: needed to perform the signupUser() just once before the tests begin.
     */
    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.signupPage = new SignupPage(driver);
        this.credentialsPage = new CredentialsPage(driver);
        this.loginPage = new LoginPage(driver);
        this.homePage = new HomePage(driver);
        signupUser();
    }


    /**
     * As requested the user must be authenticated before each test
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
     * Tests if a credentials are successfully created, and verifies if it is displayed on the credential's list.
     * Additionally it checks if the shown password is encrypted.
     * @throws InterruptedException
     */
    @Test
    @Order(1)
    public void shouldCreateCredentials() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        this.credentialsPage.clickCredentialsTab();

        TimeUnit.SECONDS.sleep(1);

        this.credentialsPage.clickAddCredentialsModalButton();

        TimeUnit.SECONDS.sleep(1);

        String newUrl = "udacity.com";
        String newUsername = "rm";
        String newPassword = "jghk43";

        this.credentialsPage.addCredentials(driver, newUrl, newUsername, newPassword);

        TimeUnit.SECONDS.sleep(1);

        this.credentialsPage.clickCredentialsTab();

        TimeUnit.SECONDS.sleep(1);

        WebElement credentialsUrlElement = this.credentialsPage.getCredentialsElementFromDom(driver, ".list-credential-url");
        WebElement credentialsUsernameElement = this.credentialsPage.getCredentialsElementFromDom(driver, ".list-credential-username");
        WebElement credentialsPasswordElement = this.credentialsPage.getCredentialsElementFromDom(driver, ".list-credential-password");

        Assertions.assertEquals(newUrl, credentialsUrlElement.getText());
        Assertions.assertEquals(newUsername, credentialsUsernameElement.getText());

        Credential credential = this.credentialService.getCredential(1);
        Assertions.assertEquals(this.encryptionService.encryptValue(newPassword, credential.getKey()), credentialsPasswordElement.getText());

    }




    /**
     * Tests if a set of credentials is successfully edited and verifies if the changes are displayed on the credentials's list.
     * Additionally it checks if the shown password is encrypted.
     * @throws InterruptedException
     */
    @Test
    @Order(2)
    public void shouldUpdateCredentials() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        this.credentialsPage.clickCredentialsTab();

        TimeUnit.SECONDS.sleep(1);

        this.credentialsPage.clickEditCredentialButton();

        TimeUnit.SECONDS.sleep(1);

        String updatedUrl = "udacity_2.com";
        String updatedUsername = "rm_2";
        String updatedPassword = "jghk43_2";

        this.credentialsPage.addCredentials(driver, updatedUrl, updatedUsername, updatedPassword);

        TimeUnit.SECONDS.sleep(1);

        this.credentialsPage.clickCredentialsTab();

        TimeUnit.SECONDS.sleep(1);

        WebElement updatedCredentialsUrlElement = this.credentialsPage.getCredentialsElementFromDom(driver, ".list-credential-url");
        WebElement updatedCredentialsUsernameElement = this.credentialsPage.getCredentialsElementFromDom(driver, ".list-credential-username");
        WebElement updatedCredentialsPasswordElement = this.credentialsPage.getCredentialsElementFromDom(driver, ".list-credential-password");

        Assertions.assertEquals(updatedUrl, updatedCredentialsUrlElement.getText());
        Assertions.assertEquals(updatedUsername, updatedCredentialsUsernameElement.getText());

        Credential credential = this.credentialService.getCredential(1);
        Assertions.assertEquals(this.encryptionService.encryptValue(updatedPassword, credential.getKey()), updatedCredentialsPasswordElement.getText());

    }




    /**
     * Tests if an existing set of credentials is successfully deleted and verifies that the it is no longer displayed on the credential's list.
     * @throws InterruptedException
     */
    @Test
    @Order(3)
    public void shouldDeleteNote() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        this.credentialsPage.clickCredentialsTab();

        TimeUnit.SECONDS.sleep(1);

        this.credentialsPage.clickDeleteCredentialButton();

        TimeUnit.SECONDS.sleep(1);

        // removes the delete confirmation popup
        Alert alert = driver.switchTo().alert();
        alert.accept();

        Assertions.assertFalse(isElementPresent(".list-credential-url"));
        Assertions.assertFalse(isElementPresent(".list-credential-username"));
        Assertions.assertFalse(isElementPresent(".list-credential-password"));

    }




    /**
     * Checks if an HTML element exists on DOM
     * @param selector
     * @return
     */
    private boolean isElementPresent(String selector){
        try{
            this.credentialsPage.getCredentialElementFromDom(driver, ".list-note-title");
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
