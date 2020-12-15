package com.myz.wxapp.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TestQuartzJob
 * Created by myz
 * Date 2020/12/14 15:42
 */
public class TestQuartzJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(TestQuartzJob.class);

    private long counter = 0;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("I am running: {} times", counter++);
    }
}
