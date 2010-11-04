/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Aspect
@Component
public class Logger {

	@Pointcut("execution(* service..*.*(..))")
	private void servicePointcute(){}

	@Before("servicePointcute()")
	public void log(JoinPoint jp) {
		System.out.println("executing...." + jp.getTarget());
	}
}
