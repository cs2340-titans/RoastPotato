package com.example.wenqixian.myfirstapp.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;


import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.models.Movie;
import com.example.wenqixian.myfirstapp.models.MovieRating;
import com.example.wenqixian.myfirstapp.models.User;
import com.example.wenqixian.myfirstapp.singletons.FirebaseSingleton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class RatingActivity extends AppCompatActivity {

    private Movie mMovie;
    String movieID = "1234";

    // get a reference to roast-potato.firebaseio.com
    final Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();
    // Direct to current movie by refering to its unique id
    private Firebase uniqueRef = myFirebaseRef.child("comments");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        String movieId = b.getString("movie-id");
        String movieName = b.getString("movie-name");
        mMovie = new Movie(movieId, movieName);
        // -- ** View and Edit Rating ** --
        uniqueRef = uniqueRef.child(uniqueRef.getAuth().getUid() + '_' + movieId);
        // Attach an listener to read the data at this reference
        uniqueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MovieRating rating = snapshot.getValue(MovieRating.class);
                if (rating != null) {
                    EditText commentText = ((EditText) findViewById(R.id.comments_editText));
                    commentText.setText(rating.getComment());
                    RatingBar ratingbar = ((RatingBar) findViewById(R.id.ratingBar));
                    ratingbar.setRating(rating.getScore());
                    TextView scoreText = ((TextView) findViewById(R.id.textView2));
                    scoreText.setText(rating.getScore() + "/ 5.0");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        // Rating text display
        final RatingBar ratingbar = ((RatingBar) findViewById(R.id.ratingBar));
        final TextView scoreText = ((TextView) findViewById(R.id.textView2));
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float score = ratingbar.getRating();
                scoreText.setText(score + "/ 5.0");
            }
        });
    }

    // --** { Send } Action Bar **-- //

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_rating, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_send:
                EditText commentText = ((EditText) findViewById(R.id.comments_editText));
                final String comment = commentText.getText().toString();
                RatingBar ratingbar = ((RatingBar) findViewById(R.id.ratingBar));
                final float score = ratingbar.getRating();
                final float ranking = 6 - score;
                Firebase userRef = myFirebaseRef.child("profile").child(myFirebaseRef.getAuth().getUid());
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            // overwrite the data at the specified id.
                            uniqueRef.setValue(new MovieRating(user.getMajor(), mMovie.getName(), score, ranking, comment));
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
                AlertDialog alertDialog = new AlertDialog.Builder(RatingActivity.this).create();
                alertDialog.setTitle("Thank you");
                alertDialog.setMessage("Your rating has been successfully posted!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
