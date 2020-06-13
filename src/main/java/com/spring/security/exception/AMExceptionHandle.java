/**
 * Created by Enzo Cotter on 20-4-15.
 */
package com.spring.security.exception;

import com.spring.security.enums.AMResponseEnums;
import com.spring.security.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;
import java.net.ConnectException;
import java.sql.SQLException;

@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class AMExceptionHandle {

    /**
     * 请求参数错误异常捕捉
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseBean<String> badRequest(BindException e) {
        log.error("=======================> occurs error when execute method ,message {}", e.getMessage());
        return new ResponseBean(AMResponseEnums.ERROR_PARAMS_FORM);
    }

    /**
     * 404错误异常的捕获
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseBean<String> badRequestNotFound(BindException e) {
        log.error("=======================> occurs error when execute method ,message {}", e.getMessage());
        return new ResponseBean(null, AMResponseEnums.NOT_FOUND);
    }

    /**
     * mybatis未绑定异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindingException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean<String> mybatis(Exception e) {
        log.error("=======================> occurs error when execute method ,message {}", e.getMessage());
        return new ResponseBean(AMResponseEnums.BOUND_STATEMENT_NOT_FOUNT);
    }

    /**
     * 自定义异常的捕获
     * 自定义抛出异常。统一的在这里捕获返回JSON格式的友好提示。
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = {AMException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public <T> ResponseBean<T> sendError(AMException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("=======================> occurs error when execute url ={} ,message {}", requestURI, exception.getMsg());
        return new ResponseBean(exception.getCode(), exception.getMsg());
    }

    /**
     * 数据库操作出现异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {SQLException.class, DataAccessException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBean<String> systemError(Exception e) {
        log.error("=======================> occurs error when execute method ,message {}", e.getMessage());
        return new ResponseBean(AMResponseEnums.DATABASE_ERROR);
    }

    /**
     * 网络连接失败！
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ConnectException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBean<String> connect(Exception e) {
        log.error("=======================> occurs error when execute method ,message {}", e.getMessage());
        return new ResponseBean(AMResponseEnums.CONNECTION_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBean<String> notAllowed(Exception e) {
        log.error("=======================> occurs error when execute method ,message {}", e.getMessage());
        return new ResponseBean(AMResponseEnums.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBean<String> nullPointException(Exception e) {
        log.error("=======================> NullPointerException message:{}", e.getMessage());
        return new ResponseBean<>(AMResponseEnums.NULL_POINTER_EXCEPTION);
    }
}
