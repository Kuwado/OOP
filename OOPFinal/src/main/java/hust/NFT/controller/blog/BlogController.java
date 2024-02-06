package hust.NFT.controller.blog;


import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hust.NFT.controller.MainController;
import hust.NFT.controller.nfts.OpenSeaController;
import hust.NFT.controller.twitter.TwitterController;
import hust.NFT.datatype.Blog;
import hust.NFT.datatype.BlogSection;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BlogController {
	
	private ObservableList<Tweet> tweets = FXCollections.observableArrayList();
	private ObservableList<Blog> blogs = FXCollections.observableArrayList();
	private ObservableList<OpenSea> ops = FXCollections.observableArrayList();
	private ObservableList<NiftyGateway> ngs = FXCollections.observableArrayList();
	private ObservableList<Rarible> ras = FXCollections.observableArrayList();
    private String date;

	
	private ObservableList<TopTag> hotTags = FXCollections.observableArrayList();
	private FilteredList<Blog> filteredBlog = new FilteredList<>(blogs, p -> true);
    private boolean blTable = false;
    private boolean blBlog = false;
    //final String TWEET_DETAIL_FXML_FILE_PATH = "/hust/NFT/controller/fxml/TweetDetail.fxml";

    @FXML
    private Button btnBack;
    
    @FXML
    private Label labelTime;

    @FXML
    private TableView<Blog> tbBlog;
    
    @FXML
    private ScrollPane spBlog;

    @FXML
    private TextArea taBlog;

    @FXML
    private TextField tfTagBlog;

    @FXML
    private TableView<TopTag> tvTagBlog;
    
    @FXML
    private TableColumn<Blog, Integer> colBlogId;

    @FXML
    private TableColumn<TopTag, Integer> colBlogNo;

    @FXML
    private TableColumn<TopTag, String> colBlogTag;

    @FXML
    private TableColumn<Blog, String> colBlogTitle;

    @FXML
    void btnBackPressed(ActionEvent event) {
    	tbBlog.setVisible(true);
    	spBlog.setVisible(false);
    	btnBack.setVisible(false);
    }

    @FXML
    void btnBlogPressed(ActionEvent event) {

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
    	try {
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hust/NFT/controller/fxml/TwitterController.fxml"));
     	    TwitterController controller = new TwitterController(tweets, blogs, ops, ngs, ras, date);
     	    fxmlLoader.setController(controller);
     	    Parent root = fxmlLoader.load();
    		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    		stage.setScene(new Scene(root));
    		stage.setTitle("Tweet");
    		stage.show();
        }catch(IOException e) {
        	e.printStackTrace();
        }
    }

    @FXML
    void tbnByDayPressed(ActionEvent event) {
    	blTable = true;
			try {
				hotTag("day");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
        tvTagBlog.setVisible(blTable);
    }

    @FXML
    void tbnByMonthPressed(ActionEvent event) {
    	blTable = true;
    	try {
			hotTag("month");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tvTagBlog.setVisible(blTable);
    }

    @FXML
    void tbnByWeekPressed(ActionEvent event) {
    	blTable = true;
    	try {
			hotTag("week");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tvTagBlog.setVisible(blTable);
    }

    
  

	public BlogController(ObservableList<Tweet> tweets, ObservableList<Blog> blogs, ObservableList<OpenSea> ops,
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
		
		filteredBlog = new FilteredList<>(blogs, p -> true);
		
        labelTime.setText(date);
        btnBack.setVisible(false);
		
		// bảng chứa blog
    	colBlogId.setCellValueFactory(
    			new PropertyValueFactory<Blog, Integer>("id"));
    	colBlogTitle.setCellValueFactory(
    			new PropertyValueFactory<Blog, String>("blog"));
    	if (blogs != null)
    		tbBlog.setItems(blogs);
    	spBlog.setVisible(blBlog); // ẩn nội dung blog
    	
    	// bảng chứa top tag
    	colBlogNo.setCellValueFactory(
    			new PropertyValueFactory<TopTag, Integer>("id"));
    	colBlogTag.setCellValueFactory(
    			new PropertyValueFactory<TopTag, String>("tag"));
    	if (hotTags != null)
    		tvTagBlog.setItems(hotTags);
    	tvTagBlog.setVisible(blTable); // ẩn bảng top tag
    	
    	// nhập text filter
    	tfTagBlog.textProperty().addListener( new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			filterByTag(newValue);
    			tbBlog.setItems(filteredBlog);
    		}});

    	
    	// set model 
    	tbBlog.getSelectionModel().selectedItemProperty().addListener(
    			(ChangeListener<? super Blog>) new ChangeListener<Blog>() {
    				@Override
    				public void changed(ObservableValue<? extends Blog> observable, Blog oldValue, Blog newValue) {
    					if(newValue != null) {
    						inforBlog(newValue);
    					}
    				}
    			}
    			);
    	
    	
    }
	
		
		// Tìm kiếm tag hot theo type 
		public void hotTag(String type) throws ParseException {
	        Map<String, Integer> hashMap = new HashMap<>();
	        hotTags.clear();
			for (Blog l : blogs) {
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
		if(filteredBlog != null) {
		getFilteredBlog().setPredicate(tweet -> {
			
			// nếu filter trống --> show full media
			if (tag == null || tag.trim().isEmpty()) {
				return true;
			}
			String lowerCaseFilter = tag.toLowerCase();
			return tweet.getHashtag().toLowerCase().contains(lowerCaseFilter);
		});}
	}
	

	public FilteredList<Blog> getFilteredBlog() {
		return filteredBlog;
	}
	
	private void inforBlog(Blog blog) {
		String str = blog.getBlog() + "\n" + "\n";
		for (BlogSection b : blog.getText()) {
			str += b.getBlogHeading() + "\n" + b.getContent();
		}
		taBlog.setText(str);
		spBlog.setVisible(true);
		tbBlog.setVisible(false);
		btnBack.setVisible(true);
	}
	
	
	
}
