package com.harutyun.data.paging;

import androidx.annotation.NonNull;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.harutyun.data.BuildConfig;
import com.harutyun.data.local.GithubReposLocalDataSource;
import com.harutyun.data.local.entities.GithubRepoLocalEntity;
import com.harutyun.data.mappers.GithubRepoMapper;
import com.harutyun.data.remote.GithubReposService;
import com.harutyun.data.remote.entities.GithubReposResponse;
import com.harutyun.domain.models.GithubRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GithubReposPagingDataSource extends RxPagingSource<Integer, GithubRepo> {

    private final GithubReposService mGithubReposService;
    private final GithubReposLocalDataSource mGithubReposLocalDataSource;
    private final String mQuery;
    private final GithubRepoMapper mGithubRepoMapper;

    public GithubReposPagingDataSource(GithubReposService githubReposService, GithubReposLocalDataSource githubReposLocalDataSource,
                                       String query, GithubRepoMapper githubRepoMapper) {
        mGithubReposService = githubReposService;
        mGithubReposLocalDataSource = githubReposLocalDataSource;
        mQuery = query;
        mGithubRepoMapper = githubRepoMapper;
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, GithubRepo>> loadSingle(@NotNull LoadParams<Integer> params) {
        int page = params.getKey() == null ? BuildConfig.GITHUB_REPOS_BEGINNING_PAGE : params.getKey();


        return Single.zip(mGithubReposService.getRepositories(mQuery, page), mGithubReposLocalDataSource.getFavouriteRepos(), new BiFunction<GithubReposResponse, List<GithubRepoLocalEntity>, Single<LoadResult<Integer, GithubRepo>>>() {
                    @Override
                    public Single<LoadResult<Integer, GithubRepo>> apply(GithubReposResponse response, List<GithubRepoLocalEntity> repoLocalEntities) {

//                        return mGithubReposService.getRepositories(mQuery, page)
//                                .subscribeOn(Schedulers.io())
//                                .map(response -> toLoadResult(response, page, params.getLoadSize()))
//                                .onErrorReturn(LoadResult.Error::new);

                        return Single
                                .just(toLoadResult(response, repoLocalEntities, page, params.getLoadSize()))
                                .onErrorReturn(LoadResult.Error::new);
                    }
                }
        ).subscribeOn(Schedulers.io()).flatMap(x -> x).onErrorReturn(new Function<Throwable, LoadResult<Integer, GithubRepo>>() {
            @Override
            public LoadResult<Integer, GithubRepo> apply(Throwable throwable) throws Throwable {
                return null;
            }
        });
    }


    private LoadResult<Integer, GithubRepo> toLoadResult(@NonNull GithubReposResponse response, List<GithubRepoLocalEntity> repoLocalEntities, int page, int loadSize) {
        List<GithubRepo> repositories = mGithubRepoMapper.mapToGithubRepoList(response.getItems());

        Single.zip(Single.just(repositories), mGithubReposLocalDataSource.getFavouriteRepos(), new BiFunction<List<GithubRepo>, List<GithubRepoLocalEntity>, LoadResult<Integer, GithubRepo>>() {
            @Override
            public LoadResult<Integer, GithubRepo> apply(List<GithubRepo> githubRepos, List<GithubRepoLocalEntity> repoLocalEntities) throws Throwable {
                return null;
            }
        });

        for (GithubRepoLocalEntity entity : repoLocalEntities) {
            for (GithubRepo repo : repositories) {
                if (entity.getId().equals(repo.getId())) {
                    repo.setFavourite(entity.isFavourite());
                }
            }
        }


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