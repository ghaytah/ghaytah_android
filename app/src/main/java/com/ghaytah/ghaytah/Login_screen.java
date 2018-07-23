package com.ghaytah.ghaytah;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Login_screen extends AppCompatActivity {

    // TODO: Add member variables here:

       private RelativeLayout rellay1, rellay2;
       private Button btn_lgn, btn_cretacct, btn_frgtpwd;


    // TODO: Adding splash screen here:

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
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        btn_lgn = (Button) findViewById(R.id.btn_lgn);
        btn_cretacct = (Button) findViewById(R.id.btn_cretacct);
        btn_frgtpwd = (Button) findViewById(R.id.btn_frgtpwd);

        mHandler.postDelayed(mRunnable, 2000); // splash time
    }
}
