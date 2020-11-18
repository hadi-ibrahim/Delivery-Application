package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Address;
import DTO.User;
import DTO.UserSavedAddress;
import Helpers.ConnectionManager;

public class RepoUserSavedAddress {
	Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
	RepoUser repoUser = new RepoUser();
	RepoAddress repoAddress = new RepoAddress();

	public RepoUserSavedAddress() {
		con = ConnectionManager.getConnection();
	}

	private UserSavedAddress extractUserSavedAddressFromResultSet(ResultSet resultSet) {
		UserSavedAddress userSavedAddress = new UserSavedAddress();
		try {
			userSavedAddress.setIdAddress(resultSet.getInt("idAddress"));
			userSavedAddress.setIdUser(resultSet.getInt("idUser"));
			userSavedAddress.setIsDisabled(resultSet.getInt("isDisabled"));
			return userSavedAddress;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

	}

	public ArrayList<UserSavedAddress> getByUser(int idUser) {
		ArrayList<UserSavedAddress> addresses = new ArrayList<UserSavedAddress>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From userSavedAddress where isDisabled=0 AND idUser =" + idUser);
			while (rs.next()) {
				addresses.add(extractUserSavedAddressFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return null;
	}

	public void setByUser() {
		for (User user : repoUser.GetAll()) {
			user.setUserAddresses(getByUser(user.getId()));
		}
	}

	public boolean create(UserSavedAddress userSavedAddress) {
		try {
			ps = con.prepareStatement(
					"Insert into userSavedAddress(idUser,idAddress,isDisabled) Values(?,?,0)");
			ps.setInt(1, userSavedAddress.getIdUser());
			ps.setInt(2, userSavedAddress.getIdAddress());
			System.out.println(ps.executeUpdate() + " record(s) created");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	public boolean Delete(UserSavedAddress userSavedAddress) {
		try {
			ps = con.prepareStatement("Update userSavedAddress SET isDeleted=1  where idUser=? AND idAddress=?");
			ps.setInt(1, userSavedAddress.getIdUser());
			ps.setInt(2, userSavedAddress.getIdAddress());
			System.out.println(ps.executeUpdate() + " record(s) deleted");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}
	public boolean deleteByUser(int userId) {
		try {
			ps = con.prepareStatement("Update address SET isDeleted=1  where id=?");
			ps.setInt(1, userId);
			System.out.println(ps.executeUpdate() + " record(s) deleted");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
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
