package com.spring.security.tools.util;

import com.spring.security.annotation.ExcelColumn;
import com.spring.security.exception.AMException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * excel导入工具类
 *
 * @author zhuweirong
 * @date 2021\1\22 0022 10:05
 */
public class ExcelColumnUtils {

    public static Map<String, ExcelColumn> getFieldExcelColumn(Class clazz) throws Exception {
        Map<String, ExcelColumn> fieldMap = new LinkedHashMap<>();
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fields = superClass.getDeclaredFields();
            for (Field f : fields) {
                ExcelColumn excelColumn = f.getAnnotation(ExcelColumn.class);
                if (excelColumn != null) {
                    fieldMap.put(f.getName(), excelColumn);
                }
            }
        }
        return fieldMap;
    }

    /**
     * 解析机票excel数据
     *
     * @param file
     * @param clazz        参照AirTicketPaperData
     * @param beginDataRow 数据开始的行序号，从0开始
     * @return
     * @throws Exception
     */
    public static <T> List<T> parseExcel2Data(File file, Class<T> clazz, int beginDataRow) throws Exception {
        List<List<ListOrderedMap>> lists = ExcelUtil.parseExcel(file);
        return parseExcel2Data(lists, clazz, beginDataRow);
    }

    /**
     * 解析机票excel数据
     *
     * @param multipartFile
     * @param clazz         参照AirTicketPaperData
     * @param beginDataRow  数据开始的行序号，从0开始
     * @return
     * @throws Exception
     */
    public static <T> List<T> parseExcel2Data(MultipartFile multipartFile, Class<T> clazz, int beginDataRow) throws Exception {
        InputStream inputStream = multipartFile.getInputStream();
        String originalFilename = multipartFile.getOriginalFilename();
        List<List<ListOrderedMap>> lists = ExcelUtil.parseExcel(inputStream, originalFilename);
        return parseExcel2Data(lists, clazz, beginDataRow);
    }

    private static <T> List<T> parseExcel2Data(List<List<ListOrderedMap>> lists, Class<T> clazz, int beginDataRow) throws Exception {
        List<T> list = new ArrayList<>();
        List<ListOrderedMap> sheet1List = lists.get(0);
        Map<String, ExcelColumn> fieldExcelColumnMap = getFieldExcelColumn(clazz);
        for (int i = 0; i < sheet1List.size(); i++) {
            if (i < beginDataRow)
                continue;
            ListOrderedMap map = sheet1List.get(i);
            T dto = clazz.newInstance();
            for (Map.Entry<String, ExcelColumn> entry : fieldExcelColumnMap.entrySet()) {
                String field = entry.getKey();
                ExcelColumn excelColumn = entry.getValue();
                boolean required = excelColumn.required();
                int cellNum = excelColumn.index();
                if(map.size() <= cellNum){
                    if(required){
                        throw new AMException(String.format("第【%s】行第【%s】列，数据为必填项!", i+1, cellNum+1));
                    }
                    continue;
                }
                Object value = map.getValue(cellNum);
                if(required){
                    if(null == value || StringUtils.isBlank(value.toString()))
                        throw new AMException(String.format("第【%s】行第【%s】列，数据为必填项!", i+1, cellNum+1));
                }
                if(null != value  && StringUtils.isNotBlank(value.toString())){
                    Object convertValue = null;
                    try {
                        convertValue = ConvertUtils.convert(value, excelColumn.fieldType());
                    } catch (Exception e) {
                        throw new AMException(String.format("第【%s】行第【%s】列，数据格式填写不对，值为【%s】!", i+1, cellNum+1, value));
                    }
                    // 可以在此加入转换数据类型逻辑
                    //Object convertValue = ConvertUtils.convert(value, excelColumn.fieldType());
                    PropertyUtils.setProperty(dto, field, convertValue);
                }
            }
            list.add(dto);
        }
        //加入数据重复检验
        List<String> errorsList = validateSameRowData(list, fieldExcelColumnMap, beginDataRow);
        if(errorsList.size() >0){
            throw new AMException(String.format(StringUtils.join(errorsList, ";")));
        }
        return list;
    }

    private static <T> List<String> validateSameRowData(List<T> list, Map<String, ExcelColumn> fieldExcelColumnMap, int beginDataRow) throws Exception {
        List<String> errorsList = new ArrayList<>();
        List<String> dataUniqueKeyList = fieldExcelColumnMap.entrySet().stream().filter(p -> p.getValue().dataUniqueKey()).map(p -> p.getKey()).collect(Collectors.toList());
        if(!dataUniqueKeyList.isEmpty()) {
            Map<String, List<Integer>> sameMap = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                T t = list.get(i);
                StringBuilder sb = new StringBuilder();
                for (String property : dataUniqueKeyList) {
                    sb.append(PropertyUtils.getProperty(t, property) + ",");
                }
                String uniqueKey = sb.toString();
                if (!sameMap.containsKey(uniqueKey))
                    sameMap.put(uniqueKey, new ArrayList<>());
                sameMap.get(uniqueKey).add(i);
            }
            sameMap.entrySet().stream().filter(p -> p.getValue().size() > 1).forEach(p -> {
                List<Integer> rowsList = p.getValue().stream().map(i -> i + beginDataRow + 1).collect(Collectors.toList());
                errorsList.add(String.format("第【%s】列数据重复", StringUtils.join(rowsList, "、")));
            });
        }
        return errorsList;
    }

}
