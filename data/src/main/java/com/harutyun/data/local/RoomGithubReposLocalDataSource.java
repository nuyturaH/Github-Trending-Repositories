package com.harutyun.data.local;

import com.harutyun.data.local.entities.GithubRepoEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class RoomGithubReposLocalDataSource implements GithubReposLocalDataSource {

    private final RoomGithubReposDao mGithubReposDao;

    public RoomGithubReposLocalDataSource(RoomGithubReposDao githubReposDao) {
        mGithubReposDao = githubReposDao;
    }

    @Override
    public Single<List<GithubRepoEntity>> getFavouriteRepos() {
        return mGithubReposDao.getAllFavouriteRepos();
    }

    @Override
    public void insertRepo(GithubRepoEntity entity) {
        mGithubReposDao.insertRepo(entity);
    }

    @Override
    public void deleteRepo(GithubRepoEntity entity) {
        mGithubReposDao.deleteRepo(entity);
    }
}
