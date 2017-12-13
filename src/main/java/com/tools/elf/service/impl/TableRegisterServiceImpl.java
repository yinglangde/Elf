package com.tools.elf.service.impl;

import com.tools.elf.bean.*;
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
    public Object registerTable(TableRegisterBean bean) {

        final TableProperty tableProperty = bean.getTableProperty();
        final List<TableColumn> columns = bean.getColumns();
        final List<TablePartition> partitions = bean.getPartitions();
        //TODO 0.check数据是否异常
        if(tableProperty.getTableName() == null || tableProperty.getDbName()==null){
            return ResponseFactory.instance(false,null,"数据异常（1）");
        }
        if(columns.size()==0){
            return ResponseFactory.instance(false,null,"数据异常（2）");
        }

        //TODO 1.创建hive表
        String hiveSql = buildHiveSql(tableProperty,columns,partitions);

        List<Object[]> res = new ArrayList<Object[]>();
        try {
            logger.info("\n excute hiveSql : [ " + hiveSql + " ]");
            //res=hive.execute(hiveSql);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error:{}",e);
            return ResponseFactory.instance(false,null,e.getMessage());
        }

        //TODO 2.创建hive表成功则写 mysql 元数据
        try {
            jdbcTemplate.update("insert into meta_table(db_name,table_name,table_comment,expired_days,valid_date,file_separator_code,storage_format_code,table_owner,create_time,dev_product,create_sql) VALUES (?,?,?,?,DATE_ADD(NOW(),INTERVAL ? day),?,?,?,NOW(),?,?)"
                    ,tableProperty.getDbName(),tableProperty.getTableName(),tableProperty.getTableComment(),tableProperty.getExpiredDays(),tableProperty.getExpiredDays(),tableProperty.getFileSeparatorCode(),tableProperty.getStorageormatCode(),tableProperty.getTableOwner(),tableProperty.getDevProduct(),hiveSql);


            Map map = jdbcTemplate.queryForMap("select id from meta_table where db_name=? and table_name=? limit 1",tableProperty.getDbName(),tableProperty.getTableName());
            final int tableId = Integer.parseInt(map.get("id").toString());

            String sqlColumn = "insert into meta_column(table_id,column_name,column_type,column_comment) values (?,?,?,?)";
            jdbcTemplate.batchUpdate(sqlColumn, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    TableColumn col = columns.get(i);
                    preparedStatement.setInt(1,tableId);
                    preparedStatement.setString(2,col.getColumnName());
                    preparedStatement.setString(3,col.getColumnType());
                    preparedStatement.setString(4,col.getColumnComment());
                }
                @Override
                public int getBatchSize() {
                    return columns.size();
                }
            });

            if(bean.getPartitions()!=null){
                String sqlPartition = "insert into meta_partition(table_id,partition_name,partition_type,partition_comment) values (?,?,?,?)";
                jdbcTemplate.batchUpdate(sqlPartition, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        TablePartition p = partitions.get(i);
                        preparedStatement.setInt(1,tableId);
                        preparedStatement.setString(2,p.getPartitionName());
                        preparedStatement.setString(3,p.getPartitionType());
                        preparedStatement.setString(4,p.getPartitionComment());
                    }
                    @Override
                    public int getBatchSize() {
                        return partitions.size();
                    }
                });
            }

            return ResponseFactory.instance(true,null,"操作成功 ！\n" + hiveSql);
        }catch (Exception e){
            logger.info("error:{}",e);
            return ResponseFactory.instance(false,null,e.getMessage());
        }
    }

    private String buildHiveSql(TableProperty tableProperty,List<TableColumn> columns, List<TablePartition> partitions){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE EXTERNAL TABLE " + tableProperty.getDbName() + "."+ tableProperty.getTableName() + "(\n");

        for (TableColumn item: columns) {
            stringBuilder.append(item.getColumnName() + " " + item.getColumnType() + " COMMENT '" + item.getColumnComment() + "',\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.append(")\n");

        if(partitions.size()!=0){
            stringBuilder.append("PARTITIONED BY(\n");
            for (TablePartition item: partitions) {
                stringBuilder.append(item.getPartitionName() + " " + item.getPartitionType() + ",\n");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
            stringBuilder.append(")");
        }

        //分隔符 0表示制表符 , 1表示逗号
        if(tableProperty.getFileSeparatorCode()==0 && tableProperty.getStorageormatCode()!=3){
            stringBuilder.append("row format delimited\n");
            stringBuilder.append("fields terminated by '\\t'\n");
        }else if(tableProperty.getFileSeparatorCode()==1 && tableProperty.getStorageormatCode()!=3){
            stringBuilder.append("row format delimited\n");
            stringBuilder.append("fields terminated by ','\n");
        }
        //文件格式  0:text, 1:RCFILE, 2:ORCFILE, 3:LZO
        if(tableProperty.getStorageormatCode()==0){
            stringBuilder.append("stored as textfile\n");
        }else if(tableProperty.getStorageormatCode()==1){
            stringBuilder.append("stored as rcfile\n");
        }else if(tableProperty.getStorageormatCode()==2){
            stringBuilder.append("stored as orcfile\n");
        }else if(tableProperty.getStorageormatCode()==3){
            stringBuilder.append("ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'\n");
            stringBuilder.append("stored as INPUTFORMAT 'com.hadoop.mapred.DeprecatedLzoTextInputFormat'\n");
            stringBuilder.append("OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'\n");
        }
        return stringBuilder.toString();
    }


}
