package com.myz.inf.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * BatchJobBeanPostProcessor
 * Created by myz
 * Date 2020/12/16 13:27
 */
public class BatchJobBeanPostProcessor implements BeanPostProcessor, Ordered, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(BatchJobBeanPostProcessor.class);

    private ApplicationContext applicationContext;

    private Map<String, JobHolder> jobRegistry = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        ReflectionUtils.doWithMethods(targetClass, method -> {
            JobEntry jobEntry = AnnotationUtils.getAnnotation(method, JobEntry.class);
            if (jobEntry != null) {
                LOG.info("found jobEntry name={}, cron={}", jobEntry.name(), jobEntry.cron());
                jobRegistry.put(jobEntry.name(), new JobHolder(jobEntry, bean, method));
            }
        });
        return bean;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext() == this.applicationContext) {
            scheduleAllJobs();
        }
    }

    private void scheduleAllJobs() {
        BatchScheduler batchScheduler = applicationContext.getBean("batchScheduler", BatchScheduler.class);

        BatchScheduleManager.setJobRegistry(jobRegistry);

        for (Map.Entry<String, JobHolder> entry : jobRegistry.entrySet()) {
            JobHolder jobHolder = entry.getValue();
            batchScheduler.scheduleJob(jobHolder.getJobEntry());
        }

        batchScheduler.startScheduler();
    }
}
