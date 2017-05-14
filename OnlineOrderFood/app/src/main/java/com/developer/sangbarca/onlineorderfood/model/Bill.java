package com.developer.sangbarca.onlineorderfood.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SANG BARCA on 5/8/2017.
 */

public class Bill implements Serializable {
    private int id;
    private int status; // 0: chưa thanh toán, 1: đã thanh toán
    private String date;
    private ArrayList<String> listFood;
    private ArrayList<Integer> arrNumber;// số lượng từng món ăn
    private float total_Money;
    private int id_table;
    private String username;

    public Bill() {
    }

    public Bill(int id, int status, String date, ArrayList<String> listFood, ArrayList<Integer> arrNumber, float total_Money, int id_table, String username) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.listFood = listFood;
        this.arrNumber = arrNumber;
        this.total_Money = total_Money;
        this.id_table = id_table;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getListFood() {
        return listFood;
    }

    public void setListFood(ArrayList<String> listFood) {
        this.listFood = listFood;
    }

    public ArrayList<Integer> getArrNumber() {
        return arrNumber;
    }

    public void setArrNumber(ArrayList<Integer> arrNumber) {
        this.arrNumber = arrNumber;
    }

    public float getTotal_Money() {
        return total_Money;
    }

    public void setTotal_Money(float total_Money) {
        this.total_Money = total_Money;
    }

    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
