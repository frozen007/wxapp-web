package com.myz.inf.batch;

import java.lang.reflect.Method;

/**
 * BatchScheduler
 * Created by myz
 * Date 2020/12/17 10:39
 */
public interface BatchScheduler {

    void scheduleJob(JobEntry jobEntry);

    void startScheduler();

}
