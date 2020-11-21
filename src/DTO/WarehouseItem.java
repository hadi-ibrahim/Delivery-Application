package DTO;

public class WarehouseItem implements IDTO {
	private int id;
	private int idWarehouse;
	private int idItem;
	private int quantity;
	private int isDeleted;

	public WarehouseItem() {

	}

	public WarehouseItem(int idWarehouse, int idItem, int quantity) {
		this.idWarehouse = idWarehouse;
		this.idItem = idItem;
		this.quantity = quantity;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getIdWarehouse() {
		return idWarehouse;
	}

	public void setIdWarehouse(int idWarehouse) {
		this.idWarehouse = idWarehouse;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
