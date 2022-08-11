package com.myz.wxapp.web;

import com.myz.wxapp.user.bean.UserInfo;
import com.myz.wxapp.util.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private WxAppClient wxAppClient;

    @RequestMapping(path="/getusers")
    public CommonResponse getUsers() {
        List<UserInfo> users = wxAppClient.getUsers();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);

        logger.info("get users size={}", users.size());

        return new CommonResponse().success(data);
    }

    @FeignClient("wxapp-web")
    interface WxAppClient {
        @RequestMapping(value = "/user/getusers", method = RequestMethod.GET)
        List<UserInfo> getUsers();
    }

}
