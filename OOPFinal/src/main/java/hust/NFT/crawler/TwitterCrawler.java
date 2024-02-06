package hust.NFT.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hust.NFT.datatype.Tweet;
import hust.NFT.storage.TweetStorage;

import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TwitterCrawler {
	public static int count = 1;
    public static final TweetStorage twStorage = new TweetStorage();

	// Đăng nhập và tìm kiếm hashtag
    private static WebDriver loginTwitter() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //driver.manage().window().maximize(); // phóng to màn hình
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Lấy link twitter
        driver.get("https://twitter.com"); 

        // Nhập tài khoản
        driver.findElement(By.cssSelector("a[href='/login']")).click();
        driver.findElement(By.name("text")).sendKeys("Kuwado5577");
        driver.findElement(By.name("text")).sendKeys(Keys.ENTER);

        //Nhập mật khẩu
        driver.findElement(By.name("password")).sendKeys("badboy0507");
        driver.findElement(By.name("password")).sendKeys(Keys.ENTER);

        // Tìm kiếm hashtag #NFT
        WebElement searchInput = driver.findElement(By.xpath("//input[@aria-label='Search query']"));
        searchInput.sendKeys("#NFT");
        searchInput.sendKeys(Keys.ENTER);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return driver;
    }

    // Crawl dữ liệu
    private static void crawlData(WebDriver driver, List<Tweet> tweets) {
    	List<WebElement> tweetElements = driver.findElements(By.xpath("//article[@data-testid='tweet']"));

        for (WebElement tweetElement : tweetElements) {
        	String hashtagg = "";
        	String tweetText = tweetElement.findElement(By.xpath(".//div[@data-testid='tweetText']")).getAttribute("textContent");
            String name = tweetElement.findElement(By.xpath(".//div[@data-testid='User-Name']//span")).getText();
            String timeValue = tweetElement.findElement(By.xpath(".//div[@data-testid='User-Name']//time")).getAttribute("datetime");
            timeValue = convertTimeFormat(timeValue);
            String cmt = tweetElement.findElement(By.cssSelector("div[data-testid='reply'] span")).getText();
            String retweet = tweetElement.findElement(By.cssSelector("div[data-testid='retweet'] span")).getText();
            String like = tweetElement.findElement(By.cssSelector("div[data-testid='like'] span")).getText();
            String view = tweetElement.findElement(By.cssSelector("a[href*='/analytics']")).getText();
            List<String> hashtags = extractHashtags(tweetText);
   
            
            for (int j = 0; j < hashtags.size(); j++) {
            	hashtagg += hashtags.get(j) + "\t";
            }
            System.out.println(count + ". Text: ");
            System.out.println(tweetText);
            System.out.println("Name: " + name);
            System.out.println("Time: " + timeValue);
            System.out.println("Comment: " + cmt);
            System.out.println("Retweet: " + retweet);
            System.out.println("Like: " + like);
            System.out.println("View: " + view);

            System.out.println("Hashtags: " + hashtagg);
            System.out.println("-------------------------------------");

            tweets.add(new Tweet(name, timeValue, tweetText, cmt, retweet, like, view, hashtags, count));
            count++;
        }

        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading-spinner")));
    }
    
    // Cuộn trang và lấy dữ liệu
    private static void scrollDownPage(WebDriver driver, int numberOfScrolls, List<Tweet> tweets) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < numberOfScrolls; i++) {
        	// Đào dữ liệu
            crawlData(driver, tweets);
            
        	long initialHeight = (long) js.executeScript("return document.body.scrollHeight");

             // Thực hiện lăn chuột với JavaScript Executor
             js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

             // Sử dụng Selenium Wait để đợi đến khi trang đã hoàn tất tải thêm tweet mới
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
             wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading-spinner")));

            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == initialHeight) {
                 // Không còn nội dung mới, thoát khỏi vòng lặp
                 break;
             }
             
        }
        System.out.println("Đã thu thập dữ liệu xong.");
    }

    // Tách Hashtags từ tweet
    public static List<String> extractHashtags(String text) {
        List<String> hashtags = new ArrayList<>();

        // Sử dụng biểu thức chính quy để tìm các hashtag trong văn bản
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String hashtag = matcher.group();
            hashtags.add(hashtag);
        }

        return hashtags;
    }

    // List tweet sau khi đã hoàn tất crawl
    public static List<Tweet> crawlTweet() {
        List<Tweet> twList = new ArrayList<>();

        WebDriver driver = loginTwitter();
        scrollDownPage(driver, 100, twList);
        driver.quit(); // Now quitting the driver after scraping

        return twList;
    }
    
    // Chuyển đổi thời gian sang múi giờ Việt Nam
    public static String convertTimeFormat(String inputTime) {
        // Chuyển đổi chuỗi thành đối tượng Instant
        Instant instant = Instant.parse(inputTime);

        // Chuyển đổi thành đối tượng DateTimeFormatter để định dạng lại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Chuyển đổi thành thời gian ở múi giờ VN
        return instant.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).format(formatter);
    }


    public static void main(String[] args) {
        TweetStorage twStorage = new TweetStorage();
        try {
            twStorage.writeJson(crawlTweet(), "src/main/resources/json/tweets.json");
            System.out.println("Đã chuyển thành file .json thành công.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
