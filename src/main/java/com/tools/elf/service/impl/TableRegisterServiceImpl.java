package com.tools.elf.service.impl;

import com.tools.elf.bean.TableRequestBody;
import com.tools.elf.service.TableRegisterService;
import com.tools.elf.util.HiveServer2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
    public Object createTable(TableRequestBody tableRequestBody) {
        String hiveSql = buildHiveSql(tableRequestBody);
        return hive.execute(hiveSql);
    }

    private String buildHiveSql(TableRequestBody tableRequestBody){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE EXTERNAL TABLE " + tableRequestBody.getDbName() + "."+ tableRequestBody.getTableName() + "(\n");

        List<Map<String,String>> list= tableRequestBody.getColnums();
        for (Map<String,String> item: list) {
            stringBuilder.append(item.get("columnName") + " " + item.get("columnType") + " COMMENT '" + item.get("columnComment") + "',\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.append(")\n");

        List<Map<String,String>> partition= tableRequestBody.getPartitions();
        if(partition.size()!=0){
            stringBuilder.append("PARTITIONED BY(\n");
            for (Map<String,String> item: partition) {
                stringBuilder.append(item.get("partitionName") + " " + item.get("partitionType") + ",\n");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
            stringBuilder.append(")");
        }

        //分隔符 0表示制表符 , 1表示逗号
        if(tableRequestBody.getFileSeparatorCode()==0 && tableRequestBody.getStorageormatCode()!=3){
            stringBuilder.append("row format delimited\n");
            stringBuilder.append("fields terminated by '\\t'\n");
        }else if(tableRequestBody.getFileSeparatorCode()==1 && tableRequestBody.getStorageormatCode()!=3){
            stringBuilder.append("row format delimited\n");
            stringBuilder.append("fields terminated by ','\n");
        }
        //文件格式  0:text, 1:RCFILE, 2:ORCFILE, 3:LZO
        if(tableRequestBody.getStorageormatCode()==0){
            stringBuilder.append("stored as textfile\n");
        }else if(tableRequestBody.getStorageormatCode()==1){
            stringBuilder.append("stored as rcfile\n");
        }else if(tableRequestBody.getStorageormatCode()==2){
            stringBuilder.append("stored as orcfile\n");
        }else if(tableRequestBody.getStorageormatCode()==3){
            stringBuilder.append("ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'\n");
            stringBuilder.append("stored as INPUTFORMAT 'com.hadoop.mapred.DeprecatedLzoTextInputFormat'\n");
            stringBuilder.append("OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'\n");
        }
        return stringBuilder.toString();
    }


}
