package com.tools.elf.bean;

/**
 * @author bin.zhang
 * @create 2017-12-13 16:25
 **/
public class TableProperty {
    private Integer id;
    private String tableName;
    private String tableComment;
    private String dbName;
    private Integer expiredDays; //申请有效天数
    private Integer fileSeparatorCode; //文件列分隔符: 0表示制表符 , 1表示逗号
    private Integer storageormatCode; //数据文件存储格式(0:text, 1:RCFILE, 2:ORCFILE, 3:LZO)
    private String tableOwner ;//表的用户(拥有者)
    private Integer devProduct; //否开发生产永久表，0否，1是
    private String createSql;
    private boolean disabled;
    private String createTime;
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateSql() {
        return createSql;
    }

    public void setCreateSql(String createSql) {
        this.createSql = createSql;
    }
}
