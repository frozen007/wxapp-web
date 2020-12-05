package com.myz.wxapp.user.controller;

import com.myz.wxapp.user.bean.UserInfo;
import com.myz.wxapp.user.dao.UserInfoDao;
import com.myz.wxapp.util.JsonKit;
import com.myz.wxapp.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JsonKit jsonKit;

    @RequestMapping(path="/login")
    public Response login(@RequestBody Map req) {

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

        return new Response().success();
    }

    @Autowired
    private DataSource dbuser;

    @Autowired
    private UserInfoDao userInfoDao;

    @RequestMapping(path="/getuser")
    public Response getUser() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbuser);
        jdbcTemplate.query("select id, user_id, creation_time, update_time from user_info",
                new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                logger.info("id={}, user_id={}, creation_time={}, update_time={}",
                        rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getLong(4));
            }
        });

        List<UserInfo> allUserInfo = userInfoDao.getAllUserInfo();
        for (UserInfo userInfo : allUserInfo) {
            logger.info("get from dao user id={} ", userInfo.getId());
        }


        return new Response().success();
    }
}
