package com.spring.security.repository.common;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作符类，这个类中存储了键值对和操作符号，另外存储了连接下一个条件的类型是and还是or
 * 创建时通过 id>=7,其中id就是key,>=就是oper操作符，7就是value
 * @author HuYaHui
 * @date: 2019年3月23日
 */
public class Op {
    private List<Op> ops=new ArrayList<Op>();
    private Boolean isDistinct;
    /**
     * 操作符的key，如查询时的name,id之类
     */
    private String key;
    /**
     * 操作符的value，具体要查询的值
     */
    private Object value;
    /**
     * 操作符，自己定义的一组操作符，用来方便查询
     */
    private String oper;
    /**
     * 连接的方式：and或者or
     */
    private List<Map<String,String>> joinList = new ArrayList<Map<String,String>>();

    private List<Map<String,String>> orderList = new ArrayList<Map<String,String>>();

    public Op(Map<String,Object> param){
        if(param!=null){
            for(String key:param.keySet()){
                if(StringUtils.isBlank(key)){
                    continue;
                }
                add(key, param.get(key), "=");
            }
        }
    }

    public Op add(String key, Object value, String oper){
        ops.add(new Op(key,value,oper));
        return this;
    }

    // key 关联对象  value 关联方式
    public Op addJoin(String key ,String value){
        Map<String,String> map = new HashMap<String, String>();
        map.put(key, value);
        joinList.add(map);
        return this;
    }

    // key 排序属性 value 排序方式
    public Op addOrder(String key ,String value){
        Map<String,String> map = new HashMap<String, String>();
        map.put(key, value);
        orderList.add(map);
        return this;
    }

    public Op(){
        super();
    }
    public Op(String key, Object value, String oper) {
        super();
        this.key = key;
        this.value = value;
        this.oper = oper;
        ops.add(this);
    }

    public Boolean isDistinct() {
        return isDistinct;
    }

    public void setDistinct(Boolean isDistinct) {
        this.isDistinct = isDistinct;
    }

    public List<Op> getOps() {
        return ops;
    }
    public void setOps(List<Op> ops) {
        this.ops = ops;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public String getOper() {
        return oper;
    }
    public void setOper(String oper) {
        this.oper = oper;
    }

    public List<Map<String,String>> getJoin() {
        return joinList;
    }

    public List<Map<String, String>> getOrderList() {
        return orderList;
    }

}
