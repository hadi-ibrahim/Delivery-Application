package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Feedback;
import DTO.IDTO;
import Helpers.ConnectionManager;

public class RepoFeedback implements IRepo, ISoftDeletable {
	ResultSet rs;
	PreparedStatement ps;
	Connection con;
	Statement stmt;
	
	public RepoFeedback() {
		con = ConnectionManager.getConnection();
	}
	
	private Feedback extractFeedbackFromResultSet(ResultSet resultSet) throws SQLException {
		Feedback feedback = new Feedback();
		feedback.setId(resultSet.getInt("id"));
		feedback.setIdOrder(resultSet.getInt("idCustomer"));
		feedback.setIdUser(resultSet.getInt("idUser"));
		feedback.setDescription(resultSet.getString("description"));
		feedback.setIsDeleted(resultSet.getInt("isDeleted"));
		return feedback;
	}
	
	@Override
	public Feedback get(int id) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From feedback where id=" + id);
			if (rs.next())
				return extractFeedbackFromResultSet(rs);
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<IDTO> getAll() {
		ArrayList<IDTO> feedbacks = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From feedback");
			while (rs.next()) {
				feedbacks.add(extractFeedbackFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return feedbacks;
	}
	
	@Override
	public ArrayList<IDTO> getAllActive() {
		ArrayList<IDTO> feedbacks = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From feedback where isDeleted=0");
			while (rs.next()) {
				feedbacks.add(extractFeedbackFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return feedbacks;
	}

	@Override
	public ArrayList<IDTO> getAllDisabled() {
		ArrayList<IDTO> feedbacks = new ArrayList<IDTO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * From feedback where isDeleted=1");
			while (rs.next()) {
				feedbacks.add(extractFeedbackFromResultSet(rs));
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return feedbacks;
	}
	
	@Override
	public boolean create(IDTO dto) {
		Feedback feedback = (Feedback) dto;
		try {
			ps = con.prepareStatement(
					"Insert into feedback(id, idUser, idOrder, description, isDeleted) VALUES(NULL,?,?,?,0");
			ps.setInt(1, feedback.getIdOrder());
			ps.setInt(2, feedback.getIdUser());
			ps.setString(3,feedback.getDescription());
			System.out.println(ps.executeUpdate() + " record(s) created");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}
	
	@Override
	public boolean update(IDTO dto) {
		Feedback feedback = (Feedback) dto;
		try {
			ps = con.prepareStatement(
					"UPDATE feedback SET idOrder = ?, idUser=?, description=?, isDeleted=? WHERE id =?");
			ps.setInt(1, feedback.getIdOrder());
			ps.setInt(2, feedback.getIdUser());
			ps.setString(3, feedback.getDescription());
			ps.setInt(4, feedback.getIsDeleted());
			ps.setInt(5, feedback.getId());
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
			ps = con.prepareStatement("Update feedback set isDeleted=1 WHERE id=?");
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
			ps = con.prepareStatement("Update feedback set isDeleted=0 WHERE id=?");
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

	

}
