package com.example.keywordnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keywordnews.model.KeywordArticles;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {
    private static final int ITEM_COUNT = 5;
    private RecyclerView mRecyclerView;
    private CustomRecyAdapter mAdapter;
    private ArrayList<KeywordArticles> mContentItems = new ArrayList<>();

    public ListFragment() {
        super();
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.keyword_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        mAdapter = new CustomRecyAdapter(mContentItems);
        mAdapter.setOnItemClickListener(new CustomRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), TestActivity.class);
                intent.putExtra("Key", mContentItems.get(position).getKeyword());
                startActivity(intent);
            }
        });

        //mAdapter = new RecyclerViewMaterialAdapter();
        mRecyclerView.setAdapter(mAdapter);


        {
            for (int i = 0; i < ITEM_COUNT; ++i) {
                mContentItems.add(new KeywordArticles("트럼프", 33));
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    public void addKeyword(String keyword, int num){
        mContentItems.add(new KeywordArticles(keyword, num));
        mAdapter.notifyDataSetChanged();
    }


    public void setOnItemClickListener(CustomRecyAdapter.OnItemClickListener onItemClickListener){
        mAdapter.setOnItemClickListener(onItemClickListener);
    }
}

