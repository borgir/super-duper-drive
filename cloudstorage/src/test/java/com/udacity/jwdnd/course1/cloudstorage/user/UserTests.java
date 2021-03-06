package com.udacity.jwdnd.course1.cloudstorage.user;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.concurrent.TimeUnit;


/**
 * Tests for User Signup, Login, and Unauthorized Access Restrictions.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    private SignupPage signupPage;

    private LoginPage loginPage;

    private HomePage homePage;


    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }


    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.signupPage = new SignupPage(driver);
        this.loginPage = new LoginPage(driver);
        this.homePage = new HomePage(driver);
    }


    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
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


    /**
     * Tests if a not authenticated user can access the login page.
     */
    @Test
    public void shouldAccessLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }


    /**
     * Tests if a not authenticated user can access the signup page.
     */
    @Test
    public void shouldAccessSignupPage() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }


    /**
     * Tests if a not authenticated user can access the home page.
     */
    @Test
    public void shouldNotAccessHomePage() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
    }


    /**
     * Tests if a user can signup, authenticate and access the home page.
     * Then the logout is preformed and the application tests if the user can still access the home page.
     */
    @Test
    public void shouldSignUpLoginLogout() throws InterruptedException {
        signupUser();
        authenticateUser();

        TimeUnit.SECONDS.sleep(1);

        Assertions.assertEquals("Home", driver.getTitle());

        homePage.logOutUser();

        TimeUnit.SECONDS.sleep(1);

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
    }

}
