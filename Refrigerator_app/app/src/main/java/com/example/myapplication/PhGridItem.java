package com.example.myapplication;

public class PhGridItem {

    public PhGridItem(int a_imageResId, String a_strName, String a_date, int a_dday) {
        mImageResId = a_imageResId;
        mStrName = a_strName;
        mDate = a_date;
        mDday = a_dday;
    }

    private int mImageResId;

    private String mStrName;

    private int mDday;

    private String mDate;

    public int getImageResId() {
        return mImageResId;
    }

    public void setName(String a_strName) {
        mStrName = a_strName;
    }

    public String getDate() {
        return mDate;
    }

    public String getName() {
        return mStrName;
    }



    public void setDate(int a_dday) {
        mDday = a_dday;
    }

    public int getDday() {
        return mDday;
    }
}
