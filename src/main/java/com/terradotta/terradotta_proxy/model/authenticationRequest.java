package com.terradotta.terradotta_proxy.model;

public class authenticationRequest {
    private String email;
    private String password;

    public authenticationRequest() {
    }

    public authenticationRequest(String username, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
