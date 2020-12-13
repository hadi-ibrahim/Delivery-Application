package businessLogicLayer;

import java.util.ArrayList;

import DTO.IDTO;
import DTO.Item;
import DTO.Warehouse;
import DTO.WarehouseItem;
import Repositories.RepoItem;
import Repositories.RepoWarehouse;
import Repositories.RepoWarehouseItem;

public class StockManager {
	RepoWarehouse repoWarehouse = new RepoWarehouse();
	RepoWarehouseItem repoWarehouseItem = new RepoWarehouseItem();
	RepoItem repoItem = new RepoItem();
	
	public void setItemAvailability() {
		for (IDTO dto : repoItem.getAll()) {
			Item item = (Item)dto;
			item.setAvailability(repoWarehouseItem.getItemInAllWarehouses(item.getId()));
		}
	}
	
	public void addItemStockToAllWarehouses(Item item, int quantity) {
		for (IDTO dto : repoWarehouseItem.getItemInAllWarehouses(item.getId())) {
			WarehouseItem warehouseItem = (WarehouseItem)dto;
			addWarehouseItemStock(warehouseItem,quantity);
		}
	}
	
	public void addWarehouseItemStock(WarehouseItem item, int quantity) {
		item.setQuantity(item.getQuantity()+quantity);
		repoWarehouseItem.update(item);
	}
	
	public void addItemToWarehouse( Warehouse warehouse, Item item, int quantity) {
		repoWarehouseItem.create( new WarehouseItem(warehouse.getId(),item.getId(), quantity));
	}
	
	public ArrayList<IDTO> getAllActiveWarehouseItems(){
		return repoWarehouseItem.getAllActive();
	}
	
	public ArrayList<IDTO> getAllDisabledWarehouseItems(){
		return repoWarehouseItem.getAllDisabled();
	}
	
	public ArrayList<IDTO> getAllDisabledWarehouses(){
		return repoWarehouseItem.getAllDisabled();
	}
	public ArrayList<IDTO> getAllItemsInWarehouse (Warehouse warehouse) {
		return repoWarehouseItem.getAllItemsInWarehouse(warehouse);
	}
	
	public void delete(int id) {
		repoWarehouseItem.delete(id);
	}
	
	public void restore(int id) {
		repoWarehouseItem.restore(id);
	}
	
	public ArrayList<IDTO> getAllItemsNotInWarehouse(Warehouse warehouse){
		return repoItem.getAllItemsNotInWarehouse(warehouse);
	}
	/*
	 * TRIGGER BEFORE INSERT ON WAREHOUSEITEM
	 * 	IF new.idWarehouse IN select idWarehouse from warehouseItem where idItem= NEW.idItem
	 * 		Update warehouseItem set quantity = quantity + NEW.quantity where idItem = new.idItem AND idWarehouse = New.idWarehouse;
	 * 		Raise e_ItemWarehouseExists
	 * 	end if;
	 * 	Exception
	 * when e_ItemWarehouseExists then
	 * dbms_output.put_line("You tried adding an existing item to the same warehouse, therefore the quantity has been added");
	 */
	
	/*TRIGGER AFTER UPDATE ON ITEM
	 * IF NEW.isDeleted = 1 AND OLD.isDeleted = 0 THEN
	 * UPDATE WAREHOUSEITEM SET isDeleted = 1, itemQuantity= 0 WHERE WarehouseItem.idItem = NEW.id 
	 */
	
	/*TRIGGER AFTER UPDATE ON ITEM
	 * IF NEW.isDeleted = 0 AND OLD.isDeleted = 1 THEN
	 * UPDATE WAREHOUSEITEM SET isDeleted = 0  WHERE WarehouseItem.idItem = NEW.id 
	 */
	
	/*TRIGGER AFTER UPDATE ON Warehouse
	 * IF NEW.isDeleted = 1 AND OLD.isDeleted = 0 THEN
	 * UPDATE WAREHOUSEITEM SET isDeleted = 1, itemQuantity = 0 WHERE WarehouseItem.idWarehouse = NEW.id 
	 */
	
	/*TRIGGER AFTER UPDATE ON Warehouse
	 * IF NEW.isDeleted = 0 AND OLD.isDeleted = 1 THEN
	 * UPDATE WAREHOUSEITEM SET isDeleted = 0  WHERE WarehouseItem.idWarehouse = NEW.id 
	 */
}
