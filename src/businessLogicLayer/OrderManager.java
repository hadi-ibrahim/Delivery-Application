package businessLogicLayer;

import java.util.ArrayList;

import DTO.DriverStatus;
import DTO.IDTO;
import DTO.Order;
import DTO.OrderStatus;
import DTO.OrderedWarehouseItem;
import DTO.User;
import DTO.WarehouseItem;
import Repositories.RepoOrder;
import Repositories.RepoOrderedWarehouseItem;
import Repositories.RepoUser;
import Repositories.RepoWarehouseItem;

public class OrderManager {
	private RepoUser repoUser = new RepoUser();
	private RepoOrder repoOrder = new RepoOrder();
	private RepoWarehouseItem repoWarehouseItem = new RepoWarehouseItem();
	private RepoOrderedWarehouseItem repoOrderedWarehouseItem = new RepoOrderedWarehouseItem();
	private StockManager stockManager = new StockManager();

	public void placeOrder(Order order) {

		if (enoughItemsQuantityInWarehouse(order)) {
			createOrderOrderedWarehouseItems(order);

			order.setOrderStatus(OrderStatus.PENDING);
			repoOrder.create(order);
		}
	}

	private boolean enoughItemsQuantityInWarehouse(Order order) {
		for (OrderedWarehouseItem orderedWarehouseItem : order.getOrderedItems())
			if (!enoughItemQuantityInWarehouse(orderedWarehouseItem.getIdWarehouseItem(),
					orderedWarehouseItem.getQuantity()))
				return false;
		return true;
	}

	public boolean enoughItemQuantityInWarehouse(int idWarehouseItem, int quantity) {
		WarehouseItem warehouseItem = repoWarehouseItem.get(idWarehouseItem);
		return warehouseItem.getQuantity() < quantity;
	}

	private void createOrderOrderedWarehouseItems(Order order) {
		for (OrderedWarehouseItem orderedWarehouseItem : order.getOrderedItems()) {
			repoOrderedWarehouseItem.create(orderedWarehouseItem);
		}
	}

	public void cancelOrder(Order order) {
		User driver;
		order.setOrderStatus(OrderStatus.CANCELED);
		if (order.getIdDriver() > 0) {
			driver = repoUser.get(order.getIdDriver());
			driver.setDriverStatus(DriverStatus.AVAILABLE);
			repoUser.update(driver);
		}
		for (OrderedWarehouseItem orderedWarehouseItem : order.getOrderedItems()) {
			stockManager.addWarehouseItemStock(repoWarehouseItem.get(orderedWarehouseItem.getId()),
					orderedWarehouseItem.getQuantity());
		}
		repoOrder.update(order);
	}

	public void takeOrder(Order order, User driver) {
		order.setOrderStatus(OrderStatus.DELIVERING);
		driver.setDriverStatus(DriverStatus.BUSY);
		order.setIdDriver(driver.getId());
		repoOrder.update(order);
		repoUser.update(driver);
	}

	public void setOrderOrderedItems(Order order) {
		order.setOrderedItems(repoOrderedWarehouseItem.getOrderedItemsFromOrder(order.getId()));
	}
	
	public ArrayList<IDTO> getAllOrders() {
		return repoOrder.getAll();
	}

	// TODO
	/*
	 * Event while driverStatus == busy update longitude latitude;
	 */

	/*
	 * Trigger after update on user if NEW.longitude != OLD.Longitude OR
	 * NEW.latitude != OLD.latitude INSERT INTO RouteCheckpoint VALUES(new
	 * longitude, new latitude, id)
	 * 
	 */

}
