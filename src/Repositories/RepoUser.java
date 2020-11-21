package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.IDTO;
import DTO.Location;
import DTO.Role;
import DTO.User;
import Helpers.ConnectionManager;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class RepoUser implements ISoftDeletableRepo {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	public RepoUser() {
		con = ConnectionManager.getConnection();
	}

	private User extractUserFromResultSet(ResultSet resultSet) {
		try {
			User user = new User();
			user.setId(resultSet.getInt("id"));
			user.setEmail(resultSet.getString("email"));
			user.setFirstname(resultSet.getString("firstName"));
			user.setLastname(resultSet.getString("lastName"));
			user.setAge(resultSet.getInt("age"));
			user.setPassword(resultSet.getString("password"));
			user.setRole(resultSet.getString("role"));
			user.setPhone(resultSet.getString("phone"));
			if (resultSet.getString("driverStatus") != null)
				user.setDriverStatus(resultSet.getString("driverStatus"));
			user.setLocation(new Location(resultSet.getDouble("longitude"), resultSet.getDouble("latitude")));
			user.setIsDeleted(resultSet.getInt("isDeleted"));
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User get(int id) {
		User user = null;
		try {
			ps = con.prepareStatement("SELECT * FROM user WHERE id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = extractUserFromResultSet(rs);
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return user;
	}

	public ArrayList<IDTO> getAll() {
		ArrayList<IDTO> LstOfUsers = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From user");
			while (rs.next()) {
				LstOfUsers.add(extractUserFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return LstOfUsers;
	}

	public boolean create(IDTO dto) {
		User user = (User) dto;
		try {
			ps = con.prepareStatement(
					"INSERT INTO user(id,firstName,lastName,age,email,password,role,phone,isDeleted) VALUE(NULL,?,?,?,?,?,?,?,0);");
			ps.setString(1, user.getFirstname());
			ps.setString(2, user.getLastname());
			ps.setInt(3, user.getAge());
			ps.setString(4, user.getEmail());
			ps.setString(5, BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
			ps.setString(6, Role.CUSTOMER.name());
			ps.setString(7, user.getPhone());

			System.out.println(ps.executeUpdate() + " record(s) created");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	public boolean update(IDTO dto) {
		User user = (User) dto;
		try {
			ps = con.prepareStatement(
					"UPDATE user SET firstName=?,lastName=?, age=?, email=?, password=?, role=?, phone=?, driverStatus=?,longitude= ?,latitude =?,isDeleted=? WHERE id=?");
			ps.setString(1, user.getFirstname());
			ps.setString(2, user.getLastname());
			ps.setInt(3, user.getAge());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getPassword());
			ps.setString(6, user.getRole().name());
			ps.setString(7, user.getPhone());
			if (user.getDriverStatus() != null)
				ps.setString(8, user.getDriverStatus().name());
			else
				ps.setString(8, null);
			if(user.getRole() == Role.DRIVER) {
				ps.setDouble(9, user.getLocation().getLongitude());
				ps.setDouble(10, user.getLocation().getLatitude());
			}
			
			else {
				ps.setString(9, null);
				ps.setString(10, null);
			}
			ps.setInt(11, user.getIsDeleted());
			ps.setInt(12, user.getId());

			System.out.println(ps.executeUpdate() + " row updated");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(int id) {
		try {
			ps = con.prepareStatement("Update user set isDeleted=1 WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records deleted");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean restore(int id) {
		try {
			ps = con.prepareStatement("Update user set isDeleted=0 WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records deleted");
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

	public User login(String email, String password) {
		try {

			ps = con.prepareStatement("SELECT * FROM USER WHERE email=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (!rs.next())
				return null;
			if (BCrypt.verifyer().verify(password.toCharArray(), rs.getString("password")).verified)
				return extractUserFromResultSet(rs);
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return null;
	}



}
