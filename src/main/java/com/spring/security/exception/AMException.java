/**
 * Created by xy on 20-4-16.
 */
package com.spring.security.exception;

import com.spring.security.enums.AMResponseEnums;
import lombok.Data;

@Data
public class AMException extends RuntimeException {
    private static final  long serialVersionUID = 1L;

    protected String code;
    protected String msg;
    protected String message;

    public AMException(AMResponseEnums enums, String message){
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.message = message;
    }

    public AMException(AMResponseEnums enums){
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public AMException(String code, String msg){
        super();
        this.code = code;
        this.msg = msg;
    }

    public AMException(){
        super();
    }

    public AMException(String message){
        super(message);
    }

    public AMException(String message, Throwable cause){
        super(message, cause);
    }

    public AMException(Throwable cause){
        super(cause);
    }
}
