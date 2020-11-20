package Helpers;

import DTO.User;

public class SessionHelper {
	public static User isLoggedIn;
	
	public static void signOut() {
		isLoggedIn = null;
	}
}
