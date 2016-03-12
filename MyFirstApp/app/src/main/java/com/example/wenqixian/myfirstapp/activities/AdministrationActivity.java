package com.example.wenqixian.myfirstapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.models.User;
import com.example.wenqixian.myfirstapp.singletons.FirebaseSingleton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdministrationActivity extends AppCompatActivity {

    final Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();
    private User mUser = new User(null, null, null, null);

    private HashMap<String, List<String>> user_status;
    private List<String> user;
    private ExpandableListView exp_list;
    private ExpandableListAdapter adapter;
    private HashMap<String, List<String>> Status;
    private List<String> status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);
        exp_list = (ExpandableListView) findViewById(R.id.expandableListView);
        user_status = new DataProvider().getInfo();
        user = new ArrayList<String>(user_status.keySet());
        adapter = new com.example.wenqixian.myfirstapp.adapters.ExpandableListAdapter(this, user_status, user);
        exp_list.setAdapter(adapter);
    }

    private class DataProvider {
        public HashMap<String, List<String>> getInfo() {
            Status = new HashMap<>();
            status = new ArrayList<>();
            status.add("Active");
            status.add("Banned");
            status.add("Locked");

            Status.put("Andy Fang", status);
            Status.put("Ryan He", status);
            Status.put("Zhen Liu", status);
            Status.put("Muchen Wu", status);
            Status.put("Xianwen Qi", status);
            Status.put("Liren Yu", status);

            return Status;
        }
    }

    /*
     * populate the HashMap for the movie recommendation
     */
    private void populateList() {
        //Retrieve user information from firebase
        Firebase userRef = myFirebaseRef.child("profile");
        userRef.addValueEventListener(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(DataSnapshot snapshot) {
                                              for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                                  User user = postSnapshot.getValue(User.class);
                                                  Status.put(user.getFullname(), status);
                                              }
                                          }

                                          @Override
                                          public void onCancelled (FirebaseError firebaseError){
                                              System.out.println("The read failed: " + firebaseError.getMessage());
                                          }
                                      }

        );

        // Filtered by user major, ordered from highest to lowest score.
//        Query queryRef = comments.orderByChild("ranking");
//        queryRef.addValueEventListener(new ValueEventListener() {
//            public int i = 1;
//            @Override
//            public void onDataChange (DataSnapshot snapshots){
//                for (DataSnapshot snapshot : snapshots.getChildren()) {
//                    MovieRating rating = snapshot.getValue(MovieRating.class);
//                    if (rating.getUserMajor().equals(selectMajor)) {
//                        // TODO:
//                        // Change EditText to Listview etc.
//                        list.add(i + ")  "  + rating.getMovie() + "\n\n" + "           " +
//                                "                " +
//                                "                                      " +
//                                "score: " + rating.getScore() + "/ 5.0");
//                        i++;
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
//            }
//        });
    }
}