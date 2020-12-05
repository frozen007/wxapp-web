package com.myz.wxapp.user.dao;

import com.myz.inf.datasource.DataSource;
import com.myz.wxapp.user.bean.UserInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserInfoDao
 * Created by myz
 * Date 2020/12/4 20:52
 */
@Repository
@DataSource("dbuser")
public interface UserInfoDao {

    @Select("select id, user_id, creation_time, update_time from user_info")
    List<UserInfo> getAllUserInfo();
}