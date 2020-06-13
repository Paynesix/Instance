/**
 * Created by Enzo Cotter on 20-4-15.
 */
package com.spring.security.vo;

import com.spring.security.enums.AMResponseEnums;
import lombok.Data;

@Data
public class ResponseBean<T>{

    private String code;
    private String msg;
    private T data;

    public ResponseBean(){
        super();
    }

    public ResponseBean(String code, String msg, T data){
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseBean(String code, String msg){
        super();
        this.code = code;
        this.msg=msg;
    }

    public ResponseBean(AMResponseEnums enums){
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public ResponseBean(T data){
        super();
        this.code = "0";
        this.msg = "OK";
        this.data= data;
    }

    public ResponseBean(T data, AMResponseEnums enums){
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.data = data;
    }

    public void ResponseSuccess(T data){
        this.code = "0";
        this.msg = "success";
        this.data= data;
    }
    public void ResponseFailure(AMResponseEnums enums){
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

}
