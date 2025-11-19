package com.example.movietrack.back.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mojtaba & Aleksander
 */

public class MovieDetails {
    private String title;
    private String year;
    private String director;
    private String actors;
    private String plot;
    private String runtime;
    private String imdbRating;
    private String poster;
    private String awards;

    @JsonProperty("Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("Year")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @JsonProperty("Director")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @JsonProperty("Actors")
    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @JsonProperty("Plot")
    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @JsonProperty("Runtime")
    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    @JsonProperty("imdbRating")
    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
    @JsonProperty("Poster")
    public String getPoster() {
        return poster;
    }

    @JsonProperty("Awards")
    public String getAwards() {
        return awards;
    }
}