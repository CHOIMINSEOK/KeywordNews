package com.example.keywordnews;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 2017-02-17.
 */

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handle = new Handler();

        handle.postDelayed(new Runnable()
        {
            public void run()
            {
                finish();
            }
        },2000); //2초후 종료
    }
}
