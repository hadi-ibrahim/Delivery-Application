package DTO;

import java.awt.Point;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
	private int id;
	private int idCustomer;
	private int idStaff;
	private int idDriver;
	private Timestamp startDate;
	private Timestamp endDate;
	private String status;
	private double totalAmount;
	private Point locationDestination;
	private ArrayList<OrderedWarehouseItem> orderedItems = new ArrayList<OrderedWarehouseItem>();

	public ArrayList<OrderedWarehouseItem> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(ArrayList<OrderedWarehouseItem> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getIdStaff() {
		return idStaff;
	}

	public void setIdStaff(int idStaff) {
		this.idStaff = idStaff;
	}

	public int getIdDriver() {
		return idDriver;
	}

	public void setIdDriver(int idDriver) {
		this.idDriver = idDriver;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Point getLocationDestination() {
		return locationDestination;
	}

	public void setLocationDestination(Point locationDestination) {
		this.locationDestination = locationDestination;
	}
}
