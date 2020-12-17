package com.myz.wxapp.config;

import com.myz.inf.batch.BatchJobBeanPostProcessor;
import com.myz.inf.batch.quartz.QuartzSchedulerSupport;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

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
    private Environment env;

    @Bean("dbquartz")
    @QuartzDataSource
    public DataSource dbquartzDataSource() throws Exception {
        Properties prop = new Properties();

        prop.put("username", env.getProperty("database.dbquartz.username"));
        prop.put("password", env.getProperty("database.dbquartz.password"));
        prop.put("url", env.getProperty("database.dbquartz.url"));
        prop.put("driverClassName", env.getProperty("database.dbquartz.driverClassName"));
        prop.put("initialSize", env.getProperty("database.dbquartz.initialSize"));
        prop.put("maxTotal", env.getProperty("database.dbquartz.maxTotal"));
        prop.put("maxIdle", env.getProperty("database.dbquartz.maxIdle"));
        prop.put("minIdle", env.getProperty("database.dbquartz.minIdle"));

        prop.put("validationQuery",env.getProperty("database.common.validationQuery"));

        return BasicDataSourceFactory.createDataSource(prop);
    }

    @Bean
    public BatchJobBeanPostProcessor batchJobBeanPostProcessor() {
        return new BatchJobBeanPostProcessor();
    }

    @Bean("batchScheduler")
    public QuartzSchedulerSupport quartzScheduleManager(@Qualifier("quartzScheduler") Scheduler quartzScheduler) {
        return new QuartzSchedulerSupport(quartzScheduler);
    }
}
