package com.tools.elf.bean;

import java.util.List;
import java.util.Map;

/**
 * @author bin.zhang
 * @create 2017-12-07 15:55
 **/
public class TableRequestBody {
    private String tableName;
    private String tableComment;
    private String dbName;
    private List<Map<String,String>> colnums;
    private List<Map<String,String>> partitions;
    private Integer expiredDays; //申请有效天数
    private Integer fileSeparatorCode; //文件列分隔符: 0表示制表符 , 1表示逗号
    private Integer storageormatCode; //数据文件存储格式(0:text, 1:RCFILE, 2:ORCFILE, 3:LZO)
    private String tableOwner ;//表的用户(拥有者)
    private Integer devProduct; //否开发生产永久表，0否，1是

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<Map<String, String>> getColnums() {
        return colnums;
    }

    public void setColnums(List<Map<String, String>> colnums) {
        this.colnums = colnums;
    }

    public List<Map<String, String>> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<Map<String, String>> partitions) {
        this.partitions = partitions;
    }

    public Integer getExpiredDays() {
        return expiredDays;
    }

    public void setExpiredDays(Integer expiredDays) {
        this.expiredDays = expiredDays;
    }

    public Integer getFileSeparatorCode() {
        return fileSeparatorCode;
    }

    public void setFileSeparatorCode(Integer fileSeparatorCode) {
        this.fileSeparatorCode = fileSeparatorCode;
    }

    public Integer getStorageormatCode() {
        return storageormatCode;
    }

    public void setStorageormatCode(Integer storageormatCode) {
        this.storageormatCode = storageormatCode;
    }

    public String getTableOwner() {
        return tableOwner;
    }

    public void setTableOwner(String tableOwner) {
        this.tableOwner = tableOwner;
    }

    public Integer getDevProduct() {
        return devProduct;
    }

    public void setDevProduct(Integer devProduct) {
        this.devProduct = devProduct;
    }
}