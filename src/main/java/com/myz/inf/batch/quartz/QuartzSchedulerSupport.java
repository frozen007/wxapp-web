package com.myz.inf.batch.quartz;

import com.myz.inf.batch.BatchScheduler;
import com.myz.inf.batch.JobEntry;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * QuartzSchedulerSupport
 * Created by myz
 * Date 2020/12/17 00:30
 */
public class QuartzSchedulerSupport implements BatchScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzSchedulerSupport.class);

    /**
     * Quartz scheduler
     */
    private Scheduler quartzScheduler;

    public QuartzSchedulerSupport(Scheduler quartzScheduler) {
        this.quartzScheduler = quartzScheduler;
        try {
            this.quartzScheduler.clear();
        } catch (SchedulerException e) {
            LOG.error("QuartzSchedulerSupport initialized error", e);
        }
    }

    @Override
    public void scheduleJob(JobEntry jobEntry) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                    .withIdentity(jobEntry.name(), "wxapp-web")
                    .requestRecovery()
                    .build();
            LOG.info("JobDetail created: key=", jobDetail.getKey());

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobEntry.name()+"_trigger", "wxapp-web")
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobEntry.cron()))
                    .build();
            LOG.info("CronTrigger created: key=", cronTrigger.getKey());

            quartzScheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void startScheduler() {

    }
}
