package com.developer.sangbarca.onlineorderfood.model;

import java.io.Serializable;

/**
 * Created by SANG BARCA on 5/9/2017.
 */

public class Table implements Serializable {
    private int id;
    private String tableName;
    private int floor;
    private int status;// 0: còn trống, 1: đang chờ, 2: đang sử dụng

    public Table() {
    }

    public Table(int id, String tableName, int floor, int status) {
        this.id = id;
        this.tableName = tableName;
        this.floor = floor;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
