package com.ghaytah.ghaytah;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Login_screen extends AppCompatActivity {

    RelativeLayout rellay1, rellay2;
    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay1 = (RelativeLayout) findViewById(R.id.rellay2);

        mHandler.postDelayed(mRunnable, 2000); // splash time
    }
}
