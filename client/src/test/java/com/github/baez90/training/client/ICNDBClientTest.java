package com.github.baez90.training.client;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
public class ICNDBClientTest {

    @Test
    public void testGetEchoLive_ExpectValidResponse() throws URISyntaxException {
        var icndbClient = new ICNDBClient("http://localhost:4567");

        assertTimeout(Duration.of(2, SECONDS), () -> {
            var echo = icndbClient.getEcho("Chuck Norris").toCompletableFuture().get();
            assertEquals("Hello, Chuck Norris", echo);
        });
    }
}