package com.ivansaprykin.dblabs.model;

import java.util.ArrayList;
import java.util.List;


public class DatabaseTable {

    private String tableName;
    private List<String> columns;

    public DatabaseTable() {
        columns = new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
}
