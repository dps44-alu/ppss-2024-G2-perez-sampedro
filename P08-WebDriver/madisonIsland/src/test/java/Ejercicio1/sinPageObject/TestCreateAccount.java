package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestCreateAccount {
    WebDriver driver;

    @BeforeAll
    static void setDriver() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://demo-store.seleniumacademy.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }


    @Test
    @Tag("OnlyOnce")
    public void S1_scenario_createAccount_should_create_new_account_in_the_demo_store_when_this_account_does_not_exist() {
        //1. Verificar título
        assertEquals("Madison Island", driver.getTitle());

        //2. Seleccionar Account y el hiperenlace de login
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();
        driver.findElement(By.linkText("Log In")).click();

        //3. Verificar título
        assertEquals("Customer Login", driver.getTitle());

        //4. Seleccionar botón "Create Account"
        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();

        //5. Verificar título
        assertEquals("Create New Customer Account", driver.getTitle());

        //6. Rellenar los campos excepto "Confirmation" y enviar
        driver.findElement(By.name("firstname")).sendKeys("David");
        driver.findElement(By.name("middlename")).sendKeys("Pérez");
        driver.findElement(By.xpath("//*[@id=\"lastname\"]")).sendKeys("Sampedro");
        driver.findElement(By.xpath("//*[@id=\"email_address\"]")).sendKeys("dps44@alu.ua.es");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("123456");
        driver.findElement(By.cssSelector("form#form-validate")).submit();

        //7. Verificar mensaje "This is a required field"
        assertEquals("This is a required field.",
                driver.findElement(By.cssSelector("div#advice-required-entry-confirmation")).getText());

        //8. Rellenar campo faltante y reenviar
        driver.findElement(By.cssSelector("input#confirmation")).sendKeys("123456");
        driver.findElement(By.cssSelector("form#form-validate")).submit();

        //9. Volver a la página anterior
        driver.navigate().back();

        //10. Verificar título
        assertEquals("My Account", driver.getTitle());
    }
}
