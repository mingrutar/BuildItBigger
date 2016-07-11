package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestJokeFetch2 extends AndroidTestCase {
    static final long DELTA = 10000;          // up to 2 sec
    static final String ERR_NULL_JOKE = "The joke sent by GCE Joke provider is null";

    public void testJokeFetcher() throws InterruptedException, ExecutionException, TimeoutException {
        JokeFetcherAsyncTask task= new JokeFetcherAsyncTask();
        task.execute( new NewJokeListener () {
            @Override
            public void updateJoke(String jokeStr) {
            } });
        String errorMessage = null;
        String result = task.get(DELTA, TimeUnit.MILLISECONDS);
        assertNotNull(ERR_NULL_JOKE, result);
    }
}
