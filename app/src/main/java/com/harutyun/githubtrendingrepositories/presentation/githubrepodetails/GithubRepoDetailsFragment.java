package com.harutyun.githubtrendingrepositories.presentation.githubrepodetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.harutyun.githubtrendingrepositories.R;
import com.harutyun.githubtrendingrepositories.databinding.FragmentGithubRepoDetailsBinding;
import com.harutyun.githubtrendingrepositories.helper.UiHelper;


public class GithubRepoDetailsFragment extends Fragment {

    private FragmentGithubRepoDetailsBinding mBinding;
    private GithubRepoDetailsReposViewModel mGithubRepoDetailsReposViewModel;
    private String mGithubPageUrl;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentGithubRepoDetailsBinding.inflate(inflater, container, false);
        resizeFragment(mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();

        setupObservables();

        setupViewListeners();
    }


    private void setupObservables() {
        mGithubRepoDetailsReposViewModel.getCurrentRepoDataLiveData().observe(getViewLifecycleOwner(), githubRepo -> {
            mBinding.tvNameRepoDetails.setText(githubRepo.getName());
            mBinding.tvDescriptionRepoDetails.setText(githubRepo.getDescription());
            mBinding.tvStargazersCountRepoDetails.setText(String.valueOf(githubRepo.getStargazersCount()));
            mBinding.tvLoginRepoDetails.setText(githubRepo.getOwner().getLogin());
            Glide.with(requireContext())
                    .load(githubRepo.getOwner().getAvatarUrl())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.ic_person_box)
                    .into(mBinding.ivAvatarRepoDetails);
            mBinding.tvLanguageRepoDetails.setVisibility(githubRepo.getLanguage() == null ? View.GONE : View.VISIBLE);
            mBinding.tvLanguageRepoDetails.setText(githubRepo.getLanguage());
            mBinding.tvForksRepoDetails.setVisibility(githubRepo.getLanguage() == null ? View.GONE : View.VISIBLE);
            mBinding.tvForksRepoDetails.setText(String.valueOf(githubRepo.getForksCount()));
            mBinding.tvDateRepoDetails.setText(githubRepo.getCreatedAt());
            mGithubPageUrl = githubRepo.getHtmlUrl();
        });
    }


    private void setupViewListeners() {
        mBinding.tvLinkRepoDetails.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mGithubPageUrl));
            startActivity(browserIntent);
        });
    }


    private void initViewModel() {
        mGithubRepoDetailsReposViewModel = new ViewModelProvider(requireActivity()).get(GithubRepoDetailsReposViewModel.class);
    }

    private void resizeFragment(View view) {
        LayoutParams params = view.getLayoutParams();
        params.height = UiHelper.getDisplayHeight(requireActivity());
        mBinding.getRoot().setLayoutParams(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}