package com.example.movie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movie.responses.TvShowDetailsResponse;
import com.example.movie.respositories.TvShowDetailsRepository;

public class TvShowDetailsViewModel extends ViewModel {

    private TvShowDetailsRepository repository;

    public TvShowDetailsViewModel() {
        repository = new TvShowDetailsRepository();
    }

    public LiveData<TvShowDetailsResponse> getMutableTvShowDetails(String tvShowID){
        return repository.getMutableTvShowDetails(tvShowID);
    }

}
