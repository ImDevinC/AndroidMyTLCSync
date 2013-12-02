package com.layer8apps;

import java.util.Calendar;

public class Shift {

    private String title;
    private String department;
    private String address;
    private Calendar startDate;
    private Calendar endDate;

    public Shift() {}

    public Shift(String title, String department, String address, Calendar startDate, Calendar endDate) {
        this.title = title;
        this.department = department;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    private void setDepartment(String department) {
        this.department = department;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    private void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    private void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

}
