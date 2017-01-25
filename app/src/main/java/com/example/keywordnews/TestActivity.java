package com.example.keywordnews;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class TestActivity extends AppCompatActivity {
    private EditText mKeyWord;
    private Button mSearchButton;

    private RecyclerView mRecyclerView;
    private RecyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<RecyItem> myDataset;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        myDataset = new ArrayList<>();
        mAdapter = new RecyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getApplicationContext(), myDataset.get(position).getText(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.plus :
                negativeNumberTask(1); break;
            case R.id.minus :
                negativeNumberTask(0); break;
        }
        return true;
    }


    private class NegativeItemNumberTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int i = myDataset.size();
            for(int j=0; j<i; j++)
                myDataset.get(j).setText(Integer.toString((params[0] > 0 ? j : -j)));

            publishProgress();
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            mAdapter.updateDataset(myDataset);
        }
    }
    void negativeNumberTask(int i){
        new NegativeItemNumberTask().execute(i);
    }


    //TODO : RSS로 뉴스정보 불러오기
    private class LoadNewsDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // 백그라운드로 구현할 것들
            for(int i=0; i<100; i++)
                myDataset.add(new RecyItem(Integer.toString(i)));
            publishProgress();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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

}
