package com.myz.wxapp.config;

import com.myz.inf.batch.BatchJobBeanPostProcessor;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JobConfiguration
 * Created by myz
 * Date 2020/12/14 15:47
 */
@Configuration
public class JobConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(JobConfiguration.class);

    public JobConfiguration() {

    }

    @Autowired
    private Scheduler quartzScheduler;

    @Bean
    public BatchJobBeanPostProcessor batchJobBeanPostProcessor() {
        return new BatchJobBeanPostProcessor();
    }

}
