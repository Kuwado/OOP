package hust.NFT.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hust.NFT.datatype.NiftyGateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NiftyGatewayStorage implements Storage<NiftyGateway> {

    @Override
    public ObservableList<NiftyGateway> readJson(String filePath) throws IOException {
    	ObservableList<NiftyGateway> niftygateway = FXCollections.observableArrayList();        
    	ObjectNode niftygatewayObject = reader.forType(new TypeReference<ObjectNode>() {
        }).readValue(new File(filePath));
        ArrayNode arrayNode = niftygatewayObject.withArray("niftygateway");
        for (JsonNode node : arrayNode) {
        	String type = (node.has("type") && !node.get("type").isNull()) ? node.get("type").asText() : "";
            String id = (node.has("id") && !node.get("id").isNull()) ? node.get("id").asText() : "";
            String collection = (node.has("collection") && !node.get("collection").isNull()) ? node.get("collection").asText() : "";
            String volume = (node.has("volume") && !node.get("volume").isNull()) ? node.get("volume").asText() : "";
            String nofsales = (node.has("no_of_sales") && !node.get("no_of_sales").isNull()) ? node.get("no_of_sales").asText() : "";
            String salesfloor = (node.has("sales_floor") && !node.get("sales_floor").isNull()) ? node.get("sales_floor").asText() : "";
            String avgprice = (node.has("avg_price") && !node.get("avg_price").isNull()) ? node.get("avg_price").asText() : "";
            String items = (node.has("items") && !node.get("items").isNull()) ? node.get("items").asText() : "";
            String owners = (node.has("owners") && !node.get("owners").isNull()) ? node.get("owners").asText() : "";
            String primarysales = (node.has("primary_sales") && !node.get("primary_sales").isNull()) ? node.get("primary_sales").asText() : "";
            String secondarysales = (node.has("secondary_sales") && !node.get("secondary_sales").isNull()) ? node.get("secondary_sales").asText() : "";
            NiftyGateway ng = new NiftyGateway(type, id, collection, volume, nofsales, salesfloor, avgprice,
                    items, owners, primarysales, secondarysales);
            niftygateway.add(ng);
        }
        return niftygateway;
    }

    @Override
    public void writeJson(List<NiftyGateway> listObject, String filePath) throws IOException {
        ArrayNode ngNodes = mapper.createArrayNode();
        for (NiftyGateway ng : listObject) {
            ObjectNode ngNode = mapper.createObjectNode();
            if(ng.getId() != "") {
            	ngNode.put("type", ng.getType());
            	ngNode.put("id", ng.getId());
            	ngNode.put("collection", ng.getTitle());
            	ngNode.put("volume", ng.getVolume());
            	ngNode.put("no_of_sales", ng.getNofsales());
            	ngNode.put("sales_floor", ng.getSalesfloor());
            	ngNode.put("avg_price", ng.getAvgprice());
            	ngNode.put("items", ng.getItems());
            	ngNode.put("owners", ng.getOwners());
            	ngNode.put("primary_sales", ng.getPrimarysales());
            	ngNode.put("secondary_sales", ng.getSecondarysales());
            	ngNodes.add(ngNode);
            }
        }
        ObjectNode ngObject = mapper.createObjectNode();
        ngObject.set("niftygateway", ngNodes);
        writer.writeValue(new File(filePath), ngObject);
    }
}
