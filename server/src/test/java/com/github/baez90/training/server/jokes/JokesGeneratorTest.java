package com.github.baez90.training.server.jokes;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JokesGeneratorTest {

    private static final Logger logger = LoggerFactory.getLogger(JokesGeneratorTest.class);
    private final JokesGenerator jokesGenerator = new JokesGenerator();

    @Test
    public void testLocalJokes_expectStreamOfJokes() {
        assertNotEquals(0, jokesGenerator.localJokes().count());
    }

    @Test
    public void testOnlineJokes_expectAnyNonNullJoke() throws ExecutionException, InterruptedException {
        var joke = jokesGenerator.onlineJokes().findFirst().get().get().orElse(null);
        logger.info(joke.getJoke());
        assertNotNull(joke);
    }
}