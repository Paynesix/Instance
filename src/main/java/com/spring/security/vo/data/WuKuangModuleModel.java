package com.spring.security.vo.data;


import com.spring.security.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;


/**
 * 票务扫描机票excel中原始数据
 *
 * @auther: zhuweirong
 * @date: 2021\1\21 0021 19:25
 */
@Data
public class WuKuangModuleModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ExcelColumn(index = 0)
    private String skuid;//SKU (ID)
    
    @ExcelColumn(index = 1)
    private String des;// 中文描述

    @ExcelColumn(index = 2)
    private String no;//备货编号
    
    @ExcelColumn(index = 3)
    private String count;//备货
}
