package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.IDTO;
import DTO.Item;
import DTO.OrderedWarehouseItem;
import Helpers.ConnectionManager;

public class RepoOrderedWarehouseItem implements IRepo {
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
		orderedItem.setId(resultSet.getInt("id"));
		orderedItem.setIdWarehouseItem(resultSet.getInt("idWarehouseItem"));
		orderedItem.setIdOrder(resultSet.getInt("idOrder"));
		orderedItem.setQuantity(resultSet.getInt("orderedQuantity"));
		orderedItem.setPricePerUnit(resultSet.getDouble("pricePerUnit"));
		return orderedItem;
	}

	@Override
	public OrderedWarehouseItem get(int id) {
		try {
			ps = con.prepareStatement("SELECT * FROM orderedwarehouseitem WHERE id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				OrderedWarehouseItem warehouse = extractOrderedWarehouseItemFromResultSet(rs);
				return warehouse;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<IDTO> getAll() {
		ArrayList<IDTO> ListOfOrderedWarehouseItems = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From warehouse");
			while (rs.next()) {
				ListOfOrderedWarehouseItems.add(extractOrderedWarehouseItemFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return ListOfOrderedWarehouseItems;
	}

	@Override
	public boolean create(IDTO dto) {
		OrderedWarehouseItem warehouse = (OrderedWarehouseItem) dto;
		try {
			ps = con.prepareStatement(
					"INSERT INTO orderedwarehouseitem(id, idWarehouseItem, idOrder, orderedQuantity, pricePerUnit) VALUES(NULL,?,?,?,?)");
			ps.setInt(1, warehouse.getIdWarehouseItem());
			ps.setInt(2, warehouse.getIdOrder());
			ps.setInt(3, warehouse.getQuantity());
			ps.setDouble(4, warehouse.getPricePerUnit());
			System.out.println(ps.executeUpdate() + " record(s) created");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;

	}
	
	@Override
	public boolean update(IDTO dto) {
		OrderedWarehouseItem warehouse = (OrderedWarehouseItem) dto;
		try {
			ps = con.prepareStatement(
					"UPDATE orderedwarehouseitem SET idWarehouseItem=?, idOrder = ?, orderedQuantity=?, pricePerUnit=? WHERE id=?");
			ps.setInt(1, warehouse.getIdWarehouseItem());
			ps.setInt(2, warehouse.getIdOrder());
			ps.setInt(3, warehouse.getQuantity());
			ps.setDouble(4, warehouse.getPricePerUnit());
			ps.setInt(5, warehouse.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				warehouse = extractOrderedWarehouseItemFromResultSet(rs);
				return true;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			ps = con.prepareStatement("DELETE FROM orderedwarehouseitem WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records deleted");
			return true;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
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

	public ArrayList<IDTO> getOrderedItemsFromOrder(int idOrder) {
		ArrayList<IDTO> itemsOrdered = new ArrayList<IDTO>();
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

}