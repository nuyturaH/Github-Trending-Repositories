package com.harutyun.domain.models;

import java.util.Objects;

public class GithubRepo {
    private Long id;
    private String name;
    private String description;
    private Integer stargazersCount;
    private Owner owner;

    public GithubRepo() {
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubRepo that = (GithubRepo) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(stargazersCount, that.stargazersCount) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, stargazersCount, owner);
    }
}
