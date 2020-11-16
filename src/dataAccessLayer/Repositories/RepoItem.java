package dataAccessLayer.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataAccessLayer.DTO.Item;
import dataAccessLayer.DTO.WarehouseItemInformation;
import dataAccessLayer.Helpers.ConnectionManager;

public class RepoItem {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	RepoWarehouseItem repoWarehouseItem = new RepoWarehouseItem();

	public RepoItem() {
		con = ConnectionManager.getConnection();
	}

	private Item extractItemFromResultSet(ResultSet resultSet) {
		try {
			Item item = new Item();
			item.setId(resultSet.getInt("id"));
			item.setPrice(resultSet.getDouble("price"));
			item.setAvailability(repoWarehouseItem.getAvailability(item.getId()));
			item.setCategory(resultSet.getString("category"));
			item.setDescription(resultSet.getString("description"));
			item.setIsDeleted(resultSet.getInt("isDeleted"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Item Get(int id) {
		Item item = null;
		try {
			ps = con.prepareStatement("SELECT * FROM item WHERE id=?");
			ps.setInt(1, id);
			if (rs.next()) {
				item = extractItemFromResultSet(rs);
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return item;
	}

	public ArrayList<Item> GetAll() {
		ArrayList<Item> LstOfItems = new ArrayList<Item>();
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

	public boolean Create(Item item) {
		try {
			ps = con.prepareStatement("INSERT INTO item(id,price,description,category,isDeleted) Values(Null,?,?,?,?)");
			ps.setDouble(1, item.getPrice());
			ps.setString(2, item.getDescription());
			ps.setString(3, item.getCategory());
			ps.setInt(4, item.getIsDeleted());
			System.out.println(ps.executeUpdate() + " record(s) created");
			ps = con.prepareStatement("INSERT INTO warehouseItem(idWarehouse,idItem,itemQuantity) Values(?,?,?)");
			for(WarehouseItemInformation wh : item.getAvailability()) {
				ps.setInt(1,wh.getIdWarehouse());
				ps.setInt(2, item.getId());
				ps.setInt(3, wh.getQuantity());
				System.out.println(ps.executeUpdate() + " record(s) created");
			}
			
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	public boolean Update(Item item) {
		try {
			ps = con.prepareStatement(
					"UPDATE item SET price=?,description=?, category=?, isDeleted=?,WHERE id=?");
			ps.setDouble(1, item.getPrice());
			ps.setString(2, item.getDescription());
			ps.setString(3, item.getCategory());
			ps.setInt(4, item.getIsDeleted());
			ps.setInt(5,item.getId());

			System.out.println(ps.executeUpdate());
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}
	public boolean addItemStockByWarehouse(int idWarehouse,int idItem, int quantity) {
		return repoWarehouseItem.addByWarehouse(idWarehouse,idItem,quantity);
	}
	public boolean addItemStockGlobally(int idItem, int quantity) {
		return repoWarehouseItem.addGlobally(idItem,quantity);
	}
	public boolean Delete(int id) {
		try {
			repoWarehouseItem.deleteItemGlobally(id);
			ps = con.prepareStatement("Update item set isDeleted=1 item WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records deleted");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}
	public boolean Restore(int id) {
		try {
			ps = con.prepareStatement("Update item set isDeleted=0 item WHERE id=?");
			ps.setInt(1, id);
			System.out.println(ps.executeUpdate() + " records restored");
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
		
	}
	public boolean Destroy() {
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
