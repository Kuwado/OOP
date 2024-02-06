package hust.NFT.datatype;

public class NiftyGateway extends NFT{
	private String nofsales;
	private String salesfloor;
	private String avgprice;
	private String primarysales;
	private String secondarysales;

	public String getNofsales() {
		return nofsales;
	}
	
	public void setNofsales(String nofsales) {
		this.nofsales = nofsales;
	}
	
	public String getSalesfloor() {
		return salesfloor;
	}
	
	public void setSalesfloor(String salesfloor) {
		this.salesfloor = salesfloor;
	}
	
	public String getAvgprice() {
		return avgprice;
	}
	
	public void setAvgprice(String avgprice) {
		this.avgprice = avgprice;
	}
	
	public String getPrimarysales() {
		return primarysales;
	}
	
	public void setPrimarysales(String primarysales) {
		this.primarysales = primarysales;
	}
	
	public String getSecondarysales() {
		return secondarysales;
	}
	
	public void setSecondarysales(String secondarysales) {
		this.secondarysales = secondarysales;
	}
	
	public NiftyGateway(String type,String id, String collection, String volume, String nofsales, String salesfloor,
			String avgprice, String items, String owners, String primarysales, String secondarysales) {
		super();
		this.type = type;
		this.id = id;
		this.title = collection;
		this.volume = volume;
		this.nofsales = nofsales;
		this.salesfloor = salesfloor;
		this.avgprice = avgprice;
		this.items = items;
		this.owners = owners;
		this.primarysales = primarysales;
		this.secondarysales = secondarysales;
	}

	public String toString() {
		return "ID: " + id + "\n"
				+ "Title: " + title + "\n"
				+ "Volume: " + volume + "\n"
				+ "Nofsales: " + nofsales + "\n"
				+ "SalesFloor: " + salesfloor + "\n"
				+ "AvgPrice: " + avgprice + "\n"
				+ "Items: " + items + "\n"
				+ "Owners: " + owners + "\n"
				+ "PrimarySales: " + primarysales + "\n"
				+ "SecondarySales: " + secondarysales + "\n";
				
	}
	
	
}