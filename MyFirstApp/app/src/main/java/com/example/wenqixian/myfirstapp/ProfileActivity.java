package com.example.wenqixian.myfirstapp;

/**
 * Created by wenqixian on 2/13/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private Activity mCurrentActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentActivity = this;
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSidebar();
            }
        });


        // -- ** View profile ** --

        // get a reference to roast-potato.firebaseio.com
        Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();
        // Direct to current user by refering to its unique id
        final Firebase uniqueRef = myFirebaseRef.child("profile").child(myFirebaseRef.getAuth().getUid());
        // Attach an listener to read the data at this reference

        uniqueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        TextView nameText = (TextView) findViewById(R.id.profile_name);
                        nameText.setText(user.getFullname());
                        TextView gtIdText = (TextView) findViewById(R.id.profile_gtid);
                        gtIdText.setText(user.getGtid());
                        TextView emailText = (TextView) findViewById(R.id.profile_email);
                        emailText.setText(user.getEmail());
                        TextView mobileText = (TextView) findViewById(R.id.profile_mobile);
                        mobileText.setText(user.getMobile());
                    }
                //}
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        // --** Edit profile **--
        Button save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get a reference to roast-potato.firebaseio.com
                Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();
                Firebase uniqueRef = myFirebaseRef.child("profile").child(myFirebaseRef.getAuth().getUid());

                String name = ((EditText) mCurrentActivity.findViewById(R.id.profile_name))
                        .getText()
                        .toString();
                String gtid = ((EditText) mCurrentActivity.findViewById(R.id.profile_gtid))
                        .getText()
                        .toString();
                String email = ((EditText) mCurrentActivity.findViewById(R.id.profile_email))
                        .getText()
                        .toString();
                String mobile = ((EditText) mCurrentActivity.findViewById(R.id.profile_mobile))
                        .getText()
                        .toString();
                // overwrite the data at the specified user id.
                uniqueRef.setValue(new User(name, gtid, email, mobile));
                Snackbar.make(v, "Save successfully!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void gotoSidebar() {
        Intent i = new Intent(ProfileActivity.this, SideBarActivity.class);
        startActivity(i);
    }
}
