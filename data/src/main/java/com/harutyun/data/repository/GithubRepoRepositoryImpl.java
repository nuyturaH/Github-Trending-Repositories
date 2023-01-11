package com.harutyun.data.repository;

import androidx.paging.PagingData;

import com.harutyun.data.local.GithubReposLocalDataSource;
import com.harutyun.data.mappers.GithubRepoMapper;
import com.harutyun.data.remote.GithubReposRemoteDataSource;
import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.repository.GithubRepoRepository;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GithubRepoRepositoryImpl implements GithubRepoRepository {

    private final GithubReposRemoteDataSource mGithubReposRemoteDataSource;
    private final GithubReposLocalDataSource mGithubReposLocalDataSource;
    private final GithubRepoMapper mGithubRepoMapper;

    public GithubRepoRepositoryImpl(GithubReposRemoteDataSource githubReposRemoteDataSource,
                                    GithubReposLocalDataSource githubReposLocalDataSource,
                                    GithubRepoMapper githubRepoMapper) {
        mGithubReposRemoteDataSource = githubReposRemoteDataSource;
        mGithubReposLocalDataSource = githubReposLocalDataSource;
        mGithubRepoMapper = githubRepoMapper;
    }

    @Override
    public Flowable<PagingData<GithubRepo>> getTrendingReposByNameCreatedLaterThanX(String name, Date x) {
        return mGithubReposRemoteDataSource.getTrendingReposByNameCreatedLaterThanXUseCase(name, x);
    }

    @Override
    public Single<List<GithubRepo>> getFavouriteReposFromLocalDb() {
        return mGithubReposLocalDataSource.getFavouriteRepos().subscribeOn(Schedulers.io()).map(mGithubRepoMapper::mapToGithubRepoListFromLocal);
    }

    @Override
    public Completable saveFavouriteRepoInLocalDb(GithubRepo githubRepo) {
        return mGithubReposLocalDataSource.insertRepo(mGithubRepoMapper.mapToGithubRepoLocalEntity(githubRepo));
    }

    @Override
    public Completable removeFavouriteRepoInLocalDb(GithubRepo githubRepo) {
        return mGithubReposLocalDataSource.deleteRepo(mGithubRepoMapper.mapToGithubRepoLocalEntity(githubRepo));
    }
}
