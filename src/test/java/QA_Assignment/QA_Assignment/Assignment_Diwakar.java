package QA_Assignment.QA_Assignment;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import junit.framework.Assert;

public class Assignment_Diwakar {
	
	WebDriver driver;
	String total;
	String email="fbcderbhhja@xvgsvgaV.com";
	String pass="123456";
	
	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver","./chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get("http://automationpractice.com/index.php");
		driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
	}
	
	@Test(priority=1)
	public void create_account() 
	{
		driver.findElement(By.xpath("//a[contains(text(),'Sign')]")).click();
		
		driver.findElement(By.xpath("//input[@id='email_create']")).sendKeys(email);
		driver.findElement(By.xpath("//button[@id='SubmitCreate']")).click();
		
		driver.findElement(By.xpath("//*[@id='id_gender1']")).click();
		
		driver.findElement(By.xpath("//input[@id='customer_firstname']")).sendKeys("abc");
		driver.findElement(By.xpath("//input[@id='customer_lastname']")).sendKeys("def");
		driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys("123456");
		
		Select sel_date=new Select(driver.findElement(By.xpath("//*[@id='days']")));
		sel_date.selectByValue("2");
		
		Select sel_month=new Select(driver.findElement(By.xpath("//*[@id='months']")));
		sel_month.selectByValue("4");
		
		Select sel_year=new Select(driver.findElement(By.xpath("//*[@id='years']")));
		sel_year.selectByValue("1993");
		
		driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("123 street");
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Pune");
		
		Select sel_state=new Select(driver.findElement(By.xpath("//*[@id='id_state']")));
		sel_state.selectByVisibleText("Alaska");
		
		
		driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys("21100");
		
		Select sel_country=new Select(driver.findElement(By.xpath("//*[@id='id_country']")));
		sel_country.selectByVisibleText("United States");
		
		
		driver.findElement(By.xpath("//input[@id='phone_mobile']")).sendKeys("7412589630");
		
		driver.findElement(By.xpath("//*[@id='submitAccount']")).click();
		
		String msg="Welcome to your account. Here you can manage all of your personal information and orders.";
		
		String actual_msg=driver.findElement(By.xpath("//p[@class='info-account']")).getText();
		
		Assert.assertEquals(msg, actual_msg);
	
	}
	
	@Test(priority=2)
	public void login() 
	{
		driver.findElement(By.xpath("//a[contains(text(),'Sign out')]")).click();
		
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("passwd")).sendKeys(pass);
		driver.findElement(By.id("SubmitLogin")).click();
		
		String title=driver.getTitle();
		
		Assert.assertEquals("My account - My Store",title);
	}
	
	@Test(priority=3)
	public void adding_product() 
	{
		driver.findElement(By.xpath("//a[contains(text(),'Women')]")).click();
		
		Actions acc=new Actions(driver);
		
		acc.moveToElement(driver.findElement(By.xpath("//img[@src='http://automationpractice.com/img/p/1/1-home_default.jpg']"))).perform();
		
		Point point=driver.findElement(By.xpath("(//img[@class='replace-2x img-responsive'])[2]")).getLocation();
		driver.findElement(By.xpath("//img[@src='http://automationpractice.com/img/p/1/1-home_default.jpg']/following::span[1]")).click();
		
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='fancybox-iframe']")));
		WebDriverWait wait=new WebDriverWait(driver,10);
		
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//body[@id='product']"))));
		driver.findElement(By.xpath("//a[@class='btn btn-default button-plus product_quantity_up']")).click();
		
		driver.findElement(By.xpath("//button[@class='exclusive']")).click();	
	}
	
	@Test(priority=4)
	public void checkout() throws InterruptedException 
	{
		driver.switchTo().defaultContent();
		
		Thread.sleep(3000);
		total=driver.findElement(By.xpath("//span[@class='ajax_block_cart_total']")).getText();
		
		driver.findElement(By.xpath("//*[@class='btn btn-default button button-medium']")).click();
		
		String total_payment=driver.findElement(By.xpath("//*[@id='total_price']")).getText();
		
		SoftAssert softassert=new SoftAssert();
		softassert.assertEquals(total,total_payment);
		
		driver.findElement(By.xpath("//*[@class='button btn btn-default standard-checkout button-medium']")).click();
		
		driver.findElement(By.xpath("//*[@class='button btn btn-default button-medium']")).click();
		
		driver.findElement(By.xpath("//*[@id='cgv']")).click();
		
		driver.findElement(By.xpath("//*[@class='button btn btn-default standard-checkout button-medium']")).click();

		driver.findElement(By.xpath("//a[@title='Pay by bank wire']")).click();   //Pay by check.
		
		String total_final=driver.findElement(By.xpath("//span[@id='amount']")).getText();
		
		softassert.assertEquals(total, total_final);
		
		driver.findElement(By.xpath("//*[@class='button btn btn-default button-medium']")).click();
		
		String total_confirm=driver.findElement(By.xpath("//span[@class='price']")).getText();
		
		softassert.assertEquals(total,total_confirm);	
		
		softassert.assertAll();
	}
	
	@Test(priority=5)
	public void order() 
	{
		driver.findElement(By.xpath("//a[@class='account']")).click();
		driver.findElement(By.xpath("//a[@title='Orders']")).click();
		String total_order=driver.findElement(By.xpath("//span[@class='price']")).getText();
		Assert.assertEquals(total,total_order);
		
	}
}
