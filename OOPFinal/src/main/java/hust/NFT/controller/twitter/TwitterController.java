package hust.NFT.controller.twitter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hust.NFT.controller.MainController;
import hust.NFT.controller.blog.BlogController;
import hust.NFT.controller.nfts.OpenSeaController;
import hust.NFT.datatype.Blog;
import hust.NFT.datatype.NiftyGateway;
import hust.NFT.datatype.OpenSea;
import hust.NFT.datatype.Rarible;
import hust.NFT.datatype.TopTag;
import hust.NFT.datatype.Tweet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TwitterController {
	
	private ObservableList<Tweet> tweets = FXCollections.observableArrayList();
	private ObservableList<Blog> blogs = FXCollections.observableArrayList();
	private ObservableList<OpenSea> ops = FXCollections.observableArrayList();
	private ObservableList<NiftyGateway> ngs = FXCollections.observableArrayList();
	private ObservableList<Rarible> ras = FXCollections.observableArrayList();
    private String date;

	
	private ObservableList<TopTag> hotTags = FXCollections.observableArrayList();
	private FilteredList<Tweet> filteredTweet = new FilteredList<>(tweets, p -> true);
    private boolean blTable = false;
    final String TWEET_DETAIL_FXML_FILE_PATH = "/hust/NFT/controller/fxml/TweetDetail.fxml";

    @FXML
    private GridPane gpTwitterController;

    @FXML
    private Label labelTime;

    @FXML
    private TableView<TopTag> tbTwitterController;
    
    @FXML
    private TableColumn<TopTag, String> colHashTagTweet;

    @FXML
    private TableColumn<TopTag, Integer> colNoTweet;

    @FXML
    private TextField tfHashtag;

    @FXML
    void btnBlogPressed(ActionEvent event) {
    	try {
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hust/NFT/controller/fxml/BlogController.fxml"));
     	    BlogController controller = new BlogController(tweets, blogs, ops, ngs, ras, date);
     	    fxmlLoader.setController(controller);
     	    Parent root = fxmlLoader.load();
    		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    		stage.setScene(new Scene(root));
    		stage.setTitle("Blog");
    		stage.show();
        }catch(IOException e) {
        	e.printStackTrace();
        }
    }

    @FXML
    void btnByDayPressed(ActionEvent event) {
    	blTable = true;
    	try {
			hotTag("day");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tbTwitterController.setVisible(blTable);
    }

    @FXML
    void btnByMonthPressed(ActionEvent event) {
    	blTable = true;
    	try {
			hotTag("month");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tbTwitterController.setVisible(blTable);
    }

    @FXML
    void btnByWeekPressed(ActionEvent event) {
    	blTable = true;
    	try {
			hotTag("week");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tbTwitterController.setVisible(blTable);
    }

    @FXML
    void btnHomePressed(ActionEvent event) {
    	try {
    		final String STORE_FXML_FILE_PATH = "/hust/NFT/controller/fxml/MainController.fxml";
    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(STORE_FXML_FILE_PATH));
    		MainController viewhomeController = new MainController(tweets, blogs, ops, ngs, ras, date);
    		fxmlloader.setController(viewhomeController);
    		Parent root = fxmlloader.load();
    		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    		stage.setScene(new Scene(root));
    		stage.setTitle("Main");
    		stage.show();
    	}catch(IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnNftsPressed(ActionEvent event) {
    	try {
    		final String STORE_FXML_FILE_PATH = "/hust/NFT/controller/fxml/OpenSeaController.fxml";
    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(STORE_FXML_FILE_PATH));
    		OpenSeaController viewOSController = new OpenSeaController(tweets, blogs, ops, ngs, ras, date);
    		fxmlloader.setController(viewOSController);
    		Parent root = fxmlloader.load();
    		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    		stage.setScene(new Scene(root));
    		stage.setTitle("OpenSea");
    		stage.show();
    	}catch(IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnTweetPressed(ActionEvent event) {

    }
    
    public TwitterController(ObservableList<Tweet> tweets, ObservableList<Blog> blogs, ObservableList<OpenSea> ops,
			ObservableList<NiftyGateway> ngs, ObservableList<Rarible> ras, String date) {
		super();
		this.tweets = tweets;
		this.blogs = blogs;
		this.ops = ops;
		this.ngs = ngs;
		this.ras = ras;
		this.date = date;
	}

	@FXML
    void initialize() {
        filteredTweet = new FilteredList<>(tweets, p -> true);

        labelTime.setText(date);

    	colNoTweet.setCellValueFactory(
    			new PropertyValueFactory<TopTag, Integer>("id"));
    	colHashTagTweet.setCellValueFactory(
    			new PropertyValueFactory<TopTag, String>("tag"));
    	if (hotTags != null)
    		tbTwitterController.setItems(hotTags);

        tbTwitterController.setVisible(blTable);
        
        int column = 0;
	    int row = 1;
	    for (Tweet tweet : tweets) {
	        try {
	            FXMLLoader fxmlLoader = new FXMLLoader();
	            fxmlLoader.setLocation(getClass().getResource(TWEET_DETAIL_FXML_FILE_PATH));
	            TweetDetailController TDController = new TweetDetailController(tweet);
	            fxmlLoader.setController(TDController);
	            AnchorPane anchorPane = new AnchorPane();
	            fxmlLoader.setRoot(anchorPane);
	            anchorPane = fxmlLoader.load();
	            TDController.setData(tweet);

	            if (column == 1) {
	                column = 0;
	                row++;
	            }

	            gpTwitterController.add(anchorPane, column++, row);
	            GridPane.setMargin(anchorPane, new Insets(0, 0, 0, 0));

	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	
     
    	
    	// nhập text filter
    	tfHashtag.textProperty().addListener( new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			filterByTag(newValue);
    			updateTweetDisplay();
    		}});
    	
    	
    }
    

	// Tìm kiếm tag hot theo type 
	public void hotTag(String type) throws ParseException {
        Map<String, Integer> hashMap = new HashMap<>();
        hotTags.clear();
		for (Tweet l : tweets) {
			List<String> tags = l.getTags();
			if (l.checkDate(l.checkTime(l.getDate(), date), type)) {
				for(String tag : tags) {
					hashMap.put(tag, hashMap.getOrDefault(tag, 0) + 1);
				}
			}
		}
		
		 List<Map.Entry<String, Integer>> list = new LinkedList<>(hashMap.entrySet());

	        // Sử dụng Comparator để sắp xếp theo giá trị giảm dần
		 Collections.sort(list, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

	        // Chuyển danh sách đã sắp xếp thành Map
		 Map<String, Integer> sortedMap = new LinkedHashMap<>();
	        
		 for (Map.Entry<String, Integer> entry : list) {
			 sortedMap.put(entry.getKey(), entry.getValue());
		 }
		 
		 for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			hotTags.add(new TopTag(entry.getValue(), entry.getKey()));
        }

	}

    
	private void filterByTag(String tag) {
		if(filteredTweet != null) {
		getFilteredTweet().setPredicate(tweet -> {
			
			// nếu filter trống --> show full media
			if (tag == null || tag.trim().isEmpty()) {
				return true;
			}
			String lowerCaseFilter = tag.toLowerCase();
			return tweet.getHashtag().toLowerCase().contains(lowerCaseFilter);
		});}
	}
	

	public FilteredList<Tweet> getFilteredTweet() {
		return filteredTweet;
	}

	private void updateTweetDisplay() {
	    // Xóa tất cả các phần tử trong gpTwitterController trước khi thêm lại
	    gpTwitterController.getChildren().clear();
	

	    int column = 0;
	    int row = 1;
	    if (filteredTweet != null) {
	    for (Tweet tweet : filteredTweet) {
	        try {
	            FXMLLoader fxmlLoader = new FXMLLoader();
	            fxmlLoader.setLocation(getClass().getResource(TWEET_DETAIL_FXML_FILE_PATH));
	            TweetDetailController TDController = new TweetDetailController(tweet);
	            fxmlLoader.setController(TDController);
	            AnchorPane anchorPane = new AnchorPane();
	            fxmlLoader.setRoot(anchorPane);
	            anchorPane = fxmlLoader.load();
	            TDController.setData(tweet);

	            if (column == 1) {
	                column = 0;
	                row++;
	            }

	            gpTwitterController.add(anchorPane, column++, row);
	            GridPane.setMargin(anchorPane, new Insets(0, 0, 0, 0));
	            labelTime.setText(date);

	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }}
	}
    
    
    

    

}
