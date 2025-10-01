package com.spring.security;

import com.opencsv.bean.CsvToBean;

/**
 * @program: payne
 * @description:
 * @author: wxy
 * @create: 2021-08-26 21:12
 * @Version 1.0
 **/
public class CsvTest {

    public static void main(String[] args) {
        CsvToBean bean = new CsvToBean();
//        bean.setFilter();
        bean.parse();
    }

}