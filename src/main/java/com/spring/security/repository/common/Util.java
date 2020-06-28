/** 
 * @(#)Util.java 1.0.0 2015年8月13日 上午7:58:53  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */ 

package com.spring.security.repository.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**   
 *  
 * @author  HuYaHui
 */
public class Util {
	public static final String KEY_SQL="sql";
	public static final String KEY_VALUE="value";
	//一次或多次
	private static final String 空格一或多="[ ]+?";
	private static final String 空格零或多="[ ]*?";
	// and|or 表别名.列名>=?
	private static final String regexCodition="(and|or)"+空格一或多+"[a-zA-Z0-9_]+?\\.[a-zA-Z0-9_]+?"+空格零或多+".??="+空格零或多+"\\?";
	// and exists(子查询 where 1=1)
	private static final String regexAndExists=空格一或多+"and"+空格一或多+"(exists|not exists)"+空格零或多+"\\(.*"+空格一或多+"where"+空格一或多+"1=1"+空格零或多+"\\)";
	// and not in(子查询 where 1=1)
	private static final String regexNotIn=空格一或多+"and"+空格一或多+"[a-zA-Z0-9_]+?\\.??[a-zA-Z0-9_]+?"+空格一或多+"(not in|in|=)"+空格零或多+"\\(.*"+空格一或多+"where"+空格一或多+"1=1"+空格零或多+"\\)";
	
	//key:列名,value:转换后值
	private static Map<String,String> camelNameMap=new HashMap<String,String>();
	
	/*public static void main(String[] args) {
//		replaceArgs("select * FROM GG_ZHB AS A wher 1=1 and A.YHYX in (select yhyx from GG_YHB b where 1=1 and b.yhyx=? or b.SJYHYX=?)",new Object[]{});
//		
//			//子查询测试
//		replaceArgs("select * from bbb1 bxs1 where 1=1 and exists(select id from cc1 where 1=1 and cc1.id>=? and cc1.name=?)",
//			new Object[]{});
//			//子查询测试
//		replaceArgs("select * from bbb1 bxs1 where 1=1 and exists(select id from cc1 where 1=1 and cc1.id>=? and cc1.name=?)",
//			new Object[]{1000});
//		
//			//子查询测试
//		replaceArgs("select * from bbb1 bxs1 where 1=1 and bxs1.id in (select id from cc1 where 1=1 and cc1.id>=? and cc1.name=?)",
//			new Object[]{1000});
//			//子查询测试
//		replaceArgs("select * from bbb1 bxs1 where 1=1 and bxs1.id not in (select id from cc1 where 1=1 and cc1.id>=? and cc1.name=?)",
//			new Object[]{1000,null});
//			//子查询测试
		replaceArgs("select * from bbb1 bxs1 where 1=1 and bxs1.id =  (select * from cc1 where 1=1 and cc1.id>=? and cc1.name=?)",
			new Object[]{new Date()});
//		
//			//参数数量跟sql匹配
//		replaceArgs("select * from bbb bc1 where 1=1 and bc1.id<=? and b.xx=? and b.asd=? and hjk=?",
//					new Object[]{"1",2,3,4+""});
		Object[] objs=new Object[]{"1","2",null,"4"};
//			//参数数量跟sql匹配,中间有null值
		Map<String,Object> rtnMap=replaceArgs("select * from bbb b where 1=1 and b.id=? and b.xx=? and b.asd=? and b.hjk=?",
					objs);
		System.out.println(rtnMap.toString());
		
		
//			//参数数量跟sql不匹配
//		replaceArgs("select * from bbb b where 1=1 and b.id=? and b.xx=? and b.asd=? and b.hjk=?",
//					new Object[]{"1",2,3,4,5});
//			//参数数量跟sql不匹配,中间有null值
//		replaceArgs("select * from bbb b where 1=1 and b.id=? and b.xx=? and b.asd=? and hjk=?",
//					new Object[]{"1","2",null,"4",5});
	}*/

