package com.harutyun.domain.repository;

import com.harutyun.domain.models.GithubRepo;

import java.util.Date;
import java.util.List;

public interface GithubRepoRepository {
    List<GithubRepo> GetTrendingReposByNameCreatedLaterThanXUseCase(String name, Date x);
}
