package businessLogicLayer;

import java.util.ArrayList;

import DTO.DriverStatus;
import DTO.IDTO;
import DTO.Order;
import DTO.OrderStatus;
import DTO.OrderedWarehouseItem;
import DTO.RouteCheckpoint;
import DTO.User;
import DTO.WarehouseItem;
import Repositories.RepoOrder;
import Repositories.RepoOrderedWarehouseItem;
import Repositories.RepoRouteCheckpoint;
import Repositories.RepoUser;
import Repositories.RepoWarehouseItem;

public class OrderManager {
	private RepoUser repoUser = new RepoUser();
	private RepoOrder repoOrder = new RepoOrder();
	private RepoWarehouseItem repoWarehouseItem = new RepoWarehouseItem();
	private RepoOrderedWarehouseItem repoOrderedWarehouseItem = new RepoOrderedWarehouseItem();
	private RepoRouteCheckpoint repoCheckpoint = new RepoRouteCheckpoint();
	private StockManager stockManager = new StockManager();

	public Order get(int id) {
		return repoOrder.get(id);
	}
	
	public void placeOrder(Order order) {

		order.setOrderStatus(OrderStatus.PENDING);
		order.setDeleted(false);
		
		repoOrder.create(order);
		int lastId= repoOrder.getLastId();
		for(OrderedWarehouseItem item : order.getOrderedItems()) {
			item.setIdOrder(lastId);
			repoOrderedWarehouseItem.create(item);
		}
		
	}
	
	public void addItemToShoppingCart(OrderedWarehouseItem orderedItem, Order order) {
		stockManager.removeWarehouseItemStock(stockManager.get(orderedItem.getIdWarehouseItem()), orderedItem.getQuantity());
		order.addOrderedItems(orderedItem);
	}
	
	public void restoreItemsFromShoppingCart(Order order) {
		for(OrderedWarehouseItem orderedItem : order.getOrderedItems())
			stockManager.addWarehouseItemStock(stockManager.get(orderedItem.getIdWarehouseItem()), orderedItem.getQuantity());
	};

	public void takeOrder(Order order, User driver) {
		order.setOrderStatus(OrderStatus.ACCEPTED);
		driver.setDriverStatus(DriverStatus.BUSY);
		order.setIdDriver(driver.getId());
		repoOrder.update(order);
		repoUser.update(driver);
	}
	
	public ArrayList<IDTO> getOrderOrderedItems(Order order) {
		return repoOrderedWarehouseItem.getOrderedItemsFromOrder(order.getId());
	}
	
	public ArrayList<IDTO> getAllOrders() {
		return repoOrder.getAll();
	}

	public ArrayList<IDTO> getAllFinishedByUser(int id){
		return repoOrder.getAllFinishedByUser(id);
	}
	public ArrayList<IDTO> getAllByUser(int id){
		return repoOrder.getAllByUser(id);
	}
	public ArrayList<IDTO> getAllCheckpointsByOrder(Order order) {
		return repoCheckpoint.getOrderRoute(order.getId());
	}
	
	public void addRouteCheckpoint(RouteCheckpoint routeCheckpoint) {
		repoCheckpoint.create(routeCheckpoint);
	}
	public ArrayList<IDTO> getAllPending() {
		return repoOrder.getAllPending();
	}
	public IDTO getActive(User driver) {
		return repoOrder.getActive(driver.getId());
	}
	
	public void update(Order order) {
		repoOrder.update(order);
	}

	public void finish(Order order) {
		repoOrder.finish(order);
	}

	public ArrayList<IDTO> getAllNotFinishedByUser(int id) {
		
		return repoOrder.getAllNotFinishedByUser(id);
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
