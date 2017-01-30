package com.example.keywordnews;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.keywordnews.model.NewsItem;

import java.util.ArrayList;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmResults;


public class TestActivity extends AppCompatActivity {
    private EditText mKeyWord;
    private Button mSearchButton;

    private RecyclerView mRecyclerView;
    private RecyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestManager mImageRequestManager;

    private RealmResults<NewsItem> myNewsset;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(getApplicationContext());
        mImageRequestManager = Glide.with(this);
        mKeyWord = (EditText)findViewById(R.id.search_key);
        mSearchButton = (Button)findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: 검색버튼 눌렸을때
                Toast.makeText(getApplicationContext(), mKeyWord.getText(),Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new CustomRecyclerDecoration(20));

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyAdapter(myNewsset, mImageRequestManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //TODO: 각각의 뉴스아이템들이 클릭됬을 때
            }
        });

        LoadNewsDataTask loadNewsDataTask = new LoadNewsDataTask();
        loadNewsDataTask.execute();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
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

    //TODO : RSS로 뉴스정보 불러오기
    private class LoadNewsDataTask extends AsyncTask<Void, Void, ArrayList<NewsItem>> {
        @Override
        protected ArrayList<NewsItem> doInBackground(Void... params) {
            ArrayList<NewsItem> NewsItems = new ArrayList<>();
            // 백그라운드로 구현할 것들
            try {
                RSSReader reader = RSSReader.getInstance();
                reader.setURL(new URL("http://www.chosun.com/site/data/rss/rss.xml")); //이미지 있는 rss 주소
                return reader.LoadNewsData(NewsItems);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //TODO : NewsItems 리턴하여 post에서 DB에 넣기

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> NewsItems) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.insert(NewsItems);
            realm.commitTransaction();

            myNewsset = realm.where(NewsItem.class).findAll();
            mAdapter.updateDataset(myNewsset);
//            mAdapter.notifyDataSetChanged();
        }
    }

    public class CustomRecyclerDecoration extends RecyclerView.ItemDecoration {
        private final int divHeight;

        public CustomRecyclerDecoration(int divHeight) {
            this.divHeight = divHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

        outRect.top = divHeight;

        }
    }
    private void loadDB() {
        myNewsset = Realm.getDefaultInstance().where(NewsItem.class).findAll();
        Toast.makeText(this, String.valueOf(myNewsset != null ? myNewsset.size() : 0), Toast.LENGTH_SHORT).show();
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



