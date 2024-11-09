// Expense.java
package com.example.triptracker;

public class Expense {
    private String id;
    private String name;
    private double cost;
    private String category;
    private int day, month, year;

    // Default constructor required for calls to DataSnapshot.getValue(Expense.class)
    public Expense() {}

    public Expense(String name, double cost, String category, int day, int month, int year) {
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
