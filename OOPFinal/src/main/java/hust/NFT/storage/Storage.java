package hust.NFT.storage;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface Storage<T> {
    ObjectMapper mapper = new ObjectMapper();
    ObjectReader reader = mapper.reader();
    ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    ObservableList<T> readJson(String filePath) throws IOException, ParseException;
    void writeJson(List<T> listObject, String filePath) throws IOException;
}
