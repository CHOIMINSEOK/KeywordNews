package com.example.keywordnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.keywordnews.model.Category;
import com.example.keywordnews.model.NewsItem;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by loll_ on 2017-02-16.
 */


//TODO : floating 버튼 -> String을 ListFragment에 전달하여 Keyword추가되도록
//TODO : 스플래쉬뷰 -> 심플하고 직관적인 로딩화면
public class HomeActivity extends AppCompatActivity {
    private MaterialViewPager mViewPager;
    private ListFragmentPagerAdapter mAdapter;
    private ArrayList<Category> categories = new ArrayList<>();
    private int currentPosition = 0;

    private RelativeLayout addKeywordPage;
    private KeywordEditText keywordEditText;
    private boolean isAddPageOpened = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startActivity(new Intent(this, Splash.class));

        Realm.init(getApplicationContext());
        new LoadNewsDataTask().execute();

        DesignManager.setContext(getApplicationContext());

        initCategorys();
        addKeywordPage = (RelativeLayout) findViewById(R.id.add_keyword_page);
        keywordEditText = (KeywordEditText) findViewById(R.id.add_keyword_edittext);
        keywordEditText.setAddManager(new KeywordEditText.AddManager() {
            @Override
            public void AddKeywordToCurrentPage(String keyword) {
                new GetKeywordArticleTask().execute(keyword);
                mAdapter.notifyDataSetChanged();
                keywordEditText.getText().clear();
                addKeywordPage.setVisibility(View.GONE);
                isAddPageOpened = false;
            }
        });

        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        mAdapter = new ListFragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public CharSequence getPageTitle(int position) {
                return categories.get(position).getCategory();
            }
        };
        mViewPager.getViewPager().setAdapter(mAdapter);

        mViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("MIM", "currentPosition : " + currentPosition);
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                return HeaderDesign.fromColorResAndDrawable(categories.get(page).getColor(), categories.get(page).getImage()) ;
            }

        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Title clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAddPageOpened) {
                    keywordEditText.getText().clear();
                    addKeywordPage.setVisibility(View.GONE);
                    isAddPageOpened = false;
                }
                else {
                    keywordEditText.requestFocus();
                    addKeywordPage.setVisibility(View.VISIBLE);
                    isAddPageOpened = true;
                }
                /*mAdapter.getFragment(currentPosition).addKeyword("트럼프", 33);
                Toast.makeText(getApplicationContext(), "Floating button clicked", Toast.LENGTH_LONG).show();*/
            }
        });
    }

    void initCategorys(){
        categories.add(new Category("국제", R.color.green, getResources().getDrawable(R.drawable.trump)));
        categories.add(new Category("정치", R.color.blue , getResources().getDrawable(R.drawable.lee)));
    }

    private class GetKeywordArticleTask extends AsyncTask<String, Void, RealmResults<NewsItem>> {
        @Override
        protected RealmResults<NewsItem> doInBackground(String... params) {
            RealmResults<NewsItem> items;
            // 백그라운드로 구현할 것들
            try {
                items =  Realm.getDefaultInstance().where(NewsItem.class).contains("title", params[0]).findAll().where().contains("category", categories.get(currentPosition).getCategory()).findAll();
                mAdapter.getFragment(currentPosition).addKeyword(params[0], items.size());
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

}
