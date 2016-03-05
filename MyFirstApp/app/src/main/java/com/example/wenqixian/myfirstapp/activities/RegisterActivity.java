package com.example.wenqixian.myfirstapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wenqixian.myfirstapp.R;
import com.example.wenqixian.myfirstapp.singletons.FirebaseSingleton;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Activity mCurrentActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentActivity = this;
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button confirm = (Button) findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase myFirebaseRef = FirebaseSingleton.getInstance().ref();
                String username = ((EditText) mCurrentActivity.findViewById(R.id.username_register))
                        .getText()
                        .toString();
                String password = ((EditText) mCurrentActivity.findViewById(R.id.password_register))
                        .getText()
                        .toString();
                myFirebaseRef.createUser(username, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        System.out.println("Successfully created user account with uid: " + result.get("uid"));
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "Submit successfully! Thank you ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        setResult(getResources().getInteger(R.integer.register_success));
                        finish();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        System.out.println("Failed to  created user account" + firebaseError.toString());
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "Submit failed! Thank you ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        setResult(getResources().getInteger(R.integer.register_failed));
                        finish();
                    }
                });

            }
        });
    }

}
