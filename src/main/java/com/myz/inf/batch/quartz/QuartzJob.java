package com.myz.inf.batch.quartz;

import com.myz.inf.batch.BatchScheduleManager;
import com.myz.inf.batch.JobRunner;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * QuartzJob
 * Created by myz
 * Date 2020/12/16 15:26
 */
public class QuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobRunner jobRunner = BatchScheduleManager.getJob(context.getJobDetail().getKey().getName());

        try {
            jobRunner.runJob();
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
