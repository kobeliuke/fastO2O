package com.will.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceHolder {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE = "slave";

    public static String getDbType(){
        String dbType = contextHolder.get();
        if(dbType == null){
            return DB_MASTER;
        }
        return dbType;
    }

    public static void setDbType(String dbType){
        logger.debug("the selected datasource: " + dbType);

        contextHolder.set(dbType);
    }

    public static void clearDbType(){
       contextHolder.remove();
    }

}
