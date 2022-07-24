package com.example.movie.responses;

import com.example.movie.models.TvShowDetailsModel;
import com.google.gson.annotations.SerializedName;

public class TvShowDetailsResponse {

    @SerializedName("tvShow")
    private TvShowDetailsModel tvShowDetailsModel;

    public TvShowDetailsModel getTvShowDetailsModel() {
        return tvShowDetailsModel;
    }
}
