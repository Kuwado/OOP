package hust.NFT.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hust.NFT.datatype.Blog;
import hust.NFT.datatype.BlogSection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogStorage implements Storage<Blog> {

    @Override
    public ObservableList<Blog> readJson(String filePath) throws IOException, ParseException {
    	ObservableList<Blog> blogs = FXCollections.observableArrayList();
        ObjectNode blogObject = reader.forType(new TypeReference<ObjectNode>() {}).readValue(new File(filePath));
        ArrayNode arrayNode = blogObject.withArray("blogs");
        for (JsonNode node : arrayNode) {
            int id = (node.has("id") && !node.get("id").isNull()) ? Integer.parseInt(node.get("id").asText()) : 0;
            String blogTitle = (node.has("blog") && !node.get("blog").isNull()) ? node.get("blog").asText() : "";
            String date = (node.has("date") && !node.get("date").isNull()) ? changeTime(node.get("date").asText()) : "";
            List<BlogSection> text = readBlogSections(node);
            List<String> tags = readTags(node);
            String author = (node.has("author") && !node.get("author").isNull()) ? node.get("author").asText() : "";

            Blog blog = new Blog(id, blogTitle, date, text, tags, author);
            blogs.add(blog);
        }
        return blogs;
    }

    @Override
    public void writeJson(List<Blog> listObject, String filePath) throws IOException {
        ArrayNode blogNodes = mapper.createArrayNode();
        for (Blog blog : listObject) {
            ObjectNode blogNode = mapper.createObjectNode();
            blogNode.put("id", blog.getId());
            blogNode.put("blog", blog.getBlog());
            blogNode.put("date", blog.getDate());
            writeBlogSections(blogNode, blog.getText());
            writeTags(blogNode, blog.getTags());
            blogNode.put("author", blog.getAuthor());
            blogNodes.add(blogNode);
        }
        ObjectNode blogObject = mapper.createObjectNode();
        blogObject.set("blogs", blogNodes);
        writer.writeValue(new File(filePath), blogObject);
    }

    private List<BlogSection> readBlogSections(JsonNode node) {
        List<BlogSection> blogSections = new ArrayList<>();
        ArrayNode sectionsNode = (ArrayNode) node.get("text");

        for (JsonNode sectionNode : sectionsNode) {
            String blogHeading = sectionNode.get("blogHeading").asText();
            String content = sectionNode.get("content").asText();

            BlogSection blogSection = new BlogSection(blogHeading, content);
            blogSections.add(blogSection);
        }

        return blogSections;
    }

    private void writeBlogSections(ObjectNode node, List<BlogSection> blogSections) {
        ArrayNode sectionsNode = mapper.createArrayNode();
        for (BlogSection blogSection : blogSections) {
            ObjectNode sectionNode = mapper.createObjectNode();
            sectionNode.put("blogHeading", blogSection.getBlogHeading());
            sectionNode.put("content", blogSection.getContent());
            sectionsNode.add(sectionNode);
        }
        node.set("text", sectionsNode);
    }


    private List<String> readTags(JsonNode node) {
        List<String> tags = new ArrayList<>();
        // Thay đổi từ "tags" thành "blogTags"
        ArrayNode tagsNode = (ArrayNode) node.get("blogTags");
        for (JsonNode tagNode : tagsNode) {
            tags.add(tagNode.asText());
        }
        return tags;
    }

    private void writeTags(ObjectNode node, List<String> tags) {
        ArrayNode tagsNode = mapper.createArrayNode();
        for (String tag : tags) {
            tagsNode.add(tag);
        }
        // Thay đổi từ "tags" thành "blogTags"
        node.set("blogTags", tagsNode);
    }
    
    private String changeTime(String time) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM d, yyyy");
 		SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
 		
 			// Chuyển đổi chuỗi thành đối tượng Date
 		Date newDate = inputFormat.parse(time);

 		// Định dạng lại LocalDateTime thành chuỗi theo định dạng mới
 		String newTime = outputFormat.format(newDate);
 		return newTime;
 	}

}
