package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Warehouse;
import Helpers.ConnectionManager;

public class RepoWarehouse {

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
			warehouse.setIdAddress(resultSet.getInt("idAddress"));
			warehouse.setIsDeleted(resultSet.getInt("isDeleted"));
			return warehouse;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Warehouse get(int id) {
		Warehouse warehouse = null;
		try {
			ps = con.prepareStatement("SELECT * FROM warehouse WHERE id=?");
			ps.setInt(1, id);
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
	
	
	public ArrayList<Warehouse> getAll() {
		ArrayList<Warehouse> LstOfWarehouse = new ArrayList<Warehouse>();
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
	
	public boolean exists(int id) {
		try {
			ps = con.prepareStatement("SELECT * FROM warehouse WHERE id=?");
			ps.setInt(1, id);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return false;
		
	}
	
	public boolean create(Warehouse warehouse) {
		try {
			ps = con.prepareStatement("INSERT INTO warehouse(id,idAddress,isDeleted) Values(Null,?,0)");
			ps.setInt(1, warehouse.getIdAddress());
			System.out.println(ps.executeUpdate() + " record(s) created");

		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	public boolean update(Warehouse warehouse) {
		try {
			ps = con.prepareStatement("UPDATE warehouse SET idAddress=?,isDeleted=? WHERE id=?");
			ps.setInt(1, warehouse.getIdAddress());
			ps.setInt(2, warehouse.getIsDeleted());
			ps.setInt(3, warehouse.getId());
			System.out.println(ps.executeUpdate());
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	public boolean delete(int id) {
		repoAddress.Delete(repoAddress.get(this.get(id).getIdAddress()));
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
