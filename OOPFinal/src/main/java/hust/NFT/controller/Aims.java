package hust.NFT.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import hust.NFT.datatype.Blog;
import hust.NFT.datatype.NiftyGateway;
import hust.NFT.datatype.OpenSea;
import hust.NFT.datatype.Rarible;
import hust.NFT.datatype.Tweet;
import hust.NFT.storage.BlogStorage;
import hust.NFT.storage.NiftyGatewayStorage;
import hust.NFT.storage.OpenSeaStorage;
import hust.NFT.storage.RaribleStorage;
import hust.NFT.storage.TweetStorage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Aims extends Application {

	public static ObservableList<Tweet> tweets = FXCollections.observableArrayList();
	public static ObservableList<Blog> blogs = FXCollections.observableArrayList();
	public static ObservableList<OpenSea> ops = FXCollections.observableArrayList();
	public static ObservableList<NiftyGateway> ngs = FXCollections.observableArrayList();
	public static ObservableList<Rarible> ras = FXCollections.observableArrayList();
	private static String date ;

	
	// đọc dữ liệu tweet
	public static void setTweet() {
		TweetStorage tweetstr = new TweetStorage();
		try {
			tweets = tweetstr.readJson("src/main/resources/json/tweets.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	// đọc dữ liệu blog
	public static void setBlog() {
		BlogStorage blogstr = new BlogStorage();
		try {
			blogs = blogstr.readJson("src/main/resources/json/blog.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	// đọc dữ liệu opensea
	public static void setOpenSea() {
		OpenSeaStorage opsstr = new OpenSeaStorage();
		try {
			ops = opsstr.readJson("src/main/resources/json/ops.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	// đọc dữ liệu niftygateway
	public static void setNiftyGateway() {
		NiftyGatewayStorage ngstr = new NiftyGatewayStorage();
		try {
			ngs = ngstr.readJson("src/main/resources/json/niftygateway.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	// đọc dữ liệu rarible
	public static void setRarible() {
		RaribleStorage rastr = new RaribleStorage();
		try {
			ras = rastr.readJson("src/main/resources/json/rarible.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


		@Override
		public void start(Stage primaryStage) {
		    setTweet();
		    setBlog();
		    setOpenSea();
		    setNiftyGateway();
		    setRarible();

			try {
			    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hust/NFT/controller/fxml/MainController.fxml"));
			    MainController controller = new MainController(tweets, blogs, ops, ngs, ras, date);
			    fxmlLoader.setController(controller);
			    Parent root;
				root = fxmlLoader.load();
				primaryStage.setTitle("Main");
				primaryStage.setScene(new Scene(root));
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

    public static void main(String[] args) throws FileNotFoundException, IOException {
    	// Tên tệp JSON và đường dẫn
        String fileName = "src/main/resources/json/date.json";

        // Sử dụng Gson để đọc tệp JSON và chuyển đổi nó thành đối tượng JsonElement
        try (FileReader fileReader = new FileReader(fileName)) {
            JsonElement jsonElement = JsonParser.parseReader(fileReader);

            // Lấy giá trị thời gian từ JsonElement
            date = jsonElement.getAsString();
            
            
        launch(args);
        }
    }
}