package presentationLayer;

import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import DTO.Address;
import DTO.Location;
import DTO.Order;
import DTO.OrderStatus;
import DTO.OrderedWarehouseItem;
import DTO.RouteCheckpoint;
import DTO.User;
import DTO.Warehouse;
import DTO.WarehouseItem;
import businessLogicLayer.LocationManager.LocationManager;

public class test {
	public static void main(String[] args) throws InterruptedException {

		/* --------------- Get user current location --------------- 
	  getLocation loc=new getLocation(); 
	  Location loca = loc.getUserLocation();
		  System.out.println(loca.getLongitude()+","+loca.getLatitude());
		 */
		
		
		
		
		
		/*---------------- Choose Destination / Save Address --------------
		  AddressPicker add = new AddressPicker();
		  Location loca = add.pickAddress();
		  System.out.println(loca.getLongitude()+","+loca.getLatitude());
		 */

		
		
		
		/*---------------- Update driver location unit test-------------
		User driverCar = new User();
		driverCar.setLocation(new Location(35.61726, 33.89821));
		driverCar.setId(1);
		
		User customer = new User();
		customer.setId(2);
		
		Address address = new Address();
		address.setId(1);
		address.setLocation(new Location(35.63449,33.88225));
		
		
		Warehouse warehouse = new Warehouse();
		warehouse.setId(1);
		warehouse.setIdAddress(1);
		
		
		WarehouseItem warehouseItem = new WarehouseItem();
		warehouseItem.setId(1);
		warehouseItem.setIdWarehouse(1);
		
		OrderedWarehouseItem orderedItem = new OrderedWarehouseItem();
		orderedItem.setIdWarehouseItem(1);
		
		ArrayList<OrderedWarehouseItem> orderedItems = new ArrayList<OrderedWarehouseItem>();
		orderedItems.add(orderedItem);
	
		Order order = new Order();
		order.setLocationDestination(new Location(35.61726, 33.82821)); // should be chosen by user
		order.setIdDriver(1);
		order.setOrderedItems(orderedItems);
		// FOR WAREHOUSE
		order.setOrderStatus(OrderStatus.PENDING);
		//FOR CUSTOMER : 
		order.setOrderStatus(OrderStatus.DELIVERING);
		
		String driverLong = Double.toString(driverCar.getLocation().getLongitude());
		String driverLat =  Double.toString(driverCar.getLocation().getLatitude());
		String destinationLong = null;
		String destinationLat= null;
		if(order.getStatus() == OrderStatus.PENDING) {
			destinationLong = Double.toString(address.getLocation().getLongitude());
			destinationLat = Double.toString(address.getLocation().getLatitude());
			System.out.println("Driver is driving to warehouse at "+ destinationLong + ","+destinationLat);
		
		}else if (order.getStatus() == OrderStatus.DELIVERING) {
			destinationLong = Double.toString(order.getLocationDestination().getLongitude());
			destinationLat = Double.toString(order.getLocationDestination().getLatitude());
			System.out.println("Driver is driving to user at "+ destinationLong + ","+destinationLat);
		}
		System.out.println("Driver is currently at : "+driverLong + ","+driverLat);
		System.setProperty("webdriver.chrome.driver", "src/Drivers/chromedriver.exe");
		String getLocationPathHTML = new File("src/businessLogicLayer/DriverLocationUpdater.html").getAbsolutePath();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=1920,1080");
		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(getLocationPathHTML);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("map")));
		driver.findElement(By.id("sentDestinationLong")).sendKeys(destinationLong);
		driver.findElement(By.id("sentDestinationLat")).sendKeys(destinationLat);
		driver.findElement(By.id("sentOriginLong")).sendKeys(driverLong);
		driver.findElement(By.id("sentOriginLat")).sendKeys(driverLat);
		driver.findElement(By.id("sent")).clear();
		driver.findElement(By.id("sent")).sendKeys("true");
		while (true) {
			WebElement flag = driver.findElement(By.id("choice"));
			
			if (flag.getAttribute("value").equalsIgnoreCase("true")) {
				WebElement longitude = driver.findElement(By.id("updateOriginLong"));
				WebElement latitude = driver.findElement(By.id("updateOriginLat"));
				driverLong = longitude.getAttribute("value");
				driverLat = latitude.getAttribute("value");
				break;
			}
			Thread.sleep(1000);
		}
		driver.quit();
		System.out.println("Driver's location has been updated, he's currently at: "+driverLong + ","+driverLat);
		*/
		
		
		
		
		
		
		
		
		
		
		/*---------- Check driver current location by customer that ordered unit test ------------
		User driverCar = new User();
		driverCar.setLocation(new Location(35.61726, 33.89821));
		driverCar.setId(1);
		
		User customer = new User();
		customer.setId(2);
		
		
		Warehouse warehouse = new Warehouse();
		warehouse.setId(1);
		warehouse.setLongitude(35.63449);
		warehouse.setLatitude(33.88225);
		
		WarehouseItem warehouseItem = new WarehouseItem();
		warehouseItem.setId(1);
		warehouseItem.setIdWarehouse(1);
		
		OrderedWarehouseItem orderedItem = new OrderedWarehouseItem();
		orderedItem.setIdWarehouseItem(1);
		
		ArrayList<OrderedWarehouseItem> orderedItems = new ArrayList<OrderedWarehouseItem>();
		orderedItems.add(orderedItem);
	
		Order order = new Order();
		order.setLocationDestination(new Location(35.61726, 33.82821)); // should be chosen by user
		order.setIdDriver(1);
		order.setOrderedItems(orderedItems);
		// FOR WAREHOUSE
		order.setOrderStatus(OrderStatus.PENDING);
		//FOR CUSTOMER : 
		//order.setOrderStatus(OrderStatus.DELIVERING);
		
		String driverLong = Double.toString(driverCar.getLocation().getLongitude());
		String driverLat =  Double.toString(driverCar.getLocation().getLatitude());
		String destinationLong = null;
		String destinationLat= null;
		if(order.getStatus() == OrderStatus.PENDING) {
			destinationLong = Double.toString(warehouse.getLongitude());
			destinationLat = Double.toString(warehouse.getLatitude());
			System.out.println("Driver is driving to warehouse at "+ destinationLong + ","+destinationLat);
		
		}else if (order.getStatus() == OrderStatus.DELIVERING) {
			destinationLong = Double.toString(order.getLocationDestination().getLongitude());
			destinationLat = Double.toString(order.getLocationDestination().getLatitude());
			System.out.println("Driver is driving to user at "+ destinationLong + ","+destinationLat);
		}
		System.out.println("Driver is currently at : "+driverLong + ","+driverLat);
		System.setProperty("webdriver.chrome.driver", "src/Drivers/chromedriver.exe");
		String getLocationPathHTML = new File("src/businessLogicLayer/DriverTracking.html").getAbsolutePath();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=1920,1080");
		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(getLocationPathHTML);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("map")));
		driver.findElement(By.id("sentDestinationLong")).sendKeys(destinationLong);
		driver.findElement(By.id("sentDestinationLat")).sendKeys(destinationLat);
		driver.findElement(By.id("sentOriginLong")).sendKeys(driverLong);
		driver.findElement(By.id("sentOriginLat")).sendKeys(driverLat);
		driver.findElement(By.id("sent")).clear();
		driver.findElement(By.id("sent")).sendKeys("true");
		while (true) {
			WebElement flag = driver.findElement(By.id("choice"));
			if (flag.getAttribute("value").equalsIgnoreCase("true")) {
				break;
			}
			Thread.sleep(1000);
		}
		driver.quit();
		*/
		
		
		/*---------- Display route taken by driver to admin unit test ------------
		Order order = new Order();
		order.setId(1);
		
		ArrayList<RouteCheckpoint> cp = new ArrayList<RouteCheckpoint>();
		
		RouteCheckpoint cp1 = new RouteCheckpoint();
		RouteCheckpoint cp2 = new RouteCheckpoint();
		RouteCheckpoint cp3 = new RouteCheckpoint();
		RouteCheckpoint cp4 = new RouteCheckpoint();
	
		cp1.setId(1);
		cp2.setId(2);
		cp3.setId(3);
		cp4.setId(4);
		cp1.setIdOrder(1);
		cp2.setIdOrder(1);
		cp3.setIdOrder(1);
		cp4.setIdOrder(1);
		
		cp1.setLocation(new Location(35.63449,33.88225));
		cp.add(cp1);
		cp2.setLocation(new Location(35.73449,33.98225));
		cp.add(cp2);
		cp3.setLocation(new Location(35.83449,33.68225));
		cp.add(cp3);
		cp4.setLocation(new Location(35.93449,33.78225));
		cp.add(cp4);
		String checkPoints="";
		for( RouteCheckpoint c : cp){
			checkPoints=checkPoints+Double.toString(c.getLocation().getLongitude())+","+Double.toString(c.getLocation().getLatitude())+";";
		}
		checkPoints = checkPoints.substring(0,checkPoints.length()-1);
		System.setProperty("webdriver.chrome.driver", "src/Drivers/chromedriver.exe");
		String getLocationPathHTML = new File("src\\businessLogicLayer\\LocationManager\\DisplayRoute.html").getAbsolutePath();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=1920,1080");
		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(getLocationPathHTML);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("map")));
		driver.findElement(By.id("sentJSON")).sendKeys(checkPoints);
		driver.findElement(By.id("isSent")).clear();
		driver.findElement(By.id("isSent")).sendKeys("true");
		while (true) {
			WebElement flag = driver.findElement(By.id("isDone"));
			if (flag.getAttribute("value").equalsIgnoreCase("true")) {
				break;
			}
			Thread.sleep(1000);
		}
		driver.quit();
		*/
}	
	
}
