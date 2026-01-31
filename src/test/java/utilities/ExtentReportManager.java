package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        String currentdatetimestamp = df.format(date);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        repName ="Test-Report-" +timeStamp+ ".html";//time stamp concatenated with report name
        sparkReporter=new ExtentSparkReporter(".\\reports\\" + repName);

        sparkReporter.config().setDocumentTitle("Automation report");//title of the report
        sparkReporter.config().setReportName("Functional Testing");//name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        //to connect ui to our report we write thisline
        extent.setSystemInfo("Application","opencart");
        extent.setSystemInfo("Module","Admin");
        extent.setSystemInfo("Sub Module","Customers");
        extent.setSystemInfo("User Name",System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os=testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser=testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        //these two statements will capture os and browser from xml file dynamically

        List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty()){
            extent.setSystemInfo("Groups",includedGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); //to display groups in report
        test.log(Status.PASS,result.getName()+"got successfully executed");
    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL,result.getName()+"got failed");
        test.log(Status.INFO,result.getThrowable().getMessage());

       /* try
        {
            String imgPath=new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (Exception e) {
                e.printStackTrace();
        }*/
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP,result.getName()+"got skipped");
        test.log(Status.INFO,result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context) {
        extent.flush();

        //after finishing i don't want to open my report manually
        //i want to open the report automatically so i write this code.

        String pathOfExtentReport =System.getProperty("user.dir")+"\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try{
            Desktop.getDesktop().browse(extentReport.toURI());
            //this method will open file in browser automatically
            //Desktop is predefined class.
        } catch (IOException e) {
                e.printStackTrace();
        }

        /*try {
            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);

            // Create the email message
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(
                    new DefaultAuthenticator("pavanoltraining@gmail.com", "password"));
            email.setSSLOnConnect(true);
            email.setFrom("pavanoltraining@gmail.com"); // Sender
            email.setSubject("Test Results");
            email.setMsg("Please find Attached Report...");
            email.addTo("pavankumar.busyag@gmail.com"); // Receiver
            email.attach(url, "extent report", "please check report...");
            email.send(); // send the email
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}
