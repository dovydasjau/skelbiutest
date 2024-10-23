import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
public class testing {
    private WebDriver driver;

    @Parameters({ "browser" })
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("chrome") String browser) {
        // Create driver
        switch (browser) {
            case "chrome":
                driver = new ChromeDriver();
                break;

            default:
                System.out.println("Do not know how to start " + browser + ", starting chrome instead");
                driver = new ChromeDriver();
                break;
        }


        System.out.println("Browser started");
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("Page is opened");

    }

    @Test
    public void webTest0() {
//Open the web site(cookies)
        String url = "https://m.skelbiu.lt/";
        driver.get(url);
        //Accept with cookies
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.xpath("/html/body/div[6]/div[2]/div/div[1]/div/div[2]/div/button[1]")).click();
        //Cookies accept validation
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        boolean isElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//html/body/div[6]/div[2]/div/div[1]/div/div[1]/div[2]/h2")));
        Assert.assertTrue(isElementInvisible);

    }
    @Test
    public void webTest2() {
//Check and validate searching(search bar)
        String url = "https://m.skelbiu.lt/";
        driver.get(url);
        //Accept with cookies
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.xpath("/html/body/div[6]/div[2]/div/div[1]/div/div[2]/div/button[1]")).click();
        driver.findElement(By.id("keywordInputNew")).click();
        driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[2]/input")).sendKeys("Traktorius");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[4]")).click();
        //Search validation
        WebElement message = driver.findElement(By.id("popular_categories_title_keyword"));
        boolean displayed1 = message.isDisplayed();
        String text = message.getText();
        Assert.assertEquals(text, "\"traktorius\"");
        Assert.assertEquals(displayed1, true);

    }
    @Test
    public void webTest3() {
//Check and validate searching(menu clicking)
        String url = "https://m.skelbiu.lt/";
        driver.get(url);
        //Accept with cookies
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.xpath("/html/body/div[6]/div[2]/div/div[1]/div/div[2]/div/button[1]")).click();
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/ul/li[2]/a")).click();
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[3]/div[7]/a")).click();
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[3]/div[10]/a")).click();
        //Wait for add
        WebElement image = driver.findElement(By.xpath("/html/body/img[1]"));
        WebDriverWait imageWait = new WebDriverWait(driver, Duration.ofSeconds(40));
        imageWait.until(ExpectedConditions.visibilityOf(image));
        //Veification
        boolean isDisplayed = image.isDisplayed();
        Assert.assertTrue(isDisplayed);
        //Turn off add
        driver.findElement(By.xpath("/html/body/img[2]")).click();
        //Search verification
        WebElement message = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/a[3]"));
        boolean displayed = message.isDisplayed();
        String text = message.getText();
        Assert.assertEquals(text, "Traktoriai");
        Assert.assertEquals(displayed, true);


    }
    @Test
    public void webTest4() {
//Search and retrieve the ID of add according to search word
        String url = "https://www.skelbiu.lt/skelbimai/1?keywords=samotines+plytos";
        driver.get(url);
        //Accept with cookies
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.id("onetrust-reject-all-handler")).click();
        //Wait until element is visible
        WebElement element = driver.findElement(By.xpath("//*[@id=\"body-container\"]/div[2]/div[1]/ul/li/span"));
        WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(40));
        waiter.until(ExpectedConditions.visibilityOf(element));

        String items = driver.findElement(By.xpath("//*[@id=\"body-container\"]/div[2]/div[1]/ul/li/span")).getText();
        String numberPart = items.replaceAll("[^\\d]", "");
        int itemsNo = Integer.parseInt(numberPart);
        int itemCnt = 0;


        for (int i = 1; i < 201; i++) {

            String url1 = "https://www.skelbiu.lt/skelbimai/" + i + "?keywords=samotines+plytos";
            driver.get(url1);
            if (!driver.getCurrentUrl().equals(url1)) {
                break; //end loop
            }
            for (int y = 1; y <= 27; y++) {
                try {
                    driver.findElement(By.xpath("(//*[@id='items-list-container']/div[" + (i + 1) + "]/div)[" + y + "]")).click();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String adID = driver.findElement(By.cssSelector("#contentArea > div.main-item-container > div.itemscope > div.left-content > div.actions-container > div.block.id")).getText();
                    if (adID.contains("ID")) {
                        itemCnt++;
                        System.out.println(adID);
                    }
                    driver.get("https://www.skelbiu.lt/skelbimai/" + i + "?keywords=samotines+plytos");
                } catch (NoSuchElementException e) {
                }
            }
        }
        System.out.println("Adds: " + itemCnt);
        Assert.assertEquals(itemCnt, itemsNo);
    }
}