package com.example.wenqixian.myfirstapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

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
import java.util.Map;

public class AdministrationActivity extends AppCompatActivity {

    final Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();

    private List<String> user_set;
    private ExpandableListView exp_list;
    private ExpandableListAdapter adapter;
    private HashMap<String, List<String>> users;
    private List<String> status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);
        populateList();
    }
    /*
    private class DataProvider {
        public HashMap<String, List<String>> getInfo() {
            users = new HashMap<>();
            status = new ArrayList<>();
            status.add("Active");
            status.add("Banned");
            status.add("Locked");

            users.put("Andy Fang", status);
            users.put("Ryan He", status);
            users.put("Zhen Liu", status);
            users.put("Muchen Wu", status);
            users.put("Xianwen Qi", status);
            users.put("Liren Yu", status);

            return users;
        }
    }
    */

    /*
     * Display and manage users - Administration feature
     */
    private void populateList() {
        users = new HashMap<>();
        status = new ArrayList<>();
        status.add("Ban");
        status.add("Lock");
        status.add("UnLock");

        //Retrieve user information from firebase
        final Firebase userRef = myFirebaseRef.child("profile");
        userRef.addValueEventListener(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(DataSnapshot snapshot) {
                                              // Populate a hashMap of active (or locked) users and their status
                                              for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                                  User user = userSnapshot.getValue(User.class);
                                                  if (!user.getStatus().equals("Banned")) {
                                                      users.put(user.getFullname() + "\n" + "[" + userSnapshot.getKey() + "]", status);
                                                  }
                                              }
                                              // Display by an expandable list
                                              exp_list = (ExpandableListView) findViewById(R.id.expandableListView);
                                              user_set = new ArrayList<String>(users.keySet());
                                              adapter = new com.example.wenqixian.myfirstapp.adapters.ExpandableListAdapter(AdministrationActivity.this, users, user_set);
                                              exp_list.setAdapter(adapter);

                                              // Action listener of expandable list view
                                              exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                                                  @Override
                                                  public boolean onChildClick(ExpandableListView parent, View v,
                                                                              int groupPosition, int childPosition, long id) {
                                                      String userKey = user_set.get(groupPosition);
                                                      String userId = userKey.substring(userKey.indexOf("[") + 1, userKey.indexOf("]"));
                                                      //Toast.makeText(getBaseContext(), userId, Toast.LENGTH_LONG).show();
                                                      switch (childPosition) {
                                                          case 0:
                                                              // If the user is tagged "Banned",
                                                              // it will immediately force the user to log out.
                                                              // it will be removed from Admin's user list,
                                                              // so that there is no way to activate the user
                                                              Map<String, Object> ban = new HashMap<String, Object>();
                                                              ban.put("status", "Banned");
                                                              userRef.child(userId).updateChildren(ban);
                                                              break;
                                                          case 1:
                                                              // If the user is tagged "Locked",
                                                              // it will immediately force the user to log out.
                                                              // it will stay in Admin's user list,
                                                              // so that there is an option for admin to activate the user
                                                              Map<String, Object> lock = new HashMap<String, Object>();
                                                              lock.put("status", "Locked");
                                                              userRef.child(userId).updateChildren(lock);
                                                              break;
                                                          case 2:
                                                              Map<String, Object> activate = new HashMap<String, Object>();
                                                              activate.put("status", "Active");
                                                              userRef.child(userId).updateChildren(activate);
                                                              break;
                                                      }
                                                      return false;
                                                  }

                                              });
                                          }

                                          @Override
                                          public void onCancelled(FirebaseError firebaseError) {
                                              System.out.println("The read failed: " + firebaseError.getMessage());
                                          }
                                      }

        );

    }
}
