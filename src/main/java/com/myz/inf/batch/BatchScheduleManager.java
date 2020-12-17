package com.myz.inf.batch;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * BatchScheduleManager
 * Created by myz
 * Date 2020/12/16 14:42
 */
public class BatchScheduleManager {

    private static Map<String, JobRunner> jobRegistry = new HashMap<>();

    public static void setJobRegistry(Map<String, JobHolder> jobs) {
        for (Map.Entry<String, JobHolder> entry : jobs.entrySet()) {
            JobHolder jobHolder = entry.getValue();
            jobRegistry.put(jobHolder.getJobEntry().name(), new JobRunner() {
                Method jobMethod = jobHolder.getJobMethod();
                Object jobBean = jobHolder.getJobBean();

                @Override
                public void runJob() throws Exception {
                    jobMethod.invoke(jobBean);
                }
            });
        }

    }

    public static JobRunner getJob(String name) {
        return jobRegistry.get(name);
    }
}
