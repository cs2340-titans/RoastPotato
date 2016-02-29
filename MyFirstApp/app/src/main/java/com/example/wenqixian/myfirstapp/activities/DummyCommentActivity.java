package com.example.wenqixian.myfirstapp.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.wenqixian.myfirstapp.R;

public class DummyCommentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_comment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        String movieId = bundle.getString("movie-id");
        View frame = findViewById(R.id.main_frame);
        TextView text = (TextView) frame.findViewById(R.id.movie_id);
        text.setText(movieId);
    }

}
