package com.example.wenqixian.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    private Activity mCurrentActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentActivity = this;
        setContentView(R.layout.activity_welcome);
        Button loginButton = (Button) findViewById(R.id.sign_in_button);
        Button registerButton = (Button) findViewById(R.id.register_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mCurrentActivity, LoginActivity.class), 1);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mCurrentActivity, RegisterActivity.class), 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            System.out.println(resultCode);
            if(resultCode == 0){
                finish();
            }
            if (resultCode == 1) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
