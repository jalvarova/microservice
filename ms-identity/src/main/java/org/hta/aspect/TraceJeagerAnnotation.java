package org.hta.aspect;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class TraceJeagerAnnotation {

    @Around("@annotation(TraceSpan)")
    public Object logAnnotationTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        Method method = methodSignature.getMethod();
        TraceSpan annotation = method.getAnnotation(TraceSpan.class);
        Object result;
        if (Objects.nonNull(annotation)) {
            long start = System.currentTimeMillis();
            String methodName = method.getName();
            Tracer tracer = GlobalTracer.get();
            Tracer.SpanBuilder spanBuilder = tracer.buildSpan(annotation.key())
                    .withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_SERVER);

            Span span = spanBuilder.start();
            Tags.COMPONENT.set(span, className);
            span.setTag(annotation.key(), methodName);
            result = joinPoint.proceed();
            span.finish();

            long end = System.currentTimeMillis();
            log.info("Trace of " + className + "." + methodName + " :: " + (end - start) + " ms");
        } else {
            result = joinPoint.proceed();
        }

        return result;
    }
}
