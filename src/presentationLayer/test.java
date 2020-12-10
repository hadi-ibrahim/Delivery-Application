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
import DTO.User;
import DTO.Warehouse;
import DTO.WarehouseItem;
import presentationLayer.LocationManager.chooseAddress;
import presentationLayer.LocationManager.getLocation;

public class test {
	public static void main(String[] args) throws InterruptedException {
		/*
		 * --------------- Get user current location --------------- getLocation loc=new
		 * getLocation(); Location loca = loc.getUserLocation();
		 * System.out.println(loca.getLongitude()+","+loca.getLatitude());
		 */

		/*---------------- Choose Destination / Save Address --------------
		 * chooseAddress add = new chooseAddress();
		 * Location loca = add.addressChoice();
		 * System.out.println(loca.getLongitude()+","+loca.getLatitude());
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
		String getLocationPathHTML = new File("src/presentationLayer/test.html").getAbsolutePath();
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
}
	
}
