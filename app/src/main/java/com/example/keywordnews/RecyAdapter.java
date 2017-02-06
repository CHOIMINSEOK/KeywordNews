package com.example.keywordnews;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.keywordnews.Utils.CollectionUtils;
import com.example.keywordnews.model.NewsItem;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by 밈석 on 2017-01-23.
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder>  {
    private ArrayList<NewsItem> mNewsset;
    private RequestManager mImageRequestManager;
    static OnItemClickListener mOnItemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mCrpView, mTitleView, mSubtitleView, mCorView;
        public LinearLayout mItemLayout;
        public ViewHolder(View v) {
            super(v);
            mCrpView = (TextView)v.findViewById(R.id.crp);
            mTitleView  = (TextView)v.findViewById(R.id.title);
            mSubtitleView = (TextView)v.findViewById(R.id.subtitle);
            mCorView = (TextView)v.findViewById(R.id.cor);
            mItemLayout = (LinearLayout) v;
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

    public RecyAdapter(ArrayList<NewsItem> newsset, RequestManager requestManager) {
        mNewsset = newsset;
        mImageRequestManager = requestManager;
    }

    @Override
    public RecyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()). inflate(R.layout.recy_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        NewsItem item = mNewsset.get(position);
        holder.mCrpView.setText(item.getCrp());
        holder.mTitleView.setText(item.getTitle());
        holder.mSubtitleView.setText(item.getSubtitle());
        holder.mCorView.setText(item.getImageurl());
        mImageRequestManager.load(item.getImageurl()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.mItemLayout.setBackground(drawable);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return CollectionUtils.size(mNewsset);
    }
    public void updateDataset(ArrayList<NewsItem> newsset){
        mNewsset = newsset;
        notifyDataSetChanged();
    }
}