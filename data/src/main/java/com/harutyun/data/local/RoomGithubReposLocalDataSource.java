package com.harutyun.data.local;

import com.harutyun.data.local.entities.GithubRepoLocalEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class RoomGithubReposLocalDataSource implements GithubReposLocalDataSource {

    private final RoomGithubReposDao mGithubReposDao;

    public RoomGithubReposLocalDataSource(RoomGithubReposDao githubReposDao) {
        mGithubReposDao = githubReposDao;
    }

    @Override
    public Single<List<GithubRepoLocalEntity>> getFavouriteRepos() {
        return mGithubReposDao.getAllFavouriteRepos();
    }

    @Override
    public Completable insertRepo(GithubRepoLocalEntity entity) {
        return mGithubReposDao.insertRepo(entity);
    }

    @Override
    public Completable deleteRepo(GithubRepoLocalEntity entity) {
        return mGithubReposDao.deleteRepo(entity);
    }
}
