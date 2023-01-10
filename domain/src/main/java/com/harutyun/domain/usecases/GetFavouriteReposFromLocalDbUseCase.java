package com.harutyun.domain.usecases;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.repository.GithubRepoRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetFavouriteReposFromLocalDbUseCase {

    private final GithubRepoRepository mGithubRepoRepository;

    public GetFavouriteReposFromLocalDbUseCase(GithubRepoRepository repository) {
        mGithubRepoRepository = repository;
    }

    public Single<List<GithubRepo>> invoke() {
        return mGithubRepoRepository.getFavouriteReposFromLocalDb();
    }
}
