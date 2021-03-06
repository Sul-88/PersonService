package com.sul.person.service.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.sul.person.service.monitoring.CustomPerformanceMonitorInterceptor;

/**
 * @author Sulaiman Abboud
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AopConfiguration {
	@Pointcut("execution(* com.sul.person.service.rest.*.*(..))")
	public void myMonitor() {
	}

	@Bean
	public CustomPerformanceMonitorInterceptor customerPerformanceMonitorInterceptor() {
		return new CustomPerformanceMonitorInterceptor(false);
	}

	@Bean
	public Advisor customPerformanceMonitorAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("com.sul.person.service.config.AopConfiguration.myMonitor()");
		return new DefaultPointcutAdvisor(pointcut, customerPerformanceMonitorInterceptor());
	}

}
