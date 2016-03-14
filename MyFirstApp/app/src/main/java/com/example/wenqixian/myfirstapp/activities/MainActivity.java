package com.example.wenqixian.myfirstapp.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.fragments.HomeFragment;
import com.example.wenqixian.myfirstapp.fragments.MovieListFragment;
import com.example.wenqixian.myfirstapp.fragments.RecentItemsFragment;
import com.example.wenqixian.myfirstapp.fragments.SearchFragment;
import com.example.wenqixian.myfirstapp.models.Movie;
import com.example.wenqixian.myfirstapp.models.User;
import com.example.wenqixian.myfirstapp.singletons.FirebaseSingleton;
import com.facebook.FacebookSdk;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

public class MainActivity extends FirebaseLoginBaseActivity
        implements HomeFragment.OnFragmentInteractionListener,
        MovieListFragment.OnListFragmentInteractionListener,
        RecentItemsFragment.OnFragmentInteractionListener {
    private Activity mCurrentActivity = null;
    Firebase masterRef;
    private View mainLayout;

    private static final int PROFILE_SETTING = 100000;

    @Override
    public Firebase getFirebaseRef() {
        return FirebaseSingleton.getInstance().ref();
    }

    @Override
    public void onFirebaseLoginProviderError(FirebaseLoginError firebaseError) {
        // TODO: Handle an error from the authentication provider
    }

    @Override
    public void onFirebaseLoginUserError(FirebaseLoginError firebaseError) {
        // TODO: Handle an error from the user
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        // TODO: Handle successful login
    }

    @Override
    public void onFirebaseLoggedOut() {
        // TODO: Handle logout
    }

    private void gotoLogin() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
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
    private Drawer drawer;
    private AccountHeader headerResult = null;
    private User currUser = new User("John Doe", "1234567890", "john.doe@gmail.com", "Computer Science", "Active");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        PrimaryDrawerItem newItems = new PrimaryDrawerItem()
                .withName(R.string.drawer_new_items)
                .withIcon(GoogleMaterial.Icon.gmd_movie);
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(currUser.getFullname())
                                .withEmail(currUser.getEmail())
                                .withIcon("https://avatars2.githubusercontent.com/u/3586644?v=3&s=460")
                                .withIdentifier(100)
                )
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            int count = 100 + headerResult.getProfiles().size() + 1;
                            IProfile newProfile = new ProfileDrawerItem()
                                    .withNameShown(true).withName("Batman" + count)
                                    .withEmail("batman" + count + "@gmail.com")
                                    .withIdentifier(count);
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;

                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        newItems,
                        new DividerDrawerItem(),
                        new ProfileSettingDrawerItem().withName("Add Account")
                                .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add))
                                .withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account")
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                                .withIdentifier(100001),
                        new ProfileSettingDrawerItem().withName("Recommendation")
                                .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_movie))
                                .withIdentifier(100002),
                        new ProfileSettingDrawerItem().withName("Administration")
                                .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_alarm))
                                .withIdentifier(100003)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 100001) {
                                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                                startActivity(intent);
                            }
                            if (drawerItem.getIdentifier() == 100002) {
                                Intent intent = new Intent(MainActivity.this, RecommendationActivity.class);
                                startActivity(intent);
                            }
                            if (drawerItem.getIdentifier() == 100003) {
                                Intent intent = new Intent(MainActivity.this, AdministrationActivity.class);
                                startActivity(intent);
                            }
                            if (drawerItem.getIdentifier() == PROFILE_SETTING) {
                                showFirebaseLoginPrompt();
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        mProgressView = findViewById(R.id.load_progress);
        mFragmentView = findViewById(R.id.flContent);
        currentFragment = RecentItemsFragment.newInstance();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, currentFragment);
        tx.commit();



    }

    @Override
    protected void onStart() {
        super.onStart();
        // All providers are optional! Remove any you don't want.
        setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
        getFirebaseRef().addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    // user is logged in
                } else {
                    showFirebaseLoginPrompt();
                }
            }
        });


        // get a reference to roast-potato.firebaseio.com
        Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();
        // Direct to current user by refering to its unique id

        if (myFirebaseRef.getAuth() != null) {
            final Firebase uniqueRef = myFirebaseRef.child("profile").child(myFirebaseRef.getAuth().getUid());
            // Attach an listener to read the data at this reference
            uniqueRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null && user.getStatus().equals("Active")) {
                        Toast.makeText(getBaseContext(), "Your account is active", Toast.LENGTH_LONG).show();
                    } else if (user != null && user.getStatus().equals("Locked")) {
                        Toast.makeText(getBaseContext(), "Your account is locked", Toast.LENGTH_LONG).show();
                        logout();
                    } else {
                        Toast.makeText(getBaseContext(), "Your account is banned", Toast.LENGTH_LONG).show();
                    }
                    // TODO:
                    // Extra: Make the administration button only visible to admins.
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
        } else {
            Toast.makeText(getBaseContext(), "auth is null", Toast.LENGTH_LONG).show();
        }

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
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragment = new MovieListFragment();
                break;
            case R.id.nav_second_fragment:
                fragment = RecentItemsFragment.newInstance();
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
                changeFragment(SearchFragment.newInstance(query), false);
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
                    changeFragment(currentFragment, true);
                }
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded]
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


}
