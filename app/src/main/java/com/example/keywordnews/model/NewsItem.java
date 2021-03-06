package com.example.keywordnews.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 밈석 on 2017-01-30.
 */
public class NewsItem extends RealmObject {
    @PrimaryKey
    String title;
    String link;
    String crp; // 언론사이름
    String category; //TODO : 관계형으로 참조할 수 있도록 DB구조 개선??
    long pubDate;

    public NewsItem() {
    }


    public NewsItem(String crp, String category, String title, String link, long pubDate) {
        this.crp = crp;
        this.category = category;
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
    }


    public String getCrp() {
        return crp;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }


    public String getLink() {
        return link;
    }

    public long getPubDate() {
        return pubDate;
    }

}