	/**
	 * 替换sql中有条件占位符却没有对应参数
	 * 如果替换后子查询中无查询条件时,自动替换对应子查询所有sql(若不要自动替换,子查询中不带where 1=1即可)
	 * 
	 * select * from ttt t where t.id=?
	 * 2015年10月2日 上午10:52:38 by HuYaHui
	 * @param sql
	 * @param args
	 * @return
	 */
	public static Map<String,Object> replaceArgs(String sql,Object[] args){
		List<Object> sqlAry=new ArrayList<Object>();
		//过滤查询中(子查询中)的where条件		and 表别名.列名>=?
		Pattern p = Pattern.compile(regexCodition);
		Matcher m = p.matcher(sql);
		int i=0;
		while (m.find()) {
			String value = m.group();
			if(i>=args.length){
				//参数长度超过sql条件,全部匹配原则,多出的条件,从sql删除
				sql=sql.replace(value,"");
				//替换没有查询条件的子查询
				sql=replaceAll(sql);
				i++;
				continue;
			}else if(args[i]==null ||args[i].toString().trim().equals("")){
				//某个参数为空,从sql中删除对应条件
				sql=sql.replace(value,"");
				//替换没有查询条件的子查询
				sql=replaceAll(sql);
				i++;
				continue;
			}
			sqlAry.add(args[i]);
			i++;
		}
		System.out.println("sql:"+sql.toString());
		System.out.println("参数："+sqlAry);
		
		Map<String,Object> rtnMap=new HashMap<>();
		rtnMap.put(KEY_SQL, sql.toString());
		rtnMap.put(KEY_VALUE, sqlAry.toArray());
		return rtnMap;
	}

	/**
	 * 替换后如果发现没带条件的子查询,直接子查询
	 * 2015年10月2日 下午1:57:46 by HuYaHui
	 * @param sql
	 * @return
	 */
	private static String replaceAll(String sql){
		if(indexOf("and[ ]+?exists",sql)){
			//存在and exists关键字,检查子查询是否需要替换
			//	   and     exists       (子查询 	where 1=1)
			sql=sql.replaceAll(regexAndExists, "");
		}else if(indexOf("(not in|in|=)[ ]+?\\(.*[ ]+?where 1=1[ ]+?\\)",sql)){
			//存在关键字,检查子查询是否需要替换
			//	   and     表别名 	  .	 列名		 (not in|in|=)       (子查询 	 where 1=1)
			sql=sql.replaceAll(regexNotIn, "");
		}
		return sql;
	}

	private static boolean indexOf(String regx,String str){
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			return true;
		}
		return false;
	}
    /**
     * 下划线转换
     * @param s			hhhOoXxx
     * @param separator	_(要替换的字符)
     * @return			hyh_oo_xx_aaaa
     */
	public static String toUnderlineName(String s, String separator) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean nextUpperCase = true;
			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}
			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(separator);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}
    
	/**
	 * 驼峰转换
	 * @param name		HHH_OO_XXX
	 * @return			hhhOoXxx
	 */
	public static String camelName(String name) {
	    StringBuilder result = new StringBuilder();
	    if (name == null || name.isEmpty()) {
	        return name;
	    }
	    String camelName=camelNameMap.get(name);
	    if(camelName!=null && !camelName.equals("")){
	    	return camelName;
	    }
	    if(name.indexOf("_")==-1 && name.matches("(.*[a-z].*)(.*[A-Z].*)") ){
		    camelNameMap.put(name, name);
	    	return name;
	    }
	    String camels[] = name.split("_");
	    for (String camel :  camels) {
	        if (camel.isEmpty()) {
	            continue;
	        }
	        if (result.length() == 0) {
	            result.append(camel.toLowerCase());
	        } else {
	            result.append(camel.substring(0, 1).toUpperCase());
	            result.append(camel.substring(1).toLowerCase());
	        }
	    }
	    camelNameMap.put(name, result.toString());
	    return result.toString();
	}
}
