package com.example.stranger.volleydemo;

/**
 * Created by Stranger on 6/25/2015.
 */
public class Movie {
    String title;
    String release;
    String thumbnailUrl;
    int score;
    int runtime;
    private long id;

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

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
    public String toString(){
        return title+" "+thumbnailUrl+" "+release+" "+runtime+ " "+score;
    }
}
