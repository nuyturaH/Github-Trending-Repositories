package com.harutyun.data.remote;

import androidx.paging.PagingData;

import com.harutyun.domain.models.GithubRepo;

import java.util.Date;

import io.reactivex.rxjava3.core.Flowable;

public interface GithubReposRemoteDataSource {

    Flowable<PagingData<GithubRepo>> getTrendingReposByNameCreatedLaterThanXUseCase(String name, Date x);
}
