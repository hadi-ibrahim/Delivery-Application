package Repositories;
/*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Item;
import DTO.Order;
import DTO.OrderedWarehouseItem;
import DTO.Warehouse;
import DTO.WarehouseItem;
import Helpers.ConnectionManager;

public class RepoOrderedWarehouseItem {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	RepoWarehouseItem repoWarehouseItem = new RepoWarehouseItem();
	RepoOrder repoOrder = new RepoOrder();

	public RepoOrderedWarehouseItem() {
		con = ConnectionManager.getConnection();
	}

	private OrderedWarehouseItem extractOrderedWarehouseItemFromResultSet(ResultSet resultSet) throws SQLException {
		OrderedWarehouseItem orderedItem = new OrderedWarehouseItem();
		orderedItem.setIdWarehouse(resultSet.getInt("idWarehouse"));
		orderedItem.setIdItem(resultSet.getInt("idItem"));
		orderedItem.setQuantity(resultSet.getInt("itemQuantity"));
		orderedItem.setIdOrder(resultSet.getInt("idOrder"));
		return orderedItem;

	}

	public ArrayList<OrderedWarehouseItem> getOrderedItems(int idOrder) {
		ArrayList<OrderedWarehouseItem> itemsOrdered = new ArrayList<OrderedWarehouseItem>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From orderedWarehouseItem where idOrder =" + idOrder);
			while (rs.next()) {
				itemsOrdered.add(extractOrderedWarehouseItemFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return itemsOrdered;
	}

	public void setOrderedItems() {
		for (Order order : repoOrder.getAll()) {
			order.setOrderedItems(this.getOrderedItems(order.getId()));
		}
	}


	public boolean orderItem(OrderedWarehouseItem orderedItem) {

		if (repoWarehouseItem.orderItemFromWarehouse(new WarehouseItem(orderedItem.getIdWarehouse(),orderedItem.getIdItem(),orderedItem.getQuantity()))) {
			try {
				ps = con.prepareStatement("INSERT INTO orderedWarehouseItem(idOrder,idItem,idWarehouse,orderedQuantity) Values(?,?,?,?)");
				ps.setInt(1, orderedItem.getIdOrder());
				ps.setInt(2, orderedItem.getIdItem());
				ps.setInt(3, orderedItem.getIdWarehouse());
				ps.setInt(4, orderedItem.getQuantity());
				System.out.println(ps.executeUpdate() + " records added");
				return true;
			}catch (SQLException ex) {
				return false;
			}
		}
		return false;
	}

	
	
//	public boolean cancelOrder(Order order) {
//		repoOrder.cancelOrder(order);
//		for(OrderedWarehouseItem item : this.getOrderedItems(order.getId())) {
//		repoWarehouseItem.addStockByWarehouse(new WarehouseItem(item.getIdWarehouse(),item.getIdItem(),item.getQuantity()));
//		}
//		return true;
//	}
	
	
	
	
	public boolean destroy() {
		repoWarehouseItem.destroy();
		repoOrder.destroy();
		ArrayList<AutoCloseable> components = new ArrayList<AutoCloseable>();
		components.add((AutoCloseable) ps);
		components.add((AutoCloseable) rs);
		components.add((AutoCloseable) con);
		components.add((AutoCloseable) stmt);
		closeComponents(components);

		if (ConnectionManager.IsConnectionOpened())
			ConnectionManager.Close();

		try {
			this.finalize();
		} catch (Throwable ex) {
			System.out.println(ex);
			return false;
		}

		return true;
	}

	public void closeComponents(ArrayList<AutoCloseable> components) {
		for (AutoCloseable component : components) {
			closeComponent(component);
		}
	}

	public void closeComponent(AutoCloseable component) {
		try {
			if (component != null)
				component.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}




























TODO ! important 
This class does not deserve a repository, as it can be replaced with simple logic and placed 
in the business logic layer. example on RepoTester





















*/