package com.myz.inf.batch;

import java.lang.reflect.Method;

/**
 * JobHolder
 * Created by myz
 * Date 2020/12/17 11:10
 */
public class JobHolder {
    private JobEntry jobEntry;
    private Object jobBean;
    private Method jobMethod;

    public JobHolder(JobEntry jobEntry, Object jobBean, Method jobMethod) {
        this.jobEntry = jobEntry;
        this.jobBean = jobBean;
        this.jobMethod = jobMethod;
    }

    public JobEntry getJobEntry() {
        return jobEntry;
    }

    public Object getJobBean() {
        return jobBean;
    }

    public Method getJobMethod() {
        return jobMethod;
    }
}
