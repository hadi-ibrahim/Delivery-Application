package DTO;

public class Address implements IDTO {
	private int id;
	private String street;
	private String city;
	private String floor;
	private String building;
	private Location location;
	private int idUser;

	public Address() {

	}

	public Address(String street, String city, String floor, String building, Location location) {
		this.street = street;
		this.city = city;
		this.floor = floor;
		this.building = building;
		this.location = location;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;

	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	@Override
	public String toString() {
		return this.city +", " + this.street + ", " + this.building + ", " + this.floor;
	}
	
	
}
