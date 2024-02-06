package hust.NFT.datatype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;


public class Blog extends Text{
    private String blog;
    private List<BlogSection> text;

    // Constructors, getters, and setters

    // Constructor
    public Blog(int id, String blog, String date, List<BlogSection> text, List<String> tags, String author) {
		super();
        this.id = id;
        this.blog = blog;
        this.date = date;
        this.text = text;
        this.tags = tags;
        this.author = author;
        String str = "";
        for (BlogSection t : text) {
			str += t.getBlogHeading() + " " + t.getContent();
		}
        this.texts = str;
    }
    

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

  
    public List<BlogSection> getText() {
        return text;
    }

    public void setText(List<BlogSection> text) {
        this.text = text;
    }

    public String getHashtag() {
    	String ht = "";
    	for (String t : tags) {
    		ht += t + "   ";
    	}
    	return ht;
    }

	@Override
	public String toString() {
		String finalText = "";
		for (BlogSection sec : text) {
			finalText += sec.getBlogHeading() + "\n" + sec.getContent();
		}
		return finalText + "\n-----------------------------";
	}

	@Override
	public String changeTime(String time) throws ParseException {
 		SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
 		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		
 			// Chuyển đổi chuỗi thành đối tượng Date
 		Date newDate = inputFormat.parse(time);

 		// Định dạng lại LocalDateTime thành chuỗi theo định dạng mới
 		return outputFormat.format(newDate);
 	}


	@Override
	public String checkTime(String time, String cdate) {
		
		// Định dạng
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Chuyển đổi chuỗi thành đối tượng LocalDateTime
		LocalDateTime checkTime = LocalDate.parse(time, formatter2).atStartOfDay();
        LocalDateTime currenttime = LocalDateTime.parse(cdate, formatter);

        long days = ChronoUnit.DAYS.between(checkTime, currenttime);
        long weeks = ChronoUnit.WEEKS.between(checkTime, currenttime);
        long months = ChronoUnit.MONTHS.between(checkTime, currenttime);

        if ( currenttime.compareTo(checkTime) > 0 ) {
        	if (days >= 0 && days <= 1) {
        		return "day";
        	} else if (weeks >= 0 && weeks <= 1) {
        		return "week";
        	} else if (months >= 0 && months <= 1) {
        		return "month";
        	} else {
        		return "other";
        	} 
        }
        else return "other";
	}
	
	
			
}
	
    
    

