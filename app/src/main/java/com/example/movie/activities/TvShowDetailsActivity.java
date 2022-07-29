package com.example.movie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.movie.R;
import com.example.movie.adapters.EpisodesAdapter;
import com.example.movie.adapters.ImageSliderAdapter;
import com.example.movie.databinding.ActivityTvShowDetailsBinding;
import com.example.movie.databinding.LayoutEpisodesBottomSheetBinding;
import com.example.movie.viewmodels.TvShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

public class TvShowDetailsActivity extends AppCompatActivity {

    private ActivityTvShowDetailsBinding binding;
    private TvShowDetailsViewModel viewModel;
    private BottomSheetDialog bottomDialog;
    private LayoutEpisodesBottomSheetBinding bindingEpisode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);

        initialize();

    }

    private void initialize() {
        viewModel = new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        binding.imageBack.setOnClickListener(view -> onBackPressed());
        getTvShowDetails();
    }

    private void getTvShowDetails() {
        binding.setIsLoading(true);
        String tvShowID = String.valueOf(getIntent().getIntExtra("id", -1));
        viewModel.getMutableTvShowDetails(tvShowID).observe(this, tvShowDetailsResponse -> {
            binding.setIsLoading(false);
            if (tvShowDetailsResponse.getTvShowDetailsModel() != null) {
                if (tvShowDetailsResponse.getTvShowDetailsModel().getPictures() != null) {
                    loadImageSlider(tvShowDetailsResponse.getTvShowDetailsModel().getPictures());
                }
                binding.setDescription(String.valueOf(HtmlCompat.fromHtml(
                        tvShowDetailsResponse.getTvShowDetailsModel().getDescription(),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                )));
                binding.tvDescription.setVisibility(View.VISIBLE);
                binding.tvReadMore.setVisibility(View.VISIBLE);
                binding.tvReadMore.setOnClickListener(view -> {
                    if (binding.tvReadMore.getText().toString().equals("Read More")) {
                        binding.tvDescription.setMaxLines(Integer.MAX_VALUE);
                        binding.tvDescription.setEllipsize(null);
                        binding.tvReadMore.setText(R.string.read_less);
                    } else {
                        binding.tvDescription.setMaxLines(4);
                        binding.tvDescription.setEllipsize(TextUtils.TruncateAt.END);
                        binding.tvReadMore.setText(R.string.read_more);
                    }
                });
                binding.setRating(String.format(Locale.getDefault(), "%.2f",
                        Double.parseDouble(tvShowDetailsResponse.getTvShowDetailsModel().getRating())));
                if (tvShowDetailsResponse.getTvShowDetailsModel().getGenres() != null) {
                    binding.setGenre(tvShowDetailsResponse.getTvShowDetailsModel().getGenres()[0]);
                } else {
                    binding.setGenre("N/A");
                }
                binding.setRuntime(tvShowDetailsResponse.getTvShowDetailsModel().getRuntime() + " Min");
                binding.viewDivider1.setVisibility(View.VISIBLE);
                binding.layoutMisc.setVisibility(View.VISIBLE);
                binding.viewDivider2.setVisibility(View.VISIBLE);
                binding.btnWebsite.setOnClickListener(view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetailsModel().getUrl()));
                    startActivity(intent);
                });
                binding.btnWebsite.setVisibility(View.VISIBLE);
                binding.btnEpisodes.setVisibility(View.VISIBLE);
                loadIntentTvShowDetails();
                binding.btnEpisodes.setOnClickListener(view -> {
                    if (bottomDialog == null) {
                        bottomDialog = new BottomSheetDialog(TvShowDetailsActivity.this);
                        bindingEpisode = DataBindingUtil.inflate(
                                LayoutInflater.from(TvShowDetailsActivity.this), R.layout.layout_episodes_bottom_sheet,
                                findViewById(R.id.episodesContainer), false);
                        bottomDialog.setContentView(bindingEpisode.getRoot());
                        EpisodesAdapter adapter = new EpisodesAdapter();
                        adapter.setEpisodeModelList(tvShowDetailsResponse.getTvShowDetailsModel().getEpisodes());
                        bindingEpisode.episodesRecycler.setAdapter(adapter);
                        adapter.setImageView(getIntent().getStringExtra("image"));
                        bindingEpisode.tvTitle.setText(String.format("Episodes | %s", getIntent().getStringExtra("name")));
                        bindingEpisode.imageClose.setOnClickListener(view1 -> bottomDialog.dismiss());

                        /** this make the bottom dialog match parent */
                        // ----- Optional section start -----
//                            FrameLayout frameLayout = bottomDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
//                            if (frameLayout != null) {
//                                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
//                                bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
//                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                            }

                        bottomDialog.show();
                    }
                });
            }
        });
    }

    private void loadImageSlider(String[] slideImages) {
        binding.sliderViewPager.setOffscreenPageLimit(1);
        binding.sliderViewPager.setAdapter(new ImageSliderAdapter(slideImages));
        binding.sliderViewPager.setVisibility(View.VISIBLE);
        binding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(slideImages.length);
        binding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicators(int count) {
        ImageView[] indicator = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicator.length; i++) {
            indicator[i] = new ImageView(this);
            indicator[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.background_slider_indicator_inactive));
            indicator[i].setLayoutParams(layoutParams);
            binding.linearSliderIndicators.addView(indicator[i]);
        }
        binding.linearSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = binding.linearSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.linearSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.background_slider_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.background_slider_indicator_inactive));
            }
        }
    }

    private void loadIntentTvShowDetails() {
        binding.setTvShowImageURL(getIntent().getStringExtra("image"));
        binding.setTvShowName(getIntent().getStringExtra("name"));
        binding.setNetworkCountry(getIntent().getStringExtra("network") + " (" +
                getIntent().getStringExtra("country") + ")");
        binding.setStatus(getIntent().getStringExtra("status"));
        binding.setStartDate(getIntent().getStringExtra("start_date"));

        binding.imageTvShow.setVisibility(View.VISIBLE);
        binding.tvName.setVisibility(View.VISIBLE);
        binding.tvNetworkCountry.setVisibility(View.VISIBLE);
        binding.tvStatus.setVisibility(View.VISIBLE);
        binding.tvStartDate.setVisibility(View.VISIBLE);
    }

}