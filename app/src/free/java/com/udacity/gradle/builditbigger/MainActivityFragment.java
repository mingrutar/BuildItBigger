package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.coderming.myandroidlib.MessageDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName()+"FREE";

    // see https://firebase.google.com/docs/admob/android/interstitial
    private InterstitialAd mInterstitialAd;

    private ProgressBar mProgressBar;
    private String mLastJoke;
    private Boolean mAdClose;

    AdRequest.Builder mAdRequest;

    public MainActivityFragment() {
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
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        mAdRequest = new AdRequest.Builder()
                .addTestDevice("ABCDEF012345");
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        mAdView.loadAd(mAdRequest.build());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInterstitialAd = new InterstitialAd(getActivity());
        // dummy id for Banner ca-app-pub-3940256099942544/6300978111
        // dummy id for interstitialAd ca-app-pub-3940256099942544/1033173712
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitial();
                synchronized (mAdClose ) {
                    if (mLastJoke != null) {
                        showJoke(mLastJoke);
                    } else {
                        mAdClose = true;
                    }
                }
            }
        });
        requestNewInterstitial();
    }

    private void showJoke(String jokeStr) {
        mProgressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getContext(), MessageDisplayActivity.class);
        intent.putExtra(MessageDisplayActivity.JOKE_MESSAGE, jokeStr);
        startActivity(intent);
    }
    NewJokeListener mHandler = new NewJokeListener () {
        public void updateJoke(String jokeStr) {
            synchronized (mAdClose ) {
                if (mAdClose) {
                    showJoke(jokeStr);
                } else {
                    mLastJoke = jokeStr;
                }
            }
        }
    };
    private void handleClick() {
        mLastJoke = null;
        mProgressBar.setVisibility(View.VISIBLE);
        new JokeFetcherAsyncTask().execute(mHandler);
        if (mInterstitialAd.isLoaded()) {
            mAdClose = false;
            mInterstitialAd.show();
        } else {
            mAdClose = true;
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("ABCDEF012345")   // SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}
