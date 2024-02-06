package hust.NFT.crawler;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hust.NFT.datatype.OpenSea;
import hust.NFT.storage.OpenSeaStorage;



public class OpenSeaCrawler {
	// Set WebDriver
	private static WebDriver loginOpensea() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //driver.manage().window().maximize(); // phóng to màn hình
   
        return driver;
	}
	
	// Kiểm tra tồn tại
	public static boolean checkExist(List<OpenSea> opss, String id, String type) {
    	for (OpenSea ops : opss) {
    		if(type.equals(ops.getType())) {
    			if (id.equals(ops.getId())){
        			return true;
        		}
    		}
    	}
    	return false;
    }
	
	
	private static void crawlData(WebDriver driver, List<OpenSea> osDatas, String type) {
		String id = "", title = "", volume  = "", change = "", floorprice = "", sales = "", owners = "", items = "", ownerspc = "", itemspc = "";
  
		// Đào dữ liệu
    	List<WebElement> tweetElements = driver.findElements(By.xpath("//a[@data-id='Item']"));
    	for (WebElement tweetElement : tweetElements) {
    		int j = 1;
            String checkId = tweetElement.findElement(By.xpath(".//span[@data-id='TextBody']")).getText();
    		if (!checkExist(osDatas, checkId, type)) {
    			List<WebElement> ops = tweetElement.findElements(By.xpath(".//span[@data-id='TextBody']"));
    	    	//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//span[@data-id='TextBody']")));
    			for(WebElement cell : ops) {
    				switch (j) {
    				case 1:
    					id = (String) ((JavascriptExecutor) driver)
     			               .executeScript("return arguments[0].textContent;", cell);
    					System.out.println("ID: " + id);
    					break;
    				case 2:
    					title = (String) ((JavascriptExecutor) driver)
      			               .executeScript("return arguments[0].textContent;", cell);
    					System.out.println("Collection: " + title);
    					break;
    				case 3:
    					volume = (String) ((JavascriptExecutor) driver)
      			               .executeScript("return arguments[0].textContent;", cell);
    					System.out.println("Volume: " + volume);
    					break;
    				case 4:
    					change = (String) ((JavascriptExecutor) driver)
      			               .executeScript("return arguments[0].textContent;", cell);
    					System.out.println("No of sales: " + change);
    					break;
    				case 5:
    					floorprice = (String) ((JavascriptExecutor) driver)
      			               .executeScript("return arguments[0].textContent;", cell);
    					System.out.println("Sales floor: " + floorprice);
    					break;
    				case 6:
    					sales = (String) ((JavascriptExecutor) driver)
      			               .executeScript("return arguments[0].textContent;", cell);
    					System.out.println("Avarage price: " + sales);
    					break;
    				case 7:
    					// lấy nội dung của phần tử ẩn
    					ownerspc = (String) ((JavascriptExecutor) driver)
    			               .executeScript("return arguments[0].textContent;", cell);
    					System.out.println("Owners_PC: " + ownerspc);
    					break;
    				case 8:
    					// lấy nội dung của phần tử ẩn
    					owners = (String) ((JavascriptExecutor) driver)
    			               .executeScript("return arguments[0].textContent;", cell);
    					owners = owners + " (" + ownerspc + ")";
    					System.out.println("Owners: " + owners);
    					break;
    				case 9:
    					itemspc = (String) ((JavascriptExecutor) driver)
			               .executeScript("return arguments[0].textContent;", cell);    					
    					System.out.println("Items_PC: " + itemspc);
    					break;
    				case 10:
    					items = (String) ((JavascriptExecutor) driver)
			               .executeScript("return arguments[0].textContent;", cell);   
    					items = items + " (" + itemspc + ")";
    					System.out.println("Items: " + items);
    					break;
              
    				default:
    					System.out.println("Lựa chọn không hợp lệ");
    				}
    				j++;
    			}
    			if(!id.equals("")) 	osDatas.add(new OpenSea(id, title, volume, change, floorprice, sales, owners, items, type));
    			System.out.println("------------------------------------");
    		
    		}	
    	}
    	try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();}
    	
	}
	
	private static void scrollDownPage(WebDriver driver, List<OpenSea> osDatas, String type) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set a reasonable timeout
       
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 30; i++) {
        	long initialHeight = (long) js.executeScript("return document.body.scrollHeight");

        	crawlData(driver, osDatas, type);
        	
             // Thực hiện lăn chuột với JavaScript Executor
            js.executeScript("window.scrollBy(0, 1500)");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();}
             // Sử dụng Selenium Wait để đợi đến khi trang đã hoàn tất tải thêm tweet mới
             wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading-spinner")));

            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == initialHeight) {
                 // Không còn nội dung mới, thoát khỏi vòng lặp
                 break;
             }
        }
	}
	
	private static void JumpToOther(List<OpenSea> osDatas, String type) {
		WebDriver driver;
        String url = "", res = "";
	       if(type.equals("week")) {
	           //WebElement button = driver.findElement(By.xpath(".//button[@data-testid='RankingsSelect--chain-option-seven_day_volume']"));
	           //button.click();
	    	   driver = loginOpensea();
	    	   url ="https://opensea.io/rankings/trending?sortBy=seven_day_volume";
	    	   res = "Thu thap xong du lieu week";
	       }else
	      
	       if(type.equals("month")){
	    	   driver = loginOpensea();
	    	   url ="https://opensea.io/rankings/trending?sortBy=thirty_day_volume";
	    	   res = "Thu thap xong du lieu month";
	       }else {
	    	   driver = loginOpensea();
	           url = "https://opensea.io/rankings/trending?sortBy=one_day_volume";
			   res = "Thu thap xong du lieu day";
	       }
			   driver.get(url);
	        
	        try {
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();}
	        
	        scrollDownPage(driver, osDatas, type);
	        System.out.println(res);
	        driver.quit();
	    }
	
		public static List<OpenSea> crawlOpenSea() {
			List<OpenSea> osList = new ArrayList<>();
			//WebDriver driver = loginOpensea();
			JumpToOther(osList, "day");
			JumpToOther(osList, "week");
			JumpToOther(osList, "month");
			System.out.println("Dữ liệu đã được thu thập xong");
	        Collections.sort(osList, Comparator.comparingInt(item -> Integer.parseInt(item.getId())));
			return osList;
		}
	
	public static void main(String[] args) {
		OpenSeaStorage twStorage = new OpenSeaStorage();
        try {
            twStorage.writeJson(crawlOpenSea(), "src/main/resources/json/ops.json");
            System.out.println("Đã chuyển thành file .json thành công.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	

}