package com.myz.inf.batch;

import java.lang.reflect.Method;

/**
 * BatchSchedulerManager
 * Created by myz
 * Date 2020/12/16 14:42
 */
public interface BatchScheduleManager {

    void scheduleJob(JobEntry jobEntry, Object jobBean, Method jobMethod);

    void startScheduler();
}
