package com.myz.inf.batch;

import java.lang.reflect.Method;

/**
 * BatchJob
 * Created by myz
 * Date 2020/12/16 15:27
 */
public abstract class BatchJob {

    private Object jobBean;

    private Method jobMethod;

    protected void executeJob() throws Exception {
        jobMethod.invoke(jobBean);
    }

    public BatchJob bean(Object jobBean) {
        this.jobBean = jobBean;
        return this;
    }

    public BatchJob method(Method jobMethod) {
        this.jobMethod = jobMethod;
        return this;
    }
}
