package com.harutyun.domain.usecases;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.repository.GithubRepoRepository;

import java.util.Date;
import java.util.List;

public class GetTrendingReposByNameCreatedLaterThanXUseCase {

    private final GithubRepoRepository mGithubRepoRepository;

    public GetTrendingReposByNameCreatedLaterThanXUseCase(GithubRepoRepository repository) {
        mGithubRepoRepository = repository;
    }

    public List<GithubRepo> invoke(String name, Date x) {
        return null;
    }
}
