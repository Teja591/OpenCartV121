package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

    @Test(groups = {"sanity","Master"})
    public void verify_login()
    {
        logger.info("********Starting Tc002_LoginTest*****");
        try {
            //validating home page
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            homePage.clickLogin();

            //validating login page
            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(prop.getProperty("email"));
            loginPage.setPassword(prop.getProperty("password"));
            loginPage.clickBtnLogin();

            //validating myaccount page after login
            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean targetPage = myAccountPage.isMyAccountPageExists();
            //Assert.assertEquals(targetPage, true, "Login failed");
            Assert.assertTrue(targetPage);
        }
        catch (Exception e) {
            Assert.fail();
        }
        logger.info("********Ending Tc002_LoginTest******");
    }

}
