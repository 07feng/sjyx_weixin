package com.sunnet.framework.interceptor;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 增强处理类
 * 
 * @author 强强
 * 
 */
@Aspect
public class Loggers {

	private static final Logger log = Logger.getLogger(Loggers.class);

	// 前置 [切入点]
	@Before("execution(* *..service.impl*..*(..))")
	public void before(JoinPoint jp) {
		System.err.println("[" + jp.getTarget() + "] - 调用:" + jp.getSignature().getName()
				+ "方法>入参：" + Arrays.toString(jp.getArgs()));
	}
	
	// 后置 [pointcut 切入点 ,returning 返回值]
	@AfterReturning(pointcut = "execution(* *..service.impl*..*(..))", returning = "reten")
	public void After(JoinPoint jp, Object reten) {
		System.err.println("[" + jp.getTarget() + "] - 调用:" + jp.getSignature().getName()
				+ "方法>返回值：" + reten);
	}

	// 异常 [pointcut 切入点 ,throwing 异常]
	@AfterThrowing(pointcut = "execution(* *..service.impl*..*(..))", throwing = "e")
	public void thrwing(JoinPoint jp, RuntimeException e) {
		System.err.println("[" + jp.getTarget() + "] - 调用:" +jp.getSignature().getName() + "方法异常：" + e);
	}
	
//	// 最终 [切入点]
//	@After("execution(* *..service.impl*..*(..))")
//	public void Afterlogger(JoinPoint jp) {
//		log.info(jp.getSignature().getName() + "方法执行结束");
//	}

}
