package com.harutyun.domain.repository;

import androidx.paging.PagingData;

import com.harutyun.domain.models.GithubRepo;

import java.util.Date;

import io.reactivex.rxjava3.core.Flowable;

public interface GithubRepoRepository {
    Flowable<PagingData<GithubRepo>> getTrendingReposByNameCreatedLaterThanX(String name, Date x);
}
