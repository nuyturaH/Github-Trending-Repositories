package com.harutyun.data.mappers;

import com.harutyun.data.remote.entities.GithubRepoEntity;
import com.harutyun.data.remote.entities.OwnerEntity;
import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.models.Owner;

import java.util.List;
import java.util.stream.Collectors;


public class GithubRepoMapper {

    public List<GithubRepo> mapToGithubRepoList(List<GithubRepoEntity> entityList) {
        return entityList.stream().map(this::mapToGithubRepo).collect(Collectors.toList());
    }
    public GithubRepo mapToGithubRepo(GithubRepoEntity entity) {
        return new GithubRepo(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getStargazersCount(), entity.getLanguage(), entity.getForksCount(),
                entity.getCreatedAt(), entity.getHtmlUrl(), mapToOwner(entity.getOwner()));
    }


    public List<GithubRepo> mapToGithubRepoListFromLocal(List<com.harutyun.data.local.entities.GithubRepoEntity> entityList) {
        return entityList.stream().map(this::mapToGithubRepoFromLocal).collect(Collectors.toList());
    }
    public GithubRepo mapToGithubRepoFromLocal(com.harutyun.data.local.entities.GithubRepoEntity entity) {
        return new GithubRepo(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getStargazersCount(), entity.getLanguage(), entity.getForksCount(),
                entity.getCreatedAt(), entity.getHtmlUrl(), entity.getOwner());
    }

    public Owner mapToOwner(OwnerEntity entity) {
        return new Owner(entity.getId(), entity.getLogin(), entity.getAvatarUrl());
    }
}
