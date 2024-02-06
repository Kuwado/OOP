package hust.NFT.storage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hust.NFT.datatype.OpenSea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OpenSeaStorage implements Storage<OpenSea> {

	    @Override
	    public ObservableList<OpenSea> readJson(String filePath) throws IOException {
	    	ObservableList<OpenSea> opensea = FXCollections.observableArrayList();       	        
	    	ObjectNode niftygatewayObject = reader.forType(new TypeReference<ObjectNode>() {
	        }).readValue(new File(filePath));
	        ArrayNode arrayNode = niftygatewayObject.withArray("niftygateway");
	        for (JsonNode node : arrayNode) {
	            String id = (node.has("id") && !node.get("id").isNull()) ? node.get("id").asText() : "";
	            String collection = (node.has("title") && !node.get("title").isNull()) ? node.get("title").asText() : "";
	            String volume = (node.has("volume") && !node.get("volume").isNull()) ? node.get("volume").asText() : "";
	            String change = (node.has("change") && !node.get("change").isNull()) ? node.get("change").asText() : "";
	            String floorprice = (node.has("floorprice") && !node.get("floorprice").isNull()) ? node.get("floorprice").asText() : "";
	            String sale = (node.has("sale") && !node.get("sale").isNull()) ? node.get("sale").asText() : "";
	            String owners = (node.has("owners") && !node.get("owners").isNull()) ? node.get("owners").asText() : "";
	            String items = (node.has("items") && !node.get("items").isNull()) ? node.get("items").asText() : "";
	        	String type = (node.has("type") && !node.get("type").isNull()) ? node.get("type").asText() : "";
	            OpenSea ng = new OpenSea(id, collection, change, volume, floorprice, sale, owners, items, type);
	            opensea.add(ng);
	        }
	        return opensea;
	    }

	    @Override
	    public void writeJson(List<OpenSea> listObject, String filePath) throws IOException {
	        ArrayNode ngNodes = mapper.createArrayNode();
	        for (OpenSea ng : listObject) {
	            ObjectNode ngNode = mapper.createObjectNode();
	            if(ng.getId() != "") {
	            	ngNode.put("id", ng.getId());
	            	ngNode.put("title", ng.getTitle());
	            	ngNode.put("volume", ng.getVolume());
	            	ngNode.put("change", ng.getChange());
	            	ngNode.put("floorprice", ng.getFloorprice());
	            	ngNode.put("sale", ng.getSales());
	            	ngNode.put("owners", ng.getOwners());
	            	ngNode.put("items", ng.getItems());
	            	ngNode.put("type", ng.getType());
	            	ngNodes.add(ngNode);
	            }
	        }
	        ObjectNode ngObject = mapper.createObjectNode();
	        ngObject.set("niftygateway", ngNodes);
	        writer.writeValue(new File(filePath), ngObject);
	    }
}
