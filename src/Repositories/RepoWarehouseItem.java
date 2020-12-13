package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.IDTO;
import DTO.Warehouse;
import DTO.WarehouseItem;
import Helpers.ConnectionManager;

public class RepoWarehouseItem implements IRepo, ISoftDeletable {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	RepoItem repoItem = new RepoItem();
	RepoWarehouse repoWarehouse = new RepoWarehouse();

	public RepoWarehouseItem() {
		con = ConnectionManager.getConnection();
	}

	private WarehouseItem extractWarehouseItemFromResultSet(ResultSet resultSet) {
		try {
			WarehouseItem warehouseItem = new WarehouseItem();
			warehouseItem.setId(resultSet.getInt("id"));
			warehouseItem.setIdWarehouse(resultSet.getInt("idWarehouse"));
			warehouseItem.setIdItem(resultSet.getInt("idItem"));
			warehouseItem.setQuantity(resultSet.getInt("itemQuantity"));
			warehouseItem.setIsDeleted(resultSet.getInt("IsDeleted"));
			return warehouseItem;
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return null;

	}

	@Override
	public WarehouseItem get(int id) {
		WarehouseItem warehouseItem = null;
		try {
			ps = con.prepareStatement("SELECT * FROM item WHERE id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				warehouseItem = extractWarehouseItemFromResultSet(rs);
				return warehouseItem;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
		return null;
	}
	
	@Override
	public ArrayList<IDTO> getAll() {
		ArrayList<IDTO> ListOfWarehouseItems = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From item");
			while (rs.next()) {
				ListOfWarehouseItems.add(extractWarehouseItemFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return ListOfWarehouseItems;
	}

	public ArrayList<IDTO> getAllItemsInWarehouse(Warehouse warehouse) {
		ArrayList<IDTO> ListOfWarehouseItems = new ArrayList<IDTO>();
		try {
			ps = con.prepareStatement("SELECT * FROM warehouseItem WHERE isDeleted = 0 AND idWarehouse = ?");
			ps.setInt(1, warehouse.getId());
			rs= ps.executeQuery();
			while (rs.next()) {
				ListOfWarehouseItems.add(extractWarehouseItemFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex );
		}
		return ListOfWarehouseItems;
	}
	
	@Override
	public ArrayList<IDTO> getAllActive() {
		ArrayList<IDTO> ListOfWarehouseItems = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From warehouseitem where isDeleted=0");
			while (rs.next()) {
				ListOfWarehouseItems.add(extractWarehouseItemFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return ListOfWarehouseItems;
	}

	@Override
	public ArrayList<IDTO> getAllDisabled() {
		ArrayList<IDTO> ListOfWarehouseItems = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From warehouseItem where isDeleted=1");
			while (rs.next()) {
				ListOfWarehouseItems.add(extractWarehouseItemFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return ListOfWarehouseItems;
	}
	
	@Override
	public boolean create(IDTO dto) {
		WarehouseItem warehouseItem = (WarehouseItem) dto;
		try {
			ps = con.prepareStatement("INSERT INTO warehouseItem(id, idWarehouse, idItem, ItemQuantity, isDeleted) Values(NULL,?,?,?,0)");
			ps.setInt(1, warehouseItem.getIdWarehouse());
			ps.setInt(2, warehouseItem.getIdItem());
			ps.setInt(3, warehouseItem.getQuantity());
			System.out.println(ps.executeUpdate() + " record(s) created");

		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean update(IDTO dto) {
		WarehouseItem warehouseItem = (WarehouseItem) dto;
		try {
			ps = con.prepareStatement("UPDATE warehouseItem SET idWarehouse=?, idItem=? , ItemQuantity=? , isDeleted=? WHERE id=?");
			ps.setInt(1, warehouseItem.getIdWarehouse());
			ps.setInt(2, warehouseItem.getIdItem());
			ps.setInt(3, warehouseItem.getQuantity());
			ps.setInt(4, warehouseItem.getIsDeleted());
			ps.setInt(3, warehouseItem.getId());

			System.out.println(ps.executeUpdate() + " record(s) created");

		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean delete(int id) {
		try {
			ps = con.prepareStatement("Update warehouseitem  set isDeleted=1 WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records deleted");
			return true;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}

	}

	public boolean restore(int id) {
		try {
			ps = con.prepareStatement("Update warehouseitem SET isDeleted=0, itemQuantity =0 where id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records deleted");
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
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
	
	
	public ArrayList<WarehouseItem> getItemInAllWarehouses(int idItem) {
		ArrayList<WarehouseItem> itemAvailability = new ArrayList<WarehouseItem>();
		try {
			ps = con.prepareStatement("SELECT * FROM warehouseItem WHERE idItem=? AND isDeleted=0");
			ps.setInt(1, idItem);
			rs = ps.executeQuery();while (rs.next()) {
				itemAvailability.add(extractWarehouseItemFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return itemAvailability;
	}
//
//	TODO
//	public boolean orderItemFromWarehouse(WarehouseItem warehouseItem) {
//		try {
//			if (enoughQuantity(warehouseItem)) {
//				ps = con.prepareStatement(
//						"Update warehouseItem SET itemQuantity=itemQuantity-? where idWarehouse =? AND idItem=?");
//				ps.setInt(1, warehouseItem.getQuantity());
//				ps.setInt(2, warehouseItem.getIdWarehouse());
//				ps.setInt(3, warehouseItem.getIdItem());
//				System.out.println(ps.executeUpdate() + " records restored");
//				return true;
//			}
//		} catch (SQLException ex) {
//			return false;
//		}
//		return false;
//	}

	


//	public boolean enoughQuantity(WarehouseItem warehouseItem) {
//		try {
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("Select itemQuantity from warehouseItem where idWarehouse ="
//					+ warehouseItem.getIdWarehouse() + " AND idItem=" + warehouseItem.getIdItem());
//			if (rs.next()) {
//				WarehouseItem whItemInfo = new WarehouseItem();
//				whItemInfo = extractWarehouseItemFromResultSet(rs);
//				if (whItemInfo.getQuantity() >= warehouseItem.getQuantity())
//					return true;
//			}
//			System.out.println(ps.executeUpdate() + " records restored");
//		} catch (SQLException ex) {
//			return false;
//		}
//		return false;
//	}
	
}
