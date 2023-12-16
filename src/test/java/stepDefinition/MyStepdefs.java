package stepDefinition;

import common.Basketball;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class MyStepdefs {
    private WebDriver driver;
    private Basketball basketball;




    @Given("I am using {} as browser")
    public void iAmUsingAsBrowser(String browser) {
        switch (browser) {
            case "Edge":
                driver = new EdgeDriver();
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                break;
            case "Chrome":
                driver = new ChromeDriver();
                break;
            default:
                System.out.println("No browser found, running default Chrome");
                driver = new ChromeDriver();
        }
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
        basketball = new Basketball();

    }


    @Given("I have a valid {} of birth")
    public void iHaveAValidOfBirth(String date) {

        WebElement field = getWebElement("[type='datetime']");
        if (date.equals("rand")){

            field.sendKeys(basketball.randomNumber(20, 1) + "/" + basketball.randomNumber(12, 1) + "/" + basketball.randomNumber(50, 1950));

        } else if (date.equals("")) {
            System.out.println("Skipping test date");

        }else {

            field.sendKeys(date);

        }
    }

    @And("I have a valid {} firstname")
    public void iHaveAValidFirstname(String firstname) {
        //faker = new Faker();
        WebElement field = driver.findElement(By.name("Forename"));
        if (firstname.equals("rand")){
            field.sendKeys(basketball.randomFirstname());
        } else if (firstname.equals("")) {
            System.out.println("Skipping test firstname");
        }else {
            field.sendKeys(firstname);
        }
    }

    @And("I have a valid {} lastname")
    public void iHaveAValidLastname(String lastname) {
        WebElement field = driver.findElement(By.name("Surname"));

        if (lastname.equals("rand")){
            field.sendKeys(basketball.randomLastname());
        } else if (lastname.equals("")) {
            System.out.println("Skipping input lastname");
        } else {
            field.sendKeys(lastname);
        }
    }

    @And("I have a valid {} email {}")
    public void iHaveAValidEmail(String email, String both) {
        WebElement field = driver.findElement(By.name("EmailAddress"));
        String newEmail = "";

        if (email.equals("rand")){
            newEmail = basketball.randomEmail();
            field.sendKeys(newEmail);
        } else if (email.equals("")) {
            System.out.println("Skipping test email");
        }else {
            newEmail = email;
            field.sendKeys(newEmail);
        }

        if (basketball.isValid(both)) {
            field = driver.findElement(By.name("ConfirmEmailAddress"));
            field.sendKeys(newEmail);
        }
    }

    @And("I have a valid {} password {}")
    public void iHaveAValidPassword(String password, String confirm) {
        WebElement field = driver.findElement(By.name("Password"));
        if (basketball.isValid(password)) {
            String newPass = basketball.randomPassword();

            field.sendKeys(newPass);
            field = driver.findElement(By.name("ConfirmPassword"));
            if (basketball.isValid(confirm)) {
                field.sendKeys(newPass);
            } else if (confirm.equals("rand")) {
                field.sendKeys(basketball.randomPassword());
            }
        } else {
            field.sendKeys(password);
            field = driver.findElement(By.name("ConfirmPassword"));
            field.sendKeys(confirm);
        }
    }

    @And("I have {} the account confirmation")
    public void iHaveTheAccountConfirmation(String checked) {
        if (basketball.isValid(checked)) {
            WebElement fieldAcc = getWebElement("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div:nth-child(1) > label > span.box");
            fieldAcc.click();
            driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div.md-checkbox.margin-top-10 > label > span.box")).click();
        }
    }

    @And("I have {} the code of conduct")
    public void iHaveTheCodeOfConduct(String checked) {
        if (basketball.isValid(checked)) {
            driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(7) > label > span.box")).click();
        }
    }

    @When("I {} the confirm button")
    public void iTheConfirmButton(String click) {
        if (basketball.isValid(click)) {
            driver.findElement(By.name("join")).click();
        }
    }

    @Then("New account is created {}")
    public void newAccountIsCreated(String result) {
        System.out.println("Get the result of " + result);
        switch (result) {
            case "created":
                WebElement account = getWebElement("body > div > div.page-content-wrapper > div > div > h2");
                String accountnr = account.getText();
                boolean checkFirstLetter = Character.isLetter(accountnr.charAt(0));
                boolean checklength = accountnr.length() == 7;
                Assert.assertEquals(true, checkFirstLetter && checklength);
                break;
            case "date":
                //System.out.println("date");
                WebElement date = driver.findElement(By.cssSelector("body > div.datepicker.datepicker-dropdown.dropdown-menu.datepicker-orient-left.datepicker-orient-bottom"));
                Assert.assertEquals(true, date.isDisplayed());
                break;
            case "firstname":
                //System.out.println("firstname");
                WebElement firstname = driver.findElement(By.cssSelector("#signup_form > div:nth-child(6) > div:nth-child(1) > div > span"));
                Assert.assertEquals(true, firstname.getText().equals("First Name is required"));
                break;
            case "lastname":
                //System.out.println("lastname");
                WebElement lastname = driver.findElement(By.cssSelector("#signup_form > div:nth-child(6) > div:nth-child(2) > div > span"));
                Assert.assertEquals(true, lastname.getText().equals("Last Name is required"));
                break;
            case "email":
                //System.out.println("email");
                WebElement email = driver.findElement(By.cssSelector("#signup_form > div:nth-child(7) > div:nth-child(1) > div > span"));
                WebElement emailconf = driver.findElement(By.cssSelector("#signup_form > div:nth-child(7) > div:nth-child(2) > div > span"));
                Assert.assertEquals(true, email.getText().equals("Email Address is required") || emailconf.getText().equals("Confirm Email Address does not match"));
                break;
            case "password":
                //System.out.println("password");
                WebElement password = driver.findElement(By.cssSelector("#signup_form > div:nth-child(9) > div > div.row > div:nth-child(1) > div > span"));
                //System.out.println("ConfText: " + password.getText());
                Assert.assertEquals(true, password.getText().equals("Password is required") );
                break;
            case "passwordConf":
                //System.out.println("password");
                WebElement passwordconf = driver.findElement(By.cssSelector("#signup_form > div:nth-child(9) > div > div.row > div:nth-child(2) > div > span > span"));
                //System.out.println("ConfText: " + passwordconf.getText());
                Assert.assertEquals(true, passwordconf.getText().equals("Confirm Password is required") ||  passwordconf.getText().equals("Password did not match"));
                break;
            case "terms":
                //System.out.println("terms");
                WebElement termsAndCon = driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div:nth-child(1) > span > span"));
                //System.out.println("TermsText: " + termsAndCon.getText());
                Assert.assertEquals(true, termsAndCon.getText().equals("You must confirm that you have read and accepted our Terms and Conditions") );
                break;
            case "ethics":
                //System.out.println("ethics");
                WebElement ethics = driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(7) > span > span"));
                //System.out.println("EthicsText: " + ethics.getText());
                Assert.assertEquals(true, ethics.getText().equals("You must confirm that you have read, understood and agree to the Code of Ethics and Conduct") );
                break;
            default:
                System.out.println("No result could be tested");
        }

    }
    @After
    public void afterScenario() {
        driver.close();
    }

    private WebElement getWebElement(String css) {
        return (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
    }


}
