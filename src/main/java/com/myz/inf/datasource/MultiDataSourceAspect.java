package com.myz.inf.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * MultiDataSourceAspect
 * Created by myz
 * Date 2020/12/4 19:52
 */
@Aspect
@Component
public class MultiDataSourceAspect {
    private static final Logger LOG = LoggerFactory.getLogger(MultiDataSourceAspect.class);

    @Pointcut("execution(public * com.myz..dao.*.*(..))")
    public void MultiDataSourceAspect() {
        //MultiDataSourceAspect() pointcut name

    }

    @Around("MultiDataSourceAspect()")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        if (LOG.isDebugEnabled()) {
            LOG.debug("MultipleDataSourceAspectAdvice invoked!");
           }

        Signature signature = jp.getSignature();
        String dataSourceKey = this.getDataSourceKey(signature);
        if (StringUtils.hasText(dataSourceKey)) {
            MultiDataSource.setDataSourceKey(dataSourceKey);
        }

        Object jpVal = jp.proceed();
        return jpVal;
    }

    public String getDataSourceKey(Signature signature) {
        if (signature == null) {
            return null;
        }

        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature)signature;
            Method method = methodSignature.getMethod();
            if (method.isAnnotationPresent(DataSource.class)) {
                DataSource dataSource = method.getAnnotation(DataSource.class);
                return dataSource.value();
            } else {
                Class declaringClazz = method.getDeclaringClass();
                if (declaringClazz.isAnnotationPresent(DataSource.class)) {
                    DataSource dataSource = (DataSource) declaringClazz.getAnnotation(DataSource.class);
                    return dataSource.value();
                }
            }
        }

        return null;
    }
}
