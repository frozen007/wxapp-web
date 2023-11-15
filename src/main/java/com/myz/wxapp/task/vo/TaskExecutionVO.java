package com.myz.wxapp.task.vo;

/**
 * @author: zhaomingyumt
 * @date: 2023/11/15 5:16 PM
 * @description:
 */
public class TaskExecutionVO {
    private int execType;

    private int periodType;

    private long fireTime;

    private long nextFireTime;

    public int getExecType() {
        return execType;
    }

    public void setExecType(int execType) {
        this.execType = execType;
    }

    public int getPeriodType() {
        return periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public long getFireTime() {
        return fireTime;
    }

    public void setFireTime(long fireTime) {
        this.fireTime = fireTime;
    }

    public long getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }
}
