package com.supernova.ai.Entity;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long user;
    private Long role;
    private Long team;

    public UserRoleId() {
    }

    public UserRoleId(Long user, Long role, Long team) {
        this.user = user;
        this.role = role;
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(role, that.role) &&
                Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role, team);
    }
}