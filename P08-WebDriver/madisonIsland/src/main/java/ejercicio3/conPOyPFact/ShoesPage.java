package ejercicio3.conPOyPFact;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;

public class ShoesPage {
    WebDriver driver;
    String myHandleId;
    @FindBy(xpath = "//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div[2]/div[2]/div[3]/ul/li[5]/div/div[2]/ul/li[2]/a")
    WebElement wingtipShoe;
    @FindBy(xpath = "//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div[2]/div[2]/div[3]/ul/li[6]/div/div[2]/ul/li[2]/a")
    WebElement suedeShoe;
    @FindBy(css = "button[title='Compare']")
    WebElement buttonCompare;
    @FindBy(xpath = "//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div[3]/div/div[2]/div/a")
    WebElement clearAll;
    @FindBy(xpath = "//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div[2]/div[2]/ul/li")
    WebElement message;

    public ShoesPage(WebDriver driver) {
        this.driver = driver;
        //obtenemos el manejador de la ventana ShoesPage
        myHandleId = driver.getWindowHandle();
    }

    public void selectShoeToCompare(int number) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        switch (number) {
            case 5:
                jse.executeScript("arguments[0].scrollIntoView();", wingtipShoe);
                wingtipShoe.click();
                break;
            case 6:
                jse.executeScript("arguments[0].scrollIntoView();", suedeShoe);
                suedeShoe.click();
                break;
        }
    }

    public ProductComparisonPage compareShoes() {
        //pulsamos sobre el botón para hacer la comparación
        buttonCompare.click(); //se abre una nueva ventana

        //el handleID de la nueva ventana se añade al conjunto de manejadores del navegador
        Set<String> setIds = driver.getWindowHandles();
        String[] handleIds = setIds.toArray(new String[setIds.size()]);
        System.out.println("ID 0: "+handleIds[0]); //manejador de la ventana ShoesPage
        System.out.println("ID 1: "+handleIds[1]); //manejador de la venana ProductComparisonPage

        ProductComparisonPage poCompare = PageFactory.initElements(driver, ProductComparisonPage.class);
        poCompare.myHandleIdFrom = handleIds[0];
        poCompare.myHandleId = handleIds[1];

        driver.switchTo().window(poCompare.myHandleId);
        return poCompare;
    }

    public String clearAll() {
        clearAll.click();
        Alert alert = driver.switchTo().alert();
        String message = alert.getText();
        alert.accept();
        return message;
    }

    public String getMessage() {
        return message.getText();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
