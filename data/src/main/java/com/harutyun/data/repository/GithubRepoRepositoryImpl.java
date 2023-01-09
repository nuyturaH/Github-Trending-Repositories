package com.harutyun.data.repository;

import androidx.paging.PagingData;

import com.harutyun.data.remote.GithubReposRemoteDataSource;
import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.repository.GithubRepoRepository;

import java.util.Date;

import io.reactivex.rxjava3.core.Flowable;

public class GithubRepoRepositoryImpl implements GithubRepoRepository {

    private final GithubReposRemoteDataSource mGithubReposRemoteDataSource;

    public GithubRepoRepositoryImpl(GithubReposRemoteDataSource githubReposRemoteDataSource) {
        mGithubReposRemoteDataSource = githubReposRemoteDataSource;
    }

    @Override
    public Flowable<PagingData<GithubRepo>> getTrendingReposByNameCreatedLaterThanX(String name, Date x) {
        return mGithubReposRemoteDataSource.getTrendingReposByNameCreatedLaterThanXUseCase(name, x);
    }
}
