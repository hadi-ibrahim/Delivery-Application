package businessLogicLayer.LocationManager;

import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import DTO.IDTO;
import DTO.Location;
import DTO.Order;
import DTO.OrderStatus;
import DTO.RouteCheckpoint;
import DTO.User;
import DTO.Warehouse;
import Repositories.RepoWarehouse;

public class LocationManager {
RepoWarehouse repoWarehouse = new RepoWarehouse();
	public Location pickAddress() {
		System.setProperty("webdriver.chrome.driver", "src/Drivers/chromedriver.exe");
		String HTMLPath = new File("src/businessLogicLayer/locationManager/AddressPicker.html").getAbsolutePath();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=1920,1080", "--app=" + HTMLPath);
		WebDriver driver = new ChromeDriver(options);
		String currLong = "0";
		String currLat = "0";
		while (true) {
			WebElement flag = driver.findElement(By.id("choice"));
			if (flag.getAttribute("value").equalsIgnoreCase("true")) {
				WebElement longitude = driver.findElement(By.id("longitude"));
				WebElement latitude = driver.findElement(By.id("latitude"));
				currLong = longitude.getAttribute("value");
				currLat = latitude.getAttribute("value");
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
		return new Location(Double.parseDouble(currLong), Double.parseDouble(currLat));
	}

	public Location getCurrentLocation() {
		System.setProperty("webdriver.chrome.driver", "src/Drivers/chromedriver.exe");
		String HTMLPath = new File("src/businessLogicLayer/LocationManager/getLocation.html").getAbsolutePath();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=0,0", "--app=" + HTMLPath);
		WebDriver driver = new ChromeDriver(options);
		String currLong = "0";
		String currLat = "0";
		while (true) {
			WebElement flag = driver.findElement(By.id("flag"));
			if (flag.getAttribute("value").equalsIgnoreCase("true")) {
				WebElement accuracy = driver.findElement(By.id("accuracy"));
				if (Integer.parseInt(accuracy.getAttribute("value")) < 100) {
					WebElement longitude = driver.findElement(By.id("longitude"));
					WebElement latitude = driver.findElement(By.id("latitude"));
					currLong = longitude.getAttribute("value");
					currLat = latitude.getAttribute("value");
				}
				break;
			}
		}
		driver.quit();
		return new Location(Double.parseDouble(currLong), Double.parseDouble(currLat));

	}

	public void displayRoute(ArrayList<IDTO> Route) {
		String checkPoints = "";
		for (IDTO temp : Route) {
			RouteCheckpoint c = (RouteCheckpoint) temp;
			checkPoints = checkPoints + Double.toString(c.getLocation().getLongitude()) + ","
					+ Double.toString(c.getLocation().getLatitude()) + ";";
		}
		checkPoints = checkPoints.substring(0, checkPoints.length() - 1);
		System.setProperty("webdriver.chrome.driver", "src/Drivers/chromedriver.exe");
		String HTMLPath = new File("src\\businessLogicLayer\\LocationManager\\DisplayRoute.html").getAbsolutePath();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=1920,1080", "--app=" + HTMLPath);
		WebDriver driver = new ChromeDriver(options);
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
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
	}

	public void trackOrderDriver(User driverCar, Order order, Warehouse warehouse) {

		String driverLong = Double.toString(driverCar.getLocation().getLongitude());
		String driverLat = Double.toString(driverCar.getLocation().getLatitude());
		String destinationLong = null;
		String destinationLat = null;
		if (order.getStatus() == OrderStatus.PENDING) {
			destinationLong = Double.toString(warehouse.getLongitude());
			destinationLat = Double.toString(warehouse.getLatitude());
		} else if (order.getStatus() == OrderStatus.DELIVERING) {
			destinationLong = Double.toString(order.getLocationDestination().getLongitude());
			destinationLat = Double.toString(order.getLocationDestination().getLatitude());
		}
		System.setProperty("webdriver.chrome.driver", "src/Drivers/chromedriver.exe");
		String HTMLPath = new File("src/businessLogicLayer/DriverTracking.html").getAbsolutePath();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=1920,1080", "--app=" + HTMLPath);
		WebDriver driver = new ChromeDriver(options);
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
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
	}

	public Location updateDriverLocation(User driverCar, Order order, Warehouse warehouse) {
		String driverLong = Double.toString(driverCar.getLocation().getLongitude());
		String driverLat = Double.toString(driverCar.getLocation().getLatitude());
		String destinationLong = null;
		String destinationLat = null;
		if (order.getStatus() == OrderStatus.PENDING) {
			destinationLong = Double.toString(warehouse.getLongitude());
			destinationLat = Double.toString(warehouse.getLatitude());

		} else if (order.getStatus() == OrderStatus.DELIVERING) {
			destinationLong = Double.toString(order.getLocationDestination().getLongitude());
			destinationLat = Double.toString(order.getLocationDestination().getLatitude());
		}
		System.setProperty("webdriver.chrome.driver", "src/Drivers/chromedriver.exe");
		String HTMLPath = new File("src/businessLogicLayer/DriverLocationUpdater.html").getAbsolutePath();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=1920,1080", "--app=" + HTMLPath);
		WebDriver driver = new ChromeDriver(options);
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
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
		return new Location(Double.parseDouble(driverLong), Double.parseDouble(driverLat));
	}

	public Double calculateDistance(Location userLocation, Location warehouseLocation) {
		final double R = 6372.8;
		double dLat = Math.toRadians(userLocation.getLatitude() - warehouseLocation.getLatitude());
		double dLon = Math.toRadians(userLocation.getLongitude() - warehouseLocation.getLongitude());
		Double warehouseLat = Math.toRadians(warehouseLocation.getLatitude());
		Double userLat = Math.toRadians(userLocation.getLatitude());
		double a = Math.pow(Math.sin(dLat / 2), 2)
				+ Math.pow(Math.sin(dLon / 2), 2) * Math.cos(warehouseLat) * Math.cos(userLat);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}
	
	public Warehouse getNearestWarehouse(User user) {
		Warehouse nearestWarehouse = null;
		Double nearestDistance= null;
		for (IDTO temp : repoWarehouse.getAllActive()) {
			Warehouse warehouse = (Warehouse) temp;
			Double distance = calculateDistance(user.getLocation(),warehouse.getLocation());
			if(nearestWarehouse == null) {
				nearestWarehouse = warehouse;
				nearestDistance = distance;
			}
			if (distance < nearestDistance) {
				nearestWarehouse = warehouse;
				nearestDistance = distance;
			}
		}
		return nearestWarehouse;
	}
}
