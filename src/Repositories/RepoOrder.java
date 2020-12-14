package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.IDTO;
import DTO.Location;
import DTO.Order;
import DTO.OrderStatus;
import Helpers.ConnectionManager;

public class RepoOrder implements IRepo, ISoftDeletable {
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
		order.setIdDriver(resultSet.getInt("idDriver"));
		order.setStartDate(resultSet.getTimestamp("dateStart"));
		order.setEndDate(resultSet.getTimestamp("dateEnd"));
		order.setOrderStatus(resultSet.getString("orderStatus"));
		order.setTotalAmount(resultSet.getDouble("totalAmount"));
		order.setLocationDestination(new Location(resultSet.getDouble("destinationLongitude"), resultSet.getDouble("destinationLatitude")));
		return order;

	}

	@Override
	public Order get(int id) {
		try {
			ps = con.prepareStatement("Select * From `order` where id= ? " );
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return extractOrderFromResultSet(rs);
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<IDTO> getAll() {
		ArrayList<IDTO> orders = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From `order`");
			while (rs.next()) {
				orders.add(extractOrderFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return orders;

	}
	
	public ArrayList<IDTO> getAllFinishedByUser(int id){
		ArrayList<IDTO> orders = new ArrayList<IDTO>();
		try {
			ps = con.prepareStatement("Select * From `order` where idCustomer=? AND orderStatus LIKE ? ");
			ps.setInt(1, id);
			ps.setString(2, OrderStatus.COMPLETED.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				orders.add(extractOrderFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return orders;
	}
	@Override
	public ArrayList<IDTO> getAllActive() {
		ArrayList<IDTO> orders = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From `order` where isDeleted=0");
			while (rs.next()) {
				orders.add(extractOrderFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return orders;

	}

	@Override
	public ArrayList<IDTO> getAllDisabled() {
		ArrayList<IDTO> orders = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From `order` where isDeleted=1");
			while (rs.next()) {
				orders.add(extractOrderFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return orders;

	}
	
	@Override
	public boolean create(IDTO dto) {
		Order order = (Order) dto;
		try {
			ps = con.prepareStatement(
					"Insert into `order`(id,idCustomer,idDriver,destinationLongitude, destinationLatitude ,dateStart,dateEnd,orderStatus,totalAmount) VALUES(NULL,?,NULL,?,?,NOW(),NULL,?,?)");
			ps.setInt(1, order.getIdCustomer());
			ps.setDouble(2, order.getLocationDestination().getLongitude());
			ps.setDouble(3, order.getLocationDestination().getLatitude());
			ps.setString(4, order.getStatus().name());
			ps.setDouble(5, order.getTotalAmount());
			System.out.println(ps.executeUpdate() + " record(s) created");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}

	@Override
	public boolean update(IDTO dto) {
		Order order = (Order) dto;
		try {
			ps = con.prepareStatement(
					"UPDATE `order` SET idDriver = ? ,destinationLongitude= ?, destinationLatitude=? ,orderStatus = ?,totalAmount=? WHERE id =?");
			ps.setInt(1, order.getIdDriver());
			ps.setDouble(2, order.getLocationDestination().getLongitude());
			ps.setDouble(3, order.getLocationDestination().getLatitude());
			ps.setString(4, order.getStatus().name());
			ps.setDouble(5, order.getTotalAmount());
			ps.setInt(6, order.getId());
			System.out.println(ps.executeUpdate() + " record(s) created");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}

	@Override
	public boolean delete(int id) {
		try {
			ps = con.prepareStatement("Update `order` set isDeleted=1 WHERE id=?");
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
			ps = con.prepareStatement("Update `order` set isDeleted=0 WHERE id=?");
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
		repoUser.destroy();
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
