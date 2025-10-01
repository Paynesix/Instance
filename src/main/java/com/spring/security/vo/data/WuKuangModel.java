package com.spring.security.vo.data;


import com.spring.security.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 票务扫描机票excel中原始数据
 *
 * @auther: zhuweirong
 * @date: 2021\1\21 0021 19:25
 */
@Data
public class WuKuangModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelColumn(index = 0)
    private String type;// 产品规格

    @ExcelColumn(index = 1)
    private String pid;//产品ID

    @ExcelColumn(index = 2)
    private String skuid;//SKUID

    @ExcelColumn(index = 3, required = true)
    private String type2;//产品规格
}
