package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class TC001_AccountRegistrationTest extends BaseClass {
    @Test(groups={"Regression","Master"})
    public void verify_account_registration() {
        logger.info("*******Starting TC001 AccountRegistrationTest******");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked on My Account link");

            homePage.clickRegister();
            logger.info("Clicked on Register link");

            logger.info("Providing user details");
            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
            regpage.setFirstName(randomString().toUpperCase());
            regpage.setLastName(randomString().toUpperCase());
            regpage.setEmail(randomString() + "@gmail.com");
            //randomly generated the email

            regpage.setTelephone(randomNumber());

            String password = randomAlphaNumeric();
            regpage.setPassword(password);
            regpage.setConfirmPassword(password);


            regpage.setPrivacyPolicy();
            regpage.clickContinue();

            logger.info("Validating expected message..");
            String confmsg = regpage.getMsgConfirmation();
            logger.info("Confirmation message received: " + confmsg);

            if (confmsg != null && confmsg.contains("Your Account Has Been Created")) {
                Assert.assertTrue(true);
            } else {
                logger.error("Account registration failed. Actual message: " + confmsg);
                Assert.fail("Expected success message not found. Actual message: " + confmsg);
            }
        }
        catch (Exception e) {
            Assert.fail();
        }
        logger.info("*******Ending TC001 AccountRegistrationTest*******");
    }
}




















