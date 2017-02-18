package com.example.keywordnews.model;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * Created by loll_ on 2017-02-18.
 */

public class Category {
    String category;
    @ColorInt
    int color;
    Drawable image;

    public Category(String category, @ColorInt int color, Drawable image) {
        this.category = category;
        this.color = color;
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public @ColorInt int getColor() {
        return color;
    }

    public Drawable getImage() {
        return image;
    }
}
