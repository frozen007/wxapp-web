package com.myz.wxapp.web;

import com.myz.wxapp.api.DemoService;
import com.myz.wxapp.api.task.QueryTaskRequest;
import com.myz.wxapp.api.task.QueryTaskResult;
import com.myz.wxapp.api.task.TaskService;
import com.myz.wxapp.api.task.UserTask;
import com.myz.wxapp.task.TaskConverter;
import com.myz.wxapp.user.bean.UserInfo;
import com.myz.wxapp.util.CommonResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TestController
 * Created by myz
 * Date 2020/12/5 17:53
 */
@RestController
@RequestMapping(path = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

//    @Autowired
//    private WxAppClient wxAppClient;

    @DubboReference
    private DemoService demoService;

    @DubboReference
    private TaskService taskService;

    @Autowired
    private ServerProperties serverProperties;

    @RequestMapping(path="/getusers")
    public CommonResponse getUsers() {
//        List<UserInfo> users = wxAppClient.getUsers();
        List<UserInfo> users = new ArrayList<UserInfo>(){{
            UserInfo userInfo = new UserInfo();
            userInfo.setUser_id(1982L);
            userInfo.setId(1007);
            userInfo.setCreation_time(System.currentTimeMillis());
            userInfo.setUpdate_time(System.currentTimeMillis());
            add(userInfo);
        }};
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);

        logger.info("get users size={}", users.size());

        return new CommonResponse().success(data);
    }

    @RequestMapping(path="/server-prop")
    public CommonResponse getServerProperties() {
        Map<String, Object> data = new HashMap<>();

        data.put("serverProperties", serverProperties);

        return new CommonResponse().success(data);
    }

    @RequestMapping(path="/dubbo-demo")
    public CommonResponse getDubboDemo(String name) {
        Map<String, Object> data = new HashMap<>();

        String result = demoService.sayHello(name);

        data.put("sayHello", result);

        return new CommonResponse().success(data);
    }

    @RequestMapping(path="/query-tasks")
    public CommonResponse queryTasks(Long userId) {
        Map<String, Object> data = new HashMap<>();

        logger.info("invoke: userId={}", userId);
        QueryTaskRequest request = QueryTaskRequest.newBuilder().setUserId(userId).build();
        QueryTaskResult queryTaskResult = taskService.queryTask(request);
        List<UserTask> tasksList = queryTaskResult.getTasksList();
        data.put("tasks", TaskConverter.convertToVO(tasksList));

        return new CommonResponse().success(data);
    }

//    @FeignClient("wxapp-web")
//    interface WxAppClient {
//        @RequestMapping(value = "/user/getusers", method = RequestMethod.GET)
//        List<UserInfo> getUsers();
//    }

}
