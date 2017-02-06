package com.example.keywordnews.model;

import java.util.ArrayList;


/**
 * Created by loll_ on 2017-02-06.
 */

// RealmObject는 쿼리된 스레드내에서만 접근할 수 있다.
    // 그 한계를 극복하려면 그 내용을 다른 객채에 백업해야할수도.
public class KeywordArticles {
    public String keyword;
    public int num;
//    public ArrayList<NewsItem> articles;

    public KeywordArticles() {
        super();
    }

    public KeywordArticles(String keyword, int num) {
        this.keyword = keyword;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setArticles(ArrayList<NewsItem> articles){
//        this.articles = articles;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

//    public ArrayList<NewsItem> getArticles() {
//        return articles;
//    }
}
