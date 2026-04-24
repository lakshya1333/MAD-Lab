package com.example.lab8_q1;

public class Task {
    int id;
    String name, date, priority;

    public Task(int id, String name, String date, String priority) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public Task(String name, String date, String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }
}
