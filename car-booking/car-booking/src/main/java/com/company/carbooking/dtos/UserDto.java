package com.company.carbooking.dtos;

public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String mobile;

    public UserDto() {
    }

    public UserDto(Long id, String email, String name, String mobile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
