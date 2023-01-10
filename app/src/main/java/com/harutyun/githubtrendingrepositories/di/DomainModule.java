package com.harutyun.githubtrendingrepositories.di;

import com.harutyun.domain.repository.GithubRepoRepository;
import com.harutyun.domain.usecases.GetFavouriteReposFromLocalDbUseCase;
import com.harutyun.domain.usecases.GetTrendingReposByNameCreatedLaterThanXUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class DomainModule {

    @Provides
    GetTrendingReposByNameCreatedLaterThanXUseCase provideGetTrendingReposByNameCreatedLaterThanXUseCase(GithubRepoRepository githubRepoRepository) {
        return new GetTrendingReposByNameCreatedLaterThanXUseCase(githubRepoRepository);
    }

    @Provides
    GetFavouriteReposFromLocalDbUseCase provideGetFavouriteReposFromLocalDbUseCase(GithubRepoRepository githubRepoRepository) {
        return new GetFavouriteReposFromLocalDbUseCase(githubRepoRepository);
    }
}
