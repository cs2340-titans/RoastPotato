package com.example.wenqixian.myfirstapp;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity implements RecentItemsFragment.OnFragmentInteractionListener {

    private Activity mCurrentActivity = null;
    Firebase masterRef;

    private void gotoLogin() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }


    private void logout() {
        LoginManager.getInstance().logOut();
        FirebaseSingleton.getInstance().ref().unauth();
    }

    private void gotoProfile() {
        Intent p = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(p);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        masterRef = FirebaseSingleton.getInstance().ref();
        masterRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    // user is logged in
                } else {
                    gotoLogin();
                }
            }
        });

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                masterRef.unauth();
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RecentItemsFragment fragment = new RecentItemsFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri) {}
}
