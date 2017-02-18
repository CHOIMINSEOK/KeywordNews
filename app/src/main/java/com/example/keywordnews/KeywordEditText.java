package com.example.keywordnews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by loll_ on 2017-02-18.
 */
public class KeywordEditText extends AppCompatEditText implements View.OnTouchListener {
    private Drawable addDrawable;
    private OnTouchListener onTouchListener;
    public AddManager addManager;

    public KeywordEditText(Context context) {
        super(context);
        init();
    }

    public KeywordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public KeywordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_check_circle_black_24dp);
        addDrawable = DrawableCompat.wrap(tempDrawable);
        DrawableCompat.setTintList(addDrawable,getHintTextColors());
        addDrawable.setBounds(0, 0, addDrawable.getIntrinsicWidth(), addDrawable.getIntrinsicHeight());

        setCompoundDrawables(null,null, addDrawable, null);
        super.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int x = (int) event.getX();
        if (addDrawable.isVisible() && x > getWidth() - getPaddingRight() - addDrawable.getIntrinsicWidth()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                addManager.AddKeywordToCurrentPage(getText().toString());
            }
            return true;
        }

        if (onTouchListener != null) {
            return onTouchListener.onTouch(v, event);
        } else {
            return false;
        }


    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        this.onTouchListener = l;
    }

    public interface AddManager {
        void AddKeywordToCurrentPage(String keyword);
    }

    public void setAddManager(AddManager addManager) {
        this.addManager = addManager;
    }
}