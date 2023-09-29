package org.example.tg_bot_ozon.service;

import org.example.tg_bot_ozon.entity.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Parser {
    private Product product;

    public Parser() {
    }

    public Parser(Product product) {
        this.product = product;
    }

    public Product parseProduct(Product product) throws InterruptedException {

            /*System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);*/

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setBrowserName("chrome");
        dc.setCapability(ChromeOptions.CAPABILITY, options);
        java.net.URL url = null;
        try {
            url = new URL("http://selenium-hub:4444/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        WebDriver driver = new RemoteWebDriver(url, dc);

//        driver.manage().window().maximize();

        String article = product.getArticle();
        String URL = "https://www.ozon.ru/product/"+article;
        driver.get(URL);

        try {
            Thread.sleep(1500);

            String productName = driver.findElements(By.tagName("h1")).get(0).getText();

            System.out.println(productName);

            product.setProductName(productName);

            String price;

//                price = driver.findElement(By.className("l4q")).getText();

            try {
                try {
                    price = driver.findElement(By.className("lp9")).getText();
                } catch (WebDriverException e2) {
                    try {
                        price = driver.findElement(By.className("lq0")).getText();
                    } catch (WebDriverException e3) {
                        try {
                            price = driver.findElement(By.className("p8l")).getText();
                        } catch (WebDriverException e4) {
                            price = driver.findElement(By.className("l4q")).getText();
                        }
                    }
                }
            } catch (WebDriverException e1) {
                throw new WebDriverException();
            }

            System.out.println(price);

//            Thread.sleep(1000);

            price = price.replaceAll("\\s+", "");
            try {
                price = price.substring(0, price.indexOf("₽"));
            } catch (
                    StringIndexOutOfBoundsException e) {       // если товара нет в наличии, то ему присваевается цена "-1"
                price = "-1";
            }
            price = price.replaceAll("\\ ","");
            System.out.println(price);
            double doublePrice = Double.parseDouble(price);
            System.out.println(price);
            product.setPrice(doublePrice);
        } catch (IllegalArgumentException e) {
            driver.quit();
            throw new IllegalArgumentException();
        } catch (InterruptedException e) {
            driver.quit();
            throw new InterruptedException();
        } catch (WebDriverException e) {
            driver.quit();
            throw new WebDriverException();
        } catch (IndexOutOfBoundsException e) {
            driver.quit();
            System.out.println(e.getMessage());
            throw new WebDriverException();
        }
        driver.quit();
        return product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
