package DTO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Order implements IDTO {
	private int id;
	private int idCustomer;
	private int idDriver;
	private Timestamp startDate;
	private Timestamp endDate;
	private OrderStatus orderStatus;
	private boolean isDeleted;
	private double totalAmount;
	private Location locationDestination;
	private ArrayList<OrderedWarehouseItem> orderedItems = new ArrayList<OrderedWarehouseItem>();

	public Order() {

	}

	public Order(int idCustomer, OrderStatus status, double totalAmount) {
		this.idCustomer = idCustomer;
		this.orderStatus = status;
		this.totalAmount = totalAmount;
	}

	public ArrayList<OrderedWarehouseItem> getOrderedItems() {
		return orderedItems;
	}
	

	public void setOrderedItems(ArrayList<OrderedWarehouseItem> orderedItems) {
		this.orderedItems = orderedItems;
	}
	
	public void addOrderedItems(OrderedWarehouseItem item) {
		this.orderedItems.add(item);
	}
	
	public void removeOrderedItems(OrderedWarehouseItem item) {
		this.orderedItems.remove(item);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public OrderStatus getStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String status) {
		this.orderStatus = OrderStatus.valueOf(status);
	}

	public void setOrderStatus(OrderStatus status) {
		this.orderStatus = status;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Location getLocationDestination() {
		return locationDestination;
	}

	public void setLocationDestination(Location locationDestination) {
		this.locationDestination = locationDestination;
	}
}
