package ejercicio3.conPOyPFact;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestShoes {
    WebDriver driver;
    ChromeOptions co;
    MyAccountPage poAccount;
    ShoesPage poShoes;
    ProductComparisonPage poCompare;

    @BeforeAll
    static void setDriver() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
        Cookies.storeCookiesToFile("dps44@alu.ua.es", "123456");
    }

    @BeforeEach
    public void setUp() {
        co = new ChromeOptions();
        co.setBrowserVersion("119");

        /*
        boolean headless = Boolean.parseBoolean(System.getProperty("chromeHeadless"));
        co.setHeadless(headless);
        */

        driver = new ChromeDriver(co);

        Cookies.loadCookiesFromFile(driver);

        driver.get("http://demo-store.seleniumacademy.com/customer/account/");

        poAccount = PageFactory.initElements(driver, MyAccountPage.class);
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void compareShoes() {
        //1. Verificar título
        String accountPageTitle = poAccount.getPageTitle();
        assertTrue(accountPageTitle.toLowerCase().contains("my account"));

        //2. Accesories -> Shoes
        poShoes = poAccount.shoesPage();

        //3. Verificar título
        String shoesPageTitle = poShoes.getPageTitle();
        assertTrue(shoesPageTitle.toLowerCase().contains("shoes - accessories"));

        //4. Seleccionar zapatos
        poShoes.selectShoeToCompare(5);
        poShoes.selectShoeToCompare(6);

        //5. Seleccionar botón "COMPARE"
        poCompare = poShoes.compareShoes();

        //6. Verificar título
        String comparePageTitle = poCompare.getPageTitle();
        assertTrue(comparePageTitle.toLowerCase().contains("products comparison list - magento commerce"));

        //7. Cerrar ventana comparativa
        poShoes = poCompare.close();

        //8. Verificar título
        shoesPageTitle = poShoes.getPageTitle();
        assertTrue(shoesPageTitle.toLowerCase().contains("shoes - accessories"));

        //9. Borrar comparativa y verificar ventana de alerta y mensaje
        poShoes.clearAll();

        //1. Verificar mensaje
        String clearAllMessage = poShoes.getMessage();
        assertTrue(clearAllMessage.toLowerCase().contains("the comparison list was cleared."));
    }
}
