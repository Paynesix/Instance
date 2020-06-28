/** 
 * @(#)CamelRowMapper.java 1.0.0 2015年8月13日 上午7:58:21  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */ 

package com.spring.security.repository.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**   
 *  
 * @author  HuYaHui
 * @version $Revision:1.0.0, $Date: 2015年8月13日 上午7:58:21 $ 
 */
public class CamelRowMapper<T> implements RowMapper<T> {

	private String clobToString(Clob clob) throws Exception {
        return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
    }
	@SuppressWarnings("unchecked")
	@Override
	public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
    	Map<String,Object> t=new HashMap<String, Object>();
    	try {
	    	// 获取查询结果的某些列
    		ResultSetMetaData metaData = rs.getMetaData();			    	  
    		for (int i = 1; i <= metaData.getColumnCount(); i++) {
    		    String column=metaData.getColumnName(i);
    		    //把非空的值，设置到对象
    		    Object columnValue=rs.getObject(column);
				if(columnValue==null){
					continue;
				}
				if(columnValue instanceof Clob){
					//转驼峰 
					t.put(Util.camelName(column), clobToString((Clob) columnValue));
				}else{

					//转驼峰 
					t.put(Util.camelName(column), columnValue);
				}
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return (T) t;  
	}

}

