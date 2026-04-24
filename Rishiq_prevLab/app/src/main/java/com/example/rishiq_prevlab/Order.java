package com.example.rishiq_prevlab;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private String date;
    private String item1;
    private String item2;
    private int cost1;
    private int cost2;
    private int qty1;
    private int qty2;
    private int total;
    private boolean isSelected;

    public Order(int id, String date, String item1, String item2, int cost1, int cost2, int qty1, int qty2, int total) {
        this.id = id;
        this.date = date;
        this.item1 = item1;
        this.item2 = item2;
        this.cost1 = cost1;
        this.cost2 = cost2;
        this.qty1 = qty1;
        this.qty2 = qty2;
        this.total = total;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getItem1() { return item1; }
    public String getItem2() { return item2; }
    public int getCost1() { return cost1; }
    public int getCost2() { return cost2; }
    public int getQty1() { return qty1; }
    public int getQty2() { return qty2; }
    public int getTotal() { return total; }
    public boolean isSelected() { return isSelected; }

    public void setQty1(int qty1) { this.qty1 = qty1; }
    public void setQty2(int qty2) { this.qty2 = qty2; }
    public void setTotal(int total) { this.total = total; }
    public void setSelected(boolean selected) { isSelected = selected; }
}
