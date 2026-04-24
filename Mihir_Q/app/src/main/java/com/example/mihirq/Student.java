package com.example.mihirq;

public class Student {
    private int id;
    private String name;
    private int subject1;
    private int subject2;
    private int subject3;

    public Student(int id, String name, int subject1, int subject2, int subject3) {
        this.id = id;
        this.name = name;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.subject3 = subject3;
    }

    public Student(String name, int subject1, int subject2, int subject3) {
        this.name = name;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.subject3 = subject3;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getSubject1() { return subject1; }
    public int getSubject2() { return subject2; }
    public int getSubject3() { return subject3; }
}
