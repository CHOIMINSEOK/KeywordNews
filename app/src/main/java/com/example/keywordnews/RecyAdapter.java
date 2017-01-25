package com.example.keywordnews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 밈석 on 2017-01-23.
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder>  {

    private ArrayList<RecyItem> mDataset;
    static OnItemClickListener mOnItemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView, mTitleView, mAuthorView, mCorView;
        public ViewHolder(View v) {
            super(v);
            mTextView   = (TextView)v.findViewById(R.id.item);/*
            mTitleView  = (TextView)v.findViewById(R.id.title);
            mSubtitleView = (TextView)v.findViewById(R.id.subtitle);
            mCorView = (TextView)v.findViewById(R.id.cor);*/
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public RecyAdapter(ArrayList<RecyItem> dataset) {
        mDataset = dataset;
    }

    @Override
    public RecyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recy_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position).getText());/*
        holder.mTitleView.setText(mDataset.get(position).getTitle());
        holder.mSubtitleView.setText(mDataset.get(position).getAuthor());
        holder.mCorView.setText(mDataset.get(position).getMyWord());*/
    }

    public void updateDataset(ArrayList<RecyItem> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
