package com.example.keywordnews;


/**
 * Created by 밈석 on 2017-01-23.
 */
public class RecyItem {
    private String mText, mNewsTitle, mNewsSubTitle, mNewsCor;

    public RecyItem(String text){
        mText = text;
    }
    public RecyItem(String text, String title, String subtitle, String cor) {
        mText = text;
        mNewsTitle = title;
        mNewsSubTitle = subtitle;
        mNewsCor = cor;
    }

    String getText(){
        return mText;
    }
    String getTitle() {return mNewsTitle;}
    String getSubTitle() {return mNewsSubTitle;}
    String getCor() {return mNewsCor;}

    void setText(String text){
        mText = text;
    }

}
