package com.ghaytah.ghaytah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainSearchActivity extends AppCompatActivity {

    //Initial initiallization
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private FirebaseAuth mAuth;

    //Fragment initilallization
    private SearchFragment mSearchFragment;
    private FavouriteFragment mFavouriteFragment;
    private ProfileFragment mProfileFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);

        //Calling items
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        mAuth = FirebaseAuth.getInstance();

        //Calling fragments
        mSearchFragment = new SearchFragment();
        mFavouriteFragment = new FavouriteFragment();
        mProfileFragment = new ProfileFragment();

        setFragment(mSearchFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //When Icons are pressed
                switch (item.getItemId()){

                    case R.id.search_icon:
                        setFragment(mSearchFragment);
                        return true;

                    case R.id.fav_icon:
                        setFragment(mFavouriteFragment);
                        return true;

                    case R.id.profile_icon:
                        setFragment(mProfileFragment);
                        return true;

                        default:
                            return false;
                }

            }
        });

    }

    //Checking if user is signed in
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null){

            Intent intent = new Intent(com.ghaytah.ghaytah.MainSearchActivity.this, com.ghaytah.ghaytah.Login_screen.class);
            startActivity(intent);
            finish();

        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
