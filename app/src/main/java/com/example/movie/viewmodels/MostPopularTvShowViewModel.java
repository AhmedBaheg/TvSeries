package com.example.movie.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movie.responses.TvShowResponse;
import com.example.movie.respositories.MostPopularTvShowRepository;

public class MostPopularTvShowViewModel extends ViewModel {

    private MostPopularTvShowRepository repository;

    public MostPopularTvShowViewModel() {
        repository = new MostPopularTvShowRepository();
    }

    public LiveData<TvShowResponse> getMostPopularTvShow(int page){
        return repository.getMostPopularTvShow(page);
    }
}
