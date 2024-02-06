package hust.NFT.controller.twitter;

import hust.NFT.datatype.Tweet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TweetDetailController {

    @FXML
    private Label lbTweetDate;

    @FXML
    private Label lbTweetLike;

    @FXML
    private Label lbTweetName;

    @FXML
    private Label lbTweetReply;

    @FXML
    private Label lbTweetRepost;

    @FXML
    private Label lbTweetView;

    @FXML
    private TextArea taTweet;
    
	private Tweet tweet;

	public TweetDetailController(Tweet tweet) {
		super();
	}
	

	public void setData(Tweet tweet) {
    	lbTweetDate.setText(tweet.getDate());
    	lbTweetLike.setText(tweet.getNumberLike());
    	lbTweetName.setText(tweet.getAuthor());
    	lbTweetReply.setText(tweet.getNumberCmt());
    	lbTweetRepost.setText(tweet.getNumberRetweet());
    	lbTweetView.setText(tweet.getNumberView());
    	taTweet.setText(tweet.getTexts());

    }


	public Tweet getTweet() {
		return tweet;
	}


	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
    
    
}
