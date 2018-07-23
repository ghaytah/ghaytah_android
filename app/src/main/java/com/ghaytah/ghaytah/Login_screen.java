package com.ghaytah.ghaytah;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_screen extends AppCompatActivity {

    // TODO: Add member variables here:
        private FirebaseAuth mAuth;

    //UI References
       private RelativeLayout rellay1, rellay2;
       private Button btn_lgn, btn_cretacct, btn_frgtpwd;
       private EditText mEmailView;
       private EditText mPasswordView;

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
        mEmailView = (EditText) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);


        // TODO: Grab an instance
        mAuth = FirebaseAuth.getInstance();
        mHandler.postDelayed(mRunnable, 2000); // splash time
    }
}
