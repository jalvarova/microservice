package org.hta.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TraceLoadProductAspect {

	@Around("@annotation(org.hta.aspect.LogTime)")
	public Object logAnnotationTrace(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		long start = System.currentTimeMillis();

		Object result = joinPoint.proceed();

		long end = System.currentTimeMillis();

		log.info("Execution time of " + className + "." + methodName + " :: " + (end - start) + " ms");
		return result;
	}
}
