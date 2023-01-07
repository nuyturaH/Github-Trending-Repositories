package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos;

import androidx.lifecycle.ViewModel;

import com.harutyun.domain.usecases.GetTrendingReposByNameCreatedLaterThanXUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GithubTrendingReposViewModel extends ViewModel {

    private final GetTrendingReposByNameCreatedLaterThanXUseCase mGetTrendingReposByNameCreatedLaterThanXUseCase;

    @Inject
    public GithubTrendingReposViewModel(GetTrendingReposByNameCreatedLaterThanXUseCase
                                                getTrendingReposByNameCreatedLaterThanXUseCase) {
        mGetTrendingReposByNameCreatedLaterThanXUseCase = getTrendingReposByNameCreatedLaterThanXUseCase;
    }
}