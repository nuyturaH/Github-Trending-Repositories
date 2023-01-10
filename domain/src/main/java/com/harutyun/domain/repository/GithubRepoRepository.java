package com.harutyun.domain.repository;

import androidx.paging.PagingData;

import com.harutyun.domain.models.GithubRepo;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface GithubRepoRepository {
    Flowable<PagingData<GithubRepo>> getTrendingReposByNameCreatedLaterThanX(String name, Date x);

    Single<List<GithubRepo>> getFavouriteReposFromLocalDb();
}
