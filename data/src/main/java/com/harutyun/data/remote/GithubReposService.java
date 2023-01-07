package com.harutyun.data.remote;

import com.harutyun.data.remote.entities.GithubReposResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubReposService {

    @GET("search/repositories?sort=stars&order=desc")
    GithubReposResponse getRepositories(@Query("q") String query, @Query("page") Integer page);
}
