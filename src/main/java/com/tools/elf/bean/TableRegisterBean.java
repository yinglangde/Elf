package com.tools.elf.bean;

import java.util.List;

/**
 * @author bin.zhang
 * @create 2017-12-13 16:45
 **/
public class TableRegisterBean {
    private TableProperty tableProperty;
    private List<TableColumn> columns;
    private List<TablePartition> partitions;

    public TableProperty getTableProperty() {
        return tableProperty;
    }

    public void setTableProperty(TableProperty tableProperty) {
        this.tableProperty = tableProperty;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }

    public List<TablePartition> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<TablePartition> partitions) {
        this.partitions = partitions;
    }
}
