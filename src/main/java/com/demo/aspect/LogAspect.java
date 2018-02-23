package com.demo.aspect;

import com.demo.Application;
import com.demo.annotation.Log;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final static ObjectMapper jsonMapper = new ObjectMapper();

    private static final Logger logger = Logger.getLogger(Application.class);
    
	@Pointcut("@annotation(com.demo.annotation.Log)")
	public void logPointCut() {
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Log syslog = method.getAnnotation(Log.class);
		if (syslog != null) {
			// 注解上的描述
			logger.info("日志切面："+syslog.value());
		}
		return result;
	}

    @Pointcut("execution(* com.demo.service.*.*(..))")
    public void show() {
    }


    @Before("show()")
    public void Befor(JoinPoint jp) {
        try {
            logger.info("调用方法前[" + jp.toLongString() + "] 传入相关数据：" + jsonMapper.writeValueAsString(jp.getArgs()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterThrowing(value = "show()", throwing = "e1")
    public void afterThrowing(JoinPoint jp, Exception e1) {
        try {
            logger.error("(错误)调用方法后[" + jp.toLongString() + "] 错误信息：" + e1.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterReturning(value = "show()", returning = "obj")
    public Object afterReturning(JoinPoint jp, Object obj) {
        try {
            logger.info("调用方法后[" + jp.toLongString() + "] 返回相关数据：" + jsonMapper.writeValueAsString(obj));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("LogAfter:" + e.getMessage());
        }
        return null;
    }
}
