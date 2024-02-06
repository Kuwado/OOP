package hust.NFT.datatype;

public class BlogSection {
    private String blogHeading;
    private String content;

    // Constructors, getters, and setters

    // Constructor
    public BlogSection(String blogHeading, String content) {
        this.blogHeading = blogHeading;
        this.content = content;
    }

    // Getters and setters
    public String getBlogHeading() {
        return blogHeading;
    }

    public void setBlogHeading(String blogHeading) {
        this.blogHeading = blogHeading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




}
