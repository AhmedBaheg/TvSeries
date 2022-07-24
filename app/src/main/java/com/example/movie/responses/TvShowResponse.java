package com.example.movie.responses;

import com.example.movie.models.TvShowModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int pages;

    @SerializedName("tv_shows")
    private List<TvShowModel> tvShowModels;

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public List<TvShowModel> getTvShowModels() {
        return tvShowModels;
    }
}
