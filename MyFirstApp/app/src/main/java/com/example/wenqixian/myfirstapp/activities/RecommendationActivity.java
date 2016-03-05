package com.example.wenqixian.myfirstapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.models.MovieRating;
import com.example.wenqixian.myfirstapp.models.User;
import com.example.wenqixian.myfirstapp.singletons.FirebaseSingleton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class RecommendationActivity extends AppCompatActivity {

    // get a reference to roast-potato.firebaseio.com
    final Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();
    // Direct to current movie by refering to its unique id
    private Firebase comments = myFirebaseRef.child("comments");
    private User mUser = new User(null, null, null, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);


        // Retrieve user information from firebase
        Firebase userRef = myFirebaseRef.child("profile").child(myFirebaseRef.getAuth().getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    mUser.setMajor(user.getMajor());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        // Filtered by user major, ordered from highest to lowest score.
        Query queryRef = comments.orderByChild("ranking");
        queryRef.addValueEventListener(new ValueEventListener() {
            public int i = 1;
            @Override
            public void onDataChange(DataSnapshot snapshots) {
                for (DataSnapshot snapshot: snapshots.getChildren()) {
                    MovieRating rating = snapshot.getValue(MovieRating.class);
                    if (rating.getUserMajor().equals(mUser.getMajor())) {
                        EditText test = ((EditText) findViewById(R.id.test));
                        test.append(i + " "  + rating.getMovie() + "    "
                                + "score: " + rating.getScore() + "/ 5.0\n");
                        i++;
                        // TODO:
                        // Change EditText to Listview etc.
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        // TODO:
        // Multiple filtering criteria (genre, recency) would be extra credit.
    }

}
