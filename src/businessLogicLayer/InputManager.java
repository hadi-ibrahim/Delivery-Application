package businessLogicLayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DTO.Category;
import DTO.Role;

public class InputManager {
	 public static boolean verifyPositiveInteger(String s) {
	        int isPositiveInt = -1;
	        try {
	            isPositiveInt = Integer.parseInt(s);
	        } catch (NumberFormatException e) {
	            return false;
	        }
	        if (isPositiveInt < 0) {
	            return false;
	        }
	        return true;
	    }

	public static boolean verifyRole(String role) {
		for (Role r : Role.values()) {
			if (r.toString().equals(role)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean verifyCategory(String category) {
		for (Category c : Category.values()) {
			if (c.toString().equals(category)) {
				return true;
			}
		}
		return false;
	}

	public static boolean verifyPositiveDouble(String s) {
		 Double isPositiveDouble = -1.00;
	        try {
	            isPositiveDouble = Double.parseDouble(s);
	        } catch (NumberFormatException e) {
	            return false;
	        }
	        if (isPositiveDouble < 0) {
	            return false;
	        }
	        return true;
	}
	
	public static boolean verifyLongitude(String s) {
		 Double validLongitude = -200.00;
	        try {
	            validLongitude = Double.parseDouble(s);
	        } catch (NumberFormatException e) {
	            return false;
	        }
	        if (validLongitude < -180 || validLongitude > 180) {
	            return false;
	        }
	        return true;	
	}
	
	public static boolean verifyLatitude(String s) {
		 Double validLatitude = -200.00;
	        try {
	            validLatitude = Double.parseDouble(s);
	        } catch (NumberFormatException e) {
	            return false;
	        }
	        if (validLatitude < -90 || validLatitude > 90) {
	            return false;
	        }
	        return true;	
	}
	
	public static boolean verifyEmail(String s) {
        Matcher matcher = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", Pattern.CASE_INSENSITIVE).matcher(s);
        return matcher.find();
	}
	
	public static boolean verifyPassword(String s ) {
        Matcher matcher = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", Pattern.CASE_INSENSITIVE).matcher(s);
        return matcher.find();
	}
	
	public static boolean verifyPhoneNumber(String s ) {
        Matcher matcher = Pattern.compile("^[0-9]{8}$", Pattern.CASE_INSENSITIVE).matcher(s);
        return matcher.find();
	}
	public static boolean verifyStringNotEmpty(String s) {
		
		if(s.isBlank() || s.isEmpty()) {
			return false;
		}
		else return true;
	}
	
}
