package com.myz.wxapp.config;

import com.myz.inf.datasource.MultiDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * DBConfiguration
 * Created by myz
 * Date 2020/12/3 20:13
 */
@Configuration
@MapperScan({"com.myz.wxapp.user.dao","com.myz.wxapp.ticket.dao"})
@ComponentScan("com.myz.inf.datasource")
public class DBConfiguration {

    @Autowired
    private Environment env;

    @Bean("dbuser")
    @Primary
    public DataSource dbuserDataSource() throws Exception {
        Properties prop = new Properties();

        prop.put("username", env.getProperty("database.dbuser.username"));
        prop.put("password", env.getProperty("database.dbuser.password"));
        prop.put("url", env.getProperty("database.dbuser.url"));
        prop.put("driverClassName", env.getProperty("database.dbuser.driverClassName"));
        prop.put("initialSize", env.getProperty("database.dbuser.initialSize"));
        prop.put("maxTotal", env.getProperty("database.dbuser.maxTotal"));
        prop.put("maxIdle", env.getProperty("database.dbuser.maxIdle"));
        prop.put("minIdle", env.getProperty("database.dbuser.minIdle"));

        prop.put("validationQuery",env.getProperty("database.common.validationQuery"));

        return BasicDataSourceFactory.createDataSource(prop);
    }

    @Bean("multiDataSource")
    public DataSource multiDataSource(DataSource dbuser) throws Exception {
        MultiDataSource multiDataSource = new MultiDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("dbuser", dbuser);
        multiDataSource.setTargetDataSources(targetDataSources);
        return multiDataSource;
    }


    @Bean("sqlSessionFactory")
    public SqlSessionFactory createSqlSessionFactory(DataSource multiDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(multiDataSource);
        return sqlSessionFactoryBean.getObject();
    }
}
