package com.ghaytah.ghaytah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.ghaytah.ghaytah.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    // TODO: Add member variables here:
    private FirebaseAuth mAuth;
    private FirebaseDatabase mdb;
    private DatabaseReference musers;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mPhoneView;
    private EditText mConfirmPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen_activity);

        //Views
        mEmailView = (EditText) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
        mNameView = (EditText) findViewById(R.id.register_name);
        mSurnameView = (EditText) findViewById(R.id.register_surname);
        mPhoneView = (EditText) findViewById(R.id.register_phone);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        mdb = FirebaseDatabase.getInstance();
        musers = mdb.getReference("Users");

        // Keyboard sign in action
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent KeyEvent) {
                if (id == R.id.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });


    }

    //Executed when sign up button is pressed
    public void signUp (View view) {
        attemptRegistration ();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPhoneView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String phone = mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid phone number.
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();

        }


    }


    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password
        String confirmPassword = mConfirmPasswordView.getText().toString();
        return confirmPassword.equals(password) && password.length() > 4;
    }

    // TODO: Create a Firebase user
    private void createFirebaseUser() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Ghaytah", "createUser onComplete: " + task.isSuccessful());

                if(!task.isSuccessful()){
                    Log.d("Ghaytah", "user creation failed");
                    showErrorDialog();
                }else{
                    saveUsers();

                    Intent inetent = new Intent(Register.this, Login_screen.class);
                    finish();
                    startActivity(inetent);

                }

            }
        });
    }

    // TODO: Saving users details
    private void saveUsers() {
        //save user to db
        User user = new User();
        user.setEmail(mEmailView.getText().toString());
        user.setFirstname(mNameView.getText().toString());
        user.setSurname(mSurnameView.getText().toString());
        user.setPassword(mPasswordView.getText().toString());
        user.setPhone(mPhoneView.getText().toString());

        //Use email to key

        musers.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //TODO need to add success notification

                        Toast.makeText(Register.this, "Registered Successfully !!!", Toast.LENGTH_SHORT).show();

                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showErrorDialog();
            }
        });

     }


    // TODO: Create an alert dialog to show in case registration failed

    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage("Registration attempt failed")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    
}

