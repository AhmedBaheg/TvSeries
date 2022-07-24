package com.example.movie.network;

import com.example.movie.responses.TvShowDetailsResponse;
import com.example.movie.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TvShowResponse> getMostPopularTvs(@Query("page") int page);

    @GET("show-details")
    Call<TvShowDetailsResponse> getTvShowDetails(@Query("q") String tvShowID);

}
