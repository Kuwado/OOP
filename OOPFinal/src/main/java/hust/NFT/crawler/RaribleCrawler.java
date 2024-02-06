package hust.NFT.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import hust.NFT.datatype.Rarible;
import hust.NFT.storage.RaribleStorage;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaribleCrawler{
	
	// Truy cập
    public static WebDriver WebAcess() {
        // Đặt đường dẫn đến trình duyệt ChromeDriver
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
       
        // Khởi tạo đối tượng WebDriver
        WebDriver driver = new ChromeDriver();
        //driver.manage().window().maximize();
       
		return driver;

    }
    
    // Kiểm tra tồn tại
    public static boolean checkExist(List<Rarible> raribles, String id, String type) {
    	for (Rarible rarible : raribles) {
    		if(type.equals(rarible.getType())) {
    			if (id.equals(rarible.getId())){
        			return true;
        		}
    		}
    	}
    	return false;
    }
    
    // Cuộn Trang
    private static void scrollDownPage(WebDriver driver, int numberOfScrolls, List<Rarible> raribles, String type) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String ID = "", Title = "", FloorPrice = "", FloorChange = "", Volume = "", VolumeChange = "", Items = "", Owners = "";
        for (int i = 0; i < numberOfScrolls; i++) {         
            List<WebElement> rows = driver.findElements(By.xpath("//div[@role='row']"));
            for (WebElement row : rows) {
                int choice = 1;
                String checkId = row.findElement(By.className("sc-gueYoa")).getText();
                if (!checkExist(raribles, checkId, type)) {
                List<WebElement> cells = row.findElements(By.xpath(".//div[@role='cell']"));
                for (WebElement cell : cells) {
                	
                	if (choice == 1) {
                		ID = cell.getText();
                        System.out.println("ID: " + ID);
                	}
                	else if(choice == 2) {
                		Title = cell.getText();
                        System.out.println("Title: " + Title);
                	}
                	else if(choice == 3) {
                		FloorPrice = cell.getText();
                        System.out.println("Floor Price: " + FloorPrice);
                	}else if(choice == 4) {
                		 FloorChange = cell.getText();
                         System.out.println("Floor Change: " + FloorChange);
                	}else if(choice == 5) {
                		 Volume = cell.getText();
                         System.out.println("Volume: " + Volume);
                	}else if(choice == 6) {
                		VolumeChange = cell.getText();
                        System.out.println("Volume Change: " + VolumeChange);
                	}else if(choice == 7) {
                		 Items = cell.getText();
                         System.out.println("Items: " + Items);
                	}else if(choice == 8) {
                		 Owners = cell.getText();
                         System.out.println("Owners: " + Owners);
                	}
                	
                    choice++;
                }
                raribles.add(new Rarible(ID, Title, FloorPrice, FloorChange, Volume, VolumeChange, Items, Owners, type));
                System.out.println("-----------------------------------------");
            }
            }
            
            // Thực hiện lăn chuột với JavaScript Executor
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void JumpToOther(List<Rarible> raribles, WebDriver driver, String type) {
       String url = "https://rarible.com/explore/ethereum/collections";
	   String res = "Thu thap xong du lieu day";
	   
       if(type.equals("week")) {
    	   url +="?period=WEEK";
    	   res = "Thu thap xong du lieu week";
       }
       if(type.equals("month")){
    	   url +="?period=MONTH";
    	   res = "Thu thap xong du lieu month";
       }
        driver.get(url);
                
        scrollDownPage(driver, 7, raribles, type);
        System.out.println(res);
    }
    
    public static List<Rarible> crawlRarible() {
    	List<Rarible> RaList = new ArrayList<>();
    	WebDriver driver = WebAcess();
    	JumpToOther(RaList, driver, "day");
    	JumpToOther(RaList, driver, "week");
    	JumpToOther(RaList, driver, "month");
    	System.out.println("Dữ liệu đã được thu thập xong");
    	driver.quit();
    	return RaList;
    }
    
    public static void main(String[] args) {
	
    	RaribleStorage RaStorage = new RaribleStorage();
    	try {
    		RaStorage.writeJson(crawlRarible(), "src/main/resources/json/rarible.json");
    		System.out.println("Đã chuyển đổi thành công sang file .Json");
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
    }
    
    
}