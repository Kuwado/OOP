package hust.NFT.controller.nfts;

import hust.NFT.datatype.Tweet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TweetDetailRelation {
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
      
    public TweetDetailRelation(Tweet tweet) {
		super();
		this.setTweet(tweet);
	}

	public void setDataR(Tweet tweet) {
    	this.setTweet(tweet);
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
