package com.example.schat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ViewPager myViewPager;
    private Toolbar mtoolbar;
    private TabLayout myTabLayout;
    private TabsAccessorAdapter myTabsAccessorAdapter;
    private FirebaseUser CurrentUser;
    private FirebaseAuth mAuth;

    //...
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        CurrentUser= mAuth.getCurrentUser();

        mtoolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mtoolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Main");
        //actionBar.setDisplayHomeAsUpEnabled(true);

        myViewPager = findViewById(R.id.main_tabs_pagers);
        myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);

        myTabLayout = findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (CurrentUser == null) {

            SendUserToLoginActivity();
        }

    }

    private void SendUserToLoginActivity() {
        //  Intent LoginIntent  = new Intent(MainActivity.this , LoginActivity.class);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
