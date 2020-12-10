package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.IDTO;
import DTO.Warehouse;
import Helpers.ConnectionManager;

public class RepoWarehouse implements ISoftDeletableRepo {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	RepoAddress repoAddress = new RepoAddress();

	public RepoWarehouse() {
		con = ConnectionManager.getConnection();
	}

	private Warehouse extractWarehouseFromResultSet(ResultSet resultSet) {
		try {
			Warehouse warehouse = new Warehouse();
			warehouse.setId(resultSet.getInt("id"));
			warehouse.setLongitude(resultSet.getDouble("longitude"));
			warehouse.setLatitude(resultSet.getDouble("latitude"));
			warehouse.setName(resultSet.getString("name"));
			return warehouse;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Warehouse get(int id) {
		Warehouse warehouse = null;
		try {
			ps = con.prepareStatement("SELECT * FROM warehouse WHERE id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				warehouse = extractWarehouseFromResultSet(rs);
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
		ArrayList<IDTO> LstOfWarehouse = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From warehouse");
			while (rs.next()) {
				LstOfWarehouse.add(extractWarehouseFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return LstOfWarehouse;
	}
	
	public ArrayList<IDTO> getAllActiveWarehouses() {
		ArrayList<IDTO> LstOfItems = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From warehouse where isDeleted=0");
			while (rs.next()) {
				LstOfItems.add(extractWarehouseFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return LstOfItems;
	}
	
	public ArrayList<IDTO> getAllDisabledWarehouses() {
		ArrayList<IDTO> LstOfItems = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From warehouse where isDeleted=1");
			while (rs.next()) {
				LstOfItems.add(extractWarehouseFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return LstOfItems;
	}

	@Override
	public boolean create(IDTO dto) {
		Warehouse warehouse = (Warehouse) dto;
		try {
			ps = con.prepareStatement("INSERT INTO warehouse(id,longitude, latitude, isDeleted, name) Values(Null,?,?,0,?)");
			ps.setDouble(1, warehouse.getLongitude());
			ps.setDouble(2, warehouse.getLatitude());

			ps.setString(3, warehouse.getName());
			System.out.println(ps.executeUpdate() + " record(s) created");

		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean update(IDTO dto) {
		Warehouse warehouse = (Warehouse) dto;
		try {
			ps = con.prepareStatement("UPDATE warehouse SET longitude=?, latitude=?, isDeleted=?, name =? WHERE id=?");
			ps.setDouble(1, warehouse.getLongitude());
			ps.setDouble(2, warehouse.getLatitude());
			ps.setInt(3, warehouse.getIsDeleted());
			ps.setString(4, warehouse.getName());
			ps.setInt(5, warehouse.getId());
			System.out.println(ps.executeUpdate());
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(int id) {
		try {
			ps = con.prepareStatement("Update warehouse set isDeleted=1 WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records deleted");
			return true;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}

	}

	@Override
	public boolean restore(int id) {
		try {
			ps = con.prepareStatement("Update warehouse set isDeleted=0 WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean destroy() {
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
