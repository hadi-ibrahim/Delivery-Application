package Repositories;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Address;
import DTO.Location;
import Helpers.ConnectionManager;

public class RepoAddress {
	Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;

	public RepoAddress() {
		con = ConnectionManager.getConnection();
	}

	private Address extractAddressFromResultSet(ResultSet resultSet) {
		Address address = new Address();
		try {
			address.setId(resultSet.getInt("id"));
			address.setCity(resultSet.getString("city"));
			address.setStreet(resultSet.getString("street"));
			address.setBuilding(resultSet.getString("building"));
			address.setFloor(resultSet.getString("floor"));
			address.setLocation(new Location(resultSet.getDouble("longitude"), resultSet.getDouble("latitude")));
			return address;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

	}

	public Address get(int id) {
		try {
			ps = con.prepareStatement("SELECT * FROM address WHERE id=?");
			ps.setInt(1, id);
			rs= ps.executeQuery();
			if (rs.next())
				return extractAddressFromResultSet(rs);
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

	public boolean create(Address address) {
		try {
			ps = con.prepareStatement(
					"Insert into address(id,city,street,building,floor,longitude,latitude) Values(NULL,?,?,?,?,?,?)");
			ps.setString(1, address.getCity());
			ps.setString(2, address.getStreet());
			ps.setString(3, address.getBuilding());
			ps.setString(4, address.getFloor());
			
			ps.setDouble(5, address.getLocation().getLongitude());
			
			ps.setDouble(6, address.getLocation().getLatitude());
			System.out.println(ps.executeUpdate() + " record(s) created");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean update(Address address) {
		try {
			ps = con.prepareStatement(
					"Update address SET city=?,street=?,building=?,floor=?,longitude=?, latitude=? where id=?");
			ps.setString(1, address.getCity());
			ps.setString(2, address.getStreet());
			ps.setString(3, address.getBuilding());
			ps.setString(4, address.getFloor());
			ps.setDouble(5, address.getLocation().getLongitude());
			ps.setDouble(6, address.getLocation().getLatitude());
			ps.setInt(7, address.getId());
			System.out.println(ps.executeUpdate() + " record(s) updated");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

//	public boolean Delete(Address address) {
//		try {
//			ps = con.prepareStatement("Update address SET isDeleted=1  where id=?");
//			ps.setInt(1, address.getId());
//			System.out.println(ps.executeUpdate() + " record(s) deleted");
//			return true;
//		} catch (SQLException e) {
//			System.out.println(e);
//			return false;
//		}
//
//	}

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
