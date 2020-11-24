package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import DTO.IDTO;
import DTO.Location;
import DTO.RouteCheckpoint;
import Helpers.ConnectionManager;

public class RepoRouteCheckpoint implements ISoftDeletableRepo {
	ResultSet rs;
	PreparedStatement ps;
	Connection con;
	Statement stmt;
	
	public RepoRouteCheckpoint() {
		con = ConnectionManager.getConnection();
	}
	
	private RouteCheckpoint extractRouteCheckpointFromResultSet(ResultSet resultSet) throws SQLException {
		RouteCheckpoint routeCheckpoint = new RouteCheckpoint();
		routeCheckpoint.setId(resultSet.getInt("id"));
		routeCheckpoint.setIdOrder(resultSet.getInt("idOrder"));
		routeCheckpoint.setLocation(new Location(resultSet.getDouble("longitude"),resultSet.getDouble("latitude")));
		routeCheckpoint.setTime(resultSet.getTimestamp("time"));
		routeCheckpoint.setIsDeleted(resultSet.getInt("isDeleted"));
		return routeCheckpoint;
	}
	
	@Override
	public RouteCheckpoint get(int id) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From order where id=" + id);
			if (rs.next())
				return extractRouteCheckpointFromResultSet(rs);
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<IDTO> getAll() {
		ArrayList<IDTO> routeCheckpoints = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From order");
			while (rs.next()) {
				routeCheckpoints.add(extractRouteCheckpointFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return routeCheckpoints;
	}
	
	@Override
	public boolean create(IDTO dto) {
		RouteCheckpoint routeCheckpoint = (RouteCheckpoint) dto;
		try {
			ps = con.prepareStatement(
					"Insert into routeCheckpoint(id, idOrder, time, longitude, latitude , isDeleted) VALUES(NULL,?,NOW(),?,?,0");
			ps.setInt(1, routeCheckpoint.getIdOrder());
			ps.setTimestamp(2, routeCheckpoint.getTime());
			ps.setDouble(3,routeCheckpoint.getLocation().getLongitude());
			ps.setDouble(4, routeCheckpoint.getLocation().getLatitude());
			System.out.println(ps.executeUpdate() + " record(s) created");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}
	
	@Override
	public boolean update(IDTO dto) {
		RouteCheckpoint routeCheckpoint = (RouteCheckpoint) dto;
		try {
			ps = con.prepareStatement(
					"UPDATE routeCheckpoint SET idOrder = ? ,longitude= ?, latitude=? ,time = ?,isDeleted=? WHERE id =?");
			ps.setInt(1, routeCheckpoint.getIdOrder());
			ps.setDouble(2, routeCheckpoint.getLocation().getLongitude());
			ps.setDouble(3, routeCheckpoint.getLocation().getLatitude());
			ps.setTimestamp(4, routeCheckpoint.getTime());
			ps.setInt(5, routeCheckpoint.getIsDeleted());
			ps.setInt(6, routeCheckpoint.getId());
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
			ps = con.prepareStatement("Update routeCheckpoint set isDeleted=1 WHERE id=?");
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
			ps = con.prepareStatement("Update routeCheckpoint set isDeleted=0 WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}
	
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
	
	public ArrayList<RouteCheckpoint> getOrderRoute(int idOrder) {
		ArrayList<RouteCheckpoint> routeCheckpoints = new ArrayList<RouteCheckpoint>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From routeCheckpoint where idOrder =" + idOrder);
			while (rs.next()) {
				routeCheckpoints.add(extractRouteCheckpointFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return routeCheckpoints;
	}

}
