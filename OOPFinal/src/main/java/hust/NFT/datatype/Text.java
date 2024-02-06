package hust.NFT.datatype;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public abstract class Text {
	public int id;
	public String date;
	public String author;
	public List<String> tags;
	public String texts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public abstract String changeTime(String time)throws ParseException;

	
	public String tostring() {
		return null;
	}
	
	public String checkTime(String time, String cdate) {
		
		// Định dạng
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

        // Chuyển đổi chuỗi thành đối tượng LocalDateTime
        LocalDateTime checkTime = LocalDateTime.parse(time, formatter);
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
	
	
	public boolean checkDate(String time, String type) {
		if(type.equals("day")) {
			if(time.equals("day"))
				return true;
		}
		if(type.equals("week")) {
			if(time.equals("day") || time.equals("week"))
				return true;
		}
		if(type.equals("month")) {
			if(time.equals("day") || time.equals("week") || time.equals("month"))
				return true;
		}
		
		return false;
		
	}
	



}
