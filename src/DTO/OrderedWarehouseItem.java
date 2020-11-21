package DTO;

public class OrderedWarehouseItem implements IDTO {
	private int id;
	private int idOrder;
	private int idWarehouseItem;
	private int quantity;
	private double pricePerUnit;

	public OrderedWarehouseItem() {

	}

	public OrderedWarehouseItem(int idOrder, int idWarehouseItem, int quantity, double price) {
		this.idOrder = idOrder;
		this.idWarehouseItem = idWarehouseItem;
		this.quantity = quantity;
		this.pricePerUnit = price;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public int getIdWarehouseItem() {
		return idWarehouseItem;
	}

	public void setIdWarehouseItem(int idWarehouse) {
		this.idWarehouseItem = idWarehouse;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

}
