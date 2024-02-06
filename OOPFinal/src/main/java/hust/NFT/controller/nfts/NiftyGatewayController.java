package hust.NFT.controller.nfts;

import java.io.IOException;

import hust.NFT.controller.MainController;
import hust.NFT.controller.blog.BlogController;
import hust.NFT.controller.twitter.TwitterController;
import hust.NFT.datatype.*;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.function.Predicate;

public class NiftyGatewayController {
	private ObservableList<Tweet> tweets = FXCollections.observableArrayList();
	private ObservableList<Blog> blogs = FXCollections.observableArrayList();
	private ObservableList<OpenSea> ops = FXCollections.observableArrayList();
	private ObservableList<NiftyGateway> ngs = FXCollections.observableArrayList();
	private ObservableList<Rarible> ras = FXCollections.observableArrayList();
	private String date;

	public NiftyGatewayController(ObservableList<Tweet> tweets, ObservableList<Blog> blogs, ObservableList<OpenSea> ops,
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
    private TableColumn<NiftyGateway, String> colNGSecondaySales;

    @FXML
    private TableView<NiftyGateway> tblNiftyGateway;

    @FXML
    private Button btnRarible;

    @FXML
    private TableColumn<NiftyGateway, String> colNGNofSales;

    @FXML
    private RadioButton radiobBtnByDay;

    @FXML
    private Label labelTime;

    @FXML
    private Button btnOpenSea;

    @FXML
    private ToggleGroup filterCat;

    @FXML
    private TableColumn<NiftyGateway, String> colNGSalesFloor;

    @FXML
    private TableColumn<NiftyGateway, String> colNGTitle;

    @FXML
    private TableColumn<NiftyGateway, String> colNGAvaragePrice;

    @FXML
    private Button btnNiftyGateway;

    @FXML
    private RadioButton radiobBtnByMonth;

    @FXML
    private Button btnNfts;

    @FXML
    private TableColumn<NiftyGateway, String> colNGOwners;

    @FXML
    private TableColumn<NiftyGateway, String> colNGType;

    @FXML
    private TableColumn<NiftyGateway, String> colNGPrimarySales;

    @FXML
    private TextField tfFilter;

    @FXML
    private TableColumn<NiftyGateway, String> colNGId;

    @FXML
    private TableColumn<NiftyGateway, String> colNGItems;

    @FXML
    private RadioButton radiobBtnByWeek;

    @FXML
    private TableColumn<NiftyGateway, String> colNGVolume;
    
    @FXML
    private Button btnHome;
    
    @FXML
    private Button btnBack;
    
    //
    @FXML
    private TableColumn<Blog, Integer> colId;
    
    @FXML
    private TableColumn<Blog, String> colTitle;
    
    @FXML
    private GridPane gpBlog;

    @FXML
    private GridPane gpTweet;
    
    @FXML
    private Pane paneBlog;

    @FXML
    private Pane paneText;
    
    @FXML
    private ScrollPane spBlog;

    @FXML
    private ScrollPane spTweet;

    @FXML
    private TextArea taBlog;

    @FXML
    private TableView<Blog> tbBlog;
    
    @FXML
    private Button btnExit;
    
    @FXML
    void btnExitPressed(ActionEvent event) {
    	tbBlog.setVisible(true);
    	spBlog.setVisible(false);
    	btnExit.setVisible(false);
    }
    
    @FXML
    void btnBackPressed(ActionEvent event) {
    	try {
    		final String STORE_FXML_FILE_PATH = "/hust/NFT/controller/fxml/NiftyGatewayController.fxml";
    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(STORE_FXML_FILE_PATH));
    		NiftyGatewayController viewNGController = new NiftyGatewayController(tweets, blogs, ops, ngs, ras, date);
    		fxmlloader.setController(viewNGController);
    		Parent root = fxmlloader.load();
    		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    		stage.setScene(new Scene(root));
    		stage.setTitle("Nifty Gateway");
    		stage.show();
    	}catch(IOException e) {
			e.printStackTrace();
		}
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
    void btnNftsPressed(ActionEvent event) {

    }

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
    void btnOpenSeaPressed(ActionEvent event) {
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
    void btnRariblePressed(ActionEvent event) {
    	try {
    		final String STORE_FXML_FILE_PATH = "/hust/NFT/controller/fxml/RaribleController.fxml";
    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(STORE_FXML_FILE_PATH));
    		RaribleController viewOSController = new RaribleController(tweets, blogs, ops, ngs, ras, date);
    		fxmlloader.setController(viewOSController);
    		Parent root = fxmlloader.load();
    		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    		stage.setScene(new Scene(root));
    		stage.setTitle("Rarible");
    		stage.show();
    	}catch(IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnNiftyGatewayPressed(ActionEvent event) {

    }
    @FXML
    public void initialize() {
    	
    	labelTime.setText(date);
    	btnExit.setVisible(false);
    	
    	colNGType.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("type"));
    	colNGId.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("id"));
    	colNGTitle.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("title"));
    	colNGVolume.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("volume"));
    	colNGNofSales.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("nofsales"));
    	colNGSalesFloor.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("salesfloor"));
    	colNGAvaragePrice.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("avgprice"));
    	colNGItems.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("items"));
    	colNGOwners.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("owners"));
    	colNGPrimarySales.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("primarysales"));
    	colNGSecondaySales.setCellValueFactory(new PropertyValueFactory<NiftyGateway, String>("secondarysales"));
   

	    radiobBtnByDay.setOnAction(event -> handleRadioButtonSelection("day"));
	    radiobBtnByWeek.setOnAction(event -> handleRadioButtonSelection("week"));
	    radiobBtnByMonth.setOnAction(event -> handleRadioButtonSelection("month"));
	    handleRadioButtonSelection("day");
	    paneText.setVisible(false);
	    btnBack.setVisible(false);
	    tblNiftyGateway.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NiftyGateway>() {
	    	


	    	@Override
			public void changed(ObservableValue<? extends NiftyGateway> arg0, NiftyGateway arg1, NiftyGateway arg2) {
				// TODO Auto-generated method stub
				update(arg2);
			}
	    	
	    });

    }
	void update(NiftyGateway a) {
		if(a == null) {
			btnBack.setVisible(false);
			paneText.setVisible(false);
		}else {
			String key = a.getTitle().toLowerCase();
			ObservableList<Tweet> rltTweets = FXCollections.observableArrayList();
			ObservableList<Blog> rltBlogs = FXCollections.observableArrayList();

			for (Tweet t : tweets) {
				if(t.getTexts().toLowerCase().contains(key) || t.getHashtag().toLowerCase().contains(key)) {
					rltTweets.add(t);
				}
			}
			
			for (Blog b : blogs) {
				if(b.getTexts().toLowerCase().contains(key) || b.getHashtag().toLowerCase().contains(key)) {
					rltBlogs.add(b);
				}
			}
			
			int column = 0;
		    int row = 1;
		    for (Tweet tweet : rltTweets) {
		        try {
		            FXMLLoader fxmlLoader = new FXMLLoader();
		            fxmlLoader.setLocation(getClass().getResource("/hust/NFT/controller/fxml/TweetDetailRelation.fxml"));
		            TweetDetailRelation TDController = new TweetDetailRelation(tweet);
		            fxmlLoader.setController(TDController);
		            AnchorPane anchorPane = new AnchorPane();
		            fxmlLoader.setRoot(anchorPane);
		            anchorPane = fxmlLoader.load();
		            TDController.setDataR(tweet);

		            if (column == 1) {
		                column = 0;
		                row++;
		            }

		            gpTweet.add(anchorPane, column++, row);
		            GridPane.setMargin(anchorPane, new Insets(0, 0, 0, 0));

		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		    }
			
		    colId.setCellValueFactory(
	    			new PropertyValueFactory<Blog, Integer>("id"));
	    	colTitle.setCellValueFactory(
	    			new PropertyValueFactory<Blog, String>("blog"));
	    	if (blogs != null)
	    		tbBlog.setItems(rltBlogs);
	    	spBlog.setVisible(false); // ẩn nội dung blog
			
			tblNiftyGateway.setVisible(false);
			paneText.setVisible(true);
			btnBack.setVisible(true);
			
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
	}
	
	private void inforBlog(Blog blog) {
		String str = blog.getBlog() + "\n" + "\n";
		for (BlogSection b : blog.getText()) {
			str += b.getBlogHeading() + "\n" + b.getContent();
		}
		taBlog.setText(str);
		spBlog.setVisible(true);
		tbBlog.setVisible(false);
		btnExit.setVisible(true);
	}
	
    private void handleRadioButtonSelection(String selectedType) {
            ObservableList<NiftyGateway> copy = FXCollections.observableArrayList();

            for (NiftyGateway os : ngs) {
                if (os.getType().equals(selectedType)) {
                    copy.add(os);
                }
            }
            tblNiftyGateway.setItems(copy);
	    	tfFilter.textProperty().addListener(new ChangeListener<String>() {
	    		@Override
	    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	    			showFilteredMedia(newValue, selectedType);
	    		}
	    	});     
    }
    void showFilteredMedia(String filter, String selectedType) {
        Predicate<NiftyGateway> filterPredicate;
        filterPredicate = media -> media.getTitle().toLowerCase().contains(filter.toLowerCase());
            ObservableList<NiftyGateway> copy = FXCollections.observableArrayList();

            for (NiftyGateway os : ngs) {
                if (os.getType().equals(selectedType)) {
                    copy.add(os);
                }
            }
            FilteredList<NiftyGateway> filteredList = new FilteredList<>(copy, filterPredicate);
            tblNiftyGateway.setItems(filteredList);
    
    }
}
