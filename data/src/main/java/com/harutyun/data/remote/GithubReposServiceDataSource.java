package com.harutyun.data.remote;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.harutyun.data.BuildConfig;
import com.harutyun.data.helpers.DateHelper;
import com.harutyun.data.local.GithubReposLocalDataSource;
import com.harutyun.data.mappers.GithubRepoMapper;
import com.harutyun.data.paging.GithubReposPagingDataSource;
import com.harutyun.domain.models.GithubRepo;

import java.util.Date;

import io.reactivex.rxjava3.core.Flowable;

public class GithubReposServiceDataSource implements GithubReposRemoteDataSource {
    private final GithubReposService mGithubReposService;
    private final GithubReposLocalDataSource mGithubReposLocalDataSource;
    private final GithubRepoMapper mGithubRepoMapper;

    public GithubReposServiceDataSource(GithubReposService githubReposService,
                                        GithubReposLocalDataSource githubReposLocalDataSource,
                                        GithubRepoMapper githubRepoMapper) {
        mGithubReposService = githubReposService;
        mGithubReposLocalDataSource = githubReposLocalDataSource;
        mGithubRepoMapper = githubRepoMapper;
    }

    @Override
    public Flowable<PagingData<GithubRepo>> getTrendingReposByNameCreatedLaterThanXUseCase(String name, Date x) {
        final String query = name + "+created:>" + DateHelper.dateToString(x);
        final Pager<Integer, GithubRepo> pager = new Pager<>(new PagingConfig(BuildConfig.GITHUB_REPOS_PAGE_SIZE), () -> new GithubReposPagingDataSource(mGithubReposService, mGithubReposLocalDataSource, query, mGithubRepoMapper));
        return PagingRx.getFlowable(pager);
    }
}
