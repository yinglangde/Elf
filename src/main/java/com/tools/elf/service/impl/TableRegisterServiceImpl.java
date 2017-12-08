package com.tools.elf.service.impl;

import com.tools.elf.bean.TableRequestBody;
import com.tools.elf.service.TableRegisterService;
import com.tools.elf.util.HiveServer2;
import com.tools.elf.util.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bin.zhang
 * @create 2017-12-07 15:28
 **/
@Service
public class TableRegisterServiceImpl implements TableRegisterService{
    private Logger logger = LoggerFactory.getLogger(TableRegisterServiceImpl.class);

    private HiveServer2 hive;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Object createTable(final TableRequestBody bean) {
/*
        //TODO 1.创建hive表
        String hiveSql = buildHiveSql(bean);
        List<Object[]> res = new ArrayList<Object[]>();
        try {
            res=hive.execute(hiveSql);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error:{}",e);
            return ResponseFactory.instance(false,null,e.getMessage());
        }*/

        //TODO 2.创建hive表成功则写 mysql 元数据
        try {
            jdbcTemplate.update("insert into meta_table(db_name,table_name,table_comment,expired_days,valid_date,file_separator_code,storage_format_code,table_owner,create_time,dev_product) VALUES (?,?,?,?,DATE_ADD(NOW(),INTERVAL ? day),?,?,?,NOW(),?)"
                    ,bean.getDbName(),bean.getTableName(),bean.getTableComment(),bean.getExpiredDays(),bean.getExpiredDays(),bean.getFileSeparatorCode(),bean.getStorageormatCode(),bean.getTableOwner(),bean.getDevProduct());


            Map map = jdbcTemplate.queryForMap("select id from meta_table where db_name=? and table_name=? limit 1",bean.getDbName(),bean.getTableName());
            final int tableId = Integer.parseInt(map.get("id").toString());

            String sqlColumn = "insert into meta_column(table_id,column_name,column_type,column_comment) values (?,?,?,?)";
            jdbcTemplate.batchUpdate(sqlColumn, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    Map<String,String> map = bean.getColnums().get(i);
                    preparedStatement.setInt(1,tableId);
                    preparedStatement.setString(2,map.get("columnName"));
                    preparedStatement.setString(3,map.get("columnType"));
                    preparedStatement.setString(4,map.get("columnComment"));
                }

                @Override
                public int getBatchSize() {
                    return bean.getColnums().size();
                }
            });

            if(bean.getPartitions()!=null){
                String sqlPartition = "insert into meta_partition(table_id,partition_name,partition_type,partition_comment) values (?,?,?,?)";
                jdbcTemplate.batchUpdate(sqlPartition, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        Map<String,String> map = bean.getPartitions().get(i);
                        preparedStatement.setInt(1,tableId);
                        preparedStatement.setString(2,map.get("partitionName"));
                        preparedStatement.setString(3,map.get("partitionType"));
                        preparedStatement.setString(4,map.get("partitionComment"));
                    }

                    @Override
                    public int getBatchSize() {
                        return bean.getPartitions().size();
                    }
                });
            }


            return ResponseFactory.instance(true,null,"操作成功 ！");
        }catch (Exception e){
            logger.info("error:{}",e);
            return ResponseFactory.instance(false,null,e.getMessage());
        }

    }

    private String buildHiveSql(TableRequestBody bean){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE EXTERNAL TABLE " + bean.getDbName() + "."+ bean.getTableName() + "(\n");

        List<Map<String,String>> list= bean.getColnums();
        for (Map<String,String> item: list) {
            stringBuilder.append(item.get("columnName") + " " + item.get("columnType") + " COMMENT '" + item.get("columnComment") + "',\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.append(")\n");

        List<Map<String,String>> partition= bean.getPartitions();
        if(partition.size()!=0){
            stringBuilder.append("PARTITIONED BY(\n");
            for (Map<String,String> item: partition) {
                stringBuilder.append(item.get("partitionName") + " " + item.get("partitionType") + ",\n");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
            stringBuilder.append(")");
        }

        //分隔符 0表示制表符 , 1表示逗号
        if(bean.getFileSeparatorCode()==0 && bean.getStorageormatCode()!=3){
            stringBuilder.append("row format delimited\n");
            stringBuilder.append("fields terminated by '\\t'\n");
        }else if(bean.getFileSeparatorCode()==1 && bean.getStorageormatCode()!=3){
            stringBuilder.append("row format delimited\n");
            stringBuilder.append("fields terminated by ','\n");
        }
        //文件格式  0:text, 1:RCFILE, 2:ORCFILE, 3:LZO
        if(bean.getStorageormatCode()==0){
            stringBuilder.append("stored as textfile\n");
        }else if(bean.getStorageormatCode()==1){
            stringBuilder.append("stored as rcfile\n");
        }else if(bean.getStorageormatCode()==2){
            stringBuilder.append("stored as orcfile\n");
        }else if(bean.getStorageormatCode()==3){
            stringBuilder.append("ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'\n");
            stringBuilder.append("stored as INPUTFORMAT 'com.hadoop.mapred.DeprecatedLzoTextInputFormat'\n");
            stringBuilder.append("OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'\n");
        }
        return stringBuilder.toString();
    }


}
