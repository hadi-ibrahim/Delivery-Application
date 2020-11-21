package DTO;

import java.util.ArrayList;

public class Item implements IDTO {
	private int id;
	private ArrayList<WarehouseItem> availability = new ArrayList<WarehouseItem>();
	private String description;
	private Category category;
	private double price;
	private int isDeleted;

	public Item() {

	}

	public Item(String description, Category category, double price) {
		this.description = description;
		this.category = category;
		this.price = price;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<WarehouseItem> getAvailability() {
		return availability;
	}

	public void setAvailability(ArrayList<WarehouseItem> availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {

		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setCategory(String category) {
		this.category = Category.valueOf(category);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
