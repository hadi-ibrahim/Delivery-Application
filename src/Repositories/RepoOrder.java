package Repositories;

import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Location;
import DTO.Order;
import Helpers.ConnectionManager;

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
		order.setIdDriver(resultSet.getInt("idDriver"));
		order.setStartDate(resultSet.getTimestamp("startDate"));
		order.setEndDate(resultSet.getTimestamp("endDate"));
		order.setOrderStatus(resultSet.getString("status"));
		order.setTotalAmount(resultSet.getDouble("totalAmount"));
		order.setLocationDestination(new Location(resultSet.getDouble("longitude"), resultSet.getDouble("latitude")));
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
		try {
			ps = con.prepareStatement(
					"Insert into order(id,idCustomer,idDriver,longitude, latitude ,dateStart,dateEnd,orderStatus,totalAmount) VALUES(NULL,?,NULL,?,?,NOW(),NULL,?,?");
			ps.setInt(1, order.getIdCustomer());
			ps.setDouble(2, order.getLocationDestination().getLongitude());
			ps.setDouble(3, order.getLocationDestination().getLatitude());
			ps.setString(4, order.getStatus().name());
			ps.setDouble(5, order.getTotalAmount());
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}
	
	
	
	
	
	
	
	
	/* to be replaced with:  
	  user.setDriverStatus(AVAILABLE)
	  order.setOrderStatus(CANCELED)
	  RepoUser.update(user)
	  RepoOrder.update(order)
	  For the end date, create a trigger on order update
	  => if status = canceled or status = completed, then set endDate = now()
	  
	  */
	

//	public boolean cancelOrder(Order order) {
//		if (order.getIdDriver() > 0) 
//			repoUser.setAvailable(order.getIdDriver());
//		try {
//			ps = con.prepareStatement("Update order SET status='Cancelled', endDate=Now() where idOrder=?");
//			ps.setInt(1, order.getId());
//			return true;
//		} catch (SQLException ex) {
//			System.out.println(ex);
//			return false;
//		}
//	}

	
	
	
	
	
	
	
	
	/* same as above */
	
//	public boolean finishOrder(Order order) {
//		if (order.getIdDriver() > 0)
//			repoUser.setAvailable(order.getIdDriver());
//		try {
//			ps = con.prepareStatement("Update order SET status='finished', endDate=Now() where idOrder=?");
//			ps.setInt(1, order.getId());
//			return true;
//		} catch (SQLException ex) {
//			System.out.println(ex);
//			return false;
//		}
//	}

	
	
	/* again, order.status= "Pending"
	  driver.status = "busy"
	  order.idDriver= driver.id
	  RepoUser.update(driver)
	  RepoOrder.update(Order)
	 
	 */
	
	
//	public boolean acceptOrder(Order order, int idDriver) {
//		repoUser.setBusy(idDriver);
//		order.setIdDriver(idDriver);
//		try {
//			ps = con.prepareStatement("Update order SET status='Pending Delivery',idDriver=? where idOrder=?");
//			ps.setInt(1, order.getIdDriver());
//			ps.setInt(2, order.getId());
//			return true;
//		} catch (SQLException ex) {
//			System.out.println(ex);
//			return false;
//		}
//	}

	
	
	
	
	
	/* can be completely skipped with a trigger on order update 
	  	if status is still pending THEN 
	  	insert into routeCheckpoint(NULL, NEW.orderId, NOW(), driver's location)
	  
	  */
//	public boolean addRouteCheckpoint(Order order) {
//		try {
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("INSERT INTO RouteCheckpoint(id,idOrder,time,location) Values(NULL," + order.getId()+",Now()," + repoUser.Get(order.getIdDriver()).getLocation() + ")");
//			if (rs.next())
//				return true;
//		} catch (SQLException ex) {
//			System.out.println(ex);
//			return false;
//		}
//		return false;
//	}

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
