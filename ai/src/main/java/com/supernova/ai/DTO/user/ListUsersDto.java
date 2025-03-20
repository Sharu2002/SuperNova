package com.supernova.ai.DTO.user;

public class ListUsersDto {

    private String userName;

    private String email;

    private String role;

    private String team;

    public ListUsersDto() {
    }

    public ListUsersDto(String userName, String email, String role, String team) {
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.team = team;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
