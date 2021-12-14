package ru.Litecart;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CheckAddProduct {
    WebDriver driver;
    WebDriverWait wait;

    @Test
    public void checkAddProduct() throws InterruptedException {
        enter();

        driver.findElement(By.cssSelector("[href=\"http://localhost/litecart/admin/?app=catalog&doc=catalog\"]")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("[href=\"http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product\"]")).click();
        Thread.sleep(1000);

        String newItem = "SuperPuperDuck";
        String relativePath = ".Duck.jpg";
        Path filePath = Paths.get(relativePath);
        String absolutePath = filePath.normalize().toAbsolutePath().toString();

        generalPage(newItem, absolutePath);

        informationPage();

        pricesPage();

        driver.findElement(By.cssSelector("[href=\"http://localhost/litecart/en/\"]")).click();
        Thread.sleep(1000);

        checkSuperDuck();

        quit();


    }

    public void enter() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

        driver.manage().window().maximize();
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("remember_me")).click();
        driver.findElement(By.name("login")).click();
    }

    private void generalPage(String item, String path) {

        driver.findElement(By.name("name[en]")).sendKeys(item);

        driver.findElement(By.cssSelector("input[name=status][value='1']")).click();

        driver.findElement(By.name("code")).sendKeys("rp001");

        driver.findElement(By.cssSelector("input[type=checkbox][value='0']")).click();
        driver.findElement(By.cssSelector("input[type=checkbox][value='1']")).click();

        driver.findElement(By.name("quantity")).sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        driver.findElement(By.name("quantity")).sendKeys("50");
        driver.findElement(By.name("new_images[]")). sendKeys(path);

    }

    private void informationPage() throws InterruptedException {
        driver.findElement(By.cssSelector("[href=\"#tab-information\"]")).click();
        Thread.sleep(1000);
        Select manufact = new Select(driver.findElement(By.name("manufacturer_id")));
        manufact.selectByVisibleText("ACME Corp.");

        driver.findElement(By.name("short_description[en]")).sendKeys("SUPER PUPER DUCK is a best bird in " +
                "the world.");

        driver.findElement(By.className("trumbowyg-editor")).sendKeys("This is a typical Russian duck. " +
                "So homey and so farmed. If you buy a model of this duck, " +
                "then all your guests will think that you are a connoisseur of the Russian hinterland" +
                ", that you love nature and agriculture.");
    }

    private void pricesPage() throws InterruptedException {
        driver.findElement(By.cssSelector("[href=\"#tab-prices\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.name("purchase_price")).sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        driver.findElement(By.name("purchase_price")).sendKeys("11");
        Select curr_code = new Select(driver.findElement(By.name("purchase_price_currency_code")));
        curr_code.selectByVisibleText("Euros");

        driver.findElement(By.name("prices[USD]")).sendKeys("17");
        driver.findElement(By.name("save")).click();
    }

    private void checkSuperDuck() {
        List<WebElement> ducks = driver.findElements(By.cssSelector(".product"));

        for (WebElement element : ducks) {
            System.out.println(element.findElement(By.className("name")).getText() + " is present ");
        }
    }

    private void quit() {
        driver.quit();
        driver = null;
    }
}
