package com.harutyun.data.local;

import com.harutyun.data.local.entities.GithubRepoEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface GithubReposLocalDataSource {

    Single<List<GithubRepoEntity>> getFavouriteRepos();

    void insertRepo(GithubRepoEntity entity);

    void deleteRepo(GithubRepoEntity entity);
}
