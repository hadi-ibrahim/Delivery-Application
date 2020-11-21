package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.IDTO;
import DTO.UserSavedAddress;
import Helpers.ConnectionManager;

public class RepoUserSavedAddress implements IRepo {
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
			return userSavedAddress;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

	}

	@Override
	public UserSavedAddress get(int id) {
		UserSavedAddress userAddress = null;
		try {
			ps = con.prepareStatement("SELECT * FROM usersavedaddress WHERE id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				userAddress = extractUserSavedAddressFromResultSet(rs);
				return userAddress;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<IDTO> getAll() {
		ArrayList<IDTO> ListOfUserSavedAddresses = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From item");
			while (rs.next()) {
				ListOfUserSavedAddresses.add(extractUserSavedAddressFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return ListOfUserSavedAddresses;
	}

	@Override
	public boolean create(IDTO dto) {
		UserSavedAddress userSavedAddress = (UserSavedAddress) dto;
		try {
			ps = con.prepareStatement("Insert into userSavedAddress(id,idUser,idAddress) Values(NULL,?,?)");
			ps.setInt(1, userSavedAddress.getIdUser());
			ps.setInt(2, userSavedAddress.getIdAddress());
			System.out.println(ps.executeUpdate() + " record(s) created");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean update(IDTO dto) {
		UserSavedAddress userSavedAddress = (UserSavedAddress) dto;
		try {
			ps = con.prepareStatement("UPDATE userSavedAddress set idUser=?, idAddress=? WHERE id=?");
			ps.setInt(1, userSavedAddress.getIdUser());
			ps.setInt(2, userSavedAddress.getIdAddress());
			ps.setInt(3, userSavedAddress.getId());

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
			ps = con.prepareStatement("DELETE FROM usersavedaddress where id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " record(s) deleted");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

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

	public ArrayList<UserSavedAddress> getAllUserAddresses(int idUser) {
		ArrayList<UserSavedAddress> addresses = new ArrayList<UserSavedAddress>();
		try {
			ps = con.prepareStatement("SELECT * FROM usersavedaddress WHERE idUser=?");
			ps.setInt(1, idUser);
			rs = ps.executeQuery();

			while (rs.next()) {
				addresses.add(extractUserSavedAddressFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return null;
	}

}
