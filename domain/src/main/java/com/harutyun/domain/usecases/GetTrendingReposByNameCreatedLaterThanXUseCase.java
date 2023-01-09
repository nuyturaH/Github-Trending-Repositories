package com.harutyun.domain.usecases;

import androidx.paging.PagingData;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.repository.GithubRepoRepository;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class GetTrendingReposByNameCreatedLaterThanXUseCase {

    private final GithubRepoRepository mGithubRepoRepository;

    public GetTrendingReposByNameCreatedLaterThanXUseCase(GithubRepoRepository repository) {
        mGithubRepoRepository = repository;
    }

    public Flowable<PagingData<GithubRepo>> invoke(String name, Date x) {
        return mGithubRepoRepository.getTrendingReposByNameCreatedLaterThanX(name, x);
    }
}
