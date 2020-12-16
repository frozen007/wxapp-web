package com.myz.inf.batch.quartz;

import com.myz.inf.batch.BatchJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * QuartzJob
 * Created by myz
 * Date 2020/12/16 15:26
 */
public class QuartzJob extends BatchJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            super.executeJob();
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
