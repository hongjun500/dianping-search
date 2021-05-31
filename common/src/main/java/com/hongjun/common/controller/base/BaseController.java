package com.hongjun.common.controller.base;

import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.common.validator.ValidatorImpl;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hongjun500
 * @date 2020/12/25 14:29
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:公共异常处理基类
 */
@Slf4j
public class BaseController {
    /**
     * 定义exceptionHandler解决未被controller层吸收的exception
     * @return obj
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(Exception e){
        Map<String, Object> responseData = new HashMap<>();
        if (e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());
        }else if (e instanceof RedisConnectionException || e instanceof RedisConnectionFailureException){
            // redis缓存连接出错
            responseData.put("errCode", EnumBusinessError.REDIS_CONNECTION_ERROR.getErrCode());
            responseData.put("errMsg", EnumBusinessError.REDIS_CONNECTION_ERROR.getErrMsg());

        }else if (e instanceof AccessDeniedException){
            responseData.put("errCode", EnumBusinessError.USER_NOT_ACCESS.getErrCode());
            responseData.put("errMsg", EnumBusinessError.USER_NOT_ACCESS.getErrMsg());
        }
        else {
            responseData.put("errCode", EnumBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EnumBusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        log.error("------错误信息{}----------",responseData.get("errMsg"));
        return CommonReturnType.create(responseData,"fail");
    }
}
