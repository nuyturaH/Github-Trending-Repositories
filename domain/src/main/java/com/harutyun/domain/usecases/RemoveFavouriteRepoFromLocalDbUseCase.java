package com.harutyun.domain.usecases;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.repository.GithubRepoRepository;

import io.reactivex.rxjava3.core.Completable;

public class RemoveFavouriteRepoFromLocalDbUseCase {

    private final GithubRepoRepository mGithubRepoRepository;

    public RemoveFavouriteRepoFromLocalDbUseCase(GithubRepoRepository githubRepoRepository) {
        mGithubRepoRepository = githubRepoRepository;
    }

    public Completable invoke(GithubRepo githubRepo) {
        return mGithubRepoRepository.removeFavouriteRepoInLocalDb(githubRepo);
    }
}
