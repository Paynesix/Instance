package com.spring.security.tools;

import com.spring.security.tools.util.DateTimeUtils;
import com.spring.security.tools.util.ExcelColumnUtils;
import com.spring.security.vo.data.AirBillModel;
import com.spring.security.vo.data.WuKuangModel;
import com.spring.security.vo.data.WuKuangModuleModel;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuweirong
 * @date 2019\6\5 0005 18:07
 */
public class ExcelParseTest {
    
    @Test
    public void testParseAir() throws Exception {
        // 1\统计每个sku售卖的数量 妙手后台下载的sku数据
        Map<String, Integer> excelSkuCount = getExcelSkuCount("src/test/excel/" + "妙手导出订单记录.xlsx");
        // 2\获取模板表格 之前统计的数据
        Map<String, WuKuangModuleModel> excelModule = getExcelModule( "src/test/excel/" + "泰国-无框-备货底板.xlsx");
        // 3\输出备货表格 输出到之前统计的数据哪里
        List<String[]> outList =  getExcelList(excelSkuCount, excelModule);
        exportToExcel(outList, "src/test/excel/" + "泰国-无框-备货"+ DateTimeUtils.getCurrentDateTime() +".xlsx");
    }
    
    private List<String[]> getExcelList(Map<String, Integer> excelSkuCount,
            Map<String, WuKuangModuleModel> excelModule) {
        List<WuKuangModuleModel> resetList = new ArrayList<>();
        for (Map.Entry<String, WuKuangModuleModel> module : excelModule.entrySet()) {
            //1\获取模板skuid
            WuKuangModuleModel resModule = new WuKuangModuleModel();
            BeanUtils.copyProperties(module.getValue(), resModule);
            //2\设置模板skuid备货量
            resModule.setCount(String.valueOf(excelSkuCount.get(module.getKey())));
            //3\存入list
            resetList.add(resModule);
        }
        //4、输出excel
        resetList.sort(Comparator.comparing(WuKuangModuleModel::getNo));
        List<String[]> outList = new ArrayList<>();
        for(WuKuangModuleModel model : resetList) {
            outList.add(convertObjectPropertiesToStringArray(model));
        }
        outList.add(0, new String[]{"SKUID","规格名称","备货号","数量"});
        return outList;
    }
    
    /**
     * 将对象的所有属性值转换为String数组
     * @param obj 要转换的对象
     * @return 包含对象所有属性值的String数组
     */
    public static String[] convertObjectPropertiesToStringArray(Object obj) {
        if (obj == null) {
            return new String[0];
        }
        
        // 获取对象的所有属性（包括私有属性）
        Field[] fields = obj.getClass().getDeclaredFields();
        List<String> propertiesList = new ArrayList<>();
        
        for (Field field : fields) {
            // 设置可访问私有属性
            field.setAccessible(true);
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            try {
                // 获取属性值并转换为String
                Object value = field.get(obj);
                propertiesList.add((value == null || String.valueOf(value).equals("null"))
                        ? "0" : String.valueOf(value));
            } catch (IllegalAccessException e) {
                // 处理访问异常，添加占位符
                propertiesList.add("无法访问属性: " + field.getName());
                e.printStackTrace();
            }
        }
        
        // 转换为数组并返回
        return propertiesList.toArray(new String[0]);
    }
    /**
     * 获取模板表格
     **/
    public Map<String, WuKuangModuleModel> getExcelModule(String filePath) throws Exception {
        List<WuKuangModuleModel> list = ExcelColumnUtils
                .parseExcel2Data(new File(filePath), WuKuangModuleModel.class, 1);
        System.err.println(list.size());
        Map<String, WuKuangModuleModel> map = new HashMap<>();
        for (WuKuangModuleModel model : list) {
            map.put(model.getSkuid(), model);
        }
        return map;
    }
    
    /**
     * 统计每个sku售卖的数量
     **/
    public Map<String, Integer> getExcelSkuCount(String filePath) throws Exception {
        List<WuKuangModel> list = ExcelColumnUtils.parseExcel2Data(new File(filePath), WuKuangModel.class, 1);
        System.err.println(list.size());
        Map<String, Integer> map = new HashMap<>();
        for (WuKuangModel model : list) {
            Integer integer = map.get(model.getSkuid());
            if (integer == null) {
                map.put(model.getSkuid(), 1);
            } else {
                map.put(model.getSkuid(), integer + 1);
            }
        }
        return map;
    }
    /**
     * 导出数据到Excel文件
     * @param data 要导出的数据
     * @param filePath 输出文件路径
     */
    public static void exportToExcel(List<String[]> data, String filePath) {
        // 创建工作簿 (XSSFWorkbook用于.xlsx格式，HSSFWorkbook用于.xls格式)
        try (Workbook workbook = new XSSFWorkbook()) {
            // 创建工作表
            Sheet sheet = workbook.createSheet("数据表格");
            
            // 创建标题样式
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            // 填充数据
            int rowNum = 0;
            for (String[] rowData : data) {
                Row row = sheet.createRow(rowNum++);
                
                int colNum = 0;
                for (String cellData : rowData) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(cellData);
                    
                    // 为标题行应用样式
                    if (rowNum == 1) {
                        cell.setCellStyle(headerStyle);
                    }
                }
            }
            
            // 自动调整列宽
            for (int i = 0; i < data.get(0).length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // 输出到文件
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                System.out.println("Excel文件已成功导出至: " + filePath);
            }
            
        } catch (IOException e) {
            System.err.println("导出Excel失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
