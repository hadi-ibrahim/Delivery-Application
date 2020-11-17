package dataAccessLayer.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Item;
import DTO.Order;
import DTO.OrderedWarehouseItem;
import DTO.WarehouseItem;
import dataAccessLayer.Helpers.ConnectionManager;

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

	private OrderedWarehouseItem extractRepoOrderedWarehouseItem(ResultSet resultSet) throws SQLException {
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
				itemsOrdered.add(extractRepoOrderedWarehouseItem(rs));
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


	public boolean orderItem(int idOrder, int idItem, int idWarehouse, int quantity) {

		if (repoWarehouseItem.orderItemFromWarehouse(idItem, idWarehouse, quantity)) {
			try {
				ps = con.prepareStatement("INSERT INTO orderedWarehouseItem(idOrder,idItem,idWarehouse,orderedQuantity) Values(?,?,?,?)");
				ps.setInt(1, idOrder);
				ps.setInt(2, idItem);
				ps.setInt(3, idWarehouse);
				ps.setInt(4, quantity);
				System.out.println(ps.executeUpdate() + " records added");
				return true;
			}catch (SQLException ex) {
				return false;
			}
		}
		return false;
	}

	public boolean cancelOrder(int idOrder) {
		repoOrder.cancelOrder(idOrder);
		for(OrderedWarehouseItem item : this.getOrderedItems(idOrder)) {
		repoWarehouseItem.addStockByWarehouse(item.getIdWarehouse(), item.getIdItem(), item.getQuantity());
		}
		return true;
	}
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
