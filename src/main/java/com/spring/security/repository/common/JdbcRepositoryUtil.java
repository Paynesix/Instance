package com.spring.security.repository.common;

/** 
 * @(#)RepositoryUtil.java 1.0.0 2015年4月16日 上午9:24:55  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**   
 * 数据库操作工具类
 *  
 * @author  HuYaHui
 * @version $Revision:1.0.0, $Date: 2015年4月16日 上午9:24:55 $ 
 */
@Component
public class JdbcRepositoryUtil {

	public static final String ORACLE="ORACLE";
	public static final String MYSQL="MYSQL";
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public EntityManager getEm() {
		return em;
	}

	public <T> T queryById(Object id, Class<T> rtnType) throws RuntimeException{
		Table table = rtnType.getAnnotation(Table.class);
		String table_name=table.name();
		List<T> list=queryForList("select * from "+table_name+
				" where id=?", new Object[]{id}, rtnType);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 若使用自动替换功能
	 * 		1.sql中每个表必须有别名
	 * 		2.where语句后的第一个条件必须是1=1
	 * 
	 * 例:
	 * 		select * from bbb b where 1=1 and b.id=? and b.xx=? 
	 * @param sql				
	 * @param args
	 * @param rowMapper
	 * @return
	 * @throws RuntimeException
	 */
	public <T> List<T> queryForList(String sql, Object[] args, RowMapper<T> rowMapper) throws RuntimeException{
		return queryForList(sql, args, rowMapper,false);
	}
	public <T> List<T> queryForList(String sql, Object[] args, RowMapper<T> rowMapper, boolean isAutoReplace) throws RuntimeException{
		try {
			String _sqlStr=sql;
			Object[] _args=args;
			if(isAutoReplace){
				//替换sql中有条件占位符却没有对应参数
				Map<String,Object> rtnMap=Util.replaceArgs(sql, args);
				_sqlStr=rtnMap.get(Util.KEY_SQL).toString();
				_args=(Object[]) rtnMap.get(Util.KEY_VALUE);
				
			}
			
			//替换sql中占位符
//			System.out.println(com.alibaba.druid.sql.SQLUtils.format(sql, 
//					JdbcUtils.ORACLE, Arrays.asList(args)));
			
			List<T> query = jdbcTemplate.query(_sqlStr,
					_args,  
					rowMapper
			); 
			return query;
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		} 
	}
	
	
	/**
	 * sql查询,返回值列名自动替换驼峰命名规则
	 * 		repositoryUtil.queryForCamelMap("select * from BAO_T_SMS_LOG_INFO where mobile=?", new Object[]{"13111111111"},0,10);
	 * 		repositoryUtil.queryForCamelMap("select VERITY_CODE from XSD_CUSTOMER", new Object[]{},0,10);
	 * 		repositoryUtil.queryForCamelMap("select VERITY_CODE \"verityCode\" from XSD_CUSTOMER", new Object[]{},0,10);
	 * 
	 * @param sql				查询字符串
	 * @param args				sql所需参数
	 * @return
	 * @throws RuntimeException
	 */
	public List<Map<String, Object>> queryForCamelMap(String sql, Object[] args) throws RuntimeException{
		return queryForCamelMap(sql, args,false);
	}
	public List<Map<String, Object>> queryForCamelMap(String sql, Object[] args,boolean isAutoReplace) throws RuntimeException{
		return queryForList(sql, args,new CamelRowMapper<Map<String, Object>>(),isAutoReplace);
	}

	/**
	 * 分页查询,返回Map格式数据,返回值列名自动替换驼峰命名规则
	 * @param sql				查询字符串
	 * @param args				sql所需参数
	 * @param start				数据从第几条开始
	 * @param length			每页显示数量
	 * @return
	 * @throws RuntimeException
	 */
	public Page<Map<String, Object>> queryForCamelMap(String sql, Object[] args, int start, int length){
		return queryForCamelMap(sql, args, start, length,false);
	}
	
	public Page<Map<String, Object>> queryForCamelMap(String sql, Object[] args, int start, int length, boolean isAutoReplace){
		StringBuilder _sql=new StringBuilder();
		String _sqlStr=sql;
		if(isAutoReplace){
			Map<String,Object> rtnMap=Util.replaceArgs(sql, args);
			_sqlStr=rtnMap.get(Util.KEY_SQL).toString();
			args=(Object[]) rtnMap.get(Util.KEY_VALUE);
		}
		long total=jdbcTemplate.queryForObject("select count(*) from ( "+_sqlStr+" ) _blbl", args,Long.class);
		if(total==0){
			return new PageImpl<Map<String, Object>>(new ArrayList<Map<String, Object>>(),new PageRequest(0, length),total);
		}
		List<Map<String, Object>> content=null;

        //MYSQL
        _sql.append(_sqlStr).append(" limit ").append(start<0?0:start*length).append(",");
        _sql.append(length);
        //_sql.append(_sqlStr).append(" limit ").append(start<0?0:start).append(",").append(start+length);
        content=queryForCamelMap(_sql.toString(), args,isAutoReplace);
    
		return new PageImpl<Map<String, Object>>(content,new PageRequest(start, length),total);
	}


	/**
	 * 分页查询,返回对象格式数据
	 * @param sql				查询字符串
	 * @param args				sql所需参数
	 * @param start				数据从第几条开始
	 * @param length			每页显示数量
	 * @param rtnType			返回对象类型
	 * @return
	 * @throws RuntimeException
	 */
	public <T> Page<T> queryForPage(String sql, Object[] args, int start, int length, final Class<T> rtnType) throws RuntimeException{
		return queryForPage(sql, args, start, length, rtnType, false);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Page<T> queryForPage(String sql, Object[] args, int start, int length, final Class<T> rtnType, boolean isAutoReplace) throws RuntimeException{
		StringBuilder _sql=new StringBuilder();
		String _sqlStr=sql;
		if(isAutoReplace){
			Map<String,Object> rtnMap=Util.replaceArgs(sql, args);
			_sqlStr=rtnMap.get(Util.KEY_SQL).toString();
			args=(Object[]) rtnMap.get(Util.KEY_VALUE);
		}
		long total=jdbcTemplate.queryForObject("select count(*) from ( "+_sqlStr+" ) _blbl", args,Long.class);
		if(total==0){
			return new PageImpl<T>(new ArrayList(),new PageRequest(0, length),total);
		}
		List<T> content=null;

        //MYSQL
        _sql.append(_sqlStr).append(" limit ").append(start<0?0:start*length).append(",").append(length);
        content=queryForList(_sql.toString(), args, rtnType,isAutoReplace);
    
		return new PageImpl<T>(content,new PageRequest(start, length),total);
	}

	/**
	 * 普通查询,返回对象格式数据
	 * @param sql				查询字符串
	 * @param args				sql所需参数
	 * @param rtnType			返回对象类型
	 * @return
	 * @throws RuntimeException
	 */
	public <T> List<T> queryForList(String sql, Object[] args, final Class<T> rtnType) throws RuntimeException{
		return queryForList(sql, args,rtnType,false);
	}
	public <T> List<T> queryForList(String sql, Object[] args, final Class<T> rtnType,boolean isAutoReplace) throws RuntimeException{
		return queryForList(sql, args, BeanPropertyRowMapper.newInstance(rtnType),isAutoReplace);
	}

}
