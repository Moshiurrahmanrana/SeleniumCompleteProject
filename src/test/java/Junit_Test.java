import com.sun.media.sound.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Junit_Test {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--headed");
        driver = new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    //    MultiWindows
    @Test
    public void MulWindows() {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id(("windowButton"))).click();
//Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String text = driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("This is a sample page"));
            }
        }
    }

    //Modal Dialog
    @Test
    public void modalDialog() throws InterruptedException {
        driver.get("https://demoqa.com/modal-dialogs");
        driver.findElement(By.id("showSmallModal")).click();
        Thread.sleep(8000);
        driver.findElement(By.id("closeSmallModal")).click();
    }

    //WebTable
    @Test
    public void webTables() {
        driver.get("https://demoqa.com/webtables");
        driver.findElement(By.id("addNewRecordButton")).click();
        driver.findElement(By.id("firstName")).sendKeys("Moshiur");
        driver.findElement(By.id("lastName")).sendKeys("Rahman");
        driver.findElement(By.id("userEmail")).sendKeys("moshiur19121997@gmail.com");
        driver.findElement(By.id("age")).sendKeys("25");
        driver.findElement(By.id("salary")).sendKeys("30000");
        driver.findElement(By.id("department")).sendKeys("Testing");
        driver.findElement(By.id("submit")).click();
    }

    //ScrapData
    @Test
    public void scrapData() {
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr"));
        int i = 0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num[" + i + "] " + cell.getText());
            }
        }
    }

    //upload Image
    @Test
    public void uploadImage() {
        driver.get("https://demoqa.com/upload-download");
        WebElement uploadElement = driver.findElement(By.id("uploadFile"));
        uploadElement.sendKeys("E:\\photo\\diu.jpg");
        String text = driver.findElement(By.id("uploadedFilePath")).getText();
        Assert.assertTrue(text.contains("diu.jpg"));
    }

    //handleIframe
    @Test
    public void handleIframe() {
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();
    }

    //MouseHover
    @Test
    public void mouseHover() throws InterruptedException {
        driver.get("https://green.edu.bd/");
        WebElement AboutUs = driver.findElement(By.className("dropdown-toggle"));
        Actions actions = new Actions(driver);
        actions.moveToElement(AboutUs).perform();
        Thread.sleep(6000);
        driver.findElement(By.xpath("//*[@id=\"menu-item-360\"]/a")).click();
    }

    //keyboardEvents
    @Test
    public void keyboardEvents() throws InterruptedException {
        driver.get("https://www.google.com/");
        WebElement searchElement = driver.findElement(By.name("q"));
        Actions action = new Actions(driver);
        action.moveToElement(searchElement);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("Selenium Webdriver")
                .keyUp(Keys.SHIFT)
                .doubleClick()
                .contextClick()
                .perform();
        Thread.sleep(5000);
    }

    //takeScreenshot
    @Test
    public void takeScreenShot() throws IOException {
        driver.get("https://demoqa.com");
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(screenshotFile, DestFile);
    }

    //readExcelFile
    @Test
    public void readExcelFile() throws IOException, InvalidFormatException {
        String filePath = ".\\src\\test\\resources";
        Utils.readFromExcel(filePath, "sampleFile.xls", "Sheet1");
    }

    //Google Image check
    @Test
    public void GoogleImage() {
        driver.get("https://www.google.com/");
        List<WebElement> list = driver.findElements(By.className("gb_f"));
        list.get(1).click();
        driver.findElement(By.className("ZaFQO")).click();
        driver.findElement(By.xpath("//*[@id=\"dRSWfb\"]/div/a")).click();
        WebElement mainMenu = driver.findElement(By.id("awyMjb"));
        Actions actions = new Actions(driver);
        actions.click(mainMenu).perform();
        mainMenu.sendKeys("E:\\photo\\IMG_8001.JPG");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Google Search"));

    }

    //Gmail Signup process
    @Test
    public void GmailSign() {
        driver.get("https://www.gmail.com");
        driver.findElement(By.id("identifierId")).sendKeys("email1@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button/span")).click();
        driver.findElement(By.name("password")).sendKeys("demo1234");
        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button/span")).click();
        driver.findElement(By.xpath("//*[@id=\":7m\"]/div/div")).click();
        driver.findElement(By.xpath("//*[@id=\":13k\"]")).sendKeys("Email2@gmail.com");
        driver.findElement(By.xpath("//input[@id=':ka']")).sendKeys("send a mail");
        driver.findElement(By.xpath("//div[@id=':lg']")).sendKeys("Hello this is a demo message");
        driver.findElement(By.xpath("//*[@id=\":12s\"]")).click();
        driver.get("https://www.gmail.com");
        driver.findElement(By.id("identifierId")).sendKeys("Email2@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button/span")).click();
        driver.findElement(By.name("password")).sendKeys("pass1234");
        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button/span")).click();
        List<WebElement> list = driver.findElements(By.className("qj "));
        list.get(0).click();
        String text = list.get(0).getText();
        Assert.assertTrue(text.contains("Hello this is a demo message"));

    }

    @After
    public void stop() {
        driver.close();
    }
}
