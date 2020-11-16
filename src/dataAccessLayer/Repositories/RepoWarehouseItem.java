package dataAccessLayer.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataAccessLayer.DTO.WarehouseItemInformation;

public class RepoWarehouseItem {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	private WarehouseItemInformation extractAvailabilityFromResultSet(ResultSet resultSet) throws SQLException {
		WarehouseItemInformation availableAt = new WarehouseItemInformation();
		availableAt.setIdWarehouse(resultSet.getInt("idWarehouse"));
		availableAt.setIdItem(resultSet.getInt("idItem"));
		availableAt.setQuantity(resultSet.getInt("itemQuantity"));
		return availableAt;
	}

	public ArrayList<WarehouseItemInformation> getAvailability(int id) {
		ArrayList<WarehouseItemInformation> itemAvailability = new ArrayList<WarehouseItemInformation>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From warehouseItem where idItem =" + id);
			while (rs.next()) {
				itemAvailability.add(extractAvailabilityFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return itemAvailability;
	}

	public boolean addGlobally(int idItem, int quantity) {
		try {
			ps = con.prepareStatement("Update warehouseItem SET itemquantity=itemquantity+? where idItem =?");

			ps.setInt(1, quantity);
			ps.setInt(2, idItem);
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	public boolean addByWarehouse(int idWarehouse, int idItem, int quantity) {
		try {
			ps = con.prepareStatement(
					"Update warehouseItem SET itemquantity=itemquantity+? where idItem =? AND idWarehouse =?");
			ps.setInt(1, quantity);
			ps.setInt(2, idItem);
			ps.setInt(3, idWarehouse);
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	public boolean deleteItemGlobally(int idItem) {
		try {
			ps = con.prepareStatement("Update warehouseItem SET itemquantity=0 where idItem =?");
			ps.setInt(1, idItem);
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}

}
