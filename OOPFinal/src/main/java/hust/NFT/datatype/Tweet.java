package hust.NFT.datatype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Tweet extends Text{
	private String numberCmt;
	private String numberRetweet;
	private String numberLike;
	private String numberView;
	
	// Constructor
	public Tweet(String author, String date, String texts, String numberCmt, String numberRetweet, String numberLike,
			String numberView, List<String> tags, int id)  {
		super();
		this.author = author;
		this.date = date;
		this.texts = texts;
		this.numberCmt = numberCmt;
		this.numberRetweet = numberRetweet;
		this.numberLike = numberLike;
		this.numberView = numberView;
		this.tags = tags;
		this.id = id;
	}

	//Getter and Setter

	public String getNumberCmt() {
		return numberCmt;
	}

	public void setNumberCmt(String numberCmt) {
		this.numberCmt = numberCmt;
	}

	public String getNumberRetweet() {
		return numberRetweet;
	}

	public void setNumberRetweet(String numberRetweet) {
		this.numberRetweet = numberRetweet;
	}

	public String getNumberLike() {
		return numberLike;
	}

	public void setNumberLike(String numberLike) {
		this.numberLike = numberLike;
	}

	public String getNumberView() {
		return numberView;
	}

	public void setNumberView(String numberView) {
		this.numberView = numberView;
	}
	
	public String getHashtag() {
        StringBuilder hashtagBuilder = new StringBuilder();
        for (String tag : tags) {
            hashtagBuilder.append(tag).append("\t");
        }
        return hashtagBuilder.toString();
    }

    @Override
    public String tostring() {
			return "Tweet: \n" +
			        "Author: " + author + "\n" +
			        "Time: " + date + "\n" +
			        "Text: " + texts + "\n" +
			        "commentCount: " + numberCmt + "\n" +
			        "retweetCount: " + numberRetweet + "\n" +
			        "likeCount: " + numberLike + "\n" +
			        "viewCount: " + numberView + "\n" +
			        "hashtags: " + tags + "\n" +
			        "id: " + id + "\n" +
			        "-------------------------------------------";
		
    }

	@Override
	public String changeTime(String time) throws ParseException {
		SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
 		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		
 			// Chuyển đổi chuỗi thành đối tượng Date
 		Date newDate = inputFormat.parse(time);

 		// Định dạng lại LocalDateTime thành chuỗi theo định dạng mới
 		return outputFormat.format(newDate);
	}
    
	


	
	


	
	
	
	
	
	
}
