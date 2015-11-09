package com.example.guest.sunlightcongress;

public class Legislator {
    private String mFirstName;
    private String mLastName;
    private String mPhone;
    private String mOffice;
    private String mState;

    public Legislator(String firstName, String lastName, String phone, String office, String state) {
        mFirstName = firstName;
        mLastName = lastName;
        mPhone = phone;
        mOffice = office;
        mState = state;
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

    public String getOffice() {
        return mOffice;
    }

    public void setOffice(String office) {
        mOffice = office;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

}
