package DTO;

import java.awt.Point;
import java.util.ArrayList;

public class User {
	private int id;
	private String firstname;
	private String lastname;
	private int age;
	private String email;
	private String password;
	private String role;
	private String phone;
	private String status;
	private Point location;
	private int isDeleted;
	private ArrayList<UserSavedAddress> userAddresses;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public ArrayList<UserSavedAddress> getUserAddresses() {
		return userAddresses;
	}

	public void setUserAddresses(ArrayList<UserSavedAddress> userAddresses) {
		this.userAddresses = userAddresses;
	}
}
