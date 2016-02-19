package com.example.wenqixian.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.support.v7.widget.SearchView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.io.Console;

public class MainActivity extends AppCompatActivity
    implements HomeFragment.OnFragmentInteractionListener,
        MovieListFragment.OnListFragmentInteractionListener {

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

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment currentFragment;
    private View mProgressView;
    private View mFragmentView;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(drawerToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        mProgressView = findViewById(R.id.load_progress);
        mFragmentView = findViewById(R.id.flContent);
        currentFragment = new HomeFragment();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, currentFragment);
        tx.commit();

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void changeFragment(Fragment fragment, boolean save) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        if (save) {
            currentFragment = fragment;
        }
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragment = new MovieListFragment();
                break;
            case R.id.nav_second_fragment:
                fragment = new HomeFragment();
                break;
            case R.id.nav_third_fragment:
                fragment = new HomeFragment();
                break;
            default:
                fragment = new HomeFragment();
        }

        changeFragment(fragment, true);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_logout:
                logout();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private SearchView mSearchView;
    private MenuItem searchMenuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            mSearchView = (SearchView) searchItem.getActionView();
        }
        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                changeFragment(new MovieListFragment(), false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                if (currentFragment != null) {
                    showProgress(false);
                    changeFragment(currentFragment, true);
                }
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                showProgress(true);
                return true;  // Return true to expand action view
            }
        };
        mSearchView.setOnQueryTextListener(listener);
        MenuItemCompat.setOnActionExpandListener(searchItem, expandListener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Movie item) {

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mFragmentView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFragmentView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFragmentView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mFragmentView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
