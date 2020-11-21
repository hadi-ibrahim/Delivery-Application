package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.IDTO;
import DTO.Item;
import Helpers.ConnectionManager;

public class RepoItem implements ISoftDeletableRepo {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	public RepoItem() {
		con = ConnectionManager.getConnection();
	}

	private Item extractItemFromResultSet(ResultSet resultSet) {
		try {
			Item item = new Item();
			item.setId(resultSet.getInt("id"));
			item.setPrice(resultSet.getDouble("price"));
			item.setCategory(resultSet.getString("category"));
			item.setDescription(resultSet.getString("description"));
			item.setIsDeleted(resultSet.getInt("isDeleted"));
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Item get(int id) {
		Item item = null;
		try {
			ps = con.prepareStatement("SELECT * FROM item WHERE id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				item = extractItemFromResultSet(rs);
				return item;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
		return null;
	}

	public ArrayList<IDTO> getAll() {
		ArrayList<IDTO> LstOfItems = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From item");
			while (rs.next()) {
				LstOfItems.add(extractItemFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return LstOfItems;
	}

//TODO implement in business logic	
//	public boolean exists(int id) {
//		try {
//			ps = con.prepareStatement("SELECT * FROM item WHERE id=?");
//			ps.setInt(1, id);
//			if (rs.next()) {
//				return true;
//			}
//		} catch (SQLException ex) {
//			System.out.println(ex);
//			return false;
//		}
//		return false;
//		
//	}

	@Override
	public boolean create(IDTO dto) {
		Item item = (Item) dto;
		try {
			ps = con.prepareStatement("INSERT INTO item(id,price,description,category,isDeleted) Values(NULL,?,?,?,0)");
			ps.setDouble(1, item.getPrice());
			ps.setString(2, item.getDescription());
			ps.setString(3, item.getCategory().name());
			System.out.println(ps.executeUpdate() + " record(s) created");

		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean update(IDTO dto) {
		Item item = (Item) dto;
		try {
			ps = con.prepareStatement("UPDATE item SET price=?,description=?, category=?, isDeleted=? WHERE id=?");
			ps.setDouble(1, item.getPrice());
			ps.setString(2, item.getDescription());
			ps.setString(3, item.getCategory().name());
			ps.setInt(4, item.getIsDeleted());
			ps.setInt(5, item.getId());

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
			ps = con.prepareStatement("Update item set isDeleted=1 WHERE id=?");
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
			ps = con.prepareStatement("Update item set isDeleted=0 WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;

	}

	@SuppressWarnings("deprecation")
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
