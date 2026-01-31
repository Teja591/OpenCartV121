package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseClass  {
    public static WebDriver driver;
    public Logger logger= LogManager.getLogger(this.getClass());//log4j
    public Properties prop;
    @BeforeClass(groups={"Regression","Master","DataDriven","sanity"})
    @Parameters({"os","browser"})
    public void setup(@Optional(value ="windows") String os, @Optional(value = "chrome") String browser) throws IOException {
        //Loading config.properties file
        FileReader fileReader = new FileReader("./src//test//resources//config.properties");
        prop = new Properties();
        prop.load(fileReader);

        if (prop.getProperty("execution_env").equalsIgnoreCase("remote"))
        {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            } else {
                System.out.println("No Matching os");
                return;
            }
            // Browser
            switch (browser.toLowerCase()) {
                case "chrome": capabilities.setBrowserName("chrome");break;
                case "firefox": capabilities.setBrowserName("firefox");break;
                case "edge": capabilities.setBrowserName("edge");break;
                default: System.out.println("No Matching browser");return;
            }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        }
        if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (browser.toLowerCase()) {
                case "chrome": driver = new ChromeDriver();break;
                case "firefox": driver = new FirefoxDriver();break;
                case "edge": driver = new EdgeDriver();break;
                default: System.out.println("No Matching browser");return;
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.get("https://tutorialsninja.com/demo/?utm_source=chatgpt.com");
        driver.get(prop.getProperty("appURL"));
    }
    @AfterClass(groups={"Regression","Master","DataDriven","sanity"})
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    public String randomString()
    {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber()
    {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric()
    {
        return RandomStringUtils.randomAlphanumeric(4);
    }

    /*public String captureScreen(String tname) throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        TakesScreenshot takeScreenshot = (TakesScreenshot)driver;
        File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath=System.getProperty("user.dir")+"screenshots\\"+tname+"_"+timeStamp+".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);
        return targetFilePath;
    }*/
}
