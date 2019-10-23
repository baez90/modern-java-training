package com.github.baez90.training.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.temporal.ChronoUnit.SECONDS;

public class ICNDBClient {

    private final String baseURL;
    private final HttpClient client;

    public ICNDBClient(String baseURL) {
        this.baseURL = baseURL;
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.of(1, SECONDS))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    public CompletionStage<String> getEcho(String name) throws URISyntaxException {
        var request = HttpRequest.newBuilder(new URI(String.format("%s/api/hello/%s", this.baseURL, URLEncoder.encode(name, UTF_8))))
                .timeout(Duration.of(1, SECONDS))
                .GET()
                .build();

        return this.client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApplyAsync(HttpResponse::body).thenApplyAsync(s -> URLDecoder.decode(s, UTF_8));
    }
}
