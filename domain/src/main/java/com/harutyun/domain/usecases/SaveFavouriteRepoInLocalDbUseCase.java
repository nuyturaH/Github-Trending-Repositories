package com.harutyun.domain.usecases;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.repository.GithubRepoRepository;

import io.reactivex.rxjava3.core.Completable;

public class SaveFavouriteRepoInLocalDbUseCase {

    private final GithubRepoRepository mGithubRepoRepository;

    public SaveFavouriteRepoInLocalDbUseCase(GithubRepoRepository githubRepoRepository) {
        mGithubRepoRepository = githubRepoRepository;
    }

    public Completable invoke(GithubRepo githubRepo) {
        githubRepo.setFavourite(true);
        return mGithubRepoRepository.saveFavouriteRepoInLocalDb(githubRepo);
    }
}
