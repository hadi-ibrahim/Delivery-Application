package Repositories;

import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.User;
import Helpers.ConnectionManager;

public class RepoUser {
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
        user.setStatus(resultSet.getString("status"));
        Point point = (Point)resultSet.getObject("location");
        user.setLocation(point);
        user.setIsDeleted(resultSet.getInt("isDeleted"));
        return user;
        }catch(SQLException e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    public User Get(int id) {
        User user = null;
        try {
            ps = con.prepareStatement("SELECT * FROM user WHERE id=?");
            ps.setInt(1, id);
            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return user;
    }

    public ArrayList<User> GetAll() {
        ArrayList<User> LstOfUsers = new ArrayList<User>();
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

    public boolean Create(User user) {
        try {
            ps = con.prepareStatement("INSERT INTO user(id,firstName,lastName,age,email,password,role,phone,isDeleted) VALUE(NULL,?,?,?,?,?,?,?,0);");
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getRole());
            ps.setString(7,user.getPhone());

            System.out.println(ps.executeUpdate() + " record(s) created");
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
    
    public boolean UpdateUser(User user) {
    	 try {
             ps = con.prepareStatement("UPDATE user SET firstName=?,lastName=?, age=?, email=?, password=?, role=?, phone=?,isDeleted=? WHERE id=?");
             ps.setString(1, user.getFirstname());
             ps.setString(2, user.getLastname());
             ps.setInt(3, user.getAge());
             ps.setString(4, user.getEmail());
             ps.setString(5, user.getPassword());
             ps.setString(6, user.getRole());
             ps.setString(7,user.getPhone());
             ps.setInt(8, user.getIsDeleted());
             ps.setInt(9, user.getId());
             System.out.println(ps.executeUpdate());
         } catch (SQLException ex) {
             System.out.println(ex);
             return false;
         }
         return true;
     }
    public boolean UpdateDriver(User user) {
    	 try {
             ps = con.prepareStatement("UPDATE user SET firstName=?,lastName=?, age=?, email=?, password=?, role=?, phone=?, status=?,location=POINT(?,?),isDeleted=? WHERE id=?");
             ps.setString(1, user.getFirstname());
             ps.setString(2, user.getLastname());
             ps.setInt(3, user.getAge());
             ps.setString(4, user.getEmail());
             ps.setString(5, user.getPassword());
             ps.setString(6, user.getRole());
             ps.setString(7,user.getPhone());
             ps.setString(8, user.getStatus());
             ps.setDouble(9, user.getLocation().getX());
             ps.setDouble(10,user.getLocation().getY());
             ps.setInt(11, user.getIsDeleted());
             ps.setInt(12, user.getId());
             
             
             System.out.println(ps.executeUpdate());
         } catch (SQLException ex) {
             System.out.println(ex);
             return false;
         }
         return true;
     }
    

    //TODO to adjust to soft delete
    public boolean Delete(User user) {
        try {
            ps = con.prepareStatement("Update user set isDeleted=1 WHERE id=?");
            ps.setInt(1, user.getId());
            System.out.println(ps.executeUpdate() + " records deleted");
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
    
    public boolean setAvailable(int idDriver) {
    	try {
    	 ps = con.prepareStatement("UPDATE user SET status=? WHERE id=?");
    	 ps.setString(1, "Available");
    	 ps.setInt(2, idDriver);
    	 return true;
    	}catch(SQLException e) {
    		return false;
    	}
		
	}
    public boolean setBusy(int idDriver) {
    	try {
    	 ps = con.prepareStatement("UPDATE user SET status=? WHERE id=?");
    	 ps.setString(1, "Busy");
    	 ps.setInt(2, idDriver);
    	 return true;
    	}catch(SQLException e) {
    		return false;
    	}
		
	}
    public boolean Destroy() {
    	ArrayList  <AutoCloseable> components = new ArrayList<AutoCloseable>(); 
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
    	for (AutoCloseable component :components) {
    		closeComponent(component);
    	}
    }
    
    public void closeComponent(AutoCloseable component){
   		try {
   			if(component!=null)
   				component.close();
   		} catch (Exception e) {
   			System.out.println(e.getMessage());
   		}
    }

	public User Login(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
