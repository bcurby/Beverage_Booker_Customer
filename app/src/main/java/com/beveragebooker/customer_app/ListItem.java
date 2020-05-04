package com.beveragebooker.customer_app;

public class ListItem {

    private int mImageResource;
    private String mText1;
    private String mTExt2;


    public ListItem(int imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mTExt2 = text2;
    }


    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mTExt2;
    }
    public String getText3() {
        return mTExt2;
    }
}
