package com.example.keywordnews;


/**
 * Created by 밈석 on 2017-01-23.
 */
public class RecyItem {
    private String mText, mNewsTitle, mNewsSubTitle, mNewsCor, link;

    public RecyItem(String text){
        mText = text;
    }
    public RecyItem(String text, String title, String subtitle, String cor,String _link) {
        mText = text;
        mNewsTitle = title;
        mNewsSubTitle = subtitle;
        mNewsCor = cor;
        link = _link;
    }

    String getText(){
        return mText;
    }
    String getTitle() {return mNewsTitle;}
    String getSubTitle() {return mNewsSubTitle;}
    String getCor() {return mNewsCor;}
    String getLink() {return link;}

    void setText(String text){
        mText = text;
    }

}
