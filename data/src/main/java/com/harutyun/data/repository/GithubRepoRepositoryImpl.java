package com.harutyun.data.repository;

import com.harutyun.data.remote.GithubReposRemoteDataSource;
import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.repository.GithubRepoRepository;

import java.util.Date;
import java.util.List;

public class GithubRepoRepositoryImpl implements GithubRepoRepository {

    private final GithubReposRemoteDataSource mGithubReposRemoteDataSource;

    public GithubRepoRepositoryImpl(GithubReposRemoteDataSource githubReposRemoteDataSource) {
        mGithubReposRemoteDataSource = githubReposRemoteDataSource;
    }

    @Override
    public List<GithubRepo> GetTrendingReposByNameCreatedLaterThanXUseCase(String name, Date x) {
        return null;
    }
}
