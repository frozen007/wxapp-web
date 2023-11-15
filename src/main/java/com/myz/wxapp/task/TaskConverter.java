package com.myz.wxapp.task;

import com.myz.wxapp.api.task.TaskExecution;
import com.myz.wxapp.api.task.UserTask;
import com.myz.wxapp.task.vo.TaskExecutionVO;
import com.myz.wxapp.task.vo.UserTaskVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhaomingyumt
 * @date: 2023/11/15 5:18 PM
 * @description:
 */
public class TaskConverter {

    public static List<UserTaskVO> convertToVO(List<UserTask> userTasks) {
        if (userTasks == null && !userTasks.isEmpty()) {
            return Collections.emptyList();
        }

        List<UserTaskVO> userTaskVOS = userTasks.stream().map(t -> {
            UserTaskVO taskVO = new UserTaskVO();
            taskVO.setId(t.getId());
            taskVO.setUserId(t.getUserId());
            taskVO.setName(t.getName());
            taskVO.setDescription(t.getDescription());
            taskVO.setStatus(t.getStatus());
            taskVO.setTaskType(t.getTaskType());
            taskVO.setCreateTime(t.getCreateTime());
            TaskExecution execution = t.getExecution();
            if (execution != null) {
                TaskExecutionVO executionVO = new TaskExecutionVO();
                executionVO.setExecType(execution.getExecType());
                executionVO.setPeriodType(execution.getPeriodType());
                executionVO.setFireTime(execution.getFireTime());
                executionVO.setNextFireTime(execution.getNextFireTime());
                taskVO.setExecution(executionVO);
            }
            return taskVO;
        }).collect(Collectors.toList());
        return userTaskVOS;
    }
}
