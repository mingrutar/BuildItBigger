package com.udacity.gradle.builditbigger;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.coderming.myandroidlib.MessageDisplayActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName()+"PAID";

    private ProgressBar mProgressBar;

     NewJokeListener mHandler = new NewJokeListener () {
        public void updateJoke(String jokeStr) {
            mProgressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(getContext(), MessageDisplayActivity.class);
            intent.putExtra(MessageDisplayActivity.JOKE_MESSAGE, jokeStr);
            startActivity(intent);
        }
    };
    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar1);
        mProgressBar.setVisibility(View.INVISIBLE);

        root.findViewById(R.id.btn_joke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick();
            }
        });
        return root;
    }
    private void handleClick() {
        Log.v(LOG_TAG, "+++++ handleClick called");
        new JokeFetcherAsyncTask().execute(mHandler);
    }
 }
