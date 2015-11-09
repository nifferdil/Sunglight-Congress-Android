package com.example.guest.sunlightcongress;

public class Legislator {
    private String mFirstName;
    private String mLastName;
    private String mPhone;

    public Legislator(String firstName, String lastName, String phone) {
        mFirstName = firstName;
        mLastName = lastName;
        mPhone = phone;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

}
