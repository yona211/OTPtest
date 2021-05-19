package com.example.otptest;

public abstract class Student {
    protected String FirstName;
    protected String LastName;
    protected String ClassName;
    public Student(String FirstName, String LastName, String ClassName) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.ClassName = ClassName;
    }
    public String getFirstName() { return FirstName; }
    public String getLastName() { return LastName; }
    public String getClassName() { return ClassName; }
    public void setFirstName(String firstName) { FirstName = firstName; }
    public void setLastName(String lastName) { LastName = lastName; }
    public void setClassName(String className) { ClassName = className; }
    public abstract String print();
}
