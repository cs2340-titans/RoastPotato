package com.example.wenqixian.myfirstapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecentItemsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecentItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentItemsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static List<RecentItemDetails> recentItemDetailList = new ArrayList<>(50);

    private static JsonObjectRequest jsObjRequest;

    private ViewPager mViewPager;

    private static final String recentDVDsUrl
            = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey=yedukp76ffytfuy24zsqk7f5";

    private static final String recentMoviesUrl
            = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=yedukp76ffytfuy24zsqk7f5";

    class newListners<T> implements Response.Listener<T> {

        private SectionsPagerAdapter adapter;
        int position;

        public newListners(SectionsPagerAdapter adapter, int position) {
            this.adapter = adapter;
            this.position = position;
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
                adapter.getRecentViewItem(position).updateRecentViewAdapter(recentItemDetailList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getRecentData extends AsyncTask<String, Integer, Long> {
        private RequestQueue requestQueue;

        protected Long doInBackground(String... inputs) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            jsObjRequest = createRecentItemRequest(inputs[0], Integer.parseInt(inputs[1]), mSectionsPagerAdapter);
            requestQueue.add(jsObjRequest);
            return (long) 0;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }

    private JsonObjectRequest createRecentItemRequest(String url, int position, SectionsPagerAdapter adapter) {
        Response.Listener<JSONObject> listen = new newListners<JSONObject>(adapter, position);
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


    public RecentItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecentItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecentItemsFragment newInstance(String param1, String param2) {
        RecentItemsFragment fragment = new RecentItemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recent_items, container, false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.clearOnPageChangeListeners();


//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        new getRecentData().execute(recentMoviesUrl, "0");
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String requiredUrl = "N/A";
                if (position == 0) {
                    requiredUrl = recentMoviesUrl;
                } else {
                    requiredUrl = recentDVDsUrl;
                }
                new getRecentData().execute(requiredUrl, Integer.toString(position));
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

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
            View rootView = inflater.inflate(R.layout.recent_items_list_layout, container, false);

            ListView recentListView = (ListView) rootView.findViewById(R.id.recent_items_list_layout);
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
     * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
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
