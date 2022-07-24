package com.example.movie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.R;
import com.example.movie.databinding.ItemContainerTvShowBinding;
import com.example.movie.listeners.TvShowListener;
import com.example.movie.models.TvShowModel;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private List<TvShowModel> tvShowModelList;
    private LayoutInflater layoutInflater;
    private TvShowListener tvShowListener;

    public TvShowAdapter(List<TvShowModel> tvShowModelList, TvShowListener tvShowListener) {
        this.tvShowModelList = tvShowModelList;
        this.tvShowListener = tvShowListener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_container_tv_show, parent, false);
        return new TvShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bindingTvShow(tvShowModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowModelList.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerTvShowBinding binding;

        public TvShowViewHolder(ItemContainerTvShowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindingTvShow(TvShowModel tvShowModel) {
            binding.setTvShow(tvShowModel);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvShowListener.onTvShowClicked(tvShowModel);
                }
            });
        }

    }

}
