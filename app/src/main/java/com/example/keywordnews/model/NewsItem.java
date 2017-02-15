package com.example.keywordnews.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 밈석 on 2017-01-30.
 */
public class NewsItem extends RealmObject {
    String crp; // 언론사이름
//    String category; TODO : 관계형으로 참조할 수 있도록 DB구조 개선
//    String pubDate; TODO : 어떻게 int로 바꿔야하나
    @PrimaryKey
    String title;
    String subtitle;
    String imageurl;

    public NewsItem() {
    }


    public NewsItem(String crp, String title, String subtitle, String imageurl) {
        this.crp = crp;
        this.title = title;
        this.subtitle = subtitle;
        this.imageurl = imageurl;
    }

    public void setCrp(String crp) {
        this.crp = crp;
    }

    public String getCrp() {
        return crp;
    }

    public void setmTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }
}
