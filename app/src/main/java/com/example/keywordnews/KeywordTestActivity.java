package com.example.keywordnews;

import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.keywordnews.model.KeywordArticles;
import com.example.keywordnews.model.NewsItem;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class KeywordTestActivity extends AppCompatActivity {
    private ArrayList<KeywordArticles> mKeywords;

    private EditText mKeyWord;
    private Button mSearchButton;

    private RecyclerView mRecyclerView;
    private CustomRecyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(getApplicationContext());

        mKeywords = new ArrayList<>();
        mKeyWord = (EditText)findViewById(R.id.search_key);
        mSearchButton = (Button)findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: 검색버튼 눌렸을때 --> DB 로드 다되고 검색되도록
                String keyword = mKeyWord.getText().toString();
                Toast.makeText(getApplicationContext(), keyword,Toast.LENGTH_SHORT).show();
                new GetKeywordArticleTask().execute(keyword);
                mKeyWord.getText().clear();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new CustomRecyclerDecoration(20));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CustomRecyAdapter(mKeywords);
        mAdapter.setOnItemClickListener(new CustomRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                intent.putExtra("Key",mKeywords.get(position).getKeyword());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        LoadNewsDataTask loadNewsDataTask = new LoadNewsDataTask();
        loadNewsDataTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //TODO : 옵션
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.plus :
                loadDB(); break;
            case R.id.minus :
                clearDB(); break;
        }
        return true;
    }


    //TODO : 키워드뉴스 모으기!
    private class GetKeywordArticleTask extends AsyncTask<String, Void, RealmResults<NewsItem>> {
        @Override
        protected RealmResults<NewsItem> doInBackground(String... params) {
            RealmResults<NewsItem> items;
            // 백그라운드로 구현할 것들
            try {
                items =  Realm.getDefaultInstance().where(NewsItem.class).contains("title", params[0]).findAll();
                mKeywords.add(new KeywordArticles(params[0], items.size()));
                mAdapter.updateDataset();
                return items;

            } catch (Exception e) {
                Log.d("MIM",e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(RealmResults<NewsItem> NewsItems) {
            //TODO: keywordarticle 처리
            mAdapter.notifyDataSetChanged();
        }
    }


    private void loadDB() {
        mAdapter.notifyDataSetChanged();
    }

    private void clearDB() {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<NewsItem> results = realm.where(NewsItem.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
                loadDB();
            }
        });

    }


}



