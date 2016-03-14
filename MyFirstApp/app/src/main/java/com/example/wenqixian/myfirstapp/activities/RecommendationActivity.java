package com.example.wenqixian.myfirstapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.models.MovieRating;
import com.example.wenqixian.myfirstapp.models.User;
import com.example.wenqixian.myfirstapp.singletons.FirebaseSingleton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class RecommendationActivity extends AppCompatActivity {

    // get a reference to roast-potato.firebaseio.com
    final Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();
    // Direct to current movie by refering to its unique id
    private Firebase comments = myFirebaseRef.child("comments");
    private User mUser = new User(null, null, null, null, null);
    private ListView lv;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String selectMajor;
    private ArrayList<String> getData()
    {
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        lv = (ListView) findViewById(R.id.listView);

        Spinner dropdown = (Spinner)findViewById(R.id.planets_spinner);
        String[] items = new String[]{"Major", "Computer Science", "Computer Engineering", "Industrial Engineering"};
        ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(sAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                                       int pos, long id) {
                // TODO Auto-generated method stub
                switch (pos) {
                    case 0:
                        Toast.makeText(RecommendationActivity.this, "Please Select a Major",
                                Toast.LENGTH_SHORT).show();
                        list.clear();
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        list.clear();
                        selectMajor = "Computer Science";
                        populateList();
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        list.clear();
                        selectMajor = "Computer Engineering";
                        populateList();
                        adapter.notifyDataSetChanged();
                        break;
                    case 3:
                        // Whatever you want to happen when the thrid item gets selected
                        list.clear();
                        selectMajor = "Industrial Engineering";
                        populateList();
                        adapter.notifyDataSetChanged();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        // Apply the adapter to the spinner
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item,
                R.id.textitem,
                getData());
        lv.setAdapter(adapter);
    }

    /*
     * populate the list for the movie recommendation
     */
    private void populateList() {
        //Retrieve user information from firebase
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
                                          public void onCancelled (FirebaseError firebaseError){
                                              System.out.println("The read failed: " + firebaseError.getMessage());
                                          }
                                      }

        );

        // Filtered by user major, ordered from highest to lowest score.
        Query queryRef = comments.orderByChild("ranking");
        queryRef.addValueEventListener(new ValueEventListener() {
            public int i = 1;
            @Override
            public void onDataChange (DataSnapshot snapshots){
                for (DataSnapshot snapshot : snapshots.getChildren()) {
                    MovieRating rating = snapshot.getValue(MovieRating.class);
                    if (rating.getUserMajor().equals(selectMajor)) {
                        // TODO:
                        // Change EditText to Listview etc.
                        list.add(i + ")  "  + rating.getMovie() + "\n\n" + "           " +
                                "                " +
                                "                                      " +
                                "score: " + rating.getScore() + "/ 5.0");
                        i++;
                        adapter.notifyDataSetChanged();
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
