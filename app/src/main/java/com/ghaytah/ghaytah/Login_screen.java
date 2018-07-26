package com.ghaytah.ghaytah;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_screen extends AppCompatActivity {

    // TODO: Add member variables here:
        private FirebaseAuth mAuth;

    //UI References
       private RelativeLayout rellay1, rellay2;
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
        mEmailView = (EditText) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent Keyevent) {
                if (id == R.id.login|| id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        // TODO: Grab an instance
        mAuth = FirebaseAuth.getInstance();
        mHandler.postDelayed(mRunnable, 2000); // splash time
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (email.isEmpty())
            if (email.equals("") || password.equals("")) return;
        Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("Ghaytah", "signInWithEmail() onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.d("Ghaytah", "Problem signing in: " + task.getException());
                    showErrorDialog("There was a problem signing in");
                } else {
                    Intent intent = new Intent(Login_screen.this, MainSearchActivity.class);
                    finish();
                    startActivity(intent);
                }

            }
        });
    }

    // Executed when Sign in button pressed
    public void SignInExistingUser(View view) {
        attemptLogin();
    }

    // Executed when Register button pressed
    public void registerNewUser(View view) {
        Intent intent = new Intent(Login_screen.this, com.ghaytah.ghaytah.Register.class);
        //finish();
        startActivity(intent);
    }

    // Executed when Forgot password button pressed
    public void ForgotPassword(View view) {
        Intent intent = new Intent(Login_screen.this, com.ghaytah.ghaytah.Forgot_Password.class);
        //finish();
        startActivity(intent);
    }

    // Error Dialog
    private void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
