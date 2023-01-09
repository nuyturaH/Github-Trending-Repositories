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
        return new GithubRepo(entity.getId(), entity.getName(), entity.getDescription(), entity.getStargazersCount(), mapToOwner(entity.getOwner()));
    }

    public Owner mapToOwner(OwnerEntity entity) {
        return new Owner(entity.getId(), entity.getLogin(), entity.getAvatarUrl());
    }
}
