package com.codegym.airbnb.model;

import javax.validation.constraints.NotBlank;

public class LoginGoogle {
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public LoginGoogle(String email, String name) {
        this.email = email;
        this.name = name;
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
}
