package com.example.keywordnews;

import android.os.AsyncTask;
import android.util.Log;
import com.example.keywordnews.model.NewsItem;
import java.util.ArrayList;
import io.realm.Realm;

public class LoadNewsDataTask extends AsyncTask<Void, Void, ArrayList<NewsItem>> {
        @Override
        protected ArrayList<NewsItem> doInBackground(Void... params) {
            ArrayList<NewsItem> NewsItems = new ArrayList<>();
            // 백그라운드로 구현할 것들
            try {
                RSSReader reader = RSSReader.getInstance();
                return reader.LoadNewsData(NewsItems);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //TODO : NewsItems 리턴하여 post에서 DB에 넣기

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> NewsItems) {
            Log.d("MiM", Integer.toString(NewsItems.size()));
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.insertOrUpdate(NewsItems);
            realm.commitTransaction();
        }
    }