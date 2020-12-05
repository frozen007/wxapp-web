package com.myz.inf.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * MultiDataSource
 * Created by myz
 * Date 2020/12/4 19:47
 */
public class MultiDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> dataSourceKey = new ThreadLocal();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dsName = (String)dataSourceKey.get();
        dataSourceKey.remove();
        return dsName;
    }
}
