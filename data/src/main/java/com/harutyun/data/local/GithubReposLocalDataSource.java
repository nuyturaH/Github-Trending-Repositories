package com.harutyun.data.local;

import com.harutyun.data.local.entities.GithubRepoLocalEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface GithubReposLocalDataSource {

    Single<List<GithubRepoLocalEntity>> getFavouriteRepos();

    Completable insertRepo(GithubRepoLocalEntity entity);

    Completable deleteRepo(GithubRepoLocalEntity entity);
}
