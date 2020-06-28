package com.spring.security.repository.common;

/**
 * @Description
 * @Author lizhou
 * @Version 1.0.0
 * @Since 1.0
 * @Date 2019-09-03
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;


/**
 * @author HuYaHui
 *
 *	2018-12-18	修改query方法，注释代码join = root.join(str.getKey(),type)
 */
public class Query {
    public static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };
    // 1.前端传入map,如果是需要join查询的字段，则key 设计为   memberLogsEntities.ip
    // 2.到controller层 根据对应业务，构造Op对象
    // 		a.无关联对象,无范围查询,直接传入map
    // 		b.无关联对象,带范围查询,分析map,日期查询满足startDate,endDate,
    // 		c.无关联对象,带范围查询,分析map,数字查询满足startNumber,endNumber,
    // 		d.有关联对象,不带关联对象查询,构建 List<Map<String,String>> { memberLogsEntities = left }
    // 		e.有关联对象,带关联对象属性查询   解析memberLogsEntities.ip   只考虑两级关联
    // 		f.无关联对象排序
    // 		g.有关联对象排序
    //		h.投影查询问题
    public static <T>Specification<T> query(final Op o,T t){
        return new Specification<T>(){
            @SuppressWarnings("unchecked")
            @Override
            public Predicate toPredicate(Root root,
                                         CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                Map<Object, Object> joinMap = new HashMap<Object, Object>();

//            	if(o.getOps().isEmpty()){
//            		return null;
//            	}

                for (int i = 0; i < o.getOps().size(); i++) {

                    Op oops=o.getOps().get(i);
                    String key=oops.getKey();
                    if(StringUtils.isBlank(key)){
                        continue;
                    }
                    if(key.equals("pageNo")){
                        continue;
                    }
                    Object value=oops.getValue();
                    if(value==null){
                        continue;
                    }
                    if (value instanceof String) {
                        if(StringUtils.isBlank(value+"")){
                            continue;
                        }
                    }

                    String oper=oops.getOper();
                  try {
                        // 常规查询条件 （不带日期）
                        if(!key.endsWith("DateStart")
                                && !key.endsWith("DateEnd")
                                && !key.endsWith("NumberStart")
                                && !key.endsWith("NumberEnd")){

                            list.addAll(getPredicate(root, cb, key, value,oper));
                        }

                        if(key.endsWith("DateStart")){
                            Predicate p = cb.greaterThanOrEqualTo(root.get(key.substring(0, key.length()-5)).as(Date.class),
                                    org.apache.commons.lang3.time.DateUtils.parseDate(value+"",parsePatterns));
                            list.add(p);
                        }else if(key.endsWith("DateEnd")){
                            Predicate p = cb.lessThanOrEqualTo(root.get(key.substring(0, key.length()-3)).as(Date.class),
                                    org.apache.commons.lang3.time.DateUtils.parseDate(value+"",parsePatterns));
                            list.add(p);

                        }else if(key.endsWith("NumberStart")){

                            Predicate p = cb.greaterThanOrEqualTo(root.get(key.substring(0, key.length()-5)).as(BigDecimal.class),(BigDecimal)value);
                            list.add(p);

                        }else if(key.endsWith("NumberEnd")){
                            Predicate p = cb.greaterThanOrEqualTo(root.get(key.substring(0, key.length()-3)).as(BigDecimal.class),(BigDecimal)value);
                            list.add(p);

                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                for(int i = 0 ; i<o.getJoin().size();i++){
                    // 解决分页问题
                    Class<?> clazz = query.getResultType();
                    if (clazz.equals(Long.class)) {
                        break;
                    }
                    Map<String,String> map = o.getJoin().get(i);
                    for (Entry<String, String> str : map.entrySet()) {
                        JoinType type = matchJoinType(str.getValue());
                        if(str.getKey().split("\\.").length==1) {
                            root.fetch(str.getKey(),type);
                        }else {
                            String[] strs=str.getKey().split("\\.");
                            javax.persistence.criteria.Fetch fetch=root.fetch(strs[0], type);
                            for (int j = 1; j < strs.length; j++) {
                                String attributeName=strs[j];
                                fetch.fetch(attributeName, type);
                            }
                        }
//            			join = root.join(str.getKey(),type);
                    }
//					root.get(attributeName)
                }

                Predicate[] p = new Predicate[list.size()];
                // 使用 关联对象属性  memberLogsEntities.ip 排序时，不能加distinct
                if(o.isDistinct()!=null && o.isDistinct()) {
                    query.distinct(true);
                }

//                query.groupBy(root.get("id"));
//                query.having(cb.equal(cb.count(root.join("configs",JoinType.LEFT).get("id")), 0));




                query.where(list.toArray(p));
                return null;
            }

        };
    }
    private static List<Predicate> getPredicate(
            Root root,CriteriaBuilder cb,String key,Object value,String oper) {
        List<Predicate>  list= new ArrayList<Predicate>();
        //普通查询 k=v
        if(key.split("\\.").length==1) {
            if(oper.equals("=") ) {
                list.add(cb.equal(root.get(key), value));
            }else if(oper.equals("in")) {
                if (value instanceof Collection){
                    list.add(root.get(key).in((Collection<?>) value));
                }else {
                    list.add(root.get(key).in(value));
                }
            }else if(oper.equals(">=")) {
                if(value instanceof Date) {
                    list.add(cb.greaterThanOrEqualTo(root.get(key), (Date)value));
                }
                if(value instanceof String) {
                    list.add(cb.greaterThanOrEqualTo(root.get(key), (String)value));
                }
            }else if(oper.equals("<=")) {
                if(value instanceof Date) {
                    list.add(cb.lessThanOrEqualTo(root.get(key), (Date)value));
                }
                if(value instanceof String) {
                    list.add(cb.lessThanOrEqualTo(root.get(key), (String)value));
                }
            }
        }else {
            String[] strs=key.split("\\.");
            Join jj=root.join(strs[0],JoinType.LEFT);
            for (int j = 1; j < strs.length; j++) {
                String attributeName=strs[j];
                if(j!=strs.length-1) {
                    //不是最后一个
                    jj=jj.join(attributeName,JoinType.LEFT);
                    continue;
                }
                //最后一个
                if(oper.equals("=") ) {
                    list.add(cb.equal(jj.get(attributeName), value));
                }else if(oper.equals("in")) {
                    list.add(jj.get(attributeName).in(value));
                }

            }
//			Join jj=root.join("productConfigs",JoinType.LEFT);
//			jj=jj.join("config",JoinType.LEFT);
//			Path path=jj.get("configType");

//			list.add(cb.equal(path, value));

//			Join jj=root.join("configs",JoinType.LEFT);
//			Path path=jj.get("configValue");
//			list.add(path.in(value));
        }
        return list;
    }
    private static JoinType matchJoinType(String joinTypeStr) {
        if("right".equals(joinTypeStr)){
            return JoinType.RIGHT;
        }else if("inner".equals(joinTypeStr)){
            return JoinType.INNER;
        }else {
            return JoinType.LEFT;
        }
    }

}
