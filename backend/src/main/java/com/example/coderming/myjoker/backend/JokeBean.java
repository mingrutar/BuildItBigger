package com.example.coderming.myjoker.backend;

import java.util.Random;

/**
 * Created by linna on 7/8/2016.
 */
public class JokeBean {
    private String[] myJokes = {" joke 1",
            "A bear walks into a bar and says to the bartender, \"I'll have a pint of beer",
            "Never play leapfrog with a unicorn.",
            "No Mrs, I just thought that maybe you are lonely being the only one standing." };
    Random mRandom = new Random();
    String category;

    public String getData() {
        int idx = Math.abs(mRandom.nextInt()) % myJokes.length;
        return myJokes[idx];
    }
}
