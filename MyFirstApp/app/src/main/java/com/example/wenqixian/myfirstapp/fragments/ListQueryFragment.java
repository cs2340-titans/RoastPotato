package com.example.wenqixian.myfirstapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wenqixian.myfirstapp.adapters.MoviesAdapter;
import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.dummy.DummyMovie;
import com.example.wenqixian.myfirstapp.models.Movie;
import com.example.wenqixian.myfirstapp.singletons.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2/19/16.
 */
public class ListQueryFragment
        extends MovieListFragment {
    public static final String ARG_LIST_QUERY = "list-query";
    private String listQuery;
    public ListQueryFragment() {
    }

    class ListResultsListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            List<Movie> movies = new ArrayList<>();
            try {
                JSONArray arrItems = response.getJSONArray("movies");
                for (int i = 0; i < arrItems.length(); i++) {
                    JSONObject itemJSON = (JSONObject) arrItems.get(i);
                    Movie newMovie = new Movie(String.valueOf(i), itemJSON.getString("title"));
                    movies.add(newMovie);
                }
                updateViewWithAdapter(getView(), new MoviesAdapter(movies, mListener));
                showProgress(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static ListQueryFragment newInstance(String query) {
        ListQueryFragment fragment = new ListQueryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            listQuery = getArguments().getString(ARG_LIST_QUERY);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movielist, container, false);
        mAdapter = new MoviesAdapter(DummyMovie.ITEMS, mListener);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return updateViewWithAdapter(view, mAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mProgressView = view.findViewById(R.id.load_progress);
        mRecyclerView = view.findViewById(R.id.list);
        if (listQuery == null) {
            mAdapter = new MoviesAdapter(DummyMovie.ITEMS, mListener);
        }
        else {
            RequestQueue queue = VolleySingleton.getInstance().queue();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    listQuery,
                    "",
                    new ListResultsListener(),
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null) {
                                Log.e("ListQueryFragment", error.getMessage());
                            }
                        }
                    });
            queue.add(jsonObjectRequest);

        }
        updateViewWithAdapter(view, mAdapter);
    }
}
