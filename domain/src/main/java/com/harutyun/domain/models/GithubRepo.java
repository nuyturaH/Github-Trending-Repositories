package com.harutyun.domain.models;

import java.util.Objects;

public class GithubRepo {
    private Long id;
    private String name;
    private String description;
    private Integer stargazersCount;
    private String language;
    private Integer forksCount;
    private String createdAt;
    private String htmlUrl;
    private Owner owner;

    public GithubRepo(Long id, String name, String description, Integer stargazersCount,
                      String language, Integer forksCount, String createdAt, String htmlUrl, Owner owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stargazersCount = stargazersCount;
        this.language = language;
        this.forksCount = forksCount;
        this.createdAt = createdAt;
        this.htmlUrl = htmlUrl;
        this.owner = owner;
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
        if (description == null || description.isEmpty()) return "N/A";
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
        return createdAt.split("T")[0];
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubRepo that = (GithubRepo) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(stargazersCount, that.stargazersCount) && Objects.equals(language, that.language) && Objects.equals(forksCount, that.forksCount) && Objects.equals(createdAt, that.createdAt) && Objects.equals(htmlUrl, that.htmlUrl) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, stargazersCount, language, forksCount, createdAt, htmlUrl, owner);
    }
}
