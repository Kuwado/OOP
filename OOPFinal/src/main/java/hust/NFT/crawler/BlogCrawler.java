package hust.NFT.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import hust.NFT.datatype.Blog;
import hust.NFT.datatype.BlogSection;
import hust.NFT.storage.BlogStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlogCrawler {
    private static final BlogStorage blogStorage = new BlogStorage();
    private static final String BASE_URL = "https://www.nftically.com/blog/page/";
    private static int currentId = 1; // Biến tăng dần

    // List Blog sau khi đã crawl
    public static List<Blog> crawlBlogs(int totalPages) {
        List<Blog> blogList = new ArrayList<>();

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Đang quét trang " + currentPage + "...");
            List<String> listAuthor = crawlBlogAuthorsFromPage(currentPage);
            List<String> blogUrls = crawlBlogUrlsFromPage(currentPage);

            // Duyệt qua từng URL của blog để lấy thông tin chi tiết và tag
            for (int i = 0; i < blogUrls.size(); i++) {
                Blog blog = crawlBlogDetails(blogUrls.get(i), listAuthor.get(i));
                blogList.add(blog);
            }
        }

        return blogList;
    }

    // List URL trong trang hiện tại
    private static List<String> crawlBlogUrlsFromPage(int page) {
        List<String> blogUrls = new ArrayList<>();

        try {
            String url = BASE_URL + page + "/";
            Document document = Jsoup.connect(url).get();

            Elements blogElements = document.select(".blog-item");

            for (Element blogElement : blogElements) {
                // Trích xuất URL của mỗi blog và thêm vào danh sách
                String blogUrl = blogElement.select(".blog-title a").attr("href");
                blogUrls.add(blogUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return blogUrls;
    }

    // List tác giả trong trang hiện tại
    private static List<String> crawlBlogAuthorsFromPage(int page) {
        List<String> listAuthor = new ArrayList<>();

        try {
            String url = BASE_URL + page + "/";
            Document document = Jsoup.connect(url).get();

            Elements blogElements = document.select(".blog-item");

            for (Element blogElement : blogElements) {
                // Trích xuất tác giả của mỗi blog và thêm vào danh sách
                Element authorElement = blogElement.select(".author span").first();
                String authorName = authorElement.text();
                listAuthor.add(authorName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listAuthor;
    }

    // Crawl dữ liệu chi tiết trong mỗi bài viết
    private static Blog crawlBlogDetails(String blogUrl, String author) {
        Blog blog = null;

        try {
            Document blogDocument = Jsoup.connect(blogUrl).get();

            // Trích xuất thông tin chi tiết và tag từ blogDocument
            String title = blogDocument.select(".page-title").text();
            // Lấy phần tử li chứa ngày
            Element dateElement = blogDocument.select(".blog-date li").last();

            // Lấy text của phần tử li
            String date = dateElement.text();
            List<BlogSection> sections = extractBlogDetails(blogDocument);
            List<String> tags = extractBlogTags(blogDocument);

            blog = new Blog(currentId++, title, date, sections, tags, author);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return blog;
    }

    // Crawl các blog tags
    private static List<String> extractBlogTags(Document blogDocument) {
        List<String> tags = new ArrayList<>();

        Elements tagElements = blogDocument.select(".blog-tags a .saspot-label");

        for (Element tagElement : tagElements) {
            tags.add(tagElement.text());
        }

        return tags;
    }

    // Crawl nội dung bài viết
    private static List<BlogSection> extractBlogDetails(Document blogDocument) {
        List<BlogSection> sections = new ArrayList<>();

        Elements blogDetailElements = blogDocument.select(".blog-detail-wrap");

        for (Element blogDetailElement : blogDetailElements) {
            Elements headingElements = blogDetailElement.select(".wp-block-heading");
            Elements contentElements = blogDetailElement.select("p");

            // Lặp qua tất cả các heading và content
            for (int i = 0; i < Math.min(headingElements.size(), contentElements.size()); i++) {
                String blogHeading = headingElements.get(i).text();
                String content = contentElements.get(i).text();
                sections.add(new BlogSection(blogHeading, content));
            }
        }

        return sections;
    }

    public static void main(String[] args) {
        int totalPages = 30; // Số trang cần quét
        List<Blog> blogs = crawlBlogs(totalPages);
        for (Blog blog : blogs) {
            System.out.println("Blog ID: " + blog.getId());
            System.out.println("Title: " + blog.getBlog());
            System.out.println("Date: " + blog.getDate());

            // In ra thông tin từ các blog sections
            List<BlogSection> sections = blog.getText();
            for (BlogSection section : sections) {
                System.out.println("Blog Heading: " + section.getBlogHeading());
                System.out.println("Content: " + section.getContent());
            }

            // In ra tags
            List<String> tags = blog.getTags();
            System.out.println("Tags: " + String.join(", ", tags));
            System.out.println("Author: " + blog.getAuthor());

            System.out.println("------------------------------------");
        }
        try {
            blogStorage.writeJson(blogs, "src/main/resources/json/blog.json");
            System.out.println("Đã chuyển thành file JSON thành công.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
