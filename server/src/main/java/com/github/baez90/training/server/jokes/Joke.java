package com.github.baez90.training.server.jokes;

public class Joke {

    private final String joke;
    private final String[] categories;

    public Joke(String joke, String[] categories) {
        this.joke = joke;
        this.categories = categories;
    }

    public String getJoke() {
        return this.getJoke("Chuck", "Norris");
    }

    public String getJoke(String firstName, String lastName) {
        return String.format(this.joke, firstName, lastName);
    }

    public String[] getCategories() {
        return categories;
    }
}
