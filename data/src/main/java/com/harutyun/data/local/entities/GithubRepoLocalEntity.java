package com.harutyun.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.harutyun.domain.models.Owner;

@Entity(tableName = "github_repo_table")
public class GithubRepoLocalEntity {
    @PrimaryKey
    private Long id;
    private String name;
    private String description;
    private Integer stargazersCount;
    private String language;
    private Integer forksCount;
    private String createdAt;
    private String htmlUrl;
    @Embedded
    private Owner owner;
    private boolean isFavourite;


    public GithubRepoLocalEntity(Long id, String name, String description, Integer stargazersCount,
                                 String language, Integer forksCount, String createdAt,
                                 String htmlUrl, Owner owner, boolean isFavourite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stargazersCount = stargazersCount;
        this.language = language;
        this.forksCount = forksCount;
        this.createdAt = createdAt;
        this.htmlUrl = htmlUrl;
        this.owner = owner;
        this.isFavourite = isFavourite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
