package hust.NFT.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hust.NFT.datatype.Tweet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TweetStorage implements Storage<Tweet> {
	
	@Override
    public ObservableList<Tweet> readJson(String filePath) throws IOException, ParseException {
		ObservableList<Tweet> twtext = FXCollections.observableArrayList();
        ObjectNode tweetObject = reader.forType(new TypeReference<ObjectNode>() {}).readValue(new File(filePath));
        ArrayNode arrayNode = tweetObject.withArray("tweets");
        for (JsonNode node : arrayNode) {
            int id = (node.get("ID").asInt());
        	String name = (node.get("Author").isNull())?"":node.get("Author").asText();
            String timeValue = (node.get("Time").isNull())?"":changeTime(node.get("Time").asText());
            String tweetText = (node.get("Tweet").isNull())?"":node.get("Tweet").asText();
            String cmt = (node.get("Number of comments").asText().equals(""))?"0":node.get("Number of comments").asText();
            String retweet = (node.get("Number of retweets").asText().equals(""))?"0":node.get("Number of retweets").asText();
            String like = (node.get("Number of likes").asText().equals(""))?"0":node.get("Number of likes").asText();
            String view = (node.get("Number of views").asText().equals(""))?"0":node.get("Number of views").asText();
            List<String> hashtags = new ArrayList<>();
            JsonNode hashtagNode = node.path("Hashtags");
            if (hashtagNode.isArray()) {
                hashtags = mapper.convertValue(hashtagNode, new TypeReference<List<String>>() {});
            }

            twtext.add(new Tweet(name, timeValue, tweetText, cmt, retweet, like, view, hashtags, id));
            
        }
        
        return twtext;
    }
    


    @Override
    public void writeJson(List<Tweet> listObject, String filePath) throws IOException {
        ArrayNode tweetNodes = mapper.createArrayNode();
        for (Tweet tweet : listObject) {
            ObjectNode tweetNode = mapper.createObjectNode();
            tweetNode.put("ID", tweet.getId());
            tweetNode.put("Author", tweet.getAuthor());
            tweetNode.put("Time", tweet.getDate());
            tweetNode.put("Tweet", tweet.getTexts());
            tweetNode.put("Number of comments", tweet.getNumberCmt());
            tweetNode.put("Number of retweets", tweet.getNumberRetweet());
            tweetNode.put("Number of likes", tweet.getNumberLike());
            tweetNode.put("Number of views", tweet.getNumberView());
            ArrayNode hashtagsNode = mapper.createArrayNode();
            for (String hashtag : tweet.getTags()) {
                hashtagsNode.add(hashtag);
            }
            tweetNode.set("Hashtags", hashtagsNode);
            tweetNodes.add(tweetNode);
        }
        ObjectNode tweetObject = mapper.createObjectNode();
        tweetObject.set("tweets", tweetNodes);
        writer.writeValue(new File(filePath), tweetObject);
    }
    
 // Chuyển đổi định dạng MM-dd--yyyy sang yyyy-MM-dd HH:mm:ss
	 	private String changeTime(String time) throws ParseException {
	 		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 		SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	 		
	 			// Chuyển đổi chuỗi thành đối tượng Date
	 		Date newDate = inputFormat.parse(time);

	 		// Định dạng lại LocalDateTime thành chuỗi theo định dạng mới
	 		String newTime = outputFormat.format(newDate);
	 		return newTime;
	 	}
    
    
}
