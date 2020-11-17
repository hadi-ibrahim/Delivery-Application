package dataAccessLayer.Repositories;

import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import DTO.Order;
import dataAccessLayer.Helpers.ConnectionManager;

public class RepoOrder {
	Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
	RepoUser repoUser = new RepoUser();

	public RepoOrder() {
		con = ConnectionManager.getConnection();
	}

	private Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getInt("id"));
		order.setIdCustomer(resultSet.getInt("idCustomer"));
		order.setIdStaff(resultSet.getInt("idStaff"));
		order.setIdDriver(resultSet.getInt("idDriver"));
		order.setStartDate(resultSet.getTimestamp("startDate"));
		order.setEndDate(resultSet.getTimestamp("endDate"));
		order.setStatus(resultSet.getString("status"));
		order.setTotalAmount(resultSet.getDouble("totalAmount"));
		order.setLocationDestination((Point) resultSet.getObject("locationDestination"));
		return order;

	}

	public Order get(int id) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From order where id=" + id);
			if (rs.next())
				return extractOrderFromResultSet(rs);
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

	public ArrayList<Order> getAll() {
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From order");
			while (rs.next()) {
				orders.add(extractOrderFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return orders;

	}
	public boolean create(Order order) {
		
	}
	public boolean cancelOrder(int idOrder,int idDriver) {
			repoUser.setAvailable(idDriver);
		try {
			ps = con.prepareStatement("Update order SET status='Cancelled', endDate=Now() where idOrder=?");
			ps.setInt(1, idOrder);
			return true;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
	}

	public boolean finishOrder(int idOrder,int idDriver) {
		repoUser.setAvailable(idDriver);
		try {
			ps = con.prepareStatement("Update order SET status='finished', endDate=Now() where idOrder=?");
			ps.setInt(1, idOrder);
			return true;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
	}
	
	public boolean acceptOrder(int idOrder,int idDriver) {
		repoUser.setBusy(idDriver);
		try {
			ps = con.prepareStatement("Update order SET status='Pending Delivery',idDriver=? where idOrder=?");
			ps.setInt(1,idDriver);
			ps.setInt(2, idOrder);
			return true;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
	}
	public boolean addRouteCheckpoint(int idOrder, Point location) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("INSERT INTO RouteCheckpoint(id,idOrder,time,location) Values(NULL," + idOrder
					+ ",Now()," + location + ")");
			if (rs.next())
				return true;
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return false;
	}

	public boolean destroy() {
		repoUser.Destroy();
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
