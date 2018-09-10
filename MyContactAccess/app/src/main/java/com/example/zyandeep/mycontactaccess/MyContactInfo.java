package com.example.zyandeep.mycontactaccess;

public class MyContactInfo {
    private String name;
    private String phoneNo;

    public MyContactInfo() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "MyContactInfo{" +
                "name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", type='" + '\'' +
                '}';
    }
}