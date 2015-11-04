package com.example.guest.sunlightcongress;

public class Legislator {
    private String mFirstName;
    private String mLastName;

    public Legislator(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
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

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

}
