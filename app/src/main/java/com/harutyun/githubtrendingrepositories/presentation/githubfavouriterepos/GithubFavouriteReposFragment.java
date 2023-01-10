package com.harutyun.githubtrendingrepositories.presentation.githubfavouriterepos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harutyun.githubtrendingrepositories.R;

public class GithubFavouriteReposFragment extends Fragment {

    private GithubFavouriteReposViewModel mGithubFavouriteReposViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github_favourite_repos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
    }

    private void initViewModel() {
        mGithubFavouriteReposViewModel = new ViewModelProvider(this).get(GithubFavouriteReposViewModel.class);
    }
}