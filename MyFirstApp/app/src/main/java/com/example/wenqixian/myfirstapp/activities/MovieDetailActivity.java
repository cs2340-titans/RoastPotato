package com.example.wenqixian.myfirstapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.wenqixian.myfirstapp.R;

public class MovieDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.rating_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(MovieDetailActivity.this, RatingActivity.class);
                startActivity(p);
            }
        });

    }


}
