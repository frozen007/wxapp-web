package com.myz.wxapp.task.vo;

/**
 * @author: zhaomingyumt
 * @date: 2023/11/15 5:13 PM
 * @description:
 */
public class UserTaskVO {

    private long id;

    private long userId;

    private String name;

    private String description;

    private int status;

    private int taskType;

    private long createTime;

    private TaskExecutionVO execution;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public TaskExecutionVO getExecution() {
        return execution;
    }

    public void setExecution(TaskExecutionVO execution) {
        this.execution = execution;
    }
}
