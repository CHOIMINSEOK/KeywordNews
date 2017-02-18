package com.example.keywordnews;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by loll_ on 2017-02-19.
 */
//?
public class DesignManager {
    private static DesignManager designManager;
    private static Context mContext;
    private static ArrayList<String> crpList = new ArrayList<>( Arrays.asList("조선", "노컷", "동아", "세계", "매일", "파이낸셜", "헤럴드"));

    private DesignManager(Context context) {
    }

    public static DesignManager getInstance(){
        if(designManager == null)
            return null;

        return designManager;
    }

    static void setContext(Context context){
        mContext = context;
    }

    public static ArrayList<String> getCorperations(){
        return crpList;
    }
    public static int getColor(String crp){
        switch (crp){
            case "조선" : return ContextCompat.getColor(mContext, R.color.turquoise);
            case "노컷" : return ContextCompat.getColor(mContext, R.color.emerald);
            case "동아" : return ContextCompat.getColor(mContext, R.color.peterreiver);
            case "세계" : return ContextCompat.getColor(mContext, R.color.amethyst);
            case "매일" : return ContextCompat.getColor(mContext, R.color.wetasphalt);
            case "파이낸셜" : return ContextCompat.getColor(mContext, R.color.carrot);
            case "헤럴드" : return ContextCompat.getColor(mContext, R.color.pumpkin);
            default: return ContextCompat.getColor(mContext, R.color.red);
        }
    }
}
