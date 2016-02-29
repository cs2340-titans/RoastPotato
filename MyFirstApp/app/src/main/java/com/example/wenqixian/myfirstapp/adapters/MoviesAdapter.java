package com.example.wenqixian.myfirstapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wenqixian.myfirstapp.MyFirstApp;
import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.activities.MovieDetailActivity;
import com.example.wenqixian.myfirstapp.fragments.MovieListFragment;
import com.example.wenqixian.myfirstapp.models.Movie;

import java.util.List;

/**
 * Created by andy on 2/19/16.
 */
public class MoviesAdapter extends
        RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context mContext;
    public static class ViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Movie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mView.setOnClickListener(this);
            mIdView.setOnClickListener(this);
            mContentView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("lol", "lol");
            Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
            intent.putExtra("movie-id", mItem.getId());
            intent.putExtra("movie-name", mItem.getName());
            view.getContext().startActivity(intent);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
    private final List<Movie> mMovies;
    private final MovieListFragment.OnListFragmentInteractionListener mListener;



    public MoviesAdapter(List<Movie> movies, MovieListFragment.OnListFragmentInteractionListener listener) {
        mMovies = movies;
        mListener = listener;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.fragment_movie, parent, false);

        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mMovies.get(position);
        holder.mIdView.setText(mMovies.get(position).getId());
        holder.mContentView.setText(mMovies.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
