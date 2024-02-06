package hust.NFT.datatype;

public class OpenSea extends NFT {
	private String change;
	private String floorprice;
	private String sales;
	
	
	// Constructor
	public OpenSea(String id, String title, String volume, String change, String floorprice, String sales, String owners, String items, String type ) {
		super();
		this.id = id;
		this.title = title;
		this.volume = volume;
		this.change = change;
		this.floorprice = floorprice;
		this.sales = sales;
		this.owners = owners;
		this.items = items;
		this.type = type;
	}


	public String getChange() {
		return change;
	}


	public void setChange(String change) {
		this.change = change;
	}


	public String getFloorprice() {
		return floorprice;
	}


	public void setFloorprice(String floorprice) {
		this.floorprice = floorprice;
	}


	public String getSales() {
		return sales;
	}


	public void setSales(String sales) {
		this.sales = sales;
	}


	public String toString() {
		return "ID: " + id + "\n"
				+ "Title: " + title + "\n"
				+ "Volume: " + volume + "\n"
				+ "Change: " + change + "\n"
				+ "FloorPrice: " + floorprice + "\n"
				+ "Sales: " + sales + "\n"
				+ "Items: " + items + "\n"
				+ "Owners: " + owners + "\n";
				
	}
	
	
	
	
	
}
