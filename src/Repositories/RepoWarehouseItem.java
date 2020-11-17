package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Item;
import DTO.WarehouseItem;
import Helpers.ConnectionManager;

public class RepoWarehouseItem {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	RepoItem repoItem = new RepoItem();
	RepoWarehouse repoWarehouse = new RepoWarehouse();
	
	public RepoWarehouseItem() {
		con=ConnectionManager.getConnection();
	}
	private WarehouseItem extractWarehouseItemFromResultSet(ResultSet resultSet) throws SQLException {
		WarehouseItem availableAt = new WarehouseItem();
		availableAt.setIdWarehouse(resultSet.getInt("idWarehouse"));
		availableAt.setIdItem(resultSet.getInt("idItem"));
		availableAt.setQuantity(resultSet.getInt("itemQuantity"));
		availableAt.setIsDisabled(resultSet.getInt("isDisabled"));
		return availableAt;

	}
	
	public ArrayList<WarehouseItem> getAvailability(int id) {
		ArrayList<WarehouseItem> itemAvailability = new ArrayList<WarehouseItem>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From warehouseItem where isDisabled=0 AND idItem =" + id);
			while (rs.next()) {
				itemAvailability.add(extractWarehouseItemFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return itemAvailability;
	}
	
	public void setAvailability() {
		for (Item item : repoItem.getAll()) {
			item.setAvailability(this.getAvailability(item.getId()));
		}
	}
	private boolean exists(WarehouseItem warehouseItem) {
		try {
			ps = con.prepareStatement("SELECT * FROM warehouseItem WHERE idItem=? AND idWarehouse=?");
			ps.setInt(1, warehouseItem.getIdItem());
			ps.setInt(2, warehouseItem.getIdWarehouse());
			if (rs.next()) {
				return true;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return false;
	}

	public boolean addStockGlobally(WarehouseItem warehouseItem) {
		try {
			ps = con.prepareStatement(
					"Update warehouseItem SET itemquantity=itemquantity+? where idItem =? AND isDisabled=0");
			ps.setInt(1, warehouseItem.getQuantity());
			ps.setInt(2, warehouseItem.getIdItem());
			System.out.println(ps.executeUpdate() + " records updated");
			return true;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
	}

	public boolean addStockByWarehouse(WarehouseItem warehouseItem) {
		try {
			ps = con.prepareStatement(
					"Update warehouseItem SET itemquantity=itemquantity+? where idItem =? AND idWarehouse =? AND isDisabled=0");
			ps.setInt(1, warehouseItem.getQuantity());
			ps.setInt(2, warehouseItem.getIdItem());
			ps.setInt(3, warehouseItem.getIdWarehouse());
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	public boolean addItemToWarehouse(WarehouseItem warehouseItem) {
		try {
			if (repoItem.exists(warehouseItem.getIdItem()) && repoWarehouse.exists(warehouseItem.getIdWarehouse()) && !this.exists(warehouseItem)) {
				ps = con.prepareStatement(
						"INSERT INTO warehouseItem(idWarehouse,idItem,itemQuantity,isDisabled) VALUES(?,?,?,0");
				ps.setInt(1, warehouseItem.getIdWarehouse());
				ps.setInt(2, warehouseItem.getIdItem());
				ps.setInt(3, warehouseItem.getQuantity());
				System.out.println(ps.executeUpdate() + " records updated");
				return true;
			}
			return false;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
	}

	public boolean orderItemFromWarehouse(WarehouseItem warehouseItem) {
		try {
			if (enoughQuantity(warehouseItem)) {
				ps = con.prepareStatement(
						"Update warehouseItem SET itemQuantity=itemQuantity-? where idWarehouse =? AND idItem=?");
				ps.setInt(1, warehouseItem.getQuantity());
				ps.setInt(2, warehouseItem.getIdWarehouse());
				ps.setInt(3, warehouseItem.getIdItem());
				System.out.println(ps.executeUpdate() + " records restored");
				return true;
			}
		} catch (SQLException ex) {
			return false;
		}
		return false;
	}

	public boolean enoughQuantity(WarehouseItem warehouseItem) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select itemQuantity from warehouseItem where idWarehouse =" + warehouseItem.getIdWarehouse()
					+ " AND idItem=" + warehouseItem.getIdItem());
			if (rs.next()) {
				WarehouseItem whItemInfo = new WarehouseItem();
				whItemInfo = extractWarehouseItemFromResultSet(rs);
				if (whItemInfo.getQuantity() >= warehouseItem.getQuantity())
					return true;
			}
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			return false;
		}
		return false;
	}
	
	public boolean deleteItemGlobally(int idItem) {
		try {
			repoItem.delete(idItem);
			ps = con.prepareStatement("Update warehouseItem SET isDisabled=1 where idItem =?");
			ps.setInt(1, idItem);
			System.out.println(ps.executeUpdate() + " records restored");
			
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}
	public boolean deleteItemByWarehouse(WarehouseItem warehouseItem) {
		try {
			ps = con.prepareStatement("Update warehouseItem SET isDisabled=1 where idItem =? AND idWarehouse=?");
			ps.setInt(1, warehouseItem.getIdItem());
			ps.setInt(2, warehouseItem.getIdWarehouse());
			System.out.println(ps.executeUpdate() + " records deleted");
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}
	public boolean deleteWarehouseGlobally(int idWarehouse) {
		try {
			ps = con.prepareStatement("Update warehouseItem SET isDisabled=1 where idWarehouse =?");
			ps.setInt(1, idWarehouse);
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}
	
	public boolean restoreItemGlobally(int idItem) {
		try {
			ps = con.prepareStatement("Update warehouseItem SET isDisabled=0 where idItem =?");
			ps.setInt(1, idItem);
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			return false;
		}
		return true;
		
	}
	public boolean restoreItemByWarehouse(WarehouseItem warehouseItem) {
		try {
			ps = con.prepareStatement("Update warehouseItem SET isDisabled=0 where idItem =? AND idWarehouse=?");
			ps.setInt(1, warehouseItem.getIdItem());
			ps.setInt(2, warehouseItem.getIdWarehouse());
			System.out.println(ps.executeUpdate() + " records deleted");
		} catch (SQLException ex) {
			return false;
		}
		return true;	
	}
	
	public boolean destroy() {
		repoItem.destroy();
		repoWarehouse.destroy();
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
