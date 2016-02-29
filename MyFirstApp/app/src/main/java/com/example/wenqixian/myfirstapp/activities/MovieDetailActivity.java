package com.example.wenqixian.myfirstapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.models.Movie;

public class MovieDetailActivity extends Activity {

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailActivity.this, DummyCommentActivity.class);
                if (mMovie != null) {
                    intent.putExtra("movie-id", mMovie.getId());
                    intent.putExtra("movie-name", mMovie.getName());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        String movieId = bundle.getString("movie-id");
        String movieName = bundle.getString("movie-name");
        mMovie = new Movie(movieId, movieName);
        TextView view = (TextView) findViewById(R.id.movie_title);
        view.setText(movieName);
    }

}
