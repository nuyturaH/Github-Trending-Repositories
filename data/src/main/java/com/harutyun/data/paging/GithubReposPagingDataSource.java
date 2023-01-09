package com.harutyun.data.paging;

import androidx.annotation.NonNull;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.harutyun.data.BuildConfig;
import com.harutyun.data.mappers.GithubRepoMapper;
import com.harutyun.data.remote.GithubReposService;
import com.harutyun.data.remote.entities.GithubReposResponse;
import com.harutyun.domain.models.GithubRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GithubReposPagingDataSource extends RxPagingSource<Integer, GithubRepo> {

    private final GithubReposService mGithubReposService;
    private final String mQuery;
    private final GithubRepoMapper mGithubRepoMapper;

    public GithubReposPagingDataSource(GithubReposService githubReposService, String query, GithubRepoMapper githubRepoMapper) {
        mGithubReposService = githubReposService;
        mQuery = query;
        mGithubRepoMapper = githubRepoMapper;
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, GithubRepo>> loadSingle(@NotNull LoadParams<Integer> params) {
        int page = params.getKey() == null ? BuildConfig.GITHUB_REPOS_BEGINNING_PAGE : params.getKey();

        return mGithubReposService.getRepositories(mQuery, page)
                .subscribeOn(Schedulers.io())
                .map(response -> toLoadResult(response, page, params.getLoadSize()))
                .onErrorReturn(LoadResult.Error::new);
    }


    private LoadResult<Integer, GithubRepo> toLoadResult(@NonNull GithubReposResponse response, int page, int loadSize) {
        List<GithubRepo> repositories = mGithubRepoMapper.mapToGithubRepoList(response.getItems());
        Integer prevKey = (page == BuildConfig.GITHUB_REPOS_BEGINNING_PAGE) ? null : page - 1;
        Integer nextKey = (repositories.isEmpty()) ? null : page + (loadSize / BuildConfig.GITHUB_REPOS_PAGE_SIZE);

        return new LoadResult.Page<>(repositories, prevKey, nextKey, LoadResult.Page.COUNT_UNDEFINED, LoadResult.Page.COUNT_UNDEFINED);
    }

    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, GithubRepo> state) {
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, GithubRepo> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }
}