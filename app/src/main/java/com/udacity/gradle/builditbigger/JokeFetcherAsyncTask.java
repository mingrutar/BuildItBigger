package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.example.Joker;
import com.example.coderming.myjoker.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by linna on 7/8/2016.
 */
public class JokeFetcherAsyncTask extends AsyncTask<NewJokeListener, Void, String> {
    private static final String LOG_TAG = JokeFetcherAsyncTask.class.getSimpleName();
    private static MyApi mApiService;
    private NewJokeListener mJokeChangeListener;

    @Override
    protected String doInBackground(NewJokeListener ... params) {
       Log.d(LOG_TAG, "+++++doInBackground is called");
       if  (null == mApiService) {
           MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                   new AndroidJsonFactory(), null);
           // options for running against local devappserver
           // - 10.0.2.2 is localhost's IP address in Android emulator
           // - turn off compression when running against local devappserver
           builder.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                   .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                       @Override
                       public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                           abstractGoogleClientRequest.setDisableGZipContent(true);
                       }
                   });
           // end options for devappserver

           mApiService = builder.build();
           Log.d(LOG_TAG, "++++mApiService is built");
       }
        mJokeChangeListener = params[0];

        try {
            String jokeStr = mApiService.sayJoke().execute().getData();
            Log.d(LOG_TAG, "++++ fetched: "+jokeStr);
            return jokeStr;
        } catch (IOException ex) {
            return  null;
        }
    }
    @Override
    protected void onPostExecute(String result) {
        String jokeStr = null;
        if ( null == result) {
            jokeStr = new Joker().getJoke();
        } else {
            jokeStr = result;
        }
        Log.d(LOG_TAG, "++++ onPostExecute: result= "+jokeStr);
        if (mJokeChangeListener != null) {
            mJokeChangeListener.updateJoke(jokeStr);
        }
    }
}
