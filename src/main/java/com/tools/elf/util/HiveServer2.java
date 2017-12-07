package com.tools.elf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bin.zhang
 * @create 2017-12-04 11:06
 **/
public class HiveServer2 {
    private static final String DIRVERNAME = "org.apache.hive.jdbc.HiveDriver";
    private static final String URL = "jdbc:hive2://10.6.0.69:10000/default";
    private static final String USERNAME = "primary_user";
    private static final String PASSWORD = "";

    private Logger logger = LoggerFactory.getLogger(HiveServer2.class);

    private Connection getHiveConnection() {
        Connection connection = null;
        try {
            Class.forName(DIRVERNAME);
        } catch (ClassNotFoundException e) {
            logger.error("class not found:{}", e);
        }
        //跟具用户所在组 设置USERNAME
        //String userName = getUserName();
        String userName = USERNAME;
        logger.debug("userName:{}", userName);
        try {
            connection = DriverManager.getConnection(URL, userName, PASSWORD);
        } catch (SQLException e) {
            logger.warn("get hive connection failed:{}", e);
        }
        return connection;
    }

    public List<Object[]> execute(String sql)  {
        long startMS = System.currentTimeMillis();
        List<Object[]> data = new ArrayList<Object[]>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try{
            connection = getHiveConnection();
            statement = connection.createStatement();

            if (!sql.trim().startsWith("SELECT")) {
                statement.execute(sql);
            }else {
                rs = statement.executeQuery(sql);
                if (rs != null) {
                    while (rs.next()) {
                        Object[] row = new Object[rs.getMetaData().getColumnCount()];
                        for (int i = 0; i < row.length; i++) {
                            if (rs.getObject(i + 1) == null) {
                                row[i] = "";
                            } else {
                                row[i] = rs.getObject(i + 1);
                            }
                        }
                        data.add(row);
                    }
                }
            }

            logger.info("[<<<--HIVE-->>>] StartTime: " + startMS );
            logger.info("[<<<--HIVE-->>>] HIVE SQL: [" + sql + "]");
            logger.info("[<<<--HIVE-->>>] EndTime : " + System.currentTimeMillis());
            logger.info("[<<<--HIVE-->>>] ExecuteTime : " + (System.currentTimeMillis() - startMS));

        }catch (Exception e){
            logger.info("[<<<--HIVE-->>>error] [" + (System.currentTimeMillis() - startMS) + "] [" + sql + "]");
            logger.error("error:", e);
            return data;
        } finally {
            close(connection, statement, rs);
        }
        return data;
    }

    private void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.warn("rs close failed:", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.warn("pre close failed:", e);
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    logger.warn("connection close failed:", e);
                }
            }
        }

    }
}
