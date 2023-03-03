import com.github.javafaker.Faker;
import com.github.javafaker.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertTrue;

@Test

public class AutProject2 {

public AutProject2() throws InterruptedException {

    //1. Launch Chrome browser.
    WebDriver driver = new ChromeDriver();

    //2. Navigate to http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx

    driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
    driver.manage().window().maximize();

    //3. Login using username Tester and password test

    driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
    driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");
    driver.findElement(By.id("ctl00_MainContent_login_button")).click();

//        WebElement userName =  driver.findElement(By.xpath("//input[@name = 'ctl00$MainContent$username']"));
//        userName.sendKeys("Tester");
//
//        WebElement passWord =  driver.findElement(By.xpath("//input[@name = 'ctl00$MainContent$password']"));
//        passWord.sendKeys("test");
//
//        WebElement login =  driver.findElement(By.xpath("//input[@name = 'ctl00$MainContent$login_button']"));
//        login.click();

    //4. Click on Order link

    driver.findElement(By.linkText("Order")).click();

    //driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).clear();

    //5. Enter a random product quantity between 1 and 100

    int quantity = new Random().nextInt(100) + 1;
    driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys(String.valueOf(quantity));


    //6. Click on Calculate and verify that the Total value is correct.

    driver.findElement(By.cssSelector("input[value='Calculate']")).click();
    double expectedTotal;
    if (quantity >= 10) {
        expectedTotal = quantity * 100 * 0.92; // 8% discount
    } else {
        expectedTotal = quantity * 100;
    }
    int actualTotal = Integer.parseInt(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtTotal")).getAttribute("value"));
    if (actualTotal == expectedTotal) {
        System.out.println("Total value is correct: " + actualTotal);
    } else {
        System.out.println("Total value is incorrect: " + actualTotal);
    }
    //  :  Using Faker class:
//    6. Enter random first name and last name.
//    7. Enter random street address
//    8. Enter random city
//    9. Enter random state
//    10. Enter a random 5 digit zip code

    Faker faker = new Faker();
    faker.address();
    Thread.sleep(2000);

    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String streetAddress = faker.address().streetAddress();
    String city = faker.address().city();
    String state = faker.address().state();
    String zipCode = faker.address().zipCode();

//  ADDRESS INFORMATION: Customernsme, Street, City, State, Zip.

    driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).sendKeys(firstName + " " + lastName);
    driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")).sendKeys(streetAddress);
    driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")).sendKeys(city);
    driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys(state);
    driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys(zipCode);

//11. Select the card type randomly. On each run your script should select a random type.
// (You need to put all checkboxes into a list and retrieve a random element from the list and click it)

WebElement cardTypeField = driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_0"));
        cardTypeField.click();
    //12. Enter the random card number:
    List<WebElement> cardTypeCheckboxes = driver.findElements(By.cssSelector("input[name='ctl00$MainContent$fmwOrder$cardList']"));
    cardTypeCheckboxes.get(new Random().nextInt(cardTypeCheckboxes.size())).click();

    String cardNumber = "";
    if (cardTypeField.getAttribute("value").equals("Visa")) {//Visa starts with 4
        cardNumber = "4" + faker.number().digits(15);
    } else if (cardTypeField.getAttribute("value").equals("MasterCard")) {//Master starts with 5
        cardNumber = "5" + faker.number().digits(15);
    } else if (cardTypeField.getAttribute("value").equals("American Express")) {//AMERICAN EXPRESS strats with 3
        cardNumber = "3" + faker.number().digits(14);
    }
    driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(cardNumber);
   // 13. Enter a valid expiration date (newer than the current date)
    //Choose random expiration date
    String expirationDate = "0" + (new Random().nextInt(9) + 1) + "/" + (new Random().nextInt(89) + 10);
    driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).sendKeys(expirationDate);
    Thread.sleep(1000);
    //14. Click on Process
    driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();// click
    Thread.sleep(1000);
    //15. Verify that “New order has been successfully added” message appeared on the page.
    WebElement successMessage = driver.findElement(By.xpath("//div[@class='buttons_process']/strong"));
    assertTrue(successMessage.isDisplayed());
    Thread.sleep(1000);

    //16. Click on View All Orders link.
    driver.findElement(By.linkText("http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/Default.aspx")).click();
    Thread.sleep(1000);
    


}
}
