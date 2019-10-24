package com.github.baez90.training.server.jokes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.SECONDS;

public final class JokesGenerator {

    private static final Logger logger = LoggerFactory.getLogger(JokesGenerator.class);

    private final Gson gson;
    private final Joke[] localJokes;

    public JokesGenerator() {
        Joke[] readJokes = new Joke[0];
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Joke.class, new JokeAdapter())
                .create();

        try (var reader = new InputStreamReader(getClass().getResourceAsStream("/jokes.json"))) {
            readJokes = gson.fromJson(reader, Joke[].class);
        } catch (IOException e) {
            logger.error("failed to read and deserialize jokes.json", e);
        }
        this.localJokes = readJokes;
    }

    public Stream<Joke> localJokes() {
        return Stream.of(localJokes);
    }

    public Stream<CompletableFuture<Optional<Joke>>> onlineJokes() {
        return Stream.generate(() -> {
            var httpClient = HttpClient
                    .newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .connectTimeout(Duration.of(2, SECONDS))
                    .build();

            var jokeRequest = HttpRequest
                    .newBuilder(URI.create("https://api.icndb.com/jokes/random"))
                    .timeout(Duration.of(2, SECONDS))
                    .GET()
                    .build();

            return httpClient
                    .sendAsync(jokeRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> (response.body() == null || response.body().isBlank() || response.body().isEmpty()) ? Optional.<String>empty() : Optional.of(response.body()))
                    .thenApply(stringBodyOptional -> stringBodyOptional.map(s -> ((ICNDBAPIResponse<Joke>)gson.fromJson(s, new TypeToken<ICNDBAPIResponse<Joke>>(){}.getType())).getValue()));
        });
    }
}
