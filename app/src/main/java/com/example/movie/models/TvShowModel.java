package com.example.movie.models;

import com.google.gson.annotations.SerializedName;

public class TvShowModel {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("start_date")
    private String start_date;

    @SerializedName("country")
    private String country;

    @SerializedName("network")
    private String network;

    @SerializedName("status")
    private String status;

    @SerializedName("image_thumbnail_path")
    private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getCountry() {
        return country;
    }

    public String getNetwork() {
        return network;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }
}
