package com.example;

import org.junit.Test;

public class JokerTest {
    @Test
    public void getJokeTest() {
        Joker joker = new Joker();
        String joke = joker.getJoke();
        assert (joke instanceof String);
    }
}