package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestLogin {
    WebDriver driver;
    ChromeOptions co;

    @BeforeAll
    static void setDriver() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
    }

    @BeforeEach
    public void setUp() {
        co = new ChromeOptions();
        co.setBrowserVersion("119");
        driver = new ChromeDriver(co);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://demo-store.seleniumacademy.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void S2_scenario_loginOK_should_login_with_success_when_user_account_exists() {
        //1. Verificar título
        assertEquals("Madison Island", driver.getTitle());

        //2. Seleccionar Account y el hiperenlace de login
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();
        driver.findElement(By.linkText("Log In")).click();

        //3. Verificar título
        assertEquals("Customer Login", driver.getTitle());

        //4. Rellenar campo email y enviar
        driver.findElement(By.cssSelector("input#email")).sendKeys("dps44@alu.ua.es");
        driver.findElement(By.xpath("//*[@id=\"send2\"]")).submit();

        //5. Verificar mensaje "This is a required field."
        assertEquals("",
                driver.findElement(By.cssSelector("div#advice-required-entry-pass")).getText());

        //6. Rellenar campo contraseña y enviar
        driver.findElement(By.cssSelector("input#pass")).sendKeys("123456");
        driver.findElement(By.xpath("//*[@id=\"send2\"]")).submit();

        //1. Verificar título
        assertEquals("My Account", driver.getTitle());
    }

    @Test
    public void S3_scenario_loginFailed_should_fail_when_user_account_not_exists() {
        //1. Verificar título
        assertEquals("Madison Island", driver.getTitle());

        //2. Seleccionar Account y el hiperenlace de login
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();
        driver.findElement(By.linkText("Log In")).click();

        //3. Verificar título
        assertEquals("Customer Login", driver.getTitle());

        //4. Rellenar campos y enviar
        driver.findElement(By.cssSelector("input#email")).sendKeys("dps44@alu.ua.es");
        driver.findElement(By.cssSelector("input#pass")).sendKeys("123455");
        driver.findElement(By.xpath("//*[@id=\"send2\"]")).submit();

        //5. Verificar mensaje "Invalid login or password."
        assertEquals("Invalid login or password.",
                driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div/div[2]/ul/li")).getText());
    }
}
