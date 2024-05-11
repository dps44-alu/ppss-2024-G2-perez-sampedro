package ejercicio2.conPO;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestLogin2 {
    WebDriver driver;
    ChromeOptions co;
    HomePage poHome;
    CustomerLoginPage poLogin;
    MyAccountPage poAccount;

    @BeforeAll
    static void setDriver() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
    }

    @BeforeEach
    public void setUp() {
        /*
        co = new ChromeOptions();
        co.setBrowserVersion("119");
        driver = new ChromeDriver(co);
        */
        driver = new ChromeDriver();
        poHome = new HomePage(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void S4_scenario_PO_loginOK_should_login_with_success_when_user_account_exists() {
        //1. Verificar título
        String homePageTitle = poHome.getPageTittle();
        assertTrue(homePageTitle.toLowerCase().contains("madison island"));

        //2. Seleccionar Account y el hiperenlace de login
        poLogin = poHome.login();

        //3. Verificar título
        String loginPageTitle = poLogin.getPageTitle();
        assertTrue(loginPageTitle.toLowerCase().contains("customer login"));

        //4. Rellenar campos y enviar
        poAccount = poLogin.login("dps44@alu.ua.es", "123456");

        //1. Verificar título
        String accountPageTitle = poAccount.getPageTitle();
        assertTrue(accountPageTitle.toLowerCase().contains("my account"));
    }

    @Test
    public void S5_scenario_PO_loginFailed_should_fail_when_user_account_not_exists() {
        //1. Verificar título
        String homePageTitle = poHome.getPageTittle();
        assertTrue(homePageTitle.toLowerCase().contains("madison island"));

        //2. Seleccionar Account y el hiperenlace de login
        poLogin = poHome.login();

        //3. Verificar título
        String loginPageTitle = poLogin.getPageTitle();
        assertTrue(loginPageTitle.toLowerCase().contains("customer login"));

        //4. Rellenar campos y enviar
        String messageLoginErr = poLogin.loginErr("dps44@alu.ua.es", "123455");

        //5. Verificar mensaje de error
        assertTrue(messageLoginErr.toLowerCase().contains("invalid login or password."));
    }
}
