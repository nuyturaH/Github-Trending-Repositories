package com.harutyun.domain.models;

import java.util.Objects;

public class Owner {
    private Long id;
    private String login;
    private String avatarUrl;

    public Owner() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(id, owner.id) && Objects.equals(login, owner.login) && Objects.equals(avatarUrl, owner.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, avatarUrl);
    }
}
