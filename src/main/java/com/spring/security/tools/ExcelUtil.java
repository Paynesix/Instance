package com.spring.security.tools;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

public class ExcelUtil {

    public static HSSFWorkbook getHSSFWorkbook(String sheetName, List<String> title, String[][] values, HSSFWorkbook wb) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet，对应Excel文件中的sheet
        HSSFSheet sheet = null;
        if((sheet = wb.getSheet(sheetName)) == null){
            sheet = wb.createSheet(sheetName);
        }

        // 第三步，在sheet中添加表头第0行，注意老版本poi对Excel行数列数有限制
        int rowSize = sheet.getPhysicalNumberOfRows();
        HSSFRow row = sheet.createRow(rowSize);

        // 第四步，创建单元格，并设置表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        // 声明列对象
        HSSFCell cell = null;

        // 创建标题
        for (int i = 0; title != null && i < title.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, 15*256);
        }
        rowSize = sheet.getPhysicalNumberOfRows();
        // 创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + rowSize);
            for (int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    public static HSSFWorkbook getHSSFWorkbookPlus(String sheetName, List<String> title, String[][] values, HSSFWorkbook wb) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet，对应Excel文件中的sheet
        HSSFSheet sheet = null;
        if(wb.getSheet(sheetName) == null){
            sheet = wb.createSheet(sheetName);
        }

        // 第三步，在sheet中添加表头第0行，注意老版本poi对Excel行数列数有限制
        int rowSize = sheet.getPhysicalNumberOfRows();
        HSSFRow row = sheet.createRow(rowSize);

        // 第四步，创建单元格，并设置表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        return wb;
    }

    public static SXSSFWorkbook getSXSSFWorkbook(String sheetName, List<String> title, String[][] values, SXSSFWorkbook wb) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new SXSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet，对应Excel文件中的sheet
        SXSSFSheet sheet = null;
        if((sheet = wb.getSheet(sheetName)) == null){
            sheet = wb.createSheet(sheetName);
        }


        // 第三步，在sheet中添加表头第0行，注意老版本poi对Excel行数列数有限制
        int rowSize = sheet.getPhysicalNumberOfRows();
        SXSSFRow row = sheet.createRow(rowSize);

        // 第四步，创建单元格，并设置表头 设置表头居中
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        // 声明列对象
        SXSSFCell cell = null;

        // 创建标题
        for (int i = 0; title != null && i < title.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, 15*256);
        }

        rowSize = sheet.getPhysicalNumberOfRows();
        // 创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + rowSize);
            for (int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    public static SXSSFWorkbook getSXSSFWorkbookPlus(String sheetName, List<String> title, String[][] values, SXSSFWorkbook wb) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new SXSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet，对应Excel文件中的sheet
        SXSSFSheet sheet = null;
        if(wb.getSheet(sheetName) == null){
            sheet = wb.createSheet(sheetName);
        }


        // 第三步，在sheet中添加表头第0行，注意老版本poi对Excel行数列数有限制
        int rowSize = sheet.getPhysicalNumberOfRows();
        SXSSFRow row = sheet.createRow(rowSize);

        // 第四步，创建单元格，并设置表头 设置表头居中
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        // 声明列对象
        SXSSFCell cell = null;

        // 创建标题
        for (int i = 0; i < title.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, 15*256);
        }

        rowSize = sheet.getPhysicalNumberOfRows();
        // 创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + rowSize);
            for (int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }
}
