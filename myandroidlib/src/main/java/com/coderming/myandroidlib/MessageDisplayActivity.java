package com.coderming.myandroidlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MessageDisplayActivity extends AppCompatActivity {
    private static final String LOG_TAG = MessageDisplayActivity.class.getSimpleName();

    public static final String JOKE_MESSAGE = "JOKE_MESSAGE";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_display);
        mTextView = (TextView) findViewById(R.id.displayer);

        Log.v(LOG_TAG, "++++ onCreate called");
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(LOG_TAG, "++++ onPause called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent.hasExtra(JOKE_MESSAGE)) {
            String str = intent.getStringExtra(JOKE_MESSAGE);
            Log.v(LOG_TAG, "++++ onResume, intent joke="+str);
            mTextView.setText(str);
        } else {
            Log.v(LOG_TAG, "++++ onResume, NO INTENT JOKE");
            mTextView.setText(getString(R.string.no_joke));
        }
    }
}
