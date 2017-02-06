package com.example.keywordnews;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import io.realm.Realm;

public class TestActivity extends AppCompatActivity {
    private EditText mKeyWord;
    private Button mSearchButton;

    private RecyclerView mRecyclerView;
    private RecyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestManager mImageRequestManager;

    private ArrayList<NewsItem> keyNewsset;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyAdapter(keyNewsset, mImageRequestManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //TODO: 각각의 뉴스아이템들이 클릭됬을 때
            }
        });

        String keyword = getIntent().getStringExtra("Key");
        Log.d("MIM", keyword);

        keyNewsset = new ArrayList<>(Realm.getDefaultInstance().where(NewsItem.class).contains("title", keyword).findAll());
        mAdapter.notifyDataSetChanged();
        mAdapter.updateDataset(keyNewsset);


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
//                loadDB(); break;
            case R.id.minus :
//                clearDB(); break;
        }
        return true;
    }
/*  //NOTE : RealmObject로의 접근은 Query를 만든 스레드에서만 이루어져야한다!
    private class LoadNewsDataTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {
        @Override
        protected ArrayList<NewsItem> doInBackground(String... params) {
            ArrayList<NewsItem> items = new ArrayList<>(Realm.getDefaultInstance().where(NewsItem.class).contains("title", params[0]).findAll());
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> NewsItems) {
            keyNewsset = NewsItems;
            mAdapter.notifyDataSetChanged();
            mAdapter.updateDataset(NewsItems);
        }
    }*/



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

}



