package hust.NFT.crawler;
import hust.NFT.datatype.NiftyGateway;
import hust.NFT.storage.NiftyGatewayStorage;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class NiftyGatewayCrawler {
    public static void dataCrawl(List<NiftyGateway> storage) {
        // Đường dẫn đến ChromeDriver 
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Khởi tạo WebDriver
        WebDriver driver = new ChromeDriver();

        // Mở trang web cần crawl
        driver.get("https://www.niftygateway.com/rankings");
        try {
        	
            Thread.sleep(8000); // Chờ 8 giây, có thể cần điều chỉnh theo tốc độ internet và tải trang web
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> period = new ArrayList<String>();
        period.add("Last 24 Hours");
        period.add("Last 7 Days");
        period.add("Last 30 Days");
    	String type = "";
    	String id="";
    	String collection="";
    	String volume = "";
    	String nofsales="";
    	String salesfloor="";
    	String avgprice="";
    	String items="";
    	String owners="";
    	String primarysales="";
    	String secondarysales="";
    	int i = 0;
        // Tìm kiếm các phần tử trên trang web và thu thập thông tin
    	for(String time : period) {
    		if(i==0) {
    			type = "day";
    		}else if(i==1) {
    			type = "week";
    		}else if(i==2) {
    			type = "month";
    		}
    		
    		// list option chọn day, week, month
            List<WebElement> typeDay = driver.findElements(By.cssSelector("[data-testid='component-button']"));
            for(WebElement item : typeDay) {
            	if(item.getText().equals(time)) {
            		item.click();
            		break;
            	}
            }
            try {
            	
                Thread.sleep(5000); // Chờ 5 giây, có thể cần điều chỉnh theo tốc độ internet và tải trang web
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Lấy 100 pt = 2 trang
	        for(int j = 0; j < 2; j++) {
		        List<WebElement> element = driver.findElements(By.tagName("tr"));
		        for (WebElement item : element) {
		        	int choice = 1;
		        	List<WebElement> data = item.findElements(By.tagName("td"));
		        	for(WebElement cell : data) {
		        		//upload(cell, i);
		                switch (choice) {
		                case 1:
		                	id = cell.getText();
		                    System.out.println("ID: " + cell.getText());
		                    break;
		                case 2:
		                	collection = cell.getText();
		                    System.out.println("Collection: " + cell.getText());
		                    break;
		                case 3:
		                	volume = cell.getText();
		                    System.out.println("Volume: " + cell.getText());
		                    break;
		                case 4:
		                	nofsales = cell.getText();
		                    System.out.println("No of sales: " + cell.getText());
		                    break;
		                case 5:
		                	salesfloor = cell.getText();
		                    System.out.println("Sales floor: " + cell.getText());
		                    break;
		                case 6:
		                	avgprice = cell.getText();
		                    System.out.println("Avarage price: " + cell.getText());
		                    break;
		                case 7:
		                	items = cell.getText();
		                    System.out.println("Items: " + cell.getText());
		                    break;
		                case 8:
		                	owners = cell.getText();
		                    System.out.println("Owners: " + cell.getText());
		                    break;
		                case 9:
		                	primarysales = cell.getText();
		                	System.out.println("Primary sales: " + cell.getText());
		                	break;
		                case 10:
		                	secondarysales = cell.getText();
		                	System.out.println("Secondary sales: " + cell.getText());
		                	break;
		                default:
		                    System.out.println("Lựa chọn không hợp lệ");
		            }
		        		choice++;
		        	}
		        	if (id != "") {
		        		storage.add(new NiftyGateway(type, id, collection, volume, nofsales, salesfloor, avgprice, items, owners, primarysales, secondarysales));
		        		System.out.println("-------------------------------------");
		        	}
		        }
		        WebElement nextPage = driver.findElement(By.cssSelector("[title ='Next Page']"));
		        nextPage.click();
		        try {
		        	
		            Thread.sleep(5000); // Chờ 5 giây, có thể cần điều chỉnh theo tốc độ internet và tải trang web
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
	        }
	        i++;
	        // back lại web ( kéo lên đầu page )
	        driver.get("https://www.niftygateway.com/rankings");
            try {
                Thread.sleep(3000); // Chờ 3 giây, có thể cần điều chỉnh theo tốc độ internet và tải trang web
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    	}    
        // Đóng trình duyệt
        driver.quit();
    }
    
    public static List<NiftyGateway> runJson() {
        List<NiftyGateway> ngList = new ArrayList<>();
        dataCrawl(ngList);
        ngList.remove(101);
        ngList.remove(202);
        return ngList;
    }
   
    public static void main(String[] args) {
        NiftyGatewayStorage ngStorage = new NiftyGatewayStorage();
        try {
            ngStorage.writeJson(runJson(), "src/main/resources/json/niftygateway.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}