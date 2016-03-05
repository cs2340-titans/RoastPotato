package com.example.wenqixian.myfirstapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.models.Movie;
import com.example.wenqixian.myfirstapp.models.MovieRating;
import com.example.wenqixian.myfirstapp.singletons.FirebaseSingleton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MovieDetailActivity extends Activity {

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.rating_fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailActivity.this, RatingActivity.class);
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
        if (getIntent().hasExtra("movie-id") && getIntent().hasExtra("movie-name")) {
            String movieId = bundle.getString("movie-id");
            String movieName = bundle.getString("movie-name");
            mMovie = new Movie(movieId, movieName);
            TextView view = (TextView) findViewById(R.id.movie_title);
            view.setText(movieName);
        }
        Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();
        Firebase comment = myFirebaseRef.child("comments").child(myFirebaseRef.getAuth().getUid()
                + '_' + mMovie.getId());
        comment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MovieRating rating = snapshot.getValue(MovieRating.class);
                if (rating != null) {
                    TextView commentText = ((TextView) findViewById(R.id.rating_content));
                    commentText.setText(rating.getComment());
                    RatingBar ratingbar = ((RatingBar) findViewById(R.id.ratingBar2));
                    ratingbar.setRating(rating.getScore());
                }
                ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar);
                pb.setVisibility(View.GONE);
                RelativeLayout rl = (RelativeLayout) findViewById(R.id.rating_area);
                rl.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


}
