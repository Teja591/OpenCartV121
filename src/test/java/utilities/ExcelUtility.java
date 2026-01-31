package utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {
    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wb;
    public static XSSFSheet ws;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static XSSFCellStyle style;
    static String path;

    public ExcelUtility(String path) {
        this.path=path;
    }


    public static int getRowCount(String sheetName)throws IOException {
        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(sheetName);
        int rowCount=ws.getLastRowNum();
        wb.close();
        fi.close();
        return rowCount;
    }
    //file contains a workbook internally
    //so we cannot extract the workbook from file.

    public static int getCellCount(String sheetName,int rownum)throws IOException {
        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(sheetName);
        row=ws.getRow(rownum);
        int cellCount=row.getLastCellNum();
        wb.close();
        fi.close();
        return cellCount;
    }
    public static String getCellData(String sheetName,int rownum,int colnum)throws IOException {
        //this will read data in only one single cell
        //so we use this method in loop
        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(sheetName);
        row=ws.getRow(rownum);
        cell=row.getCell(colnum);
        String data;
        try
        {
            DataFormatter formatter = new DataFormatter();
            data=formatter.formatCellValue(cell);
        }
        catch(Exception e)
        {
            data="";
        }
        wb.close();
        fi.close();
        return data;
    }

    public static void setCellData(String sheetName,int rownum,int colnum,String data)throws IOException{
       File xlfile=new File(path);
       if(!xlfile.exists())
       {
           wb=new XSSFWorkbook();
           fo=new FileOutputStream(path);
           wb.write(fo);
       }
       fi=new FileInputStream(path);
       wb=new XSSFWorkbook(fi);

       if(wb.getSheetIndex(sheetName)==-1)
           wb.createSheet(sheetName);
       ws=wb.getSheet(sheetName);

       if(ws.getRow(rownum)==null)
           ws.createRow(rownum);
       row=ws.getRow(rownum);

       cell=row.getCell(colnum);
       cell.setCellValue(data);
       fo=new FileOutputStream(path);
       wb.write(fo);
       wb.close();
       fi.close();
       fo.close();
    }

    public static void fillGreenColor(String sheetName,int rownum,int colnum)throws IOException{
        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(sheetName);
        row=ws.getRow(rownum);
        cell=row.getCell(colnum);

        style=wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();

    }

    public static void fillRedColor(String sheetName,int rownum,int colnum)throws IOException{
        fi=new FileInputStream(path);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(sheetName);
        row=ws.getRow(rownum);
        cell=row.getCell(colnum);

        style=wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();

    }

}














