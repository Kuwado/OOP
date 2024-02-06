package hust.NFT.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hust.NFT.datatype.Rarible;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class RaribleStorage implements Storage<Rarible>  {

	@Override
	public ObservableList<Rarible> readJson(String filePath) throws IOException {
		ObservableList<Rarible> raribles = FXCollections.observableArrayList();       		
		ObjectNode raribleObject = reader.forType(new TypeReference<ObjectNode>() {
        }).readValue(new File(filePath));
		 ArrayNode arrayNode = raribleObject.withArray("rarible");
		 for (JsonNode node : arrayNode) {
			    String ID = (node.has("ID") && !node.get("ID").isNull()) ? node.get("ID").asText() : "";
	            String Title = (node.has("Title") && !node.get("Title").isNull()) ? node.get("Title").asText() : "";
	            String FloorPrice = (node.has("FloorPrice") && !node.get("FloorPrice").isNull()) ? node.get("FloorPrice").asText() : "";
	            String FloorChange = (node.has("FloorChange") && !node.get("FloorChange").isNull()) ? node.get("FloorChange").asText() : "";
	            String Volume = (node.has("Volume") && !node.get("Volume").isNull()) ? node.get("Volume").asText() : "";
	            String VolumeChange = (node.has("VolumeChange") && !node.get("VolumeChange").isNull()) ? node.get("VolumeChange").asText() : "";
	            String Items = (node.has("Items") && !node.get("Items").isNull()) ? node.get("Items").asText() : "";
	            String Owners = (node.has("Owners") && !node.get("Owners").isNull()) ? node.get("Owners").asText() : "";
	            String Type = (node.has("Type") && !node.get("Type").isNull()) ? node.get("Type").asText() : "";

	            Rarible ng = new Rarible(ID, Title, FloorPrice, FloorChange, Volume,VolumeChange,Items, Owners, Type);
	            raribles.add(ng);
		 }
		return raribles;
	}

	@Override
	public void writeJson(List<Rarible> listObject, String filePath) throws IOException {
		ArrayNode ngNodes = mapper.createArrayNode();
        for (Rarible ng : listObject) {
            ObjectNode ngNode = mapper.createObjectNode();
            if(ng.getId() != "") {
            	ngNode.put("ID", ng.getId());
            	ngNode.put("Title", ng.getTitle());
            	ngNode.put("FloorPrice", ng.getFloorPrice());
            	ngNode.put("FloorChange", ng.getFloorChange());
            	ngNode.put("Volume", ng.getVolume());
            	ngNode.put("VolumeChange", ng.getVolumeChange());
            	ngNode.put("Items", ng.getItems());
            	ngNode.put("Owners", ng.getOwners());
            	ngNode.put("Type", ng.getType());
            	ngNodes.add(ngNode);
            }
        }
        ObjectNode ngObject = mapper.createObjectNode();
        ngObject.set("rarible", ngNodes);
        writer.writeValue(new File(filePath), ngObject);
    }
}

		

