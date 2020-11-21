package Repositories;

import java.awt.Point;

import DTO.Address;
import DTO.Category;
import DTO.DriverStatus;
import DTO.Item;
import DTO.Location;
import DTO.Role;
import DTO.User;
import DTO.Warehouse;
import DTO.WarehouseItem;

public class RepoTester {
	static RepoAddress address = new RepoAddress ();
	static RepoItem item = new RepoItem();
	RepoOrder order = new RepoOrder();
//	RepoOrderedWarehouseItem orderedWarehouseItem = new RepoOrderedWarehouseItem();
	RepoUserSavedAddress userAddress = new RepoUserSavedAddress();
	static RepoWarehouse warehouse = new RepoWarehouse();
	RepoWarehouseItem warehouseItem = new RepoWarehouseItem();
	static RepoUser user= new RepoUser();
	
	public static void main(String args[]) {


		Address a = new Address();
		a.setLocation(new Location(22.44, 23.666));
		address.create(a);
		
		a.setLocation(new Location(55.123124, 180.24242));
		a.setStreet("Elias al Herawi");
		a.setCity("Furn al chebbek");
		a.setBuilding("Residence de la ville");
		a.setFloor("2nd floor");
		System.out.println(a.getLocation().getLongitude());
		address.create(a);
		
		a=address.get(2);
		System.out.println(a.getLocation().getLongitude() + " " + a.getLocation().getLatitude());
		a= address.get(1);
		a.setBuilding("UA");
		address.update(a);
		
		Item i= new Item();
		i.setCategory(Category.FOOD);
		i.setDescription("Heart attack burger");
		i.setPrice(15.3);
		item.create(i);
		i= item.get(1);
		i.setCategory(Category.GROCERY);
		item.update(i);
		
		System.out.println(i.getCategory() + " " + i.getDescription() );
		
		Warehouse w = new Warehouse();
		w.setName("Junkies");
		w.setIdAddress(2);
		warehouse.create(w);
		w = warehouse.get(1);
		w.setName("Carrefour");
		warehouse.update(w);
		System.out.println(w.getName());
		
//		WarehouseItem wi = new WarehouseItem();
//		wi.setIdItem(1);
//		wi.setIdWarehouse(1);
//		wi.setQuantity(500);
	
		
		User u;
		u =user.get(2);
		System.out.println(u.getFirstname()+ u.getLocation().getLatitude());
		u.setFirstname("Courier");
		u.setLastname("Swindler");
		u.setRole(Role.DRIVER);
		u.setDriverStatus(DriverStatus.AVAILABLE);
		u.setLocation(new Location(23.2323424,130.2331));
		user.update(u);
		
		}
	
	// example of methods in business logic instead of repositories
	private static void setAvailable(User u) {
		u.setDriverStatus(DriverStatus.AVAILABLE);
		user.update(u);
	}
	
	private static void setBusy(User u) {
		u.setDriverStatus(DriverStatus.BUSY);
		user.update(u);
	}
	
	private static void setUnavailable(User u) {
		u.setDriverStatus(DriverStatus.UNAVAILABLE);
		user.update(u);
	}

}
