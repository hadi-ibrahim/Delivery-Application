package dataAccessLayer.DTO;

import java.util.ArrayList;

public class Item {
	private int id;
	private ArrayList<WarehouseItemInformation> availability = new ArrayList<WarehouseItemInformation>();
	private String description;
	private String category;
	private double price;
	private int isDeleted;

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<WarehouseItemInformation> getAvailability() {
		return availability;
	}

	public void setAvailability(ArrayList<WarehouseItemInformation> availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
