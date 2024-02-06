package hust.NFT.datatype;

	public class Rarible extends NFT{
		private String FloorPrice;
		private String FloorChange;
		private String VolumeChange;
     
     //contructor
		public Rarible(String ID, String Title, String FloorPrice, String FloorChange, String Volume, String VolumeChange,
		String Items, String Owners, String Type) {
			super();
			this.id = ID;
			this.title = Title;
			this.FloorPrice = FloorPrice;
			this.FloorChange = FloorChange;
			this.volume = Volume;
			this.VolumeChange = VolumeChange;
			this.items = Items;
			this.owners = Owners;
			this.type = Type;
		}


		//get

		public String getFloorPrice() {
			return FloorPrice;
		}

		public void setFloorPrice(String floorPrice) {
			FloorPrice = floorPrice;
		}

		public String getFloorChange() {
			return FloorChange;
		}

		public void setFloorChange(String floorChange) {
			FloorChange = floorChange;
		}

		public String getVolumeChange() {
			return VolumeChange;
		}

		public void setVolumeChange(String volumeChange) {
			VolumeChange = volumeChange;
		}

		@Override
		public String toString() {
			return "ID: " + id + "\n"
					+ "Title: " + title + "\n"
					+ "FloorPrice: " + FloorPrice + "\n"
					+ "FloorChange: " + FloorChange + "\n"
					+ "Volume: " + volume + "\n"
					+ "VolumeChange: " + VolumeChange + "\n"
					+ "Items: " + items + "\n"
					+ "Owners: " + owners ;
		}



}
