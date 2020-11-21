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
	private Role role;
	private String phone;
	private DriverStatus driverStatus;
	private Location location;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setRole(String role) {
		this.role = Role.valueOf(role);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public DriverStatus getDriverStatus() {
		return driverStatus;
	}

	public void setDriverStatus(DriverStatus driverStatus) {
		this.driverStatus = driverStatus;
	}
	
	public void setDriverStatus(String driverStatus) {
		this.driverStatus = DriverStatus.valueOf(driverStatus);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
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
