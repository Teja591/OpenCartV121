package testCases;

//Data is valid - login success-test passed-logout
                // -login failed -test fail

//Data is invalid --login success --test fail -logout
                // --login failed -test passed


import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDataDrivenTest extends BaseClass {

    @Test(dataProvider = "LoginData",dataProviderClass = DataProviders.class,groups="DataDriven")//getting dataprovider from different class and package
    public void verify_loginDDT(String email,String password,String exp)
    {
        logger.info("********Starting TC002_LoginDDTest*****");
        try {
            //validating home page
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            homePage.clickLogin();

            //validating login page
            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(email);
            loginPage.setPassword(password);
            loginPage.clickBtnLogin();

            //validating myaccount page after login
            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean targetPage = myAccountPage.isMyAccountPageExists();
            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage == true) {
                    myAccountPage.clickLogout();
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue(false);
                }
            }
            if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage == true) {
                    myAccountPage.clickLogout();
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);
                }
            }
        }
        catch (Exception e) {
            Assert.fail();
        }
        logger.info("********Ending TC002_LoginDDTest*****");

    }
}
