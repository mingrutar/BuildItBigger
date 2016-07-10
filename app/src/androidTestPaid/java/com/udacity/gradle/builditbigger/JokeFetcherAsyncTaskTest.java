package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

public class JokeFetcherAsyncTaskTest extends AndroidTestCase {
    String jokeStr = null;

    public void testJokeFetcher() {
        new JokeFetcherAsyncTask() {
            @Override
            protected void onPostExecute(String result) {
                jokeStr = result;
            }
        }.execute();
        assert (jokeStr != null);
    }
}
