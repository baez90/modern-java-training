package com.github.baez90.training.server.jokes;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import static org.apache.commons.text.StringEscapeUtils.unescapeHtml4;

public class JokeAdapter extends TypeAdapter<Joke> {

    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter jsonWriter, Joke joke) throws IOException {
        throw new UnsupportedOperationException("do not write jokes directly because the joke itself has to be formatted");
    }

    @Override
    public Joke read(JsonReader jsonReader) throws IOException {
        String joke = "";
        String[] categories = new String[0];
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            var token = jsonReader.peek();
            switch (token) {
                case NAME:
                    switch (jsonReader.nextName()) {
                        case "joke":
                            joke = jsonReader.nextString();
                            break;
                        case "categories":
                            categories = gson.fromJson(jsonReader, String[].class);
                            break;
                        default:
                            jsonReader.nextString();
                    }
                    break;
            }
        }
        jsonReader.endObject();
        return new Joke(unescapeHtml4(joke), categories);
    }
}
