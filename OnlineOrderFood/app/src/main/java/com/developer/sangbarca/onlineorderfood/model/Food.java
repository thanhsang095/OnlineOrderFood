package com.developer.sangbarca.onlineorderfood.model;

import java.io.Serializable;

/**
 * Created by SANG BARCA on 5/8/2017.
 */

public class Food implements Serializable {
    private int id;
    private String food_Name;
    private String kind;
    private float price;
    private int accumulation;
    String image;

    public Food() {
    }

    public Food(int id, String food_Name, String kind, float price, int accumulation, String image) {
        this.id = id;
        this.food_Name = food_Name;
        this.kind = kind;
        this.price = price;
        this.accumulation = accumulation;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood_Name() {
        return food_Name;
    }

    public void setFood_Name(String food_Name) {
        this.food_Name = food_Name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAccumulation() {
        return accumulation;
    }

    public void setAccumulation(int accumulation) {
        this.accumulation = accumulation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
