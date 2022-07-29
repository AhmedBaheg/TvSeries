package com.example.movie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.R;
import com.example.movie.databinding.LayoutContainerEpisodeBinding;
import com.example.movie.models.EpisodeModel;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {

    private List<EpisodeModel> episodeModelList;
    private String  imageView;
    private LayoutInflater layoutInflater;

    public void setEpisodeModelList(List<EpisodeModel> episodeModelList) {
        this.episodeModelList = episodeModelList;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutContainerEpisodeBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.layout_container_episode, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindingEpisodes(episodeModelList.get(position));
    }

    @Override
    public int getItemCount() {
        if (episodeModelList != null){
            return episodeModelList.size();
        }else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private LayoutContainerEpisodeBinding binding;

        public ViewHolder(LayoutContainerEpisodeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindingEpisodes(EpisodeModel model){
            String title = "S";
            String  season = model.getSeason();
            if (season.length() == 1){
                season = "0".concat(season);
            }
            String episodesNum = model.getEpisode();
            if (episodesNum.length() == 1){
                episodesNum = "0".concat(episodesNum);
            }
            episodesNum = "E".concat(episodesNum);
            title = title.concat(season).concat(episodesNum);

            binding.setTitle(title);
            binding.setName(model.getName());
            binding.setAirDate(model.getAir_date());
            binding.setImage(imageView);
        }
    }

}
