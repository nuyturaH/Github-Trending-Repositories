package com.harutyun.data.mappers;

import com.harutyun.data.local.entities.GithubRepoLocalEntity;
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
                entity.getCreatedAt(), entity.getHtmlUrl(), mapToOwner(entity.getOwner()), false);
    }


    public List<GithubRepo> mapToGithubRepoListFromLocal(List<GithubRepoLocalEntity> entityList) {
        return entityList.stream().map(this::mapToGithubRepoFromLocal).collect(Collectors.toList());
    }
    public GithubRepo mapToGithubRepoFromLocal(GithubRepoLocalEntity entity) {
        return new GithubRepo(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getStargazersCount(), entity.getLanguage(), entity.getForksCount(),
                entity.getCreatedAt(), entity.getHtmlUrl(), entity.getOwner(), entity.isFavourite());
    }

    public Owner mapToOwner(OwnerEntity entity) {
        return new Owner(entity.getId(), entity.getLogin(), entity.getAvatarUrl());
    }


    public GithubRepoLocalEntity mapToGithubRepoLocalEntity(GithubRepo githubRepo) {
        return new GithubRepoLocalEntity(githubRepo.getId(), githubRepo.getName(),
                githubRepo.getDescription(), githubRepo.getStargazersCount(),
                githubRepo.getLanguage(), githubRepo.getForksCount(), githubRepo.getCreatedAt(),
                githubRepo.getHtmlUrl(), githubRepo.getOwner(), githubRepo.isFavourite());
    }
}
