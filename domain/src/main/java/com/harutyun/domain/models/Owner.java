package com.harutyun.domain.models;

import java.util.Objects;

public class Owner {
    private Long ownerId;
    private String login;
    private String avatarUrl;

    public Owner(Long ownerId, String login, String avatarUrl) {
        this.ownerId = ownerId;
        this.login = login;
        this.avatarUrl = avatarUrl;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
        return Objects.equals(ownerId, owner.ownerId) && Objects.equals(login, owner.login) && Objects.equals(avatarUrl, owner.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, login, avatarUrl);
    }
}
