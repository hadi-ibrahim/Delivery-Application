package presentationLayer.LocationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import DTO.Location;

public class getLocation {

	public Location getUserLocation() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\david\\Desktop\\Projects\\Year 3\\Object Oriented Programming II\\DeliveryApp\\src\\Drivers\\chromedriver.exe");
		String getLocationPathHTML = "WC:\\Users\\david\\\\Desktop\\Projects\\\\Year 3\\Object Oriented Programming II\\DeliveryApp\\\\src\\presentationLayer\\LocationManager\\getLocation.html";
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=0,0");
		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(getLocationPathHTML);
		String currLong = "0";
		String currLat = "0";
		while (true) {
			WebElement flag = driver.findElement(By.id("flag"));
			System.out.println(flag.getAttribute("value"));
			if (flag.getAttribute("value").equalsIgnoreCase("true")) {
				WebElement accuracy = driver.findElement(By.id("accuracy"));
				if(Integer.parseInt(accuracy.getAttribute("value")) < 100) {
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
}
