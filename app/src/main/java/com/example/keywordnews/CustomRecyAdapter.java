package com.example.keywordnews;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.keywordnews.model.KeywordArticles;

import java.util.ArrayList;

public class CustomRecyAdapter extends RecyclerView.Adapter<CustomRecyAdapter.ViewHolder>  {
    static OnItemClickListener mOnItemClickListener;
    public ArrayList<KeywordArticles> mKeywords;



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mKeywordView;
        public TextView mArticleNum;
        public CardView mCardView;
        public ViewHolder(View v) {
            super(v);
            mKeywordView = (TextView)v.findViewById(R.id.keyword);
            mArticleNum = (TextView)v.findViewById(R.id.num);
            mCardView = (CardView)v.findViewById(R.id.cardview);
            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(view, getLayoutPosition());
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public CustomRecyAdapter(ArrayList<KeywordArticles> mKeywords) {
        this.mKeywords = mKeywords;
    }

    @Override
    public CustomRecyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_keyword, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        KeywordArticles article = mKeywords.get(position);
        holder.mKeywordView.setText(article.getKeyword());
        holder.mArticleNum.setText("( "+Integer.toString(article.getNum())+" )");
    }
    @Override
    public int getItemCount() {
        return mKeywords.size();
    }

    public void updateDataset(){
        notifyDataSetChanged();
    }



}