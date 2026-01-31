package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {
    //DataProvider

    @DataProvider(name="LoginData")
    public Object[][] getData() throws IOException {
        String path=".\\testData\\Book1.xlsx";
        ExcelUtility xlutil=new ExcelUtility(path);

        int totalrows=xlutil.getRowCount("Sheet1");
        int totalcols=xlutil.getCellCount("Sheet1",1);

        String logindata[][]=new String[totalrows][totalcols];

        for(int i=1;i<=totalrows;i++){
            for(int j=0;j<totalcols;j++){
                logindata[i-1][j]=xlutil.getCellData("Sheet1",i,j);
            }
        }
        return logindata;
    }

    //DataProvider2

    //DataProvider3

    //DataProvider4
}
