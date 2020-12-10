package DTO;

public class Warehouse implements IDTO {
	private int id;
	private double longitude;
	private double latitude;
	private int isDeleted;
	private String name;

	public Warehouse() {

	}

	public Warehouse(String name, double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude; 
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

}
