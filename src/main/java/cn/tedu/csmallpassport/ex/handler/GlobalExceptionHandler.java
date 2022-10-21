package cn.tedu.csmallpassport.ex.handler;


import cn.tedu.csmallpassport.ex.ServiceException;
import cn.tedu.csmallpassport.web.JsonResult;
import cn.tedu.csmallpassport.web.ServiceCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e) {
        logger.info("插入失败了:{}", e.getMessage());
//        JsonResult jsonResult = new JsonResult();
//        jsonResult.setState(1);
//        jsonResult.setMessage(e.getMessage());
        return JsonResult.fail(e.getServiceCode(),e.getMessage());
    }

    @ExceptionHandler
    public String handleThrowable(Throwable e) {
        logger.debug("捕获到Throwable：{}", e.getMessage());
        e.printStackTrace(); // 强烈建议
        return e.getMessage();
    }

    @ExceptionHandler
    public JsonResult handleBindException(BindException e){
        logger.debug("捕获到BindException：{}", e.getMessage());
        String message = e.getFieldError().getDefaultMessage();
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, message);
//        StringBuilder stringBuilder = new StringBuilder();
//        List<FieldError> fieldErrors = e.getFieldErrors();
//        for (FieldError fieldError : fieldErrors) {
//            stringBuilder.append(fieldError.getDefaultMessage());
//        }
//        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, stringBuilder.toString());
    }


    @ExceptionHandler
    public JsonResult handleConstraintViolationException(ConstraintViolationException e) {
        logger.debug("捕获到ConstraintViolationException：{}", e.getMessage());
        StringBuilder stringBuilder=new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            stringBuilder.append(constraintViolation.getMessage());
        }
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST,stringBuilder.toString());
    }

    @ExceptionHandler
    public JsonResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.debug("捕获到HttpRequestMethodNotSupportedException：{}", e.getMessage());
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN,"非法请求");
    }

    @ExceptionHandler
    public JsonResult handledAccessDeniedException(AccessDeniedException e) {
        logger.debug("捕获到AccessDeniedException：{}", e.getMessage());
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN,"请求失败,当前用户不具备此权限");

    }


    @ExceptionHandler
    public JsonResult handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        logger.debug("InternalAuthenticationServiceException：{}", e.getMessage());
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED,"账号不存在");

    }


    @ExceptionHandler
    public JsonResult handleBadCredentialsException(BadCredentialsException e) {
        logger.debug("BadCredentialsException：{}", e.getMessage());
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED,"密码错误");
    }

    @ExceptionHandler
    public JsonResult handleDisabledException(DisabledException e) {
        logger.debug("DisabledException：{}", e.getMessage());
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED_DISABLE,"账号被禁用");
    }





}
