package com.hongjun.adminweb.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author hongjun500
 * @date 2021/3/19 17:00
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Component
@Aspect
@Slf4j
public class AopAspectjLogInfo {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 切入点
     */
    @Pointcut(value = "execution(* com.hongjun.adminweb.controller.*.*(..))")
    public void webLog(){

    }

    /**
     * 前置通知
     */
    @Before("webLog()")
    public void before(JoinPoint joinPoint) {
        // 开始计时
        startTime.set(System.currentTimeMillis());

        // 接受请求，并记录
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LocalVariableTableParameterNameDiscoverer localVariable = new LocalVariableTableParameterNameDiscoverer();
        // 参数值
        Object[] args = joinPoint.getArgs();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        log.info("来路地址: {}", request.getRemoteAddr());
        log.info("访问地址: {}", request.getRequestURI());
        log.info("请求方式: {}", request.getMethod());
        log.info("请求参数名: {}, 请求参数值: {}", Arrays.toString(localVariable.getParameterNames(method)), Arrays.toString(args));
        log.info("执行类: {}, 执行方法: {}", joinPoint.getSignature().getDeclaringType(), method.getName());
    }

    /**
     * 后置通知
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("返回: {}", ret);
    }

    /**
     * 最终通知
     */
    @After("webLog()")
    public void after(){
        log.info("执行时间: {}毫秒" ,(System.currentTimeMillis() - startTime.get()));
        startTime.remove();//用完之后记得清除，不然可能导致内存泄露;
    }
}
