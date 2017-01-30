package com.example.keywordnews.model;

import io.realm.RealmObject;
/**
 * Created by 밈석 on 2017-01-30.
 */
public class NewsItem extends RealmObject {
    String title;
    String subtitle;
    String imageurl;

    public NewsItem() {
    }

    public NewsItem(String title, String subtitle, String imageurl) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageurl = imageurl;
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
