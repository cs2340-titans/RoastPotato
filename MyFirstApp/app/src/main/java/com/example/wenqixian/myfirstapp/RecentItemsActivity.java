package com.example.wenqixian.myfirstapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.database.DataSetObserver;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecentItemsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static RequestQueue requestQueue;

    private static List<RecentItemDetails> recentItemDetailList = new ArrayList<>(50);

    private static String currentType = "Movie";

    private static JsonObjectRequest jsObjRequest;

    private static final String recentDVDsUrl
            = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey=yedukp76ffytfuy24zsqk7f5";

    private static final String recentMoviesUrl
            = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=yedukp76ffytfuy24zsqk7f5";


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    class newListners<T> implements Response.Listener<T> {

        private SectionsPagerAdapter adapter;

        public newListners(SectionsPagerAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onResponse(T response) {
            JSONObject newresponse = (JSONObject) response;
            recentItemDetailList = new ArrayList<>(50);
            try {
                JSONArray arrItems = newresponse.getJSONArray("movies");
                for (int i = 0; i < arrItems.length(); i++) {
                    JSONObject recentItemJSON = (JSONObject) arrItems.get(i);
                    RecentItemDetails tempRecentItem = new RecentItemDetails();
                    tempRecentItem.id = Integer.parseInt(recentItemJSON.getString("id"));
                    tempRecentItem.title = recentItemJSON.getString("title");
                    JSONObject ratingItem = recentItemJSON.getJSONObject("ratings");
                    tempRecentItem.critics_score = Integer.parseInt(ratingItem.getString("critics_score"));
                    recentItemDetailList.add(tempRecentItem);
                }
                adapter.getRecentViewItem(0).updateRecentViewAdapter(recentItemDetailList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private JsonObjectRequest createRecentItemRequest(String url, SectionsPagerAdapter adapter) {
        Response.Listener<JSONObject> listen = new newListners<JSONObject>(adapter);
        jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                "",
                listen,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.e("RecentItemsActivity", error.getMessage());
                        }
                    }
                });
        return jsObjRequest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final RecentItemsActivity currentRecentItemActivity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_items);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.clearOnPageChangeListeners();

        requestQueue = Volley.newRequestQueue(this);
        jsObjRequest = createRecentItemRequest(recentMoviesUrl, mSectionsPagerAdapter);
        requestQueue.add(jsObjRequest);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                requestQueue = Volley.newRequestQueue(currentRecentItemActivity);
                String requiredUrl = "N/A";
                if (position == 0) {requiredUrl = recentMoviesUrl;} else {requiredUrl = recentDVDsUrl;}
                jsObjRequest = createRecentItemRequest(requiredUrl, mSectionsPagerAdapter);
                requestQueue.add(jsObjRequest);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class RecentViewFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

//        private static final String[] randomStringArray = new String[] {"abc", "Euler"};
        private RecentItemAdapter tempAdapter = null;

        public RecentViewFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static RecentViewFragment newInstance(int sectionNumber) {
            RecentViewFragment fragment = new RecentViewFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_recent_items, container, false);

            ListView recentListView = (ListView) rootView.findViewById(R.id.recent_items_option_layout);
            tempAdapter = new RecentItemAdapter(getActivity(), recentItemDetailList);
            recentListView.setAdapter(tempAdapter);
            return rootView;

//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;

        }

        public void updateRecentViewAdapter(List<RecentItemDetails> inList){
            if(tempAdapter != null){
                tempAdapter.dataComing(inList);
            }
        }

        @Override
        public String toString() {
            return tempAdapter.toString();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private RecentViewFragment movieFrag = null;
        private RecentViewFragment dvdFrag = null;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                if (movieFrag == null) {
                    movieFrag = RecentViewFragment.newInstance(0);
                }
                return movieFrag;
            } else {
                if (dvdFrag == null) {
                    dvdFrag = RecentViewFragment.newInstance(1);
                }
                return dvdFrag;
            }
        }

        public RecentViewFragment getRecentViewItem(int position) {
            return (RecentViewFragment) getItem(position);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Recent Movies";
                case 1:
                    return "Recent DVDs";
            }
            return null;
        }
    }
}
