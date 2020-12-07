package presentationLayer;

import DTO.Location;
import businessLogicLayer.LocationManager.AddressPicker;
import businessLogicLayer.LocationManager.getLocation;

public class test {
	public static void main(String[] args) throws InterruptedException {
		
		//  --------------- Get user current location --------------- 
//		  getLocation loc=new getLocation(); 
//		  Location loca = loc.getUserLocation();
//		  System.out.println(loca.getLongitude()+","+loca.getLatitude());
		 
		
		//---------------- Choose Destination / Save Address --------------
		  AddressPicker add = new AddressPicker();
		  Location loca = add.pickAddress();
		  System.out.println(loca.getLongitude()+","+loca.getLatitude());
		 
	}
}
