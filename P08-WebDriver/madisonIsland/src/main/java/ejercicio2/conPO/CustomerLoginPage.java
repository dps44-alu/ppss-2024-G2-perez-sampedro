package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomerLoginPage {
    WebDriver driver;
    WebElement userID;
    WebElement password;
    WebElement login;

    public CustomerLoginPage(WebDriver driver) {
        this.driver = driver;
        userID = this.driver.findElement(By.cssSelector("input#email"));
        password = this.driver.findElement(By.cssSelector("input#pass"));
        login = this.driver.findElement(By.xpath("//*[@id=\"send2\"]"));
    }

    public MyAccountPage login(String user, String pass) {
        userID.sendKeys(user);
        password.sendKeys(pass);
        login.click();
        return new MyAccountPage(driver);
    }

    public String loginErr(String user, String pass) {
        userID.sendKeys(user);
        password.sendKeys(pass);
        login.click();
        return driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div/div[2]/ul/li")).getText();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
