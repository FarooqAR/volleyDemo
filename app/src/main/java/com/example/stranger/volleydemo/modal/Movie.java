package com.example.stranger.volleydemo.modal;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by Stranger on 6/25/2015.
 */
public class Movie implements Serializable {
    String title;
    String release;
    String thumbnailUrl;
    JSONArray director;

    public JSONArray getDirector() {
        return director;
    }

    String producer;
    JSONArray cast;
    JSONArray genres;
    int score;
    int runtime;
    private long id;
    int year;

    String synopsis;



    public String getProducer() {
        return producer;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public JSONArray getCast() {
        return cast;
    }

    public JSONArray getGenres() {
        return genres;
    }

    public int getYear() {
        return year;
    }

    public void setSynopsis(String synopsis) {

        this.synopsis = synopsis;
    }

    public void setDirector(JSONArray director) {
        this.director = director;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setCast(JSONArray cast) {
        this.cast = cast;
    }

    public void setGenres(JSONArray genres) {
        this.genres = genres;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease() {
        return release;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getScore() {
        return score;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String toString() {
        return title + " " + thumbnailUrl + " " + release + " " + runtime + " " + score;
    }
}
