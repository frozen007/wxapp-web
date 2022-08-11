package com.myz.wxapp.user.controller;

import com.myz.wxapp.user.bean.UserInfo;
import com.myz.wxapp.user.dao.UserInfoDao;
import com.myz.wxapp.util.JsonKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JsonKit jsonKit;

    @RequestMapping(path="/login")
    public String login(@RequestBody Map req) {

        String js_code = (String) req.get("code");
        logger.info("code={}", js_code);

        Map<String, Object> var = new HashMap<>();
        var.put("appid", "wx242cd4b1d4a5400b");
        var.put("secret", "e624c4302a067acf16f45ab57654c4f1");
        var.put("js_code", js_code);
        var.put("grant_type", "authorization_code");
        String result = restTemplate.getForObject(
                "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}",
                String.class, var);

        try {
            Map<String, Object> map = jsonKit.fromJson(result);
            String session_key= (String) map.get("session_key");
            String openid = (String) map.get("openid");
            String unionid = (String) map.get("unionid");
            logger.info("session_key={}, openid={}, unionid={}", session_key, openid, unionid);
        } catch (Exception e) {
            logger.error("", e);
        }

        return "SUCCESS";
    }

    @Autowired
    private UserInfoDao userInfoDao;

    @RequestMapping(path="/getusers")
    public List<UserInfo> getUsers() {
        logger.info("getUsers from db");
        List<UserInfo> allUserInfo = userInfoDao.getAllUserInfo();
        for (UserInfo userInfo : allUserInfo) {
            logger.info("get from dao user id={} user_id={}", userInfo.getId(), userInfo.getUser_id());
        }

        return allUserInfo;
    }
}
