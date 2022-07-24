package com.example.movie.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.movie.R;
import com.example.movie.adapters.TvShowAdapter;
import com.example.movie.databinding.ActivityMainBinding;
import com.example.movie.listeners.TvShowListener;
import com.example.movie.models.TvShowModel;
import com.example.movie.viewmodels.MostPopularTvShowViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TvShowListener {

    private MostPopularTvShowViewModel viewModel;
    private ActivityMainBinding binding;
    private final List<TvShowModel> tvShowModelList = new ArrayList<>();
    private TvShowAdapter adapter;
    private int currentPage = 1;
    private int totalPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initialization();

    }

    private void initialization() {
        binding.tvShowRecycler.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowViewModel.class);
        adapter = new TvShowAdapter(tvShowModelList, this);
        binding.tvShowRecycler.setAdapter(adapter);
        binding.tvShowRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.tvShowRecycler.canScrollVertically(1)){
                    if (currentPage <= totalPage){
                        currentPage +=1 ;
                        getMostPopularTvShow();
                    }
                }
            }
        });
        getMostPopularTvShow();
    }

    private void getMostPopularTvShow() {
        toggleLoading(); // here will equal null so will set progress true to be Visible
        viewModel.getMostPopularTvShow(currentPage).observe(this, tvShowResponse -> {
            toggleLoading(); // here will not equal null so will set progress false to be Gone
            if (tvShowResponse != null) {
                totalPage = tvShowResponse.getPages();
                if (tvShowResponse.getTvShowModels() != null) {
                    tvShowModelList.addAll(tvShowResponse.getTvShowModels());
                    int oldCount = tvShowModelList.size();
                    adapter.notifyItemRangeInserted(oldCount, tvShowModelList.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (binding.getIsLoading() != null && binding.getIsLoading()) {
                binding.setIsLoading(false);
            } else {
                binding.setIsLoading(true);
            }
        } else {
            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore()) {
                binding.setIsLoadingMore(false);
            } else {
                binding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTvShowClicked(TvShowModel tvShowModel) {
        Intent intent = new Intent(MainActivity.this, TvShowDetailsActivity.class);
        intent.putExtra("id", tvShowModel.getId());
        intent.putExtra("name", tvShowModel.getName());
        intent.putExtra("start_date", tvShowModel.getStart_date());
        intent.putExtra("country", tvShowModel.getCountry());
        intent.putExtra("network", tvShowModel.getNetwork());
        intent.putExtra("status", tvShowModel.getStatus());
        intent.putExtra("status", tvShowModel.getStatus());
        intent.putExtra("image", tvShowModel.getImage());
        startActivity(intent);
    }
}