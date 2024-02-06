package hust.NFT.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import hust.NFT.crawler.BlogCrawler;
import hust.NFT.crawler.NiftyGatewayCrawler;
import hust.NFT.crawler.OpenSeaCrawler;
import hust.NFT.crawler.RaribleCrawler;
import hust.NFT.crawler.TwitterCrawler;

import hust.NFT.storage.BlogStorage;
import hust.NFT.storage.NiftyGatewayStorage;
import hust.NFT.storage.OpenSeaStorage;
import hust.NFT.storage.RaribleStorage;
import hust.NFT.storage.TweetStorage;

public class CrawlMain {
	public static String date;
	
	public static String getDate() {
		return date;
	}

	public static void setDate(String date) {
		CrawlMain.date = date;
	}

	
	
	public static void main(String[] args) {
        TweetStorage twStorage = new TweetStorage();
        BlogStorage blogStorage = new BlogStorage();
        OpenSeaStorage opsStorage = new OpenSeaStorage();
        NiftyGatewayStorage ngStorage = new NiftyGatewayStorage();
        RaribleStorage raStorage = new RaribleStorage();
        
       
        try {
        	twStorage.writeJson(TwitterCrawler.crawlTweet(), "src/main/resources/json/tweets.json");
            System.out.println("Tweet đã chuyển thành file .json thành công.");
            
            blogStorage.writeJson(BlogCrawler.crawlBlogs(30), "src/main/resources/json/blog.json");
            System.out.println("Blog đã chuyển thành file JSON thành công.");
            
            opsStorage.writeJson(OpenSeaCrawler.crawlOpenSea(), "src/main/resources/json/ops.json");
            System.out.println("OpenSea đã chuyển thành file .json thành công.");
            
            ngStorage.writeJson(NiftyGatewayCrawler.runJson(), "src/main/resources/json/niftygateway.json");
            System.out.println("NiftyGateway đã chuyển thành file .json thành công.");
            
            raStorage.writeJson(RaribleCrawler.crawlRarible(), "src/main/resources/json/rarible.json");
    		System.out.println("Đã chuyển đổi thành công sang file .Json");
    		
    		Date currentTime = new Date();

            // Định dạng chuỗi thời gian theo định dạng mong muốn
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            date = dateFormat.format(currentTime);
            System.out.println(date);

            // Tên tệp JSON và đường dẫn
            String fileName = "src/main/resources/json/date.json";

            // Sử dụng Gson để chuyển đổi chuỗi thành đối tượng JSON
            Gson gson = new Gson();
            String jsonContent = gson.toJson(date);

            // Lưu đối tượng JSON vào tệp
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(jsonContent);
                System.out.println("Date đã được lưu vào tệp JSON thành công.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
