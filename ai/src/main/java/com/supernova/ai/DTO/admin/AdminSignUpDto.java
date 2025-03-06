package com.supernova.ai.DTO.admin;

public class AdminSignUpDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;


    public AdminSignUpDto(){

    }

    public AdminSignUpDto(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword(){
        return password;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
